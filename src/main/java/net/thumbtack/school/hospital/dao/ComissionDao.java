package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Comission;

import java.util.List;

public interface ComissionDao {
    Comission insert(Comission comission) throws ServerException;

    boolean delete(int id, int userId) throws ServerException;

    List<Comission> getByPatientId(int userId) throws ServerException;
}
