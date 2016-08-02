package br.com.emerson.service;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class RedisServiceIT extends TestParent {

    @Test
    public void createSession() {
        String json = "{\"name\":\"Emerson\"}";
        given().
        content(json).with().contentType(ContentType.JSON).then().
        expect().
        log().all().
        statusCode(201).
        when().
        post(serverPath);
    }

    @Test
    public void createSessionBadRequest() {
        String json = "{\"name\":\"\"}";
        given().
        content(json).with().contentType(ContentType.JSON).then().
        expect().
        log().all().
        statusCode(400).
        when().
        post(serverPath);
    }

    @Test
    public void getSession() {
        String json = "{\"name\":\"Emerson Luiz\"}";
        Response response = given().content(json).with().contentType(ContentType.JSON).when().post(serverPath);

        expect().
        log().all().
        statusCode(200).
        contentType(ContentType.JSON).
        body("name", equalTo("Emerson Luiz")).
        when().
        get(serverPath + "/" + response.body().asString());
    }

    @Test
    public void getSessionNotFound() {
        expect().
        log().all().
        statusCode(404).
        when().
        get(serverPath + "/bf4e76a7-d2d0-4c30-8a98-b5967e2bb297");
    }

    @Test
    public void deleteSession() {
        String json = "{\"name\":\"Luiz\"}";
        Response response = given().content(json).with().contentType(ContentType.JSON).when().post(serverPath);

        expect().
        log().all().
        statusCode(200).
        contentType(ContentType.JSON).
        body("name", equalTo("Luiz")).
        when().
        get(serverPath + "/" + response.body().asString());

        expect().
        log().all().
        statusCode(204).
        when().
        delete(serverPath + "/" + response.body().asString());

        expect().
        log().all().
        statusCode(404).
        when().
        get(serverPath + "/" + response.body().asString());
    }
}
