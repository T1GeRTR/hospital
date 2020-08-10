package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;

public interface RoomDao {
    Integer getByName(String name) throws ServerException;
}
