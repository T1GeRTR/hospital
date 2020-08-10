package net.thumbtack.school.hospital.mybatis.daoimpl;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Appointment;
import net.thumbtack.school.hospital.model.AppointmentState;
import net.thumbtack.school.hospital.model.Comission;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.dao.ComissionDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComissionDaoImpl extends DaoImplBase implements ComissionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComissionDaoImpl.class);

    @Override
    public Comission insert(Comission comission) throws ServerException {
        LOGGER.debug("DAO insert");
        try (SqlSession sqlSession = getSession()) {
            try {
                getComissionMapper(sqlSession).insertComission(comission.getPatient().getId(), comission);
                getComissionMapper(sqlSession).insertComissionDoctor(comission.getId(), comission.getDoctors());
                List<Integer> appointmentIds = new ArrayList<>();
                for (Appointment appointment : comission.getAppointments()) {
                    appointmentIds.add(appointment.getId());
                }
                int count = getAppointmentMapper(sqlSession).updateBusy(appointmentIds, comission.getPatient().getId());
                if (count != comission.getAppointments().size()) {
                    sqlSession.rollback();
                    throw new ServerException(ErrorCode.APPOINTMENT_BUSY);
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't insert comission {}, {}", comission, e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return comission;
    }

    @Override
    public boolean delete(int id, int patientId) throws ServerException {
        LOGGER.debug("DAO delete");
        try (SqlSession sqlSession = getSession()) {
            int count;
            try {

                if (getAppointmentMapper(sqlSession).updateFree(patientId, id) == 0) {
                    throw new ServerException(ErrorCode.APPOINTMENT_NOT_FOUND);
                }
                count = getComissionMapper(sqlSession).deleteComission(id);
                if (count == 0) {
                    sqlSession.rollback();
                    throw new ServerException(ErrorCode.CANT_DELETE_COMISSION, id);
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't delete comission {}", id, e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
            return count == 1;
        }
    }

    @Override
    public List<Comission> getByPatientId(int patientId) throws ServerException {
        LOGGER.debug("DAO get by patient");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getComissionMapper(sqlSession).getByPatientId(patientId);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get comission by patient: {}", patientId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
