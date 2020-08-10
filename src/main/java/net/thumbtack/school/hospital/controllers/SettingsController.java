package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.response.SettingsDtoResponse;
import net.thumbtack.school.hospital.services.SettingService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {
    private SettingService settingService;

    public SettingsController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping(path = "/api/settings")
    public SettingsDtoResponse getSettings(@CookieValue("JAVASESSIONID") String sessionId) throws ServerException {
        return settingService.getSettings(sessionId);
    }
}
