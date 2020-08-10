package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.mybatis.daoimpl.*;
import net.thumbtack.school.hospital.request.*;
import net.thumbtack.school.hospital.response.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDoctorService {
    DoctorDao doctorDao = mock(DoctorDaoImpl.class);
    UserDao userDao = mock(UserDaoImpl.class);
    PatientDao patientDao = mock(PatientDaoImpl.class);
    RoomDao roomDao = mock(RoomDaoImpl.class);
    SpecialityDao specialityDao = mock(SpecialityDaoImpl.class);
    DoctorService doctorService = new DoctorService(doctorDao, patientDao, userDao, roomDao, specialityDao);

    @Test
    public void testInsert() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "ADMIN");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        when(roomDao.getByName("44")).thenReturn(44);
        when(specialityDao.getByName("dentist")).thenReturn(1);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor = new Doctor("Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.insert(doctor)).thenReturn(new Doctor(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15)));
        RegisterDoctorDtoResponse registerDoctorDtoResponse = doctorService.register(new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, null, 15), "12345Admin");
        Assert.assertEquals(registerDoctorDtoResponse.getId(), 1);
    }

    @Test
    public void testInsertFail() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "PATIENT");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        when(roomDao.getByName("44")).thenReturn(44);
        when(specialityDao.getByName("dentist")).thenReturn(1);
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor = new Doctor("Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.insert(doctor)).thenReturn(new Doctor(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15)));
        assertThrows(ServerException.class, () -> doctorService.register(new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, null, 15), "12345Admin"));

    }

    @Test
    public void testGetById() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        DoctorView doctor = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.getByIdWithSchedule(1, LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(doctor);
        GetByIdDoctorDtoResponse getByIdDoctorDtoResponse = doctorService.getById(1, "12345Patient", "no", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17));
        Assert.assertNull(getByIdDoctorDtoResponse.getSchedule());
    }


    @Test
    public void testGetByIdFail() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        DoctorView doctor = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.getByIdWithSchedule(1, LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(doctor);
        assertThrows(ServerException.class, () -> doctorService.getById(999, "12345Patient", "no", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17)));
    }

    @Test
    public void testGetAllWithParams() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "PATIENT");

        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        DoctorView doctor = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        List<DoctorView> doctors = new ArrayList<>();
        doctors.add(doctor);
        when(doctorDao.getAllDoctorsWithParams("dentist", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(doctors);
        List<GetAllDoctorWithParamsDtoResponse> getAllDoctorWithParamsDtoResponse = doctorService.getAllWithParams("dentist", "12345Patient", "no", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17));
        Assert.assertEquals(1, getAllDoctorWithParamsDtoResponse.size());
    }

    @Test
    public void testGetAllWithParamsFail() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        DoctorView doctor = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "doctor", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        List<DoctorView> doctors = new ArrayList<>();
        doctors.add(doctor);
        when(doctorDao.getAllDoctorsWithParams("doctor", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(doctors);
        assertThrows(ServerException.class, () -> doctorService.getAllWithParams("dentist", "12345Patient", "no", LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17)));
    }

    @Test
    public void testEditSchedule() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "Admin");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor = new Doctor(1, Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        DoctorView doctor2 = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.getByIdWithSchedule(doctor.getId(), LocalDate.now().minusYears(5), LocalDate.now().plusYears(5))).thenReturn(doctor2);
        when(doctorDao.editSchedule(doctor, LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(true);
        EditScheduleDoctorDtoResponse editScheduleDoctorDtoResponse = doctorService.editSchedule(new EditScheduleDoctorDtoRequest(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, null, 15), "12345Admin", 1);
        Assert.assertEquals(editScheduleDoctorDtoResponse.getId(), 1);
    }

    @Test
    public void testEditScheduleFail() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "DOCTOR");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor = new Doctor(1, Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        DoctorView doctor2 = new DoctorView(1, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, 15));
        when(doctorDao.getByIdWithSchedule(doctor.getId(), LocalDate.now().minusYears(5), LocalDate.now().plusYears(5))).thenReturn(doctor2);
        when(doctorDao.editSchedule(doctor, LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17))).thenReturn(true);
        assertThrows(ServerException.class, () -> doctorService.editSchedule(new EditScheduleDoctorDtoRequest(LocalDate.of(2020, 4, 17), LocalDate.of(2020, 4, 17), dayScheduleDtoRequest, null, 15), "12345Admin", 1));
    }

    @Test
    public void testDelete() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "ADMIN");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        when(doctorDao.delete(1, LocalDate.now())).thenReturn(true);
        EmptyResponse emptyResponse = doctorService.delete(new DeleteDoctorDtoRequest(LocalDate.now()), "12345Admin", 1);
        Assert.assertNotNull(emptyResponse);
    }

    @Test
    public void testDeleteFail() throws ServerException {
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "DOCTOR");
        when(userDao.getBySessionId("12345Doctor")).thenReturn(user);
        when(doctorDao.delete(1, LocalDate.now())).thenReturn(true);
        assertThrows(ServerException.class, () -> doctorService.delete(new DeleteDoctorDtoRequest(LocalDate.now()), "12345Doctor", 1));
    }

}
