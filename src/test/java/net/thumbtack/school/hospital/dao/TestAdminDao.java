package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.mybatis.daoimpl.AdminDaoImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAdminDao extends TestBaseCreateHistory {
    AdminDao adminDao = new AdminDaoImpl();

    @Test
    public void testInsert() throws ServerException {
        Admin admin = new Admin("Имя2", "Фамилия2", "Отчество2", "admin2", "login2", "password2");
        adminDao.insert(admin);
        Assert.assertNotEquals(0, admin.getId());
    }

    @Test
    public void testInsertFail() {
        assertThrows(ServerException.class, () -> {
            Admin admin = new Admin("Имя2", "Фамилия2", "Отчество2", "admin", "admin", "password2");
            adminDao.insert(admin);
        });
    }

    @Test
    public void testUpdate() throws ServerException {
        Admin admin = new Admin(1,"Имя3", "Фамилия3", "Отчество3", "admin", "admin", "password3");
        adminDao.update(admin);
        Assert.assertEquals("Фамилия3", adminDao.getByUserId(1).getLastName());
        adminDao.update(new Admin(1,"Иван", "Иванов", "Иванович", "Главный администратор", "admin", "passAdmin"));
    }
}
