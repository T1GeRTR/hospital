package net.thumbtack.school.hospital.mybatis.daoimpl;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Appointment;
import net.thumbtack.school.hospital.model.Comission;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.dao.AppointmentDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentDaoImpl extends DaoImplBase implements AppointmentDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentDaoImpl.class);

    @Override
    public Appointment insert(Appointment appointment) throws ServerException {
        LOGGER.debug("DAO insert appointment");
        try (SqlSession sqlSession = getSession()) {
            try {
                if (getAppointmentMapper(sqlSession).update(appointment.getId(), appointment.getPatient().getId()) == 0) {
                    appointment.setId(0);
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't update appointment {}, {}", appointment.getId(), e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return appointment;
    }


    @Override
    public boolean delete(int id, int userId) throws ServerException {
        LOGGER.debug("DAO delete appointment");
        try (SqlSession sqlSession = getSession()) {
            int count;
            try {
                count = getAppointmentMapper(sqlSession).delete(id, userId);
                if (count == 0) {
                    throw new ServerException(ErrorCode.APPOINTMENT_NOT_FOUND);
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't delete appointment {}", id, e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
            return count == 1;
        }
    }

    @Override
    public List<Appointment> getByPatientId(int patientId) throws ServerException {
        List<Appointment> appointments;
        LOGGER.debug("DAO get by patient");
        try (SqlSession sqlSession = getSession()) {
            try {
                appointments = getAppointmentMapper(sqlSession).getByPatientId(patientId);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get appointments by patient: {}", patientId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
        return appointments;
    }

    @Override
    public Appointment getById(int id) throws ServerException {
        LOGGER.debug("DAO get by patient");
        Appointment appointment;
        try (SqlSession sqlSession = getSession()) {
            try {
                appointment = getAppointmentMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get appointments by id: {}", id, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
        return appointment;
    }

    @Override
    public List<Appointment> getForComission(Comission comission) throws ServerException {
        List<Appointment> appointments = new ArrayList<>();
        LOGGER.debug("DAO get by patient");
        try (SqlSession sqlSession = getSession()) {
            try {
                for (Doctor doctor : comission.getDoctors()) {
                    appointments.addAll(getAppointmentMapper(sqlSession).getForComission(comission.getDate(), doctor.getId(), comission.getTimeStart(), comission.getTimeEnd(), comission.getPatient().getId()));
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get appointments by comission for patient: {}", comission.getPatient().getId(), e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
        return appointments;
    }
}
