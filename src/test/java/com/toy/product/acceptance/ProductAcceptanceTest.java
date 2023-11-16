package com.toy.product.acceptance;

import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import com.toy.product.command.presentation.dto.ProductRegisterRequest;
import com.toy.test.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.toy.brand.acceptance.BrandAcceptanceTest.브랜드_생성_요청;
import static com.toy.brand.acceptance.BrandAcceptanceTest.브랜드_생성됨;
import static org.springframework.http.HttpStatus.CREATED;

@DisplayName("Product 인수테스트")
public class ProductAcceptanceTest extends AcceptanceTest {
    String baseUrl = "/products";

    @Test
    void 상품_생성_수정_삭제_시나리오() {
        // 브랜드 생성
        BrandRegisterRequest brandRegisterRequest = new BrandRegisterRequest("test");
        ExtractableResponse<Response> brandResponse = 브랜드_생성_요청(brandRegisterRequest);
        Long brandId = 브랜드_생성됨(brandResponse);

        // 상품 생성
        ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest(brandId, "TOP", 1000);
        ExtractableResponse<Response> response1 = 상품_생성_요청(productRegisterRequest);
        상품_생성됨(response1);

        // 상품 수정

        // 상품 삭제
    }

    private void 상품_생성됨(ExtractableResponse<Response> response1) {
        assertHttpStatus(response1, CREATED);
    }

    private ExtractableResponse<Response> 상품_생성_요청(ProductRegisterRequest productRegisterRequest) {
        return post(baseUrl, productRegisterRequest);
    }
}
