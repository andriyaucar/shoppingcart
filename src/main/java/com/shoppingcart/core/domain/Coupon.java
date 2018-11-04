package com.shoppingcart.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.shoppingcart.core.domain.enumeration.DiscountType;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "min_cart_price", nullable = false)
    private Double minCartPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @NotNull
    @Column(name = "discount_number", nullable = false)
    private Double discountNumber;

    @OneToMany(mappedBy = "coupon")
    private Set<Cart> carts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinCartPrice() {
        return minCartPrice;
    }

    public Coupon minCartPrice(Double minCartPrice) {
        this.minCartPrice = minCartPrice;
        return this;
    }

    public void setMinCartPrice(Double minCartPrice) {
        this.minCartPrice = minCartPrice;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public Coupon discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountNumber() {
        return discountNumber;
    }

    public Coupon discountNumber(Double discountNumber) {
        this.discountNumber = discountNumber;
        return this;
    }

    public void setDiscountNumber(Double discountNumber) {
        this.discountNumber = discountNumber;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public Coupon carts(Set<Cart> carts) {
        this.carts = carts;
        return this;
    }

    public Coupon addCart(Cart cart) {
        this.carts.add(cart);
        cart.setCoupon(this);
        return this;
    }

    public Coupon removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setCoupon(null);
        return this;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
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
        Coupon coupon = (Coupon) o;
        if (coupon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coupon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", minCartPrice=" + getMinCartPrice() +
            ", discountType='" + getDiscountType() + "'" +
            ", discountNumber=" + getDiscountNumber() +
            "}";
    }
}
