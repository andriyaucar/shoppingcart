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
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @NotNull
    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @NotNull
    @Column(name = "total_product_count", nullable = false)
    private Long totalProductCount;

    @NotNull
    @Column(name = "available_product_count", nullable = false)
    private Long availableProductCount;

    @NotNull
    @Column(name = "product_number", nullable = false)
    private String productNumber;

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductContent> productContents = new HashSet<>();
    @OneToMany(mappedBy = "product")
    private Set<CartProductRelation> cartProductRelations = new HashSet<>();
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Cart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Product productTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Product productPrice(Double productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getTotalProductCount() {
        return totalProductCount;
    }

    public Product totalProductCount(Long totalProductCount) {
        this.totalProductCount = totalProductCount;
        return this;
    }

    public void setTotalProductCount(Long totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public Long getAvailableProductCount() {
        return availableProductCount;
    }

    public Product availableProductCount(Long availableProductCount) {
        this.availableProductCount = availableProductCount;
        return this;
    }

    public void setAvailableProductCount(Long availableProductCount) {
        this.availableProductCount = availableProductCount;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public Product productNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Product serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ProductContent> getProductContents() {
        return productContents;
    }

    public Product productContents(Set<ProductContent> productContents) {
        this.productContents = productContents;
        return this;
    }

    public Product addProductContent(ProductContent productContent) {
        this.productContents.add(productContent);
        productContent.setProduct(this);
        return this;
    }

    public Product removeProductContent(ProductContent productContent) {
        this.productContents.remove(productContent);
        productContent.setProduct(null);
        return this;
    }

    public void setProductContents(Set<ProductContent> productContents) {
        this.productContents = productContents;
    }

    public Set<CartProductRelation> getCartProductRelations() {
        return cartProductRelations;
    }

    public Product cartProductRelations(Set<CartProductRelation> cartProductRelations) {
        this.cartProductRelations = cartProductRelations;
        return this;
    }

    public Product addCartProductRelation(CartProductRelation cartProductRelation) {
        this.cartProductRelations.add(cartProductRelation);
        cartProductRelation.setProduct(this);
        return this;
    }

    public Product removeCartProductRelation(CartProductRelation cartProductRelation) {
        this.cartProductRelations.remove(cartProductRelation);
        cartProductRelation.setProduct(null);
        return this;
    }

    public void setCartProductRelations(Set<CartProductRelation> cartProductRelations) {
        this.cartProductRelations = cartProductRelations;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public Product carts(Set<Cart> carts) {
        this.carts = carts;
        return this;
    }

    public Product addCart(Cart cart) {
        this.carts.add(cart);
        cart.getProducts().add(this);
        return this;
    }

    public Product removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.getProducts().remove(this);
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productTitle='" + getProductTitle() + "'" +
            ", productPrice=" + getProductPrice() +
            ", totalProductCount=" + getTotalProductCount() +
            ", availableProductCount=" + getAvailableProductCount() +
            ", productNumber='" + getProductNumber() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            "}";
    }
}
