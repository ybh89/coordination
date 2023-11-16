package com.toy.brand.command.domain;

import com.toy.common.exception.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.toy.common.exception.ExceptionCode.BRAND_NAME_NOT_EXIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Brand {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Deprecated
    protected Brand() {
    }

    private Brand(String name) {
        this.name = name;
    }

    public static Brand create(String name) {
        validate(name);
        return new Brand(name);
    }

    private static void validate(String name) {
        if (!StringUtils.hasText(name)) {
            throw new DomainException(BRAND_NAME_NOT_EXIST);
        }
    }

    public Long id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
