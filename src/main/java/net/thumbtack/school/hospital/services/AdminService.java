package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.UserType;
import net.thumbtack.school.hospital.dao.AdminDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.request.RegisterAdminDtoRequest;
import net.thumbtack.school.hospital.request.UpdateAdminDtoRequest;
import net.thumbtack.school.hospital.response.RegisterAdminDtoResponse;
import net.thumbtack.school.hospital.response.UpdateAdminDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class AdminService extends ServiceBase {
    private AdminDao adminDao;
    private UserDao userDao;

    public AdminService(AdminDao adminDao, UserDao userDao) {
        super(userDao);
        this.adminDao = adminDao;
        this.userDao = userDao;
    }

    public RegisterAdminDtoResponse register(RegisterAdminDtoRequest request, String sessionId) throws ServerException {
        if (userDao.getBySessionId(sessionId).getType() != UserType.ADMIN) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "admins");
        }
        Admin admin = new Admin(request.getFirstName(), request.getLastName(),
                request.getPatronymic(), request.getPosition(), request.getLogin(), request.getPassword());
        admin.setType(UserType.ADMIN.toString());
        admin = adminDao.insert(admin);
        return new RegisterAdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(),
                admin.getPatronymic(), admin.getPosition());
    }

    public UpdateAdminDtoResponse update(UpdateAdminDtoRequest request, String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        Admin admin = adminDao.getByUserId(user.getId());
        if (!admin.getPassword().equals(request.getOldPassword())) {
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setPatronymic(request.getPatronymic());
        admin.setPosition(request.getPosition());
        admin.setPassword(request.getNewPassword());
        adminDao.update(admin);
        return new UpdateAdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(),
                admin.getPatronymic(), admin.getPosition());
    }
}
