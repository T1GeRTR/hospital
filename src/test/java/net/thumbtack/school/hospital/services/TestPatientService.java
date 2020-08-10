package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.PatientDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.mybatis.daoimpl.PatientDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.request.UpdatePatientDtoRequest;
import net.thumbtack.school.hospital.response.GetByIdPatientDtoResponse;
import net.thumbtack.school.hospital.response.RegisterPatientDtoResponse;
import net.thumbtack.school.hospital.response.UpdatePatientDtoResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPatientService {
    UserDao userDao = mock(UserDaoImpl.class);
    PatientDao patientDao = mock(PatientDaoImpl.class);
    PatientService patientService = new PatientService(patientDao, userDao);

    @Test
    public void testInsert() throws ServerException {
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.insert(any(Patient.class), any(Session.class))).thenReturn(patient);
        RegisterPatientDtoResponse response = patientService.register(new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "petr@gmail.com", "patient", "passPatient", "Omsk", "+7-999-455-76-09"));
        Assert.assertEquals(1, response.getId());
    }

    @Test
    public void testUpdate() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passDoctor", "PATIENT");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient(1,"Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.update(patient)).thenReturn(new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>()));
        when(patientDao.getByUserId(user.getId())).thenReturn(new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passDoctor", new ArrayList<>(), new ArrayList<>()));
        UpdatePatientDtoResponse response = patientService.update(new UpdatePatientDtoRequest("Петр", "Петров", "Петрович", "passDoctor", "passPatient", "petr@gmail.com", "Omsk", "+79994557609"), "12345Patient");
        Assert.assertEquals(response.getId(), 1);
    }

    @Test
    public void testUpdateFail() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "admin", "passAdmin", "ADMIN");
        when(userDao.getBySessionId("12345Patient")).thenReturn(user);
        Patient patient = new Patient("Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.update(patient)).thenReturn(new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>()));
        when(patientDao.getByUserId(user.getId())).thenReturn(patient);
        assertThrows(ServerException.class, () -> patientService.update(new UpdatePatientDtoRequest("Петр", "Петров", "Петрович", "passDoctor", "passPatient", "petr@gmail.com", "Omsk", "+79994557609"), "12345Patient"));
    }

    @Test
    public void testGetById() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "admin", "passAdmin", "ADMIN");
        user.setType("admin");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        GetByIdPatientDtoResponse response = patientService.getById("12345Admin", 1);
        Assert.assertEquals(response.getId(), 1);
    }

    @Test
    public void testGetByIdFail() throws ServerException {
        User user = new User(1, "Петр", "Петров", "Петрович", "patient", "passAdmin", "PATIENT");
        user.setType("patient");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        Patient patient = new Patient(1, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "+79994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        when(patientDao.getByUserId(1)).thenReturn(patient);
        assertThrows(ServerException.class, () -> patientService.getById("12345Admin", 1));
    }
}
