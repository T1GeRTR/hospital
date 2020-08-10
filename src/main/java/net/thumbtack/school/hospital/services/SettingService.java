package net.thumbtack.school.hospital.services;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.request.PropertiesDto;
import net.thumbtack.school.hospital.response.SettingsDtoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SettingService extends ServiceBase {
    @Value("${max_name_length}")
    private int max_name_length;
    @Value("${min_password_length}")
    private int min_password_length;

    public SettingService(UserDao userDao) {
        super(userDao);
    }

    public SettingsDtoResponse getSettings(String sessionId) throws ServerException {
        if (sessionId == null) {
            return new SettingsDtoResponse(PropertiesDto.max_name_length, PropertiesDto.min_password_length);
        }
        User user = getUserBySessionId(sessionId);
        switch (user.getType()) {
            case ADMIN:
                return new SettingsDtoResponse(max_name_length, min_password_length);
            case PATIENT:
                return new SettingsDtoResponse(max_name_length, min_password_length);
            case DOCTOR:
                return new SettingsDtoResponse(max_name_length, min_password_length);
            default:
                return new SettingsDtoResponse(max_name_length, min_password_length);
        }
    }
}
