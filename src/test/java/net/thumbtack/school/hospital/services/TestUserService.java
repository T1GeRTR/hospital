package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.AdminDao;
import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.dao.PatientDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.mybatis.daoimpl.AdminDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.DoctorDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.PatientDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.request.LoginUserDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.GetUserDtoResponse;
import net.thumbtack.school.hospital.response.LoginUserDtoResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserService {
    UserDao userDao = mock(UserDaoImpl.class);
    AdminDao adminDao = mock(AdminDaoImpl.class);
    DoctorDao doctorDao = mock(DoctorDaoImpl.class);
    PatientDao patientDao = mock(PatientDaoImpl.class);
    UserService userService = new UserService(userDao, doctorDao, patientDao, adminDao);

    @Test
    public void testLogin() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        when(userDao.login(eq("patient"), eq("passPatient"), any(Session.class))).thenReturn(new Session("12345Patient"));
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        LoginUserDtoResponse response = userService.login(new LoginUserDtoRequest("patient", "passPatient"));
        Assert.assertEquals("12345Patient", response.getSessionId());
    }

    @Test
    public void testLoginFail() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        when(userDao.login(eq("patient"), eq("passPatient"), any(Session.class))).thenReturn(new Session("12345Patient"));
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(null);
        assertThrows(ServerException.class, () -> userService.login(new LoginUserDtoRequest("patient", "passPatient")));
    }

    @Test
    public void testLogout() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        when(userDao.logout("12345Patient")).thenReturn(true);
        EmptyResponse emptyResponse = userService.logout("12345Patient");
        Assert.assertNotNull(emptyResponse);
    }

    @Test
    public void testLogoutFail() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        when(userDao.logout("12345Patient")).thenReturn(true);
        assertThrows(ServerException.class, () -> userService.logout("12345"));
    }


    @Test
    public void testGet() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        GetUserDtoResponse getUserDtoResponse = userService.get("12345Patient");
        Assert.assertEquals(patient.getId(), getUserDtoResponse.getId());
    }

    @Test
    public void testGetFail() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passPatient", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(null);
        assertThrows(ServerException.class, () ->userService.get("12345Patient"));
    }
}
