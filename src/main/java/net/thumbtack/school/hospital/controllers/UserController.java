package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.LoginUserDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.GetUserDtoResponse;
import net.thumbtack.school.hospital.response.LoginUserDtoResponse;
import net.thumbtack.school.hospital.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/api/session", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginUserDtoResponse login(@Valid @RequestBody LoginUserDtoRequest request, HttpServletResponse httpServletResponse) throws ServerException {
        LoginUserDtoResponse response = userService.login(new LoginUserDtoRequest(request.getLogin(), request.getPassword()));
        httpServletResponse.addCookie(new Cookie("JAVASESSIONID", response.getSessionId()));
        return response;
    }

    @DeleteMapping(path = "/api/session")
    public EmptyResponse logout(@CookieValue("JAVASESSIONID") String sessionId, HttpServletResponse response) throws ServerException {
        Cookie cookie = new Cookie("JAVASESSIONID", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return userService.logout(sessionId);

    }

    @GetMapping(path = "/api/account")
    GetUserDtoResponse get(@CookieValue("JAVASESSIONID") String sessionId) throws ServerException {
        return userService.get(sessionId);
    }
}
