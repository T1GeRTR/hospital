package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceBase {
    @Autowired
    private UserDao userDao;

    public ServiceBase(UserDao userDao) {
        this.userDao = userDao;
    }

    protected User getUserBySessionId(String sessionId) throws ServerException {
        if (sessionId == null) {
            throw new ServerException(ErrorCode.WRONG_SESSION_ID);
        }
        User user = userDao.getBySessionId(sessionId);
        if (user == null) {
            throw new ServerException(ErrorCode.WRONG_SESSION_ID);
        }
        return user;
    }
}
