package com.toy.brand.acceptance;

import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import com.toy.test.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.CREATED;

@DisplayName("Brand 인수테스트")
class BrandAcceptanceTest extends AcceptanceTest {
    String baseUrl = "/brands";

    @Test
    void 브랜드_생성_시나리오() {
        BrandRegisterRequest brandRegisterRequest = new BrandRegisterRequest("test");
        ExtractableResponse<Response> response = 브랜드_생성_요청(brandRegisterRequest);
        브랜드_생성됨(response);
    }

    private void 브랜드_생성됨(ExtractableResponse<Response> response) {
        assertHttpStatus(response, CREATED);
    }

    private ExtractableResponse<Response> 브랜드_생성_요청(BrandRegisterRequest brandRegisterRequest) {
        return post(baseUrl, brandRegisterRequest);
    }
}
