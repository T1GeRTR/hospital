package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.mybatis.daoimpl.UserDaoImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUserDao extends TestBaseCreateHistory {
    UserDao userDao = new UserDaoImpl();

    @Test
    public void testLogin() throws ServerException {
        Assert.assertNotNull(userDao.login("doctor2", "passDoctor", new Session("123456Doctor")));
    }

    @Test
    public void testLoginFail() {
        assertThrows(ServerException.class, () -> userDao.login("wrongLogin", "passWrong", new Session("123456455523523Wrong")));
    }

    @Test
    public void testGetBySession() throws ServerException {
        User user = userDao.getBySessionId("12345Admin");
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testGetBySessionFail() {
        assertThrows(ServerException.class, () -> userDao.getBySessionId("wrongSessionId"));
    }

    @Test
    public void testLogout() throws ServerException {
        userDao.logout("12345Admin");
        assertThrows(ServerException.class, () -> userDao.logout("12345Admin"));
        userDao.login("Admin", "passAdmin", new Session("12345Admin"));
    }

    @Test
    public void testLogoutFail() {
        assertThrows(ServerException.class, () -> userDao.logout("wrongSessionId"));
    }

}
