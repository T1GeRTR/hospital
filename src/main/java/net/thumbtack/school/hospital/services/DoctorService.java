package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.converter.Converter;
import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.UserType;
import net.thumbtack.school.hospital.dao.*;
import net.thumbtack.school.hospital.request.*;
import net.thumbtack.school.hospital.response.DayScheduleDtoResponse;
import net.thumbtack.school.hospital.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorService extends ServiceBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private DoctorDao doctorDao;
    private PatientDao patientDao;
    private RoomDao roomDao;
    private SpecialityDao specialityDao;
    private static final String YES = "yes";
    private static final String NO = "no";

    public DoctorService(DoctorDao doctorDao, PatientDao patientDao, UserDao userDao, RoomDao roomDao, SpecialityDao specialityDao) {
        super(userDao);
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
        this.roomDao = roomDao;
        this.specialityDao = specialityDao;
    }

    public RegisterDoctorDtoResponse register(RegisterDoctorDtoRequest request, String sessionId) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (user.getType() == UserType.ADMIN) {
            if (request.getWeekSchedule() != null && request.getWeekDaysSchedule().size() != 0) {
                throw new ServerException(ErrorCode.WRONG_SCHEDULE);
            }
            if (request.getWeekDaysSchedule().size() == 0) {
                request.setWeekDaysSchedule(Converter.convertWeekDaysToListSchedule(request.getWeekSchedule()));
            }
            roomDao.getByName(request.getRoom());
            specialityDao.getByName(request.getSpeciality());
            Doctor doctor = new Doctor(request.getFirstName(), request.getLastName(),
                    request.getPatronymic(), request.getSpeciality(), request.getRoom(),
                    request.getLogin(), request.getPassword(), Converter.convertSchedule(request.getDateStart(),
                    request.getDateEnd(), request.getWeekDaysSchedule(), request.getDuration()));
            doctor.setType(UserType.DOCTOR.toString());
            doctor = doctorDao.insert(doctor);
            return new RegisterDoctorDtoResponse(doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                    doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(),
                    Converter.convertScheduleModelToDto(doctor.getSchedule()));
        }
        throw new ServerException(ErrorCode.PERMISSION_DENIED, "admins");
    }

    public GetByIdDoctorDtoResponse getById(int id, String sessionId, String schedule, LocalDate startDate, LocalDate endDate) throws ServerException {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now().plusMonths(2);
        }
        if (startDate.isAfter(endDate)) {
            throw new ServerException(ErrorCode.WRONG_DATE_INTERVAL);
        }
        DoctorView doctor = doctorDao.getByIdWithSchedule(id, startDate, endDate);
        if (doctor == null) {
            throw new ServerException(ErrorCode.DOCTORS_NOT_FOUND, id);
        }
        GetByIdDoctorDtoResponse response = new GetByIdDoctorDtoResponse(doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(),
                Converter.convertScheduleModelToDto(doctor.getSchedule()));
        User user = getUserBySessionId(sessionId);
        switch (schedule.toLowerCase()) {
            case ("yes"):
                if (user.getType() == UserType.PATIENT) {
                    for (DayScheduleDtoResponse dayScheduleDtoResponse : response.getSchedule()) {
                        for (AppointmentDto appointmentDto : dayScheduleDtoResponse.getDaySchedule()) {
                            if (appointmentDto.getPatient() == null || patientDao.getByUserId(user.getId()).getId() != appointmentDto.getPatient().getId()) {
                                appointmentDto.setPatient(null);
                            }
                        }
                    }
                }
                return response;
            case ("no"):
                response.setSchedule(null);
                return response;
            default:
                throw new ServerException(ErrorCode.WRONG_YES_OR_NO_STRING);
        }
    }

    public List<GetAllDoctorWithParamsDtoResponse> getAllWithParams(String speciality, String sessionId, String schedule, LocalDate startDate, LocalDate endDate) throws ServerException {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now().plusMonths(2);
        }
        if (startDate.isAfter(endDate)) {
            throw new ServerException(ErrorCode.WRONG_DATE_INTERVAL);
        }
        List<DoctorView> doctors = doctorDao.getAllDoctorsWithParams(speciality,
                startDate, endDate);
        if (doctors.size() == 0) {
            String message = speciality == null ? "Doctors with this date interval not found" : "Doctors with speciality " + speciality + " not found";
            throw new ServerException(ErrorCode.DOCTORS_CANT_GET_WITH_PARAMS, message);
        }
        List<GetAllDoctorWithParamsDtoResponse> responseList = new ArrayList<>();
        for (DoctorView doctor : doctors) {
            responseList.add(new GetAllDoctorWithParamsDtoResponse(doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                    doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom(),
                    Converter.convertScheduleModelToDto(doctor.getSchedule())));
        }
        User user = getUserBySessionId(sessionId);
        for (GetAllDoctorWithParamsDtoResponse response : responseList) {
            switch (schedule.toLowerCase()) {
                case (YES): {
                    if (user.getType() == UserType.PATIENT) {
                        for (DayScheduleDtoResponse dayScheduleDtoResponse : response.getSchedule()) {
                            for (AppointmentDto appointmentDto : dayScheduleDtoResponse.getDaySchedule()) {
                                if (patientDao.getByUserId(user.getId()).getId() != appointmentDto.getPatient().getId()) {
                                    appointmentDto.setPatient(null);
                                }
                            }
                        }
                    }
                    break;
                }
                case (NO):
                    response.setSchedule(null);
                    break;
                default:
                    throw new ServerException(ErrorCode.WRONG_YES_OR_NO_STRING);
            }
        }
        return responseList;
    }

    public EditScheduleDoctorDtoResponse editSchedule(EditScheduleDoctorDtoRequest request, String sessionId, int id) throws ServerException {
        if (request.getWeekSchedule() != null && request.getWeekDaysSchedule().size() != 0) {
            throw new ServerException(ErrorCode.WRONG_SCHEDULE);
        }
        if (request.getWeekDaysSchedule().size() == 0) {
            request.setWeekSchedule(Converter.convertWeekDaysToListSchedule(request.getWeekSchedule()));
        }
        Doctor doctor = new Doctor(id, Converter.convertSchedule(request.getDateStart(),
                request.getDateEnd(), request.getWeekDaysSchedule(), request.getDuration()));
        User user = getUserBySessionId(sessionId);
        if (user.getType() == UserType.ADMIN) {
            DoctorView doctorView;
            if (doctorDao.editSchedule(doctor, request.getDateStart(), request.getDateEnd())) {
                doctorView = doctorDao.getByIdWithSchedule(doctor.getId(), LocalDate.now().minusYears(5), LocalDate.now().plusYears(5));
                return new EditScheduleDoctorDtoResponse(doctorView.getId(), doctorView.getFirstName(), doctorView.getLastName(),
                        doctorView.getPatronymic(), doctorView.getSpeciality(), doctorView.getRoom(),
                        Converter.convertScheduleModelToDto(doctorView.getSchedule()));
            }
        }
        throw new ServerException(ErrorCode.PERMISSION_DENIED, "admins");
    }

    public EmptyResponse delete(DeleteDoctorDtoRequest request, String sessionId, int id) throws ServerException {
        User user = getUserBySessionId(sessionId);
        if (user.getType() != UserType.ADMIN) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED, "admins");
        }
        if (doctorDao.delete(id, request.getDate())) {
            LOGGER.debug("SMS and Email sent");
            return new EmptyResponse();
        }
        throw new ServerException(ErrorCode.CANT_DELETE_DOCTOR, id);
    }
}
