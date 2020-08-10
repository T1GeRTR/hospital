package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Appointment;
import net.thumbtack.school.hospital.model.Comission;

import java.util.List;

public interface AppointmentDao {
    Appointment insert(Appointment appointment) throws ServerException;

    boolean delete(int id, int userId) throws ServerException;

    List<Appointment> getByPatientId(int patientId) throws ServerException;

    Appointment getById(int id) throws ServerException;

    List<Appointment> getForComission(Comission comission) throws ServerException;
}
