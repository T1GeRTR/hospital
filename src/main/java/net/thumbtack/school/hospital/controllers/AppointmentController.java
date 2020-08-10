package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.InsertAppointmentDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.GetByPatientTicketDtoResponse;
import net.thumbtack.school.hospital.response.InsertAppointmentDtoResponse;
import net.thumbtack.school.hospital.services.AppointmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(path = "/api/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InsertAppointmentDtoResponse insert(@CookieValue("JAVASESSIONID") String sessionId, @Valid @RequestBody InsertAppointmentDtoRequest insertRequest) throws ServerException {
        return appointmentService.insert(insertRequest, sessionId);
    }

    @DeleteMapping(path = "/api/tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse delete(@CookieValue("JAVASESSIONID") String sessionId, @PathVariable("id") int id) throws ServerException {
        return appointmentService.delete(sessionId, id);
    }

    @GetMapping(path = "/api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetByPatientTicketDtoResponse> getByPatient(@CookieValue("JAVASESSIONID") String sessionId) throws ServerException {
        return appointmentService.getByPatientId(sessionId);
    }

}
