package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.mybatis.daoimpl.PatientDaoImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPatientDao extends TestBaseCreateHistory {
    private final PatientDao patientDao = new PatientDaoImpl();

    @Test
    public void testInsert() throws ServerException {
        Patient patient = new Patient("Иван", "Иванов", "Иванович", "t1gerok.tr@gmail.com", "Omsk", "89994557609", "patient99", "password2", new ArrayList<>(), new ArrayList<>());
        patientDao.insert(patient, new Session("123456Patient"));
        Assert.assertNotEquals(0, patient.getId());
        Assert.assertEquals("patient99", patient.getLogin());
    }

    @Test
    public void testInsertFail() {
        assertThrows(ServerException.class, () -> {
            Patient patient = new Patient("Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "89994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
            patientDao.insert(patient, new Session("12343Patient"));
        });
    }

    @Test
    public void testGetById() throws ServerException {
        Patient patient = patientDao.getByUserId(4);
        Assert.assertEquals("patient", patient.getLogin());
    }

    @Test
    public void testGetByIdFail() {
        assertThrows(ServerException.class, () -> patientDao.getByUserId(99));
    }

    @Test
    public void testUpdate() throws ServerException {
        Patient patient = new Patient(4,"Иван2", "Иванов2", "Иванович2", "ivan@gmail.com", "Omsk", "89994557609", "patient", "password", new ArrayList<>(), new ArrayList<>());
        Assert.assertEquals(4, patientDao.update(patient).getId());
        patient = new Patient(4,"Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "89994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
        patientDao.update(patient);
    }
}
