package net.thumbtack.school.hospital;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.mybatis.daoimpl.*;
import net.thumbtack.school.hospital.mybatis.utils.MyBatisUtils;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBaseCreateHistory {

    protected static boolean setUpIsDone = false;
    private static final UserDao userDao = new UserDaoImpl();
    private static final AdminDao adminDao = new AdminDaoImpl();
    private static final DoctorDao doctorDao = new DoctorDaoImpl();
    private static final PatientDao patientDao = new PatientDaoImpl();
    private static final AppointmentDao appointmentDao = new AppointmentDaoImpl();
    private static final ComissionDao comissionDao = new ComissionDaoImpl();

    @BeforeAll
    public static void setUp() throws ServerException {
        if (!setUpIsDone) {
            boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
            if (!initSqlSessionFactory) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;

            userDao.login("admin", "passAdmin", new Session("12345Admin"));

            //userId = 2;
            List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
            Doctor doctor = new Doctor(2, "Талгат", "Ракишев", "Адылханович", "dentist", "44", "doctor", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15));
            doctorDao.insert(doctor);
            userDao.login("doctor", "passDoctor", new Session("12345Doctor"));
            //userId = 3;
            Doctor doctor2 = new Doctor(3, "Талгат", "Ракишев", "Адылханович", "dentist", "47", "doctor2", "passDoctor", Converter.convertSchedule(LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, 15));
            doctorDao.insert(doctor2);

            //userId = 4;
            Patient patient = new Patient(4, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "89994557609", "patient", "passPatient", new ArrayList<>(), new ArrayList<>());
            patientDao.insert(patient, new Session("12345Patient"));
            //userId = 5;
            Patient patient2 = new Patient(5, "Петр", "Петров", "Петрович", "petr@gmail.com", "Omsk", "89994557609", "patient2", "passPatient", new ArrayList<>(), new ArrayList<>());
            patientDao.insert(patient2, new Session("12345Patient2"));

            //id = 35, doctorId = 1;
            Appointment appointment = new Appointment(35, "D<1>150420200830", LocalTime.of(8, 30), LocalTime.of(8, 30).plusMinutes(15), new Patient(5, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
            appointmentDao.insert(appointment);
            //id = 3, doctorId = 1;
            Appointment appointment2 = new Appointment(3, "D<1>140420200830", LocalTime.of(8, 30), LocalTime.of(8, 30).plusMinutes(15), new Patient(4, null, null, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()), AppointmentState.FREE, new DaySchedule());
            appointmentDao.insert(appointment2);

            //id = 1;
            List<Doctor> doctors = new ArrayList<>();
            doctors.add(doctor);
            Comission comission = new Comission(LocalDate.of(2020, 4, 15), LocalTime.of(13, 0), LocalTime.of(13, 0).plusMinutes(30), patient, doctors, "44");
            List<Appointment> appointments = appointmentDao.getForComission(comission);
            comission.setAppointments(appointments);
            comissionDao.insert(comission);
        }
    }
}
