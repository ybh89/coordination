package com.toy.product.acceptance;

import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import com.toy.product.command.presentation.dto.ProductPriceChangeRequest;
import com.toy.product.command.presentation.dto.ProductRegisterRequest;
import com.toy.test.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.toy.brand.acceptance.BrandAcceptanceTest.브랜드_생성_요청;
import static com.toy.brand.acceptance.BrandAcceptanceTest.브랜드_생성됨;
import static org.springframework.http.HttpStatus.*;

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
        Long productId = 상품_생성됨(response1);

        // 상품가격 변경
        ProductPriceChangeRequest productPriceChangeRequest = new ProductPriceChangeRequest(2000);
        ExtractableResponse<Response> response2 = 상품가격_변경_요청(productId, productPriceChangeRequest);
        상품가격_변경됨(response2);

        // 상품 삭제
        ExtractableResponse<Response> response3 = 상품_삭제_요청(productId);
        상품_삭제됨(response3);
    }

    private void 상품_삭제됨(ExtractableResponse<Response> response3) {
        assertHttpStatus(response3, NO_CONTENT);
    }

    private ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return delete(baseUrl + "/" + productId);
    }

    private void 상품가격_변경됨(ExtractableResponse<Response> response2) {
        assertHttpStatus(response2, OK);
    }

    private ExtractableResponse<Response> 상품가격_변경_요청(Long productId, ProductPriceChangeRequest productPriceChangeRequest) {
        return patch(baseUrl + "/" + productId + "/price", productPriceChangeRequest);
    }

    private Long 상품_생성됨(ExtractableResponse<Response> response1) {
        assertHttpStatus(response1, CREATED);
        String[] locations = response1.header("Location").split("/");
        return Long.valueOf(locations[locations.length - 1]);
    }

    private ExtractableResponse<Response> 상품_생성_요청(ProductRegisterRequest productRegisterRequest) {
        return post(baseUrl, productRegisterRequest);
    }
}
