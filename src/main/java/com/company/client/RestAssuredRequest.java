package com.company.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

@Component
public class RestAssuredRequest<B> {

    private URIBuilder uriBuilder;
    private Map<String, String> queryParams = new HashMap<>();
    private Method method = Method.GET;
    private B body;

    public RestAssuredRequest(@Value("${host}") URI host) {
        this.uriBuilder = new URIBuilder(host);
    }

    public RestAssuredRequest<B> path(String path) {
        this.uriBuilder.setPath(path);
        return this;
    }

    public RestAssuredRequest<B> queryParam(String name, Object value) {
        this.queryParams.put(name, String.valueOf(value));
        return this;
    }

    public RestAssuredRequest<B> queryParams(Map<String, String> queryParams) {
        this.queryParams.putAll(queryParams);
        return this;
    }

    public RestAssuredRequest<B> post() {
        this.method = Method.POST;
        return this;
    }

    public RestAssuredRequest<B> put() {
        this.method = Method.PUT;
        return this;
    }

    public RestAssuredRequest<B> body(B body) {
        this.body = body;
        return this;
    }

    @SneakyThrows(URISyntaxException.class)
    public Response readAsResponse() {
        queryParams.forEach((key, value) -> this.uriBuilder.addParameter(key, value));
        RequestSpecification specification = given().filter(new AllureRestAssured()).log().uri();
        if (Objects.nonNull(body)) {
            specification.body(body);
        }
        return specification.request(method, uriBuilder.build()).prettyPeek().thenReturn();
    }
}
