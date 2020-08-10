package net.thumbtack.school.hospital.mybatis.daoimpl;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.dao.DebugDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DebugDaoImpl extends DaoImplBase implements DebugDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDaoImpl.class);

    @Override
    public void clear() throws ServerException {
        LOGGER.debug("DAO insert");
        try (SqlSession sqlSession = getSession()) {
            try {
                getAdminMapper(sqlSession).clear();
                getComissionMapper(sqlSession).clear();
                getComissionMapper(sqlSession).clearComissionDoctor();
                getDoctorMapper(sqlSession).clear();
                getPatientMapper(sqlSession).clear();
                getUserMapper(sqlSession).clear();
                getUserMapper(sqlSession).clearSession();
            } catch (RuntimeException e) {
                LOGGER.debug("Can't clear database", e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.CANT_CLEAR_DATABASE);
            }
            sqlSession.commit();
        }
    }

}
