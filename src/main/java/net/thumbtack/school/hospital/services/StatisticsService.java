package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.ComissionDao;
import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.response.statictics.StatisticsAppointments;
import net.thumbtack.school.hospital.response.statictics.StatisticsDoctorDto;
import net.thumbtack.school.hospital.response.statictics.StatisticsDtoResponse;
import net.thumbtack.school.hospital.response.statictics.StatisticsPatientDto;
import net.thumbtack.school.hospital.view.DoctorView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class StatisticsService extends ServiceBase {

    private DoctorDao doctorDao;
    private ComissionDao comissionDao;

    public StatisticsService(UserDao userDao, DoctorDao doctorDao, ComissionDao comissionDao) {
        super(userDao);
        this.doctorDao = doctorDao;
        this.comissionDao = comissionDao;
    }

    private List<StatisticsDoctorDto> setDoctors(List<DoctorView> list) throws ServerException {
        List<StatisticsDoctorDto> doctors = new ArrayList<>();
        for (DoctorView doctor : list) {
            int appointments = 0;
            int comissions = 0;
            for (DaySchedule daySchedule : doctor.getSchedule()) {
                int comissionPatientId = 0;
                for (Appointment appointment : daySchedule.getAppointments()) {
                    if (appointment.getAppointmentState() == AppointmentState.TICKET) {
                        appointments += 1;
                    }
                    if (appointment.getAppointmentState() == AppointmentState.BUSY && comissionPatientId != appointment.getPatient().getId()) {
                        comissionPatientId = appointment.getPatient().getId();
                        comissions += 1;
                    }
                }
            }
            StatisticsDoctorDto doctorDto = new StatisticsDoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(), appointments, comissions);
            doctors.add(doctorDto);
        }
        return doctors;
    }

    private List<StatisticsPatientDto> setPatients(List<DoctorView> list) throws ServerException {
        List<StatisticsPatientDto> patients = new ArrayList<>();
        for (DoctorView doctor : list) {
            for (DaySchedule daySchedule : doctor.getSchedule()) {
                for (Appointment appointment : daySchedule.getAppointments()) {
                    if (appointment.getPatient() != null) {
                        StatisticsPatientDto patientDto = new StatisticsPatientDto(appointment.getPatient().getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(), appointment.getPatient().getPatronymic(), appointment.getPatient().getEmail(), appointment.getPatient().getPhone(), appointment.getPatient().getAddress(), 0, 0);
                        if (!patients.contains(patientDto)) {
                            List<Comission> comissionList = comissionDao.getByPatientId(patientDto.getId());
                            if (comissionList.size() != 0) {
                                patientDto.setComissions(comissionList.size());
                            }
                            patients.add(patientDto);
                        }
                    }
                }
            }
        }
        for (DoctorView doctor : list) {
            for (DaySchedule daySchedule : doctor.getSchedule()) {
                for (Appointment appointment : daySchedule.getAppointments()) {
                    if (appointment.getPatient() != null) {
                        StatisticsPatientDto patientDto = new StatisticsPatientDto(appointment.getPatient().getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(), appointment.getPatient().getPatronymic(), appointment.getPatient().getEmail(), appointment.getPatient().getPhone(), appointment.getPatient().getAddress(), 0, 0);
                        if (appointment.getAppointmentState() == AppointmentState.TICKET) {
                            patients.get(patients.indexOf(patientDto)).setAppointments(patients.get(patients.indexOf(patientDto)).getAppointments() + 1);
                        }
                    }
                }
            }
        }
        return patients;
    }

    private StatisticsDtoResponse setStatisticsDtoResponse(List<DoctorView> list) throws ServerException {
        List<StatisticsDoctorDto> doctors = setDoctors(list);
        doctors.sort(Comparator.comparing(StatisticsDoctorDto::getId));
        List<StatisticsPatientDto> patients = setPatients(list);
        patients.sort(Comparator.comparing(StatisticsPatientDto::getId));
        StatisticsAppointments appointments = new StatisticsAppointments(0, 0, 0, 0);
        int comissions;
        for (DoctorView doctor : list) {
            for (DaySchedule daySchedule : doctor.getSchedule()) {
                for (Appointment appointment : daySchedule.getAppointments()) {
                    appointments.setAll(appointments.getAll() + 1);
                    if (appointment.getAppointmentState() == AppointmentState.TICKET) {
                        appointments.setTicket(appointments.getTicket() + 1);
                    }
                    if (appointment.getAppointmentState() == AppointmentState.BUSY) {
                        appointments.setBusy(appointments.getBusy() + 1);
                    }
                    if (appointment.getAppointmentState() == AppointmentState.FREE) {
                        appointments.setFree(appointments.getFree() + 1);
                    }
                }
            }
        }
        List<Comission> comissionList = new ArrayList<>();
        for (StatisticsPatientDto patientDto : patients) {
            List<Comission> patientComissions = comissionDao.getByPatientId(patientDto.getId());
            comissionList.addAll(patientComissions);
        }
        comissions = comissionList.size();
        return new StatisticsDtoResponse(doctors, patients, appointments, comissions);
    }

    public StatisticsDtoResponse getStatistics(LocalDate startDate, LocalDate endDate) throws ServerException {
        final LocalDate MAX = LocalDate.of(9999, 12, 31);
        final LocalDate MIN = LocalDate.of(1, 1, 1);
        if (startDate != null) {
            if (endDate != null) {
                if (startDate.isAfter(endDate)) {
                    throw new ServerException(ErrorCode.WRONG_END_DATE);
                }
                //4
                List<DoctorView> doctors = doctorDao.getDoctorsForStatistics(startDate, endDate);
                return setStatisticsDtoResponse(doctors);
            }
            //2
            List<DoctorView> doctors = doctorDao.getDoctorsForStatistics(LocalDate.now(), MAX);
            return setStatisticsDtoResponse(doctors);
        }
        if (endDate != null) {
            //3
            List<DoctorView> doctors = doctorDao.getDoctorsForStatistics(MIN, LocalDate.now());
            return setStatisticsDtoResponse(doctors);
        }
        //1
        List<DoctorView> doctors = doctorDao.getDoctorsForStatistics(MIN, MAX);
        return setStatisticsDtoResponse(doctors);
    }
}

