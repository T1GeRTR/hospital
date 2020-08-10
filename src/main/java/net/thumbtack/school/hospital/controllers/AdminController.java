package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.RegisterAdminDtoRequest;
import net.thumbtack.school.hospital.request.UpdateAdminDtoRequest;
import net.thumbtack.school.hospital.response.RegisterAdminDtoResponse;
import net.thumbtack.school.hospital.response.UpdateAdminDtoResponse;
import net.thumbtack.school.hospital.services.AdminService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminController{

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path = "/api/admins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterAdminDtoResponse register(@CookieValue("JAVASESSIONID") String sessionId, @Valid @RequestBody RegisterAdminDtoRequest registerRequest) throws ServerException {
        return adminService.register(registerRequest, sessionId);
    }

    @PutMapping(path = "/api/admins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateAdminDtoResponse update(@CookieValue("JAVASESSIONID") String sessionId, @Valid @RequestBody UpdateAdminDtoRequest updateRequest) throws ServerException {
        return adminService.update(updateRequest, sessionId);
    }

}
