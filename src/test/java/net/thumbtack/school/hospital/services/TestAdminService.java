package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.AdminDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.mybatis.daoimpl.AdminDaoImpl;
import net.thumbtack.school.hospital.mybatis.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.request.RegisterAdminDtoRequest;
import net.thumbtack.school.hospital.request.UpdateAdminDtoRequest;
import net.thumbtack.school.hospital.response.RegisterAdminDtoResponse;
import net.thumbtack.school.hospital.response.UpdateAdminDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestAdminService {
    AdminDao adminDao = mock(AdminDaoImpl.class);
    UserDao userDao = mock(UserDaoImpl.class);
    AdminService adminService = new AdminService(adminDao, userDao);

    @Test
    public void testInsert() throws Exception {
        Admin admin = new Admin("Ковальчук", "Максим", "Александрович", "admin", "MaxAdmin", "password");
        when(adminDao.insert(admin)).thenReturn(new Admin(1, admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition(), admin.getLogin(), admin.getPassword()));
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "ADMIN");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        RegisterAdminDtoResponse response = adminService.register(new RegisterAdminDtoRequest("Ковальчук", "Максим", "Александрович", "admin", "MaxAdmin", "password"), "12345Admin");
        Assertions.assertEquals(1, response.getId());
    }

    @Test
    public void testInsertFail() throws Exception {
        Admin admin = new Admin("Ковальчук", "Максим", "Александрович", "admin", "MaxAdmin", "password");
        when(adminDao.insert(admin)).thenReturn(new Admin(1, admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition(), admin.getLogin(), admin.getPassword()));
        User user = new User("Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "patient");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        assertThrows(ServerException.class, () -> adminService.register(new RegisterAdminDtoRequest("Ковальчук", "Максим", "Александрович", "admin", "MaxAdmin", "password"), "12345Admin"));
    }

    @Test
    public void testUpdate() throws ServerException {
        Admin admin = new Admin("Ковальчук", "Максим", "Алексеевич", "admin", "MaxAdmin", "password2");
        when(adminDao.update(any())).thenReturn(new Admin(1, admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition(), admin.getLogin(), admin.getPassword()));
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "ADMIN");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        when(adminDao.getByUserId(user.getId())).thenReturn(new Admin("Ковальчук", "Максим", "Алексеевич", "admin", "MaxAdmin", "password"));
        UpdateAdminDtoResponse updateAdminDtoResponse = adminService.update(new UpdateAdminDtoRequest("Ковальчук", "Максим", "Алексеевич", "admin", "password", "password2"), "12345Admin");
        Assertions.assertEquals("Алексеевич", updateAdminDtoResponse.getPatronymic());
    }

    @Test
    public void testUpdateFail() throws ServerException {
        Admin admin = new Admin("Ковальчук", "Максим", "Алексеевич", "admin", "MaxAdmin", "password2");
        when(adminDao.update(admin)).thenReturn(new Admin(1, admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition(), admin.getLogin(), admin.getPassword()));
        User user = new User(1, "Ковальчук", "Максим", "Александрович", "MaxAdmin", "password", "ADMIN");
        when(userDao.getBySessionId("12345Admin")).thenReturn(user);
        when(adminDao.getByUserId(user.getId())).thenReturn(admin);
        assertThrows(ServerException.class, () -> adminService.update(new UpdateAdminDtoRequest("Ковальчук", "Максим", "Алексеевич", "admin", "passwordWrong", "password2"), "12345Admin"));
    }
}
