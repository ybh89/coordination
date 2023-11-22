package com.toy.coordination.command.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(of = "id")
@Entity
public class LowestPriceProductByCategory {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "category", nullable = false, unique = true)
    private String category;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "price", nullable = false)
    private int price;

    @Version
    private int version;

    @Deprecated
    protected LowestPriceProductByCategory() {}

    public LowestPriceProductByCategory(Long productId, String category, String brandName, int price) {
        this.productId = productId;
        this.category = category;
        this.brandName = brandName;
        this.price = price;
    }

    public int price() {
        return price;
    }

    public Long productId() {
        return productId;
    }

    public String category() {
        return category;
    }

    public String brandName() {
        return brandName;
    }

    public void resetToLowestPriceProduct(LowestPriceProductByCategory other) {
        if (this.price <= other.price()) {
            return;
        }

        this.productId = other.productId();
        this.category = other.category();
        this.price = other.price();
        this.brandName = other.brandName();
    }

    public void resetToNewLowestPriceProduct(LowestPriceProductByCategory other) {
        this.productId = other.productId();
        this.category = other.category();
        this.price = other.price();
        this.brandName = other.brandName();
    }
}
