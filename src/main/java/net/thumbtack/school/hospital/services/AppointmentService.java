package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.request.InsertAppointmentDtoRequest;
import net.thumbtack.school.hospital.response.*;
import net.thumbtack.school.hospital.view.DoctorView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentService extends ServiceBase {
    private UserDao userDao;
    private PatientDao patientDao;
    private AppointmentDao appointmentDao;
    private ComissionDao comissionDao;
    private DoctorDao doctorDao;
    private SpecialityDao specialityDao;

    public AppointmentService(UserDao userDao, PatientDao patientDao, AppointmentDao appointmentDao, ComissionDao comissionDao, DoctorDao doctorDao, SpecialityDao specialityDao) {
        super(userDao);
        this.userDao = userDao;
        this.appointmentDao = appointmentDao;
        this.comissionDao = comissionDao;
        this.patientDao = patientDao;
        this.doctorDao = doctorDao;
        this.specialityDao = specialityDao;
    }

    public InsertAppointmentDtoResponse insert(InsertAppointmentDtoRequest request, String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (user.getType() != UserType.PATIENT) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "patients");
        }
        if (request.getDate().isAfter(LocalDate.now().plusMonths(2))) {
            throw new ServerException(ErrorCode.WRONG_APPOINTMENT_DATE);
        }
        Patient patient = patientDao.getByUserId(user.getId());
        Appointment appointment;
        if (request.getDoctorId() != null && request.getDoctorId() != 0) {
            DoctorView doctor = doctorDao.getByIdWithSchedule(request.getDoctorId(), request.getDate(), request.getDate());
            if (doctor.getSchedule() == null) {
                throw new ServerException(ErrorCode.DAY_IN_DOCTOR_SCHEDULE_NOT_FOUND);
            }
            for (Appointment elem : doctor.getSchedule().get(0).getAppointments()) {
                if (elem.getPatient() != null && elem.getPatient() == patient) {
                    throw new ServerException(ErrorCode.CANT_INSERT_APPOINTMENT);
                }
                if (elem.getTimeStart() == request.getTime()) {
                    appointment = elem;
                    checkAppointments(patient.getAppointments(), request, elem);
                    checkComissions(patient.getComissions(), request, elem);
                    appointment.setPatient(patient);
                    appointment = appointmentDao.insert(appointment);
                    if (appointment.getId() != 0) {
                        return new InsertAppointmentDtoResponse(appointment.getTicket(), doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(), doctor.getSchedule().get(0).getDate(), appointment.getTimeStart());
                    }
                }
            }
        }
        if (request.getSpeciality() != null) {
            specialityDao.getByName(request.getSpeciality());
            List<DoctorView> doctors = doctorDao.getAllDoctorsWithParams(request.getSpeciality(), request.getDate(), request.getDate());
            for (DoctorView doctor : doctors) {
                if (doctor.getSchedule() == null) {
                    continue;
                }
                for (Appointment elem : doctor.getSchedule().get(0).getAppointments()) {
                    if (elem.getPatient() != null && elem.getPatient() == patient) {
                        throw new ServerException(ErrorCode.CANT_INSERT_APPOINTMENT);
                    }
                    if (elem.getTimeStart() == request.getTime()) {
                        checkAppointments(patient.getAppointments(), request, elem);
                        checkComissions(patient.getComissions(), request, elem);
                        appointment = elem;
                        appointment.setPatient(patient);
                        appointment = appointmentDao.insert(appointment);
                        if (appointment.getId() != 0) {
                            return new InsertAppointmentDtoResponse(appointment.getTicket(), doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(), doctor.getSchedule().get(0).getDate(), appointment.getTimeStart());
                        }
                    }
                }
            }
        }
        throw new ServerException(ErrorCode.APPOINTMENT_BUSY);
    }

    private void checkComissions(List<Comission> comissions, InsertAppointmentDtoRequest request, Appointment elem) throws ServerException {
        if (comissions != null) {
            for (Comission comission : comissions) {
                boolean checkTimeInterval = comission.getTimeStart().isBefore(elem.getTimeEnd()) && comission.getTimeEnd().isAfter(elem.getTimeStart());
                if ((request.getTime() == comission.getTimeStart() || checkTimeInterval) && request.getDate() == comission.getDate()) {
                    throw new ServerException(ErrorCode.PATIENT_BUSY);
                }
            }
        }
    }

    private void checkAppointments(List<Appointment> appointments, InsertAppointmentDtoRequest request, Appointment elem) throws ServerException {
        if (appointments != null) {
            for (Appointment app : appointments) {
                boolean checkTimeInterval = app.getTimeStart().isBefore(elem.getTimeEnd()) && app.getTimeEnd().isAfter(elem.getTimeStart());
                if ((request.getTime() == app.getTimeStart() || checkTimeInterval) && request.getDate() == app.getDaySchedule().getDate()) {
                    throw new ServerException(ErrorCode.PATIENT_BUSY);
                }
            }
        }
    }

    public EmptyResponse delete(String sessionId, int id) throws ServerException {
        User user = getUserBySessionId(sessionId);
        Doctor doctor = doctorDao.getByAppointmentId(id);
        if (doctor == null) {
            throw new ServerException(ErrorCode.CANT_DELETE_APPOINTMENT, id);
        }
        Appointment appointment = appointmentDao.getById(id);
        boolean condition = appointment == null || appointment.getAppointmentState() != AppointmentState.TICKET || user.getId() != appointment.getPatient().getId();
        if (!user.getLogin().equals(doctor.getLogin()) && condition && user.getType() != UserType.ADMIN) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "appointment's doctor, patient and all doctors");
        }
        if (!appointmentDao.delete(id, user.getId())) {
            throw new ServerException(ErrorCode.CANT_DELETE_APPOINTMENT, id);
        }
        return new EmptyResponse();
    }

    public List<GetByPatientTicketDtoResponse> getByPatientId(String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (user.getType() != UserType.PATIENT) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "patients");
        }
        List<GetByPatientTicketDtoResponse> tickets = new ArrayList<>();
        List<Appointment> appointments = appointmentDao.getByPatientId(user.getId());
        List<Comission> comissions = comissionDao.getByPatientId(user.getId());
        tickets.addAll(Converter.convertAppointmentToAppointmentDto(appointments, doctorDao));
        tickets.addAll(Converter.convertComissionModelToDto(comissions));
        return tickets;
    }
}
