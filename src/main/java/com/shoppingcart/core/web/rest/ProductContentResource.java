package com.shoppingcart.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shoppingcart.core.domain.ProductContent;
import com.shoppingcart.core.repository.ProductContentRepository;
import com.shoppingcart.core.web.rest.errors.BadRequestAlertException;
import com.shoppingcart.core.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductContent.
 */
@RestController
@RequestMapping("/api")
public class ProductContentResource {

    private final Logger log = LoggerFactory.getLogger(ProductContentResource.class);

    private static final String ENTITY_NAME = "shoppingcartProductContent";

    private final ProductContentRepository productContentRepository;

    public ProductContentResource(ProductContentRepository productContentRepository) {
        this.productContentRepository = productContentRepository;
    }

    /**
     * POST  /product-contents : Create a new productContent.
     *
     * @param productContent the productContent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productContent, or with status 400 (Bad Request) if the productContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-contents")
    @Timed
    public ResponseEntity<ProductContent> createProductContent(@Valid @RequestBody ProductContent productContent) throws URISyntaxException {
        log.debug("REST request to save ProductContent : {}", productContent);
        if (productContent.getId() != null) {
            throw new BadRequestAlertException("A new productContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductContent result = productContentRepository.save(productContent);
        return ResponseEntity.created(new URI("/api/product-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-contents : Updates an existing productContent.
     *
     * @param productContent the productContent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productContent,
     * or with status 400 (Bad Request) if the productContent is not valid,
     * or with status 500 (Internal Server Error) if the productContent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-contents")
    @Timed
    public ResponseEntity<ProductContent> updateProductContent(@Valid @RequestBody ProductContent productContent) throws URISyntaxException {
        log.debug("REST request to update ProductContent : {}", productContent);
        if (productContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductContent result = productContentRepository.save(productContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productContent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-contents : get all the productContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productContents in body
     */
    @GetMapping("/product-contents")
    @Timed
    public List<ProductContent> getAllProductContents() {
        log.debug("REST request to get all ProductContents");
        return productContentRepository.findAll();
    }

    /**
     * GET  /product-contents/:id : get the "id" productContent.
     *
     * @param id the id of the productContent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productContent, or with status 404 (Not Found)
     */
    @GetMapping("/product-contents/{id}")
    @Timed
    public ResponseEntity<ProductContent> getProductContent(@PathVariable Long id) {
        log.debug("REST request to get ProductContent : {}", id);
        Optional<ProductContent> productContent = productContentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productContent);
    }

    /**
     * DELETE  /product-contents/:id : delete the "id" productContent.
     *
     * @param id the id of the productContent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductContent(@PathVariable Long id) {
        log.debug("REST request to delete ProductContent : {}", id);

        productContentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
