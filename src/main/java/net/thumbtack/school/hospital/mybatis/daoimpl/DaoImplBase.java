package net.thumbtack.school.hospital.mybatis.daoimpl;

import net.thumbtack.school.hospital.mybatis.mappers.*;
import net.thumbtack.school.hospital.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        MyBatisUtils.initSqlSessionFactory();
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected AdminMapper getAdminMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AdminMapper.class);
    }

    protected DoctorMapper getDoctorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DoctorMapper.class);
    }

    protected PatientMapper getPatientMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PatientMapper.class);
    }

    protected DayScheduleMapper getDayScheduleMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DayScheduleMapper.class);
    }

    protected AppointmentMapper getAppointmentMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AppointmentMapper.class);
    }

    protected ComissionMapper getComissionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ComissionMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected RoomMapper getRoomMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(RoomMapper.class);
    }

    protected SpecialityMapper getSpecialityMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SpecialityMapper.class);
    }
}