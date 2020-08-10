package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.TestBaseCreateHistory;
import net.thumbtack.school.hospital.exception.ServerException;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class TestControllerBaseCreateHistory extends TestBaseCreateHistory {
    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void checkSetup() throws ServerException {
        setUp();
    }
}
