package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.request.InsertComissionDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertComissionDtoResponse;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class ComissionService extends ServiceBase {
    private ComissionDao comissionDao;
    private DoctorDao doctorDao;
    private PatientDao patientDao;
    private AppointmentDao appointmentDao;

    public ComissionService(ComissionDao comissionDao, UserDao userDao, DoctorDao doctorDao, PatientDao patientDao, AppointmentDao appointmentDao) {
        super(userDao);
        this.comissionDao = comissionDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
        this.appointmentDao = appointmentDao;
    }


    public InsertComissionDtoResponse insert(InsertComissionDtoRequest request, String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (!(user.getType() == UserType.DOCTOR)) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "doctors");
        }
        Doctor doctorCreator = doctorDao.getByUserId(user.getId());
        if (!request.getDoctorIds().contains(doctorCreator.getId())) {
            request.getDoctorIds().add(doctorCreator.getId());
        }
        boolean roomContains = false;
        List<Doctor> doctors = new ArrayList<>();
        for (int id : request.getDoctorIds()) {
            Doctor doctor = doctorDao.getByUserId(id);
            doctors.add(doctor);
            if (doctor.getRoom().equals(request.getRoom())) {
                roomContains = true;
            }
        }
        if (!roomContains) {
            throw new ServerException(ErrorCode.WRONG_ROOM);
        }
        Patient patient = patientDao.getByUserId(request.getPatientId());
        List<Comission> comissions = comissionDao.getByPatientId(patient.getId());
        Comission comission = new Comission(request.getDate(), request.getTime(), request.getTime().plusMinutes(request.getDuration()), patient, doctors, request.getRoom());
        for (Comission elem : comissions) {
            boolean checkTime = Math.max(comission.getTimeStart().toSecondOfDay(),
                    elem.getTimeStart().getSecond()) < Math.min(comission.getTimeEnd().toSecondOfDay(), elem.getTimeEnd().toSecondOfDay());
            boolean checkDate = elem.getDate().equals(comission.getDate());
            if (checkDate && checkTime) {
                throw new ServerException(ErrorCode.PATIENT_BUSY);
            }
        }
        List<Appointment> appointments = appointmentDao.getForComission(comission);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentState() != AppointmentState.FREE) {
                throw new ServerException(ErrorCode.APPOINTMENT_BUSY);
            }
        }
        comission.setAppointments(appointments);
        if (request.getDoctorIds().size() > 1) {
            comission = comissionDao.insert(comission);
            return new InsertComissionDtoResponse(comission.getTicket(), request.getPatientId(), request.getDoctorIds(), comission.getRoom(), comission.getDate(), comission.getTimeStart(), request.getDuration());
        }
        throw new ServerException(ErrorCode.WRONG_DOCTORS_COUNT);
    }

    public EmptyResponse delete(String sessionId, int id) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (!(user.getType() == UserType.PATIENT)) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "patients");
        }
        if (!comissionDao.delete(id, patientDao.getByUserId(user.getId()).getId())) {
            throw new ServerException(ErrorCode.CANT_DELETE_COMISSION, id);
        }
        return new EmptyResponse();
    }
}
