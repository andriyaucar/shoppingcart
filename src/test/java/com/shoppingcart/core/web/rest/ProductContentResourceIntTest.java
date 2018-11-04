package com.shoppingcart.core.web.rest;

import com.shoppingcart.core.ShoppingcartApp;

import com.shoppingcart.core.domain.ProductContent;
import com.shoppingcart.core.repository.ProductContentRepository;
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

/**
 * Test class for the ProductContentResource REST controller.
 *
 * @see ProductContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingcartApp.class)
public class ProductContentResourceIntTest {

    private static final String DEFAULT_PRODUCT_CONTENT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CONTENT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CONTENT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CONTENT_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductContentRepository productContentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductContentMockMvc;

    private ProductContent productContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductContentResource productContentResource = new ProductContentResource(productContentRepository);
        this.restProductContentMockMvc = MockMvcBuilders.standaloneSetup(productContentResource)
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
    public static ProductContent createEntity(EntityManager em) {
        ProductContent productContent = new ProductContent()
            .productContentKey(DEFAULT_PRODUCT_CONTENT_KEY)
            .productContentValue(DEFAULT_PRODUCT_CONTENT_VALUE);
        return productContent;
    }

    @Before
    public void initTest() {
        productContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductContent() throws Exception {
        int databaseSizeBeforeCreate = productContentRepository.findAll().size();

        // Create the ProductContent
        restProductContentMockMvc.perform(post("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productContent)))
            .andExpect(status().isCreated());

        // Validate the ProductContent in the database
        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductContent testProductContent = productContentList.get(productContentList.size() - 1);
        assertThat(testProductContent.getProductContentKey()).isEqualTo(DEFAULT_PRODUCT_CONTENT_KEY);
        assertThat(testProductContent.getProductContentValue()).isEqualTo(DEFAULT_PRODUCT_CONTENT_VALUE);
    }

    @Test
    @Transactional
    public void createProductContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productContentRepository.findAll().size();

        // Create the ProductContent with an existing ID
        productContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductContentMockMvc.perform(post("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productContent)))
            .andExpect(status().isBadRequest());

        // Validate the ProductContent in the database
        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductContentKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = productContentRepository.findAll().size();
        // set the field null
        productContent.setProductContentKey(null);

        // Create the ProductContent, which fails.

        restProductContentMockMvc.perform(post("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productContent)))
            .andExpect(status().isBadRequest());

        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductContentValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productContentRepository.findAll().size();
        // set the field null
        productContent.setProductContentValue(null);

        // Create the ProductContent, which fails.

        restProductContentMockMvc.perform(post("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productContent)))
            .andExpect(status().isBadRequest());

        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductContents() throws Exception {
        // Initialize the database
        productContentRepository.saveAndFlush(productContent);

        // Get all the productContentList
        restProductContentMockMvc.perform(get("/api/product-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].productContentKey").value(hasItem(DEFAULT_PRODUCT_CONTENT_KEY.toString())))
            .andExpect(jsonPath("$.[*].productContentValue").value(hasItem(DEFAULT_PRODUCT_CONTENT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductContent() throws Exception {
        // Initialize the database
        productContentRepository.saveAndFlush(productContent);

        // Get the productContent
        restProductContentMockMvc.perform(get("/api/product-contents/{id}", productContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productContent.getId().intValue()))
            .andExpect(jsonPath("$.productContentKey").value(DEFAULT_PRODUCT_CONTENT_KEY.toString()))
            .andExpect(jsonPath("$.productContentValue").value(DEFAULT_PRODUCT_CONTENT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductContent() throws Exception {
        // Get the productContent
        restProductContentMockMvc.perform(get("/api/product-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductContent() throws Exception {
        // Initialize the database
        productContentRepository.saveAndFlush(productContent);

        int databaseSizeBeforeUpdate = productContentRepository.findAll().size();

        // Update the productContent
        ProductContent updatedProductContent = productContentRepository.findById(productContent.getId()).get();
        // Disconnect from session so that the updates on updatedProductContent are not directly saved in db
        em.detach(updatedProductContent);
        updatedProductContent
            .productContentKey(UPDATED_PRODUCT_CONTENT_KEY)
            .productContentValue(UPDATED_PRODUCT_CONTENT_VALUE);

        restProductContentMockMvc.perform(put("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductContent)))
            .andExpect(status().isOk());

        // Validate the ProductContent in the database
        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeUpdate);
        ProductContent testProductContent = productContentList.get(productContentList.size() - 1);
        assertThat(testProductContent.getProductContentKey()).isEqualTo(UPDATED_PRODUCT_CONTENT_KEY);
        assertThat(testProductContent.getProductContentValue()).isEqualTo(UPDATED_PRODUCT_CONTENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductContent() throws Exception {
        int databaseSizeBeforeUpdate = productContentRepository.findAll().size();

        // Create the ProductContent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductContentMockMvc.perform(put("/api/product-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productContent)))
            .andExpect(status().isBadRequest());

        // Validate the ProductContent in the database
        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductContent() throws Exception {
        // Initialize the database
        productContentRepository.saveAndFlush(productContent);

        int databaseSizeBeforeDelete = productContentRepository.findAll().size();

        // Get the productContent
        restProductContentMockMvc.perform(delete("/api/product-contents/{id}", productContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductContent> productContentList = productContentRepository.findAll();
        assertThat(productContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductContent.class);
        ProductContent productContent1 = new ProductContent();
        productContent1.setId(1L);
        ProductContent productContent2 = new ProductContent();
        productContent2.setId(productContent1.getId());
        assertThat(productContent1).isEqualTo(productContent2);
        productContent2.setId(2L);
        assertThat(productContent1).isNotEqualTo(productContent2);
        productContent1.setId(null);
        assertThat(productContent1).isNotEqualTo(productContent2);
    }
}
