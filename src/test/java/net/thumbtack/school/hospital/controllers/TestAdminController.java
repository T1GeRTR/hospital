package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.RegisterAdminDtoRequest;
import net.thumbtack.school.hospital.request.UpdateAdminDtoRequest;
import net.thumbtack.school.hospital.response.RegisterAdminDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAdminController extends TestControllerBaseCreateHistory {
    private static final String url = "/api/admins";

    @Test
    public void testRegister() {
        RegisterAdminDtoRequest admin = new RegisterAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin", "MaxAdmin", "password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<RegisterAdminDtoRequest> request = new HttpEntity<>(admin, httpHeaders);
        ResponseEntity<RegisterAdminDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterAdminDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        Assert.assertEquals("Максим", response.getBody().getFirstName());
    }

    @Test
    public void testRegisterFail() {
        RegisterAdminDtoRequest admin = new RegisterAdminDtoRequest("Maxim", "Ковальчук", "Александрович", "admin", "admin", "password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<RegisterAdminDtoRequest> request = new HttpEntity<>(admin, httpHeaders);
        ResponseEntity<RegisterAdminDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterAdminDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate() {
        UpdateAdminDtoRequest admin = new UpdateAdminDtoRequest("Иван", "Иванов", "Иванович", "admin", "passAdmin", "password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<UpdateAdminDtoRequest> request = new HttpEntity<>(admin, httpHeaders);
        ResponseEntity<RegisterAdminDtoResponse> response = restTemplate.exchange(url, HttpMethod.PUT, request, RegisterAdminDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        Assert.assertEquals("Иван", response.getBody().getFirstName());
    }

    @Test
    public void testUpdateFail() {
        UpdateAdminDtoRequest admin = new UpdateAdminDtoRequest("Иван", "Иванов", "Иванович", "admin", "wrongPassword", "password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<UpdateAdminDtoRequest> request = new HttpEntity<>(admin, httpHeaders);
        ResponseEntity<RegisterAdminDtoResponse> response = restTemplate.exchange(url, HttpMethod.PUT, request, RegisterAdminDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}
