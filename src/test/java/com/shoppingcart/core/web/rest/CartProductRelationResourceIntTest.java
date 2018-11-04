package com.shoppingcart.core.web.rest;

import com.shoppingcart.core.ShoppingcartApp;

import com.shoppingcart.core.domain.CartProductRelation;
import com.shoppingcart.core.repository.CartProductRelationRepository;
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

import com.shoppingcart.core.domain.enumeration.ProductStatus;
/**
 * Test class for the CartProductRelationResource REST controller.
 *
 * @see CartProductRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingcartApp.class)
public class CartProductRelationResourceIntTest {

    private static final ProductStatus DEFAULT_PRODUCT_STATUS = ProductStatus.RESOLVED;
    private static final ProductStatus UPDATED_PRODUCT_STATUS = ProductStatus.SOLD;

    private static final Long DEFAULT_PRODUCT_COUNT = 1L;
    private static final Long UPDATED_PRODUCT_COUNT = 2L;

    private static final Long DEFAULT_COUPON_ID = 1L;
    private static final Long UPDATED_COUPON_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    @Autowired
    private CartProductRelationRepository cartProductRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartProductRelationMockMvc;

    private CartProductRelation cartProductRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartProductRelationResource cartProductRelationResource = new CartProductRelationResource(cartProductRelationRepository);
        this.restCartProductRelationMockMvc = MockMvcBuilders.standaloneSetup(cartProductRelationResource)
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
    public static CartProductRelation createEntity(EntityManager em) {
        CartProductRelation cartProductRelation = new CartProductRelation()
            .productStatus(DEFAULT_PRODUCT_STATUS)
            .productCount(DEFAULT_PRODUCT_COUNT)
            .couponId(DEFAULT_COUPON_ID)
            .categoryId(DEFAULT_CATEGORY_ID);
        return cartProductRelation;
    }

    @Before
    public void initTest() {
        cartProductRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartProductRelation() throws Exception {
        int databaseSizeBeforeCreate = cartProductRelationRepository.findAll().size();

        // Create the CartProductRelation
        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isCreated());

        // Validate the CartProductRelation in the database
        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeCreate + 1);
        CartProductRelation testCartProductRelation = cartProductRelationList.get(cartProductRelationList.size() - 1);
        assertThat(testCartProductRelation.getProductStatus()).isEqualTo(DEFAULT_PRODUCT_STATUS);
        assertThat(testCartProductRelation.getProductCount()).isEqualTo(DEFAULT_PRODUCT_COUNT);
        assertThat(testCartProductRelation.getCouponId()).isEqualTo(DEFAULT_COUPON_ID);
        assertThat(testCartProductRelation.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void createCartProductRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartProductRelationRepository.findAll().size();

        // Create the CartProductRelation with an existing ID
        cartProductRelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CartProductRelation in the database
        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartProductRelationRepository.findAll().size();
        // set the field null
        cartProductRelation.setProductStatus(null);

        // Create the CartProductRelation, which fails.

        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartProductRelationRepository.findAll().size();
        // set the field null
        cartProductRelation.setProductCount(null);

        // Create the CartProductRelation, which fails.

        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartProductRelationRepository.findAll().size();
        // set the field null
        cartProductRelation.setCouponId(null);

        // Create the CartProductRelation, which fails.

        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartProductRelationRepository.findAll().size();
        // set the field null
        cartProductRelation.setCategoryId(null);

        // Create the CartProductRelation, which fails.

        restCartProductRelationMockMvc.perform(post("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartProductRelations() throws Exception {
        // Initialize the database
        cartProductRelationRepository.saveAndFlush(cartProductRelation);

        // Get all the cartProductRelationList
        restCartProductRelationMockMvc.perform(get("/api/cart-product-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartProductRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].productCount").value(hasItem(DEFAULT_PRODUCT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].couponId").value(hasItem(DEFAULT_COUPON_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCartProductRelation() throws Exception {
        // Initialize the database
        cartProductRelationRepository.saveAndFlush(cartProductRelation);

        // Get the cartProductRelation
        restCartProductRelationMockMvc.perform(get("/api/cart-product-relations/{id}", cartProductRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartProductRelation.getId().intValue()))
            .andExpect(jsonPath("$.productStatus").value(DEFAULT_PRODUCT_STATUS.toString()))
            .andExpect(jsonPath("$.productCount").value(DEFAULT_PRODUCT_COUNT.intValue()))
            .andExpect(jsonPath("$.couponId").value(DEFAULT_COUPON_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCartProductRelation() throws Exception {
        // Get the cartProductRelation
        restCartProductRelationMockMvc.perform(get("/api/cart-product-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartProductRelation() throws Exception {
        // Initialize the database
        cartProductRelationRepository.saveAndFlush(cartProductRelation);

        int databaseSizeBeforeUpdate = cartProductRelationRepository.findAll().size();

        // Update the cartProductRelation
        CartProductRelation updatedCartProductRelation = cartProductRelationRepository.findById(cartProductRelation.getId()).get();
        // Disconnect from session so that the updates on updatedCartProductRelation are not directly saved in db
        em.detach(updatedCartProductRelation);
        updatedCartProductRelation
            .productStatus(UPDATED_PRODUCT_STATUS)
            .productCount(UPDATED_PRODUCT_COUNT)
            .couponId(UPDATED_COUPON_ID)
            .categoryId(UPDATED_CATEGORY_ID);

        restCartProductRelationMockMvc.perform(put("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartProductRelation)))
            .andExpect(status().isOk());

        // Validate the CartProductRelation in the database
        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeUpdate);
        CartProductRelation testCartProductRelation = cartProductRelationList.get(cartProductRelationList.size() - 1);
        assertThat(testCartProductRelation.getProductStatus()).isEqualTo(UPDATED_PRODUCT_STATUS);
        assertThat(testCartProductRelation.getProductCount()).isEqualTo(UPDATED_PRODUCT_COUNT);
        assertThat(testCartProductRelation.getCouponId()).isEqualTo(UPDATED_COUPON_ID);
        assertThat(testCartProductRelation.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCartProductRelation() throws Exception {
        int databaseSizeBeforeUpdate = cartProductRelationRepository.findAll().size();

        // Create the CartProductRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartProductRelationMockMvc.perform(put("/api/cart-product-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartProductRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CartProductRelation in the database
        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartProductRelation() throws Exception {
        // Initialize the database
        cartProductRelationRepository.saveAndFlush(cartProductRelation);

        int databaseSizeBeforeDelete = cartProductRelationRepository.findAll().size();

        // Get the cartProductRelation
        restCartProductRelationMockMvc.perform(delete("/api/cart-product-relations/{id}", cartProductRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CartProductRelation> cartProductRelationList = cartProductRelationRepository.findAll();
        assertThat(cartProductRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartProductRelation.class);
        CartProductRelation cartProductRelation1 = new CartProductRelation();
        cartProductRelation1.setId(1L);
        CartProductRelation cartProductRelation2 = new CartProductRelation();
        cartProductRelation2.setId(cartProductRelation1.getId());
        assertThat(cartProductRelation1).isEqualTo(cartProductRelation2);
        cartProductRelation2.setId(2L);
        assertThat(cartProductRelation1).isNotEqualTo(cartProductRelation2);
        cartProductRelation1.setId(null);
        assertThat(cartProductRelation1).isNotEqualTo(cartProductRelation2);
    }
}
