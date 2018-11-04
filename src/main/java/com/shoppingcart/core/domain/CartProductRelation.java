package com.shoppingcart.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.shoppingcart.core.domain.enumeration.ProductStatus;

/**
 * A CartProductRelation.
 */
@Entity
@Table(name = "cart_product_relation")
public class CartProductRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus productStatus;

    @NotNull
    @Column(name = "product_count", nullable = false)
    private Long productCount;

    @NotNull
    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ManyToOne
    @JsonIgnoreProperties("cartProductRelations")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("cartProductRelations")
    private Cart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public CartProductRelation productStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Long getProductCount() {
        return productCount;
    }

    public CartProductRelation productCount(Long productCount) {
        this.productCount = productCount;
        return this;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public Long getCouponId() {
        return couponId;
    }

    public CartProductRelation couponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public CartProductRelation categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Product getProduct() {
        return product;
    }

    public CartProductRelation product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public CartProductRelation cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
        CartProductRelation cartProductRelation = (CartProductRelation) o;
        if (cartProductRelation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartProductRelation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartProductRelation{" +
            "id=" + getId() +
            ", productStatus='" + getProductStatus() + "'" +
            ", productCount=" + getProductCount() +
            ", couponId=" + getCouponId() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
