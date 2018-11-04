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
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "campaign_title", nullable = false)
    private String campaignTitle;

    @NotNull
    @Column(name = "discount_number", nullable = false)
    private Double discountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @NotNull
    @Column(name = "min_product_count", nullable = false)
    private Long minProductCount;

    @ManyToMany(mappedBy = "campaigns")
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public Campaign campaignTitle(String campaignTitle) {
        this.campaignTitle = campaignTitle;
        return this;
    }

    public void setCampaignTitle(String campaignTitle) {
        this.campaignTitle = campaignTitle;
    }

    public Double getDiscountNumber() {
        return discountNumber;
    }

    public Campaign discountNumber(Double discountNumber) {
        this.discountNumber = discountNumber;
        return this;
    }

    public void setDiscountNumber(Double discountNumber) {
        this.discountNumber = discountNumber;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public Campaign discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Long getMinProductCount() {
        return minProductCount;
    }

    public Campaign minProductCount(Long minProductCount) {
        this.minProductCount = minProductCount;
        return this;
    }

    public void setMinProductCount(Long minProductCount) {
        this.minProductCount = minProductCount;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Campaign categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Campaign addCategory(Category category) {
        this.categories.add(category);
        category.getCampaigns().add(this);
        return this;
    }

    public Campaign removeCategory(Category category) {
        this.categories.remove(category);
        category.getCampaigns().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
        Campaign campaign = (Campaign) o;
        if (campaign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", campaignTitle='" + getCampaignTitle() + "'" +
            ", discountNumber=" + getDiscountNumber() +
            ", discountType='" + getDiscountType() + "'" +
            ", minProductCount=" + getMinProductCount() +
            "}";
    }
}
