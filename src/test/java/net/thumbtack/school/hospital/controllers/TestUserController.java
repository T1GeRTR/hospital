package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.LoginUserDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.GetUserDtoResponse;
import net.thumbtack.school.hospital.response.LoginUserDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserController extends TestControllerBaseCreateHistory {

    private static final String url = "/api/session";

    @Test
    public void testLogin() {
        LoginUserDtoRequest user = new LoginUserDtoRequest("doctor", "passDoctor");
        ResponseEntity<LoginUserDtoResponse> response = restTemplate.postForEntity(url, user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getHeaders());
        Assert.assertEquals("Талгат", Objects.requireNonNull(response.getBody()).getFirstName());
    }

    @Test
    public void testLoginFail() {
        LoginUserDtoRequest user = new LoginUserDtoRequest("doctor", "PASSDOCTOR");
        ResponseEntity<LoginUserDtoResponse> response = restTemplate.postForEntity(url, user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testLogout() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient2");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url, HttpMethod.DELETE, request, EmptyResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testLogoutFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=wrongSessionId");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url, HttpMethod.DELETE, request, EmptyResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGet() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetUserDtoResponse> response = restTemplate.exchange("/api/account", HttpMethod.GET, request, GetUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).getId());
        Assert.assertEquals("Иван", response.getBody().getFirstName());
    }

    @Test
    public void testGetFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=WrongSessionId");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetUserDtoResponse> response = restTemplate.exchange("/api/account", HttpMethod.GET, request, GetUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
