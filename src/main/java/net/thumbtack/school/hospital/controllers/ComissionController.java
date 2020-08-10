package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.request.InsertComissionDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertComissionDtoResponse;
import net.thumbtack.school.hospital.services.ComissionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ComissionController {
    private ComissionService comissionService;

    public ComissionController(ComissionService comissionService) {
        this.comissionService = comissionService;
    }

    @PostMapping(path = "/api/comissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InsertComissionDtoResponse insert(@CookieValue("JAVASESSIONID") String sessionId, @Valid @RequestBody InsertComissionDtoRequest insertRequest) throws ServerException {
        return comissionService.insert(insertRequest, sessionId);
    }

    @DeleteMapping(path = "/api/comissions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse delete(@CookieValue("JAVASESSIONID") String sessionId, @PathVariable("id") int id) throws ServerException {
        return comissionService.delete(sessionId, id);
    }
}
