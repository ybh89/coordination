package com.toy.coordination.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

import static jakarta.persistence.LockModeType.OPTIMISTIC;

public interface LowestPriceProductByCategoryRepository extends JpaRepository<LowestPriceProductByCategory, Long> {
    @Lock(OPTIMISTIC)
    LowestPriceProductByCategory findByCategory(String category);

    List<LowestPriceProductByCategory> findAllByBrandName(String brandName);
}
