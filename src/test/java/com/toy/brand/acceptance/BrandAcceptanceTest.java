package com.toy.brand.acceptance;

import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import com.toy.test.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.CREATED;

@DisplayName("Brand 인수테스트")
public class BrandAcceptanceTest extends AcceptanceTest {
    static String baseUrl = "/brands";

    @Test
    void 브랜드_생성_시나리오() {
        BrandRegisterRequest brandRegisterRequest = new BrandRegisterRequest("test");
        ExtractableResponse<Response> response = 브랜드_생성_요청(brandRegisterRequest);
        브랜드_생성됨(response);
    }

    public static Long 브랜드_생성됨(ExtractableResponse<Response> response) {
        assertHttpStatus(response, CREATED);
        String[] locations = response.header("Location").split("/");
        return Long.valueOf(locations[locations.length - 1]);
    }

    public static ExtractableResponse<Response> 브랜드_생성_요청(BrandRegisterRequest brandRegisterRequest) {
        return post(baseUrl, brandRegisterRequest);
    }
}
