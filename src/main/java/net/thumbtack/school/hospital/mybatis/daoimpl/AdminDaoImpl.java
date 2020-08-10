package net.thumbtack.school.hospital.mybatis.daoimpl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.dao.AdminDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminDaoImpl extends DaoImplBase implements AdminDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Override
    public Admin insert(Admin admin) throws ServerException {
        LOGGER.debug("DAO insert");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insertUser(admin);
                getAdminMapper(sqlSession).insertAdmin(admin);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't insert admin {}", admin, e);
                sqlSession.rollback();
                if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    throw new ServerException(ErrorCode.DUPLICATE_LOGIN, admin.getLogin());
                }
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return admin;
    }

    @Override
    public Admin update(Admin admin) throws ServerException {
        LOGGER.debug("DAO update");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).updateUser(admin);
                getAdminMapper(sqlSession).updateAdmin(admin);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't update userId: {}", admin.getId(), e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return admin;
    }

    @Override
    public Admin getByUserId(int userId) throws ServerException {
        LOGGER.debug("DAO getByUserId");
        try (SqlSession sqlSession = getSession()) {
            try {
                Admin admin = getAdminMapper(sqlSession).getByUserId(userId);
                if (admin == null) {
                    throw new ServerException(ErrorCode.CANT_GET_USER_BY_ID, userId);
                }
                return admin;
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get by userId: {}, {}", userId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
