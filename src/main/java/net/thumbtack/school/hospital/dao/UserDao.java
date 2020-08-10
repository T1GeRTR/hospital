package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;

public interface UserDao {
    Session login(String login, String password, Session session) throws ServerException;

    boolean logout(String sessionId) throws ServerException;

    User getBySessionId(String sessionId) throws ServerException;
}
