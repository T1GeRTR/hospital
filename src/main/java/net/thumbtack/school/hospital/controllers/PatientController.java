package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.request.UpdatePatientDtoRequest;
import net.thumbtack.school.hospital.response.GetByIdPatientDtoResponse;
import net.thumbtack.school.hospital.response.RegisterPatientDtoResponse;
import net.thumbtack.school.hospital.response.UpdatePatientDtoResponse;
import net.thumbtack.school.hospital.services.PatientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(path = "/api/patients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterPatientDtoResponse register(HttpServletResponse response, @Valid @RequestBody RegisterPatientDtoRequest request) throws ServerException {
        RegisterPatientDtoResponse registerResponse = patientService.register(request);
        response.addCookie(new Cookie("JAVASESSIONID", registerResponse.getSessionId()));
        return registerResponse;
    }

    @PutMapping(path = "/api/patients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdatePatientDtoResponse update(@CookieValue("JAVASESSIONID") String sessionId, @Valid @RequestBody UpdatePatientDtoRequest updateRequest) throws ServerException {
        return patientService.update(updateRequest, sessionId);
    }

    @GetMapping(path = "/api/patients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetByIdPatientDtoResponse getById(@PathVariable("id") int id, @CookieValue("JAVASESSIONID") String sessionId) throws ServerException {
        return patientService.getById(sessionId, id);
    }

}
