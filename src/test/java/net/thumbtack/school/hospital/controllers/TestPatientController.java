package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.request.UpdatePatientDtoRequest;
import net.thumbtack.school.hospital.response.GetByIdPatientDtoResponse;
import net.thumbtack.school.hospital.response.RegisterPatientDtoResponse;
import net.thumbtack.school.hospital.response.UpdatePatientDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestPatientController extends TestControllerBaseCreateHistory {
    private static final String url = "/api/patients";

    @Test
    public void testRegister() {
        RegisterPatientDtoRequest patient = new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "PetrPatient3", "passPatient", "petr@gmail.com", "Omsk", "+79994557609");
        ResponseEntity<RegisterPatientDtoResponse> response = restTemplate.postForEntity(url, patient, RegisterPatientDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).getId());
        Assert.assertEquals("Петр", response.getBody().getFirstName());
    }

    @Test
    public void testRegisterFail() {
        RegisterPatientDtoRequest patient = new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "PATIENT", "passPatient", "petr@gmail.com", "Omsk", "+79994557609");
        ResponseEntity<RegisterPatientDtoResponse> response = restTemplate.postForEntity(url, patient, RegisterPatientDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate() {
        UpdatePatientDtoRequest patient = new UpdatePatientDtoRequest("Петр", "Иванов", "Петрович", "passPatient", "passPatient2", "petr@gmail.com", "Omsk", "+7-999-455-7609");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<UpdatePatientDtoRequest> request = new HttpEntity<>(patient, httpHeaders);
        ResponseEntity<UpdatePatientDtoResponse> response = restTemplate.exchange(url, HttpMethod.PUT, request, UpdatePatientDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Иванов", Objects.requireNonNull(response.getBody()).getLastName());
        Assert.assertEquals("+79994557609", response.getBody().getPhone());
    }

    @Test
    public void testUpdateFail() {
        UpdatePatientDtoRequest patient = new UpdatePatientDtoRequest("Петр", "Иванов", "Петрович", "passPatient", "passPatient2", "petr@gmail.com", "Omsk", "+8-999-455-7609");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<UpdatePatientDtoRequest> request = new HttpEntity<>(patient, httpHeaders);
        ResponseEntity<UpdatePatientDtoResponse> response = restTemplate.exchange(url, HttpMethod.PUT, request, UpdatePatientDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGet() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Doctor");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetByIdPatientDtoResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.GET, request, GetByIdPatientDtoResponse.class, 20);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(20, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testGetFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetByIdPatientDtoResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.GET, request, GetByIdPatientDtoResponse.class, 6);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
