package com.shoppingcart.core.repository;

import com.shoppingcart.core.domain.ProductContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductContentRepository extends JpaRepository<ProductContent, Long> {

}
