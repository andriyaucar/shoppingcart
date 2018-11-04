package com.shoppingcart.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductContent.
 */
@Entity
@Table(name = "product_content")
public class ProductContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_content_key", nullable = false)
    private String productContentKey;

    @NotNull
    @Column(name = "product_content_value", nullable = false)
    private String productContentValue;

    @ManyToOne
    @JsonIgnoreProperties("productContents")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductContentKey() {
        return productContentKey;
    }

    public ProductContent productContentKey(String productContentKey) {
        this.productContentKey = productContentKey;
        return this;
    }

    public void setProductContentKey(String productContentKey) {
        this.productContentKey = productContentKey;
    }

    public String getProductContentValue() {
        return productContentValue;
    }

    public ProductContent productContentValue(String productContentValue) {
        this.productContentValue = productContentValue;
        return this;
    }

    public void setProductContentValue(String productContentValue) {
        this.productContentValue = productContentValue;
    }

    public Product getProduct() {
        return product;
    }

    public ProductContent product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductContent productContent = (ProductContent) o;
        if (productContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductContent{" +
            "id=" + getId() +
            ", productContentKey='" + getProductContentKey() + "'" +
            ", productContentValue='" + getProductContentValue() + "'" +
            "}";
    }
}
