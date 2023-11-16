package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import jakarta.persistence.*;

import java.util.Objects;

import static com.toy.common.exception.ExceptionCode.BRAND_ID_IS_NULL;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Product {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    @Enumerated(STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Embedded
    private Price price;

    @Deprecated
    protected Product() {
    }

    private Product(Long brandId, Category category, Price price) {
        this.brandId = brandId;
        this.category = category;
        this.price = price;
    }

    public static Product create(Long brandId, String category, int price, ProductRegisterValidator productRegisterValidator) {
        validate(brandId);
        productRegisterValidator.validateBrandExists(brandId);
        return new Product(brandId, Category.from(category), Price.from(price));
    }

    private static void validate(Long brandId) {
        if (brandId == null) {
            throw new DomainException(BRAND_ID_IS_NULL);
        }
    }

    public Long id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
