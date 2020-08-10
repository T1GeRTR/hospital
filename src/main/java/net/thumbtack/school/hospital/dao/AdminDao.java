// REVU это просто dao, а не mybatis.dao
// если MyBatis заменить на что-то иное - это не изменится
package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Admin;

public interface AdminDao {
    Admin insert(Admin admin) throws ServerException;

    Admin update(Admin admin) throws ServerException;

    Admin getByUserId(int userId) throws ServerException;
}
