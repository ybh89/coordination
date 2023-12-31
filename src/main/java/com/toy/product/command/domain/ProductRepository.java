package com.toy.product.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findTopByCategoryOrderByPriceAscIdDesc(Category category);

    void deleteAllByBrandId(Long brandId);
}
