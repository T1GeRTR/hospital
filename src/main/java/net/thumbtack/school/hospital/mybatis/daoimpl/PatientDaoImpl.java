package net.thumbtack.school.hospital.mybatis.daoimpl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.dao.PatientDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientDaoImpl extends DaoImplBase implements PatientDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    @Override
    public Patient insert(Patient patient, Session session) throws ServerException {
        LOGGER.debug("DAO insert");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insertUser(patient);
                getPatientMapper(sqlSession).insertPatient(patient);
                getSessionMapper(sqlSession).insertSession(session, patient.getId());
            } catch (RuntimeException e) {
                LOGGER.debug("Can't insert patient {}, {}", patient, e);
                sqlSession.rollback();
                if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    throw new ServerException(ErrorCode.DUPLICATE_LOGIN, patient.getLogin());
                }
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return patient;
    }

    @Override
    public Patient update(Patient patient) throws ServerException {
        LOGGER.debug("DAO update");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).updateUser(patient);
                getPatientMapper(sqlSession).updatePatient(patient);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't update patient {}", patient, e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.CANT_UPDATE_PATIENT, patient.getLogin());
            }
            sqlSession.commit();
        }
        return patient;
    }

    @Override
    public Patient getByUserId(int userId) throws ServerException {
        LOGGER.debug("DAO getByUserId");
        try (SqlSession sqlSession = getSession()) {
            try {
                Patient patient = getPatientMapper(sqlSession).getByUserId(userId);
                if (patient == null) {
                    throw new ServerException(ErrorCode.CANT_GET_USER_BY_ID, userId);
                }
                return patient;
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get by userId: {}", userId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
