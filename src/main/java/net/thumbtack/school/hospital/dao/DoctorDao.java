package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;

import java.time.LocalDate;
import java.util.List;

public interface DoctorDao {
    Doctor insert(Doctor doctor) throws ServerException;

    DoctorView getByIdWithSchedule(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

    List<DoctorView> getAllDoctorsWithParams(String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

    boolean editSchedule(Doctor doctor, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

    boolean delete(int id, LocalDate date) throws ServerException;

    Doctor getByUserId(int userId) throws ServerException;

    List<DoctorView> getDoctorsForStatistics(LocalDate dateStart, LocalDate dateEnd) throws ServerException;

    Doctor getByAppointmentId(int userId) throws ServerException;
}
