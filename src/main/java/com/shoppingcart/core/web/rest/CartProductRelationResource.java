package com.shoppingcart.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shoppingcart.core.domain.CartProductRelation;
import com.shoppingcart.core.repository.CartProductRelationRepository;
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
 * REST controller for managing CartProductRelation.
 */
@RestController
@RequestMapping("/api")
public class CartProductRelationResource {

    private final Logger log = LoggerFactory.getLogger(CartProductRelationResource.class);

    private static final String ENTITY_NAME = "shoppingcartCartProductRelation";

    private final CartProductRelationRepository cartProductRelationRepository;

    public CartProductRelationResource(CartProductRelationRepository cartProductRelationRepository) {
        this.cartProductRelationRepository = cartProductRelationRepository;
    }

    /**
     * POST  /cart-product-relations : Create a new cartProductRelation.
     *
     * @param cartProductRelation the cartProductRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartProductRelation, or with status 400 (Bad Request) if the cartProductRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cart-product-relations")
    @Timed
    public ResponseEntity<CartProductRelation> createCartProductRelation(@Valid @RequestBody CartProductRelation cartProductRelation) throws URISyntaxException {
        log.debug("REST request to save CartProductRelation : {}", cartProductRelation);
        if (cartProductRelation.getId() != null) {
            throw new BadRequestAlertException("A new cartProductRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartProductRelation result = cartProductRelationRepository.save(cartProductRelation);
        return ResponseEntity.created(new URI("/api/cart-product-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cart-product-relations : Updates an existing cartProductRelation.
     *
     * @param cartProductRelation the cartProductRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartProductRelation,
     * or with status 400 (Bad Request) if the cartProductRelation is not valid,
     * or with status 500 (Internal Server Error) if the cartProductRelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cart-product-relations")
    @Timed
    public ResponseEntity<CartProductRelation> updateCartProductRelation(@Valid @RequestBody CartProductRelation cartProductRelation) throws URISyntaxException {
        log.debug("REST request to update CartProductRelation : {}", cartProductRelation);
        if (cartProductRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartProductRelation result = cartProductRelationRepository.save(cartProductRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartProductRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cart-product-relations : get all the cartProductRelations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cartProductRelations in body
     */
    @GetMapping("/cart-product-relations")
    @Timed
    public List<CartProductRelation> getAllCartProductRelations() {
        log.debug("REST request to get all CartProductRelations");
        return cartProductRelationRepository.findAll();
    }

    /**
     * GET  /cart-product-relations/:id : get the "id" cartProductRelation.
     *
     * @param id the id of the cartProductRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartProductRelation, or with status 404 (Not Found)
     */
    @GetMapping("/cart-product-relations/{id}")
    @Timed
    public ResponseEntity<CartProductRelation> getCartProductRelation(@PathVariable Long id) {
        log.debug("REST request to get CartProductRelation : {}", id);
        Optional<CartProductRelation> cartProductRelation = cartProductRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cartProductRelation);
    }

    /**
     * DELETE  /cart-product-relations/:id : delete the "id" cartProductRelation.
     *
     * @param id the id of the cartProductRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cart-product-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartProductRelation(@PathVariable Long id) {
        log.debug("REST request to delete CartProductRelation : {}", id);

        cartProductRelationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
