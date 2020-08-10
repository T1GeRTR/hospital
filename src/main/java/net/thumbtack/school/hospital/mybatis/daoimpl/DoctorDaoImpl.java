package net.thumbtack.school.hospital.mybatis.daoimpl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.DaySchedule;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;
import net.thumbtack.school.hospital.dao.DoctorDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DoctorDaoImpl extends DaoImplBase implements DoctorDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    @Override
    public Doctor insert(Doctor doctor) throws ServerException {
        LOGGER.debug("DAO insert");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insertUser(doctor);
                try {
                    getDoctorMapper(sqlSession).insertDoctor(doctor);
                } catch (RuntimeException ex) {
                    throw new ServerException(ErrorCode.ROOM_IS_BUSY, doctor.getRoom());
                }
                getDayScheduleMapper(sqlSession).insertDaySchedule(doctor.getSchedule(), doctor.getId());
                doctor.setTickets();
                for (DaySchedule daySchedule : doctor.getSchedule()) {
                    getAppointmentMapper(sqlSession).insertAppointments(daySchedule.getAppointments(), daySchedule.getId());
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't insert Doctor {}, {}", doctor, e);
                sqlSession.rollback();
                if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    throw new ServerException(ErrorCode.DUPLICATE_LOGIN, doctor.getLogin());
                }
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return doctor;
    }

    @Override
    public DoctorView getByIdWithSchedule(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO getById");
        try (SqlSession sqlSession = getSession()) {
            try {
                DoctorView doctor = getDoctorMapper(sqlSession).getByIdWithSchedule(id, dateStart, dateEnd);
                if (doctor == null) {
                    throw new ServerException(ErrorCode.CANT_GET_DOCTOR_BY_ID, id);
                }
                doctor.setDateStart(dateStart);
                doctor.setDateEnd(dateEnd);
                return doctor;
            } catch (RuntimeException e) {
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<DoctorView> getAllDoctorsWithParams(String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO getAllSpeciality");
        try (SqlSession sqlSession = getSession()) {
            try {
                List<DoctorView> doctors = getDoctorMapper(sqlSession).getAllDoctorsWithParams(speciality, dateStart, dateEnd);
                if (doctors.size() == 0) {
                    throw new ServerException(ErrorCode.CANT_GET_DOCTORS_BY_SPECIALITY);
                }
                for (DoctorView doctor : doctors){
                    doctor.setDateStart(dateStart);
                    doctor.setDateEnd(dateEnd);
                }
                return doctors;
            } catch (RuntimeException e) {
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<DoctorView> getDoctorsForStatistics(LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO getDoctorsForStatistics");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getDoctorMapper(sqlSession).getAllDoctorsWithParams(null, dateStart, dateEnd);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get doctors", e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public boolean editSchedule(Doctor doctor, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO editSchedule");
        try (SqlSession sqlSession = getSession()) {
            try {
                getDayScheduleMapper(sqlSession).deleteDaySchedule(dateStart, dateEnd, doctor.getId());
                getDayScheduleMapper(sqlSession).insertDaySchedule(doctor.getSchedule(), doctor.getId());
                doctor.setTickets();
                for (DaySchedule daySchedule : doctor.getSchedule()) {
                    getAppointmentMapper(sqlSession).insertAppointments(daySchedule.getAppointments(), daySchedule.getId());
                }
            } catch (RuntimeException e) {
                LOGGER.debug("Can't update schedule {}", doctor.getId(), e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return true;
    }

    @Override
    public boolean delete(int id, LocalDate fromDate) throws ServerException {
        LOGGER.debug("DAO delete");
        try (SqlSession sqlSession = getSession()) {
            try {
                if (getDoctorMapper(sqlSession).getByUserId(id) == null) {
                    throw new ServerException(ErrorCode.DOCTORS_NOT_FOUND, id);
                }
                getDoctorMapper(sqlSession).delete(id, fromDate);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't delete doctor {}", id, e);
                sqlSession.rollback();
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return true;
    }

    @Override
    public Doctor getByUserId(int userId) throws ServerException {
        LOGGER.debug("DAO getByUserId");
        try (SqlSession sqlSession = getSession()) {
            try {
                Doctor doctor = getDoctorMapper(sqlSession).getByUserId(userId);
                if (doctor == null) {
                    throw new ServerException(ErrorCode.CANT_GET_USER_BY_ID, userId);
                }
                return doctor;
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get by userId: {}, {}", userId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public Doctor getByAppointmentId(int appointmentId) throws ServerException {
        LOGGER.debug("DAO getByUserId");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getDoctorMapper(sqlSession).getByAppointmentId(appointmentId);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get by appointmentId: {}, {}", appointmentId, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
