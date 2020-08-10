package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Appointment;
import net.thumbtack.school.hospital.model.AppointmentState;
import net.thumbtack.school.hospital.model.DaySchedule;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.mybatis.daoimpl.AppointmentDaoImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAppointmentDao extends TestBaseCreateHistory {
    AppointmentDao appointmentDao = new AppointmentDaoImpl();

    @Test
    public void testInsert() throws ServerException {
        Appointment appointment = new Appointment(71, "D<1>200420200930", LocalTime.of(9, 30), LocalTime.of(9, 30).plusMinutes(15), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
        Assert.assertNotEquals(0, appointmentDao.insert(appointment).getId());
    }

    @Test
    public void testInsertFail() throws ServerException {
        Appointment appointment = new Appointment(0, "D<1>200420200930", LocalTime.of(9, 30), LocalTime.of(9, 30).plusMinutes(15), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE,new DaySchedule());
        appointmentDao.insert(appointment);
        Assert.assertEquals(0, appointment.getId());
    }

    @Test
    public void testDelete() throws ServerException {
        Assert.assertTrue(appointmentDao.delete(35, 5));
        Appointment appointment = new Appointment(35, "D<2>150420200830", LocalTime.of(8, 30), LocalTime.of(8, 30).plusMinutes(15), new Patient(5, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
        appointmentDao.insert(appointment);
    }

    @Test
    public void testDeleteFail() {
        assertThrows(ServerException.class, () -> appointmentDao.delete(20, 3));
    }

    @Test
    public void testGetByPatientId() throws ServerException {
        List<Appointment> appointments = appointmentDao.getByPatientId(4);
        Assert.assertEquals(3, appointments.get(0).getId());
    }
}
