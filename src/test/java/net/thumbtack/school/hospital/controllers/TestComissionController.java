package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.InsertComissionDtoRequest;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.InsertComissionDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestComissionController extends TestControllerBaseCreateHistory {
    private static final String url = "/api/comissions";

    @Test
    public void testInsert() {
        List<Integer> doctorIds = new ArrayList<>();
        doctorIds.add(18);
        doctorIds.add(19);
        InsertComissionDtoRequest comission = new InsertComissionDtoRequest(20, doctorIds, "44", LocalDate.of(2020, 4, 20), LocalTime.of(13, 0), 60);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Doctor");
        HttpEntity<InsertComissionDtoRequest> request = new HttpEntity<>(comission, httpHeaders);
        ResponseEntity<InsertComissionDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, InsertComissionDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInsertFail() {
        InsertComissionDtoRequest comission = new InsertComissionDtoRequest(4, new ArrayList<>(), "44", LocalDate.of(2020, 4, 17), LocalTime.of(13, 0), 60);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Doctor");
        HttpEntity<InsertComissionDtoRequest> request = new HttpEntity<>(comission, httpHeaders);
        ResponseEntity<InsertComissionDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, InsertComissionDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDelete() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 7);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Doctor");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 99);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }
}
