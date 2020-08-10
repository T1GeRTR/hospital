package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.*;
import net.thumbtack.school.hospital.response.*;
import net.thumbtack.school.hospital.services.DoctorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping(path = "/api/doctors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterDoctorDtoResponse register(@Valid @RequestBody RegisterDoctorDtoRequest registerRequest, @CookieValue("JAVASESSIONID") String sessionId) throws ServerException {
        return doctorService.register(registerRequest, sessionId);
    }

    @GetMapping(path = "/api/doctors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetByIdDoctorDtoResponse getById(
            @PathVariable("id") int id, @CookieValue("JAVASESSIONID") String sessionId, @RequestParam String schedule,
            @RequestParam String startDate, @RequestParam String endDate) throws ServerException {
        return doctorService.getById(id, sessionId, schedule, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping(path = "/api/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetAllDoctorWithParamsDtoResponse> getAllWithParams(
            @CookieValue("JAVASESSIONID") String sessionId, @RequestParam String schedule, @RequestParam String speciality,
            @RequestParam String startDate, @RequestParam String endDate) throws ServerException {
        return doctorService.getAllWithParams(speciality, sessionId, schedule, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @PutMapping(path = "/api/doctors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EditScheduleDoctorDtoResponse editSchedule(@CookieValue("JAVASESSIONID") String sessionId, @PathVariable("id") int id, @Valid @RequestBody EditScheduleDoctorDtoRequest editRequest) throws ServerException {
        return doctorService.editSchedule(editRequest, sessionId, id);
    }

    @DeleteMapping(path = "/api/doctors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse delete(@Valid @RequestBody DeleteDoctorDtoRequest deleteRequest, @CookieValue("JAVASESSIONID") String sessionId, @PathVariable("id") int id) throws ServerException {
        return doctorService.delete(deleteRequest, sessionId, id);
    }
}
