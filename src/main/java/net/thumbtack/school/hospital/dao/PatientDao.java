package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;

public interface PatientDao {

    Patient insert(Patient patient, Session session) throws ServerException;

    Patient update(Patient patient) throws ServerException;

    Patient getByUserId(int userId) throws ServerException;
}
