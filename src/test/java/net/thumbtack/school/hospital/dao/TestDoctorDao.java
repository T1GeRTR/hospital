package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;
import net.thumbtack.school.hospital.mybatis.daoimpl.DoctorDaoImpl;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDoctorDao extends TestBaseCreateHistory {
    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Test
    public void testInsert() throws ServerException {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        Doctor doctor = doctorDao.insert(new Doctor("Дайана", "Ракишева", "Иркимовна", "dentist", "45", "dayana", "password", Converter.convertSchedule(LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
        Assert.assertNotEquals(0, doctor.getId());
    }

    @Test
    public void testInsertFail() throws ServerException {
        assertThrows(ServerException.class, () -> {
            List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            Doctor doctor = new Doctor("Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "password", Converter.convertSchedule(LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15));
            doctorDao.insert(doctor);
        });
    }

    @Test
    public void testGetById() throws ServerException {
        DoctorView doctor = doctorDao.getByIdWithSchedule(2, LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 30));
        Assert.assertEquals("doctor", doctor.getLogin());
    }

    @Test
    public void testGetByIdFail() {
        assertThrows(ServerException.class, () -> doctorDao.getByIdWithSchedule(50, LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 30)));
    }

    @Test
    public void testGetAllSpeciality() throws ServerException {
        List<DoctorView> doctors = doctorDao.getAllDoctorsWithParams(null, LocalDate.of(2020, 4, 15), LocalDate.of(2020, 4, 30));
        Assert.assertTrue(doctors.size() != 0);
    }

    @Test
    public void testGetAllSpecialityFail() {
        assertThrows(ServerException.class, () -> doctorDao.getAllDoctorsWithParams(null, LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 30)));

    }

    @Test
    public void testEditSchedule() throws ServerException {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        DoctorDao doctorDao = new DoctorDaoImpl();
        Assert.assertTrue(doctorDao.editSchedule(new Doctor(2, Converter.convertSchedule(LocalDate.of(2020, 4, 29), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 16)), LocalDate.of(2020, 4, 29), LocalDate.of(2020, 4, 30)));
    }

    @Test
    public void testEditScheduleFail() {
        assertThrows(ServerException.class, () -> {
            List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            DoctorDao doctorDao = new DoctorDaoImpl();
            doctorDao.editSchedule(new Doctor(12, Converter.convertSchedule(LocalDate.of(2020, 4, 29), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 16)), LocalDate.of(2020, 4, 29), LocalDate.of(2020, 4, 30));
        });
    }

    @Test
    public void testDelete() throws ServerException {
        Assert.assertTrue(doctorDao.delete(2, LocalDate.of(2020, 4, 30)));
    }

    @Test
    public void testDeleteFail() {
        assertThrows(ServerException.class, () -> doctorDao.delete(25, LocalDate.of(2020, 4, 30)));
    }

    @Test
    public void testGetByUserId() throws ServerException {
        Doctor doctor = doctorDao.getByUserId(2);
        Assert.assertEquals(doctorDao.getByUserId(2).getLogin(), "doctor");
    }
}
