package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.services.DebugService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {
    private DebugService debugService;

    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    @PostMapping(path = "/api/debug/clear")
    public EmptyResponse clear() throws ServerException {
        return debugService.clear();
    }
}
