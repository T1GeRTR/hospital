package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.InsertAppointmentDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertAppointmentDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAppointmentController extends TestControllerBaseCreateHistory {
    private static final String url = "/api/tickets";


    @Test
    public void testInsert() {
        InsertAppointmentDtoRequest appointment = new InsertAppointmentDtoRequest(18, null, LocalDate.of(2020, 4, 21), LocalTime.of(8, 0));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<InsertAppointmentDtoRequest> request = new HttpEntity<>(appointment, httpHeaders);
        ResponseEntity<InsertAppointmentDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, InsertAppointmentDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("D<18>210420200800", Objects.requireNonNull(response.getBody()).getTicket());
        Assert.assertEquals("Талгат", response.getBody().getFirstName());
    }

    @Test
    public void testInsertFail() {
        InsertAppointmentDtoRequest appointment = new InsertAppointmentDtoRequest(1, "dentist", LocalDate.of(2020, 4, 22), LocalTime.of(8, 0));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<InsertAppointmentDtoRequest> request = new HttpEntity<>(appointment, httpHeaders);
        ResponseEntity<InsertAppointmentDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, InsertAppointmentDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDelete() {
        testInsert();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 1695);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 9999999);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
