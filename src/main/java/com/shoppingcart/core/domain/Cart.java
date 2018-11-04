package com.shoppingcart.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cost_per_delivery", nullable = false)
    private Double costPerDelivery;

    @NotNull
    @Column(name = "cost_per_product", nullable = false)
    private Double costPerProduct;

    @OneToMany(mappedBy = "cart")
    private Set<CartProductRelation> cartProductRelations = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "cart_product",
               joinColumns = @JoinColumn(name = "carts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("carts")
    private Coupon coupon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCostPerDelivery() {
        return costPerDelivery;
    }

    public Cart costPerDelivery(Double costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
        return this;
    }

    public void setCostPerDelivery(Double costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public Double getCostPerProduct() {
        return costPerProduct;
    }

    public Cart costPerProduct(Double costPerProduct) {
        this.costPerProduct = costPerProduct;
        return this;
    }

    public void setCostPerProduct(Double costPerProduct) {
        this.costPerProduct = costPerProduct;
    }

    public Set<CartProductRelation> getCartProductRelations() {
        return cartProductRelations;
    }

    public Cart cartProductRelations(Set<CartProductRelation> cartProductRelations) {
        this.cartProductRelations = cartProductRelations;
        return this;
    }

    public Cart addCartProductRelation(CartProductRelation cartProductRelation) {
        this.cartProductRelations.add(cartProductRelation);
        cartProductRelation.setCart(this);
        return this;
    }

    public Cart removeCartProductRelation(CartProductRelation cartProductRelation) {
        this.cartProductRelations.remove(cartProductRelation);
        cartProductRelation.setCart(null);
        return this;
    }

    public void setCartProductRelations(Set<CartProductRelation> cartProductRelations) {
        this.cartProductRelations = cartProductRelations;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Cart products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Cart addProduct(Product product) {
        this.products.add(product);
        product.getCarts().add(this);
        return this;
    }

    public Cart removeProduct(Product product) {
        this.products.remove(product);
        product.getCarts().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Cart coupon(Coupon coupon) {
        this.coupon = coupon;
        return this;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
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
        Cart cart = (Cart) o;
        if (cart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", costPerDelivery=" + getCostPerDelivery() +
            ", costPerProduct=" + getCostPerProduct() +
            "}";
    }
}
