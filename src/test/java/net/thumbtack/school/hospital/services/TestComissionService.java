package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.mybatis.daoimpl.*;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import net.thumbtack.school.hospital.request.InsertComissionDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertComissionDtoResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestComissionService {
    ComissionDao comissionDao = mock(ComissionDaoImpl.class);
    DoctorDao doctorDao = mock(DoctorDaoImpl.class);
    UserDao userDao = mock(UserDaoImpl.class);
    PatientDao patientDao = mock(PatientDaoImpl.class);
    AppointmentDao appointmentDao = mock(AppointmentDaoImpl.class);
    ComissionService comissionService = new ComissionService(comissionDao, userDao, doctorDao, patientDao, appointmentDao);

    @Test
    public void testInsert() throws ServerException {
        User user = new User(1, "Талгат", "Ракишев", "Адылханович", "doctor", "passDoctor", "DOCTOR");
        when(userDao.getBySessionId("12345Doctor")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor1 = new Doctor(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.getByUserId(1)).thenReturn(doctor1);
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor2 = new Doctor(2, "Талгат", "Ракишев", "Адылханович", "dentist", "47", "doctor2", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15));
        doctors.add(doctor1);
        doctors.add(doctor2);
        when(doctorDao.getByUserId(1)).thenReturn(doctor1);
        when(doctorDao.getByUserId(2)).thenReturn(doctor2);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(53, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(54, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(55, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(56, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(309, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(310, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(311, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(312, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        Comission comission = new Comission(LocalDate.of(2020, 4, 17), LocalTime.of(13, 0), LocalTime.of(14, 0), new Patient(1, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), doctors, "44");
        when(appointmentDao.getForComission(any())).thenReturn(appointments);
        List<Integer> doctorIds = new ArrayList<>();
        doctorIds.add(1);
        doctorIds.add(2);
        when(patientDao.getByUserId(1)).thenReturn(new Patient(1, null, null, null, null,null,null,null,null,new ArrayList<>(), new ArrayList<>()));
        when(comissionDao.getByPatientId(1)).thenReturn(new ArrayList<>());
        when(comissionDao.insert(any())).thenReturn(comission);
        InsertComissionDtoResponse response = comissionService.insert(new InsertComissionDtoRequest(1, doctorIds, "44", LocalDate.of(2020, 4, 17), LocalTime.of(13, 0), 60), "12345Doctor");
        Assert.assertEquals(LocalTime.of(13, 0), response.getTime());
    }

    @Test
    void testDelete() throws ServerException {
        User user = new User(1, "Талгат", "Ракишев", "Адылханович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1, "Талгат", "Ракишев", "Адылханович", "petr@gmail.com", "Omsk", "89994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(user.getId())).thenReturn(patient);
        when(comissionDao.delete(1, patient.getId())).thenReturn(true);
        EmptyResponse emptyResponse = comissionService.delete("12345Patient", 1);
        Assert.assertNotNull(emptyResponse);
    }
}
