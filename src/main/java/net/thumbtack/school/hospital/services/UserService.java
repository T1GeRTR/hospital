package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.AdminDao;
import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.dao.PatientDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.request.LoginUserDtoRequest;
import net.thumbtack.school.hospital.response.*;
import org.springframework.stereotype.Component;

@Component
public class UserService extends ServiceBase {
    private UserDao userDao;
    private DoctorDao doctorDao;
    private PatientDao patientDao;
    private AdminDao adminDao;

    public UserService(UserDao userDao, DoctorDao doctorDao, PatientDao patientDao, AdminDao adminDao) {
        super(userDao);
        this.userDao = userDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
        this.adminDao = adminDao;
    }

    public LoginUserDtoResponse login(LoginUserDtoRequest request) throws ServerException {
        String sessionId = userDao.login(request.getLogin(), request.getPassword(), new Session()).getSessionId();
        User user = getUserBySessionId(sessionId);
        switch (user.getType()) {
            case ADMIN:
                Admin admin = adminDao.getByUserId(user.getId());
                if (admin == null) {
                    break;
                }
                return new LoginAdminDtoResponse(sessionId, admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition());
            case DOCTOR:
                Doctor doctor = doctorDao.getByUserId(user.getId());
                if (doctor == null) {
                    break;
                }
                return new LoginDoctorDtoResponse(sessionId, doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(), Converter.convertScheduleModelToDto(doctor.getSchedule()));
            case PATIENT:
                Patient patient = patientDao.getByUserId(user.getId());
                if (patient == null) {
                    break;
                }
                return new LoginPatientDtoResponse(sessionId, patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone());
        }
        throw new ServerException(ErrorCode.CANT_LOGIN, request.getLogin());
    }

    public EmptyResponse logout(String sessionId) throws ServerException {
        getUserBySessionId(sessionId);
        if (userDao.logout(sessionId)) {
            return new EmptyResponse();
        }
        throw new ServerException(ErrorCode.CANT_LOGOUT);
    }

    public GetUserDtoResponse get(String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        switch (user.getType()) {
            case ADMIN:
                Admin admin = adminDao.getByUserId(user.getId());
                if (admin == null) {
                    break;
                }
                return new GetAdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition());
            case DOCTOR:
                Doctor doctor = doctorDao.getByUserId(user.getId());
                if (doctor == null) {
                    break;
                }
                return new GetDoctorDtoResponse(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(), Converter.convertScheduleModelToDto(doctor.getSchedule()));
            case PATIENT:
                Patient patient = patientDao.getByUserId(user.getId());
                if (patient == null) {
                    break;
                }
                return new GetPatientDtoResponse(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone());
        }
        throw new ServerException(ErrorCode.CANT_GET_ACCOUNT_INFO);
    }
}
