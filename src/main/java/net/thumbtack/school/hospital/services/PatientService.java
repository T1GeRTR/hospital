package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.UserType;
import net.thumbtack.school.hospital.dao.PatientDao;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.request.UpdatePatientDtoRequest;
import net.thumbtack.school.hospital.response.GetByIdPatientDtoResponse;
import net.thumbtack.school.hospital.response.RegisterPatientDtoResponse;
import net.thumbtack.school.hospital.response.UpdatePatientDtoResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PatientService extends ServiceBase {
    private PatientDao patientDao;

    public PatientService(PatientDao patientDao, UserDao userDao) {
        super(userDao);
        this.patientDao = patientDao;
    }

    public RegisterPatientDtoResponse register(RegisterPatientDtoRequest request) throws ServerException {
        Patient patient = new Patient(request.getFirstName(), request.getLastName(), request.getPatronymic(),
                request.getEmail(), request.getAddress(), Converter.convertPhone(request.getPhone()), request.getLogin(), request.getPassword(), new ArrayList<>(), new ArrayList<>());
        Session session = new Session();
        patient.setType(UserType.PATIENT.toString());
        patient = patientDao.insert(patient, new Session());
        return new RegisterPatientDtoResponse(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone(), session.getSessionId());
    }

    public UpdatePatientDtoResponse update(UpdatePatientDtoRequest request, String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        Patient patient = patientDao.getByUserId(user.getId());
        if (!(patient.getPassword().equals(request.getOldPassword()) && patient.getType() == UserType.PATIENT)) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "patients");
        }
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPatronymic(request.getPatronymic());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setPhone(Converter.convertPhone(request.getPhone()));
        patient.setPassword(request.getNewPassword());
        patientDao.update(patient);
        return new UpdatePatientDtoResponse(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getPatronymic(), patient.getEmail(), request.getAddress(), Converter.convertPhone(request.getPhone()));
    }


    public GetByIdPatientDtoResponse getById(String sessionId, int id) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (user.getType() != UserType.ADMIN && user.getType() != UserType.DOCTOR) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "admins and doctors");
        }
        Patient patient = patientDao.getByUserId(id);
        return new GetByIdPatientDtoResponse(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone());
    }

}

