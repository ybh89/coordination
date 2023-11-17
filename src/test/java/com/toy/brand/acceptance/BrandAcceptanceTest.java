package com.toy.brand.acceptance;

import com.toy.brand.command.presentation.dto.BrandModifyRequest;
import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import com.toy.test.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.*;

@DisplayName("Brand 인수테스트")
public class BrandAcceptanceTest extends AcceptanceTest {
    static String baseUrl = "/brands";

    @Test
    void 브랜드_생성_수정_삭제_시나리오() {
        // 브랜드 생성
        BrandRegisterRequest brandRegisterRequest = new BrandRegisterRequest("test");
        ExtractableResponse<Response> response1 = 브랜드_생성_요청(brandRegisterRequest);
        Long brandId = 브랜드_생성됨(response1);

        // 브랜드 수정
        BrandModifyRequest brandModifyRequest = new BrandModifyRequest("changeName");
        ExtractableResponse<Response> response2 = 브랜드_수정_요청(brandId, brandModifyRequest);
        브랜드_수정됨(response2);

        // 브랜드 삭제
        ExtractableResponse<Response> response3 = 브랜드_삭제_요청(brandId);
        브랜드_삭제됨(response3);
    }

    private void 브랜드_삭제됨(ExtractableResponse<Response> response3) {
        assertHttpStatus(response3, NO_CONTENT);
    }

    private ExtractableResponse<Response> 브랜드_삭제_요청(Long brandId) {
        return delete(baseUrl + "/" + brandId);
    }

    private void 브랜드_수정됨(ExtractableResponse<Response> response2) {
        assertHttpStatus(response2, OK);
    }

    private ExtractableResponse<Response> 브랜드_수정_요청(Long brandId, BrandModifyRequest brandModifyRequest) {
        return put(baseUrl + "/" + brandId, brandModifyRequest);
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
