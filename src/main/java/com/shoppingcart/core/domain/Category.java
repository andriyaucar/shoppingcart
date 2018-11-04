package com.shoppingcart.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "category_title", nullable = false)
    private String categoryTitle;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "category_campaign",
               joinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "campaigns_id", referencedColumnName = "id"))
    private Set<Campaign> campaigns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public Category categoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
        return this;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Category products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Category addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
        return this;
    }

    public Category removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public Category campaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
        return this;
    }

    public Category addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.getCategories().add(this);
        return this;
    }

    public Category removeCampaign(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.getCategories().remove(this);
        return this;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryTitle='" + getCategoryTitle() + "'" +
            "}";
    }
}
