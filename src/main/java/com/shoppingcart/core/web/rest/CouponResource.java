package com.shoppingcart.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shoppingcart.core.domain.Coupon;
import com.shoppingcart.core.repository.CouponRepository;
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
 * REST controller for managing Coupon.
 */
@RestController
@RequestMapping("/api")
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "shoppingcartCoupon";

    private final CouponRepository couponRepository;

    public CouponResource(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * POST  /coupons : Create a new coupon.
     *
     * @param coupon the coupon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coupon, or with status 400 (Bad Request) if the coupon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coupons")
    @Timed
    public ResponseEntity<Coupon> createCoupon(@Valid @RequestBody Coupon coupon) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", coupon);
        if (coupon.getId() != null) {
            throw new BadRequestAlertException("A new coupon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coupon result = couponRepository.save(coupon);
        return ResponseEntity.created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coupons : Updates an existing coupon.
     *
     * @param coupon the coupon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coupon,
     * or with status 400 (Bad Request) if the coupon is not valid,
     * or with status 500 (Internal Server Error) if the coupon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coupons")
    @Timed
    public ResponseEntity<Coupon> updateCoupon(@Valid @RequestBody Coupon coupon) throws URISyntaxException {
        log.debug("REST request to update Coupon : {}", coupon);
        if (coupon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coupon result = couponRepository.save(coupon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coupon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coupons : get all the coupons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coupons in body
     */
    @GetMapping("/coupons")
    @Timed
    public List<Coupon> getAllCoupons() {
        log.debug("REST request to get all Coupons");
        return couponRepository.findAll();
    }

    /**
     * GET  /coupons/:id : get the "id" coupon.
     *
     * @param id the id of the coupon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coupon, or with status 404 (Not Found)
     */
    @GetMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        Optional<Coupon> coupon = couponRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(coupon);
    }

    /**
     * DELETE  /coupons/:id : delete the "id" coupon.
     *
     * @param id the id of the coupon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);

        couponRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
