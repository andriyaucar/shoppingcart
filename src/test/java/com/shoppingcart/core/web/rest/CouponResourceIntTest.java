package com.shoppingcart.core.web.rest;

import com.shoppingcart.core.ShoppingcartApp;

import com.shoppingcart.core.domain.Coupon;
import com.shoppingcart.core.repository.CouponRepository;
import com.shoppingcart.core.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.shoppingcart.core.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shoppingcart.core.domain.enumeration.DiscountType;
/**
 * Test class for the CouponResource REST controller.
 *
 * @see CouponResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingcartApp.class)
public class CouponResourceIntTest {

    private static final Double DEFAULT_MIN_CART_PRICE = 1D;
    private static final Double UPDATED_MIN_CART_PRICE = 2D;

    private static final DiscountType DEFAULT_DISCOUNT_TYPE = DiscountType.RATE;
    private static final DiscountType UPDATED_DISCOUNT_TYPE = DiscountType.AMOUNT;

    private static final Double DEFAULT_DISCOUNT_NUMBER = 1D;
    private static final Double UPDATED_DISCOUNT_NUMBER = 2D;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCouponMockMvc;

    private Coupon coupon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CouponResource couponResource = new CouponResource(couponRepository);
        this.restCouponMockMvc = MockMvcBuilders.standaloneSetup(couponResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coupon createEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .minCartPrice(DEFAULT_MIN_CART_PRICE)
            .discountType(DEFAULT_DISCOUNT_TYPE)
            .discountNumber(DEFAULT_DISCOUNT_NUMBER);
        return coupon;
    }

    @Before
    public void initTest() {
        coupon = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupon() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate + 1);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getMinCartPrice()).isEqualTo(DEFAULT_MIN_CART_PRICE);
        assertThat(testCoupon.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
        assertThat(testCoupon.getDiscountNumber()).isEqualTo(DEFAULT_DISCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void createCouponWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon with an existing ID
        coupon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMinCartPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponRepository.findAll().size();
        // set the field null
        coupon.setMinCartPrice(null);

        // Create the Coupon, which fails.

        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponRepository.findAll().size();
        // set the field null
        coupon.setDiscountType(null);

        // Create the Coupon, which fails.

        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponRepository.findAll().size();
        // set the field null
        coupon.setDiscountNumber(null);

        // Create the Coupon, which fails.

        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get all the couponList
        restCouponMockMvc.perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].minCartPrice").value(hasItem(DEFAULT_MIN_CART_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].discountNumber").value(hasItem(DEFAULT_DISCOUNT_NUMBER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coupon.getId().intValue()))
            .andExpect(jsonPath("$.minCartPrice").value(DEFAULT_MIN_CART_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.discountNumber").value(DEFAULT_DISCOUNT_NUMBER.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoupon() throws Exception {
        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Update the coupon
        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).get();
        // Disconnect from session so that the updates on updatedCoupon are not directly saved in db
        em.detach(updatedCoupon);
        updatedCoupon
            .minCartPrice(UPDATED_MIN_CART_PRICE)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountNumber(UPDATED_DISCOUNT_NUMBER);

        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoupon)))
            .andExpect(status().isOk());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getMinCartPrice()).isEqualTo(UPDATED_MIN_CART_PRICE);
        assertThat(testCoupon.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
        assertThat(testCoupon.getDiscountNumber()).isEqualTo(UPDATED_DISCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupon() throws Exception {
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Create the Coupon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeDelete = couponRepository.findAll().size();

        // Get the coupon
        restCouponMockMvc.perform(delete("/api/coupons/{id}", coupon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupon.class);
        Coupon coupon1 = new Coupon();
        coupon1.setId(1L);
        Coupon coupon2 = new Coupon();
        coupon2.setId(coupon1.getId());
        assertThat(coupon1).isEqualTo(coupon2);
        coupon2.setId(2L);
        assertThat(coupon1).isNotEqualTo(coupon2);
        coupon1.setId(null);
        assertThat(coupon1).isNotEqualTo(coupon2);
    }
}
