package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.mybatis.daoimpl.ComissionDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.DoctorDaoImpl;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import net.thumbtack.school.hospital.view.DoctorView;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestComissionDao extends TestBaseCreateHistory {
    ComissionDao comissionDao = new ComissionDaoImpl();
    DoctorDao doctorDao = new DoctorDaoImpl();

    @Test
    public void testInsert() throws ServerException {
        List<Doctor> doctors = new ArrayList<>();
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        doctors.add(new Doctor(2, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
        doctors.add(new Doctor(3, "Талгат", "Ракишев", "Адылханович", "dentist", "47", "doctor2", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(21, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(22, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(23, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(24, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(341, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(342, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(343, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(344, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        Comission comission = new Comission(LocalDate.of(2020, 4, 14), LocalTime.of(13, 0), LocalTime.of(14, 0), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), doctors, "44", appointments);
        Comission comission1 = comissionDao.insert(comission);
        Assert.assertEquals("CD<2>D<3>140420201300", comission1.getTicket());
    }

    @Test
    public void testInsertFail() {
        assertThrows(ServerException.class, () -> {
            List<Doctor> doctors = new ArrayList<>();
            List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            doctors.add(new Doctor(3, "Талгат", "Ракишев", "Адылханович", "dentist", "47", "doctor2", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
            doctors.add(new Doctor(2, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment(53, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(54, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(55, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(56, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(309, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(310, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(311, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            appointments.add(new Appointment(312, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
            Comission comission = new Comission(LocalDate.of(2020, 4, 15), LocalTime.of(13, 0), LocalTime.of(14, 0), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), doctors, "44", appointments);
            comissionDao.insert(comission);
        });
    }

    @Test
    public void testDeleteFail() {
        assertThrows(ServerException.class, () -> comissionDao.delete(4, 4));
    }

    @Test
    public void testGetByPatientId() throws ServerException {
        List<Comission> comissions = comissionDao.getByPatientId(4);
        Assert.assertEquals("CD<2>150420201300", comissions.get(0).getTicket());
    }

    @Test
    public void testCheckBusy() throws ServerException {
        List<Doctor> doctors = new ArrayList<>();
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        doctors.add(new Doctor(2, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
        doctors.add(new Doctor(3, "Талгат", "Ракишев", "Адылханович", "dentist", "47", "doctor2", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15)));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(117, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(118, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(119, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(120, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(373, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(374, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(375, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        appointments.add(new Appointment(376, "D<1>210420201300", LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(15), null, AppointmentState.FREE, new DaySchedule()));
        Comission comission = new Comission(LocalDate.of(2020, 4, 21), LocalTime.of(13, 0), LocalTime.of(14, 0), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), doctors, "44", appointments);
        DoctorView doctorView = doctorDao.getByIdWithSchedule(2, LocalDate.of(2020, 4, 21), LocalDate.of(2020, 4, 21));
        int countBusy = 0;
        for (Appointment appointment : doctorView.getSchedule().get(0).getAppointments()) {
            if (appointment.getAppointmentState() == AppointmentState.BUSY) {
                countBusy += 1;
            }
        }
        Assert.assertEquals(0, countBusy);
        comissionDao.insert(comission);
        doctorView = doctorDao.getByIdWithSchedule(2, LocalDate.of(2020, 4, 21), LocalDate.of(2020, 4, 21));
        countBusy = 0;
        for (Appointment appointment : doctorView.getSchedule().get(0).getAppointments()) {
            if (appointment.getAppointmentState() == AppointmentState.BUSY) {
                countBusy += 1;
            }
        }
        Assert.assertEquals(4, countBusy);
    }

    @Test
    public void testCheckFree() throws ServerException {
        DoctorView doctorView = doctorDao.getByIdWithSchedule(2, LocalDate.of(2020, 4, 21), LocalDate.of(2020, 4, 21));
        int countFree = 0;
        for (Appointment appointment : doctorView.getSchedule().get(0).getAppointments()) {
            if (appointment.getAppointmentState() == AppointmentState.FREE) {
                countFree += 1;
            }
        }
        Assert.assertEquals(28, countFree);
        List<Comission> comissions = comissionDao.getByPatientId(4);
        int comissionId = 0;
        for (Comission comission : comissions) {
            if (comission.getDate().equals(LocalDate.of(2020, 4, 21)) && comission.getTimeStart().equals(LocalTime.of(13, 0))) {
                comissionId = comission.getId();
            }
        }
        comissionDao.delete(comissionId, 4);
        doctorView = doctorDao.getByIdWithSchedule(2, LocalDate.of(2020, 4, 21), LocalDate.of(2020, 4, 21));
        countFree = 0;
        for (Appointment appointment : doctorView.getSchedule().get(0).getAppointments()) {
            if (appointment.getAppointmentState() == AppointmentState.FREE) {
                countFree += 1;
            }
        }
        Assert.assertEquals(32, countFree);
    }
}
