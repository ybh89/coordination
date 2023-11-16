package com.toy.test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 인수 테스트란?
 * 사용자 스토리를 검증하는 기능 테스트
 * 사용자 스토리로 테스트할 시나리오를 지정
 * 명세나 계약의 요구 사항이 충족되는지 확인하기 위해 수행되는 테스트
 * 보통 마지막 단계에서 수행하는 테스트를 의미
 * 작업을 종료 시켜도 되는지 검증하는 테스트 - 인수 테스트가 통과되면, 기능 구현은 끝이다.(ATDD)
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    public static void assertHttpStatus(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public static ExtractableResponse<Response> get(String path, Map<String, Object> queryParams) {
        return given()
                .log().all()
                .queryParams(queryParams)
                .when()
                .get(path)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> post(String path, Object params) {
        return given()
                .log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(String path, Object params) {
        return given()
                .log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(path)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path) {
        return given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then()
                .log().all()
                .extract();
    }
}
