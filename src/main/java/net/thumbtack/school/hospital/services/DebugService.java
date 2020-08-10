package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.dao.DebugDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.response.EmptyResponse;
import org.springframework.stereotype.Component;

@Component
public class DebugService extends ServiceBase {
    private DebugDao debugDao;

    public DebugService(UserDao userDao, DebugDao debugDao) {
        super(userDao);
        this.debugDao = debugDao;
    }

    public EmptyResponse clear() throws ServerException {
        debugDao.clear();
        return new EmptyResponse();
    }
}
