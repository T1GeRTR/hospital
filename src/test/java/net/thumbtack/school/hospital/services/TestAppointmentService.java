package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.mybatis.daoimpl.*;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import net.thumbtack.school.hospital.request.InsertAppointmentDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertAppointmentDtoResponse;
import net.thumbtack.school.hospital.view.DoctorView;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestAppointmentService {
    UserDao userDao = mock(UserDaoImpl.class);
    PatientDao patientDao = mock(PatientDaoImpl.class);
    AppointmentDao appointmentDao = mock(AppointmentDaoImpl.class);
    ComissionDao comissionDao = mock(ComissionDaoImpl.class);
    DoctorDao doctorDao = mock(DoctorDaoImpl.class);
    SpecialityDao specialityDao = mock(SpecialityDaoImpl.class);
    AppointmentService appointmentService = new AppointmentService(userDao, patientDao, appointmentDao, comissionDao, doctorDao, specialityDao);

    @Test
    public void testInsert() throws ServerException {
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "patient", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        List<DoctorView> doctors = new ArrayList<>();
        when(patientDao.getByUserId(user.getId())).thenReturn(new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>()));
        List<DayScheduleDtoRequest> dayScheduleDtoRequest2 = new ArrayList<>();
        dayScheduleDtoRequest2.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(8, 15)));
        DoctorView doctor = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor2", "password", Converter.convertSchedule(LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 15), dayScheduleDtoRequest2, 15));
        doctor.setTickets();
        doctors.add(doctor);
        when(doctorDao.getAllDoctorsWithParams("dentist", LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 15))).thenReturn(doctors);
        Appointment appointment = new Appointment(35, "D<1>150420200800", LocalTime.of(8, 30), LocalTime.of(8, 30).plusMinutes(15), new Patient(1, "Петр", "Петров", "Петрович", "pert@gmail.com", "Omsk", "+79994557609", "patient", "pasPatient", "PATIENT", new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
        when(appointmentDao.insert(any())).thenReturn(appointment);
        when(specialityDao.getByName("dentist")).thenReturn(1);
        InsertAppointmentDtoResponse appointmentDto = appointmentService.insert(new InsertAppointmentDtoRequest(null, "dentist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0)), "12345Patient");
        Assert.assertEquals(appointmentDto.getTicket(), "D<1>150420200800");
    }

    @Test
    public void testInsertFail() throws ServerException {
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "patient", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Appointment appointment = new Appointment(35, "D<1>150420200800", LocalTime.of(8, 30), LocalTime.of(8, 30).plusMinutes(15), new Patient(5, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
        when(patientDao.getByUserId(user.getId())).thenReturn(new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>()));
        when(appointmentDao.insert(appointment)).thenReturn(appointment);
        when(specialityDao.getByName("dentist")).thenReturn(1);
        assertThrows(ServerException.class, () -> appointmentService.insert(new InsertAppointmentDtoRequest(null, "dentist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0)), "12345Patient"));
    }

    @Test
    public void testDelete() throws ServerException {
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "patient", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        Appointment appointment = new Appointment(1, null, null, null, patient, AppointmentState.TICKET, new DaySchedule());
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(doctorDao.getByAppointmentId(1)).thenReturn(new Doctor(1, null, null, null, null, null, "doctor", null, null));
        when(appointmentDao.getByPatientId(user.getId())).thenReturn(appointments);
        when(appointmentDao.getById(1)).thenReturn(appointment);
        when(appointmentDao.delete(1, 1)).thenReturn(true);
        EmptyResponse emptyResponse = appointmentService.delete("12345Patient", 1);
        Assert.assertNotNull(emptyResponse);
    }

    @Test
    public void testDeleteFail() throws ServerException {
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "patient", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        Appointment appointment = new Appointment(3, null, null, null, patient, null, new DaySchedule());
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(appointmentDao.getByPatientId(user.getId())).thenReturn(appointments);
        when(appointmentDao.delete(1, 1)).thenReturn(false);
        assertThrows(ServerException.class, () -> appointmentService.delete("12345Patient", 3));
    }
}
