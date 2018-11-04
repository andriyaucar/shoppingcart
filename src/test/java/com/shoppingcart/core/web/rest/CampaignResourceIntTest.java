package com.shoppingcart.core.web.rest;

import com.shoppingcart.core.ShoppingcartApp;

import com.shoppingcart.core.domain.Campaign;
import com.shoppingcart.core.repository.CampaignRepository;
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
 * Test class for the CampaignResource REST controller.
 *
 * @see CampaignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingcartApp.class)
public class CampaignResourceIntTest {

    private static final String DEFAULT_CAMPAIGN_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT_NUMBER = 1D;
    private static final Double UPDATED_DISCOUNT_NUMBER = 2D;

    private static final DiscountType DEFAULT_DISCOUNT_TYPE = DiscountType.RATE;
    private static final DiscountType UPDATED_DISCOUNT_TYPE = DiscountType.AMOUNT;

    private static final Long DEFAULT_MIN_PRODUCT_COUNT = 1L;
    private static final Long UPDATED_MIN_PRODUCT_COUNT = 2L;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampaignMockMvc;

    private Campaign campaign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignResource campaignResource = new CampaignResource(campaignRepository);
        this.restCampaignMockMvc = MockMvcBuilders.standaloneSetup(campaignResource)
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
    public static Campaign createEntity(EntityManager em) {
        Campaign campaign = new Campaign()
            .campaignTitle(DEFAULT_CAMPAIGN_TITLE)
            .discountNumber(DEFAULT_DISCOUNT_NUMBER)
            .discountType(DEFAULT_DISCOUNT_TYPE)
            .minProductCount(DEFAULT_MIN_PRODUCT_COUNT);
        return campaign;
    }

    @Before
    public void initTest() {
        campaign = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaign() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign
        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isCreated());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate + 1);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getCampaignTitle()).isEqualTo(DEFAULT_CAMPAIGN_TITLE);
        assertThat(testCampaign.getDiscountNumber()).isEqualTo(DEFAULT_DISCOUNT_NUMBER);
        assertThat(testCampaign.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
        assertThat(testCampaign.getMinProductCount()).isEqualTo(DEFAULT_MIN_PRODUCT_COUNT);
    }

    @Test
    @Transactional
    public void createCampaignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign with an existing ID
        campaign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCampaignTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setCampaignTitle(null);

        // Create the Campaign, which fails.

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setDiscountNumber(null);

        // Create the Campaign, which fails.

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setDiscountType(null);

        // Create the Campaign, which fails.

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinProductCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setMinProductCount(null);

        // Create the Campaign, which fails.

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaigns() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignTitle").value(hasItem(DEFAULT_CAMPAIGN_TITLE.toString())))
            .andExpect(jsonPath("$.[*].discountNumber").value(hasItem(DEFAULT_DISCOUNT_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minProductCount").value(hasItem(DEFAULT_MIN_PRODUCT_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", campaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaign.getId().intValue()))
            .andExpect(jsonPath("$.campaignTitle").value(DEFAULT_CAMPAIGN_TITLE.toString()))
            .andExpect(jsonPath("$.discountNumber").value(DEFAULT_DISCOUNT_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.minProductCount").value(DEFAULT_MIN_PRODUCT_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaign() throws Exception {
        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Update the campaign
        Campaign updatedCampaign = campaignRepository.findById(campaign.getId()).get();
        // Disconnect from session so that the updates on updatedCampaign are not directly saved in db
        em.detach(updatedCampaign);
        updatedCampaign
            .campaignTitle(UPDATED_CAMPAIGN_TITLE)
            .discountNumber(UPDATED_DISCOUNT_NUMBER)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .minProductCount(UPDATED_MIN_PRODUCT_COUNT);

        restCampaignMockMvc.perform(put("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampaign)))
            .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getCampaignTitle()).isEqualTo(UPDATED_CAMPAIGN_TITLE);
        assertThat(testCampaign.getDiscountNumber()).isEqualTo(UPDATED_DISCOUNT_NUMBER);
        assertThat(testCampaign.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
        assertThat(testCampaign.getMinProductCount()).isEqualTo(UPDATED_MIN_PRODUCT_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaign() throws Exception {
        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Create the Campaign

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignMockMvc.perform(put("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaign)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeDelete = campaignRepository.findAll().size();

        // Get the campaign
        restCampaignMockMvc.perform(delete("/api/campaigns/{id}", campaign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campaign.class);
        Campaign campaign1 = new Campaign();
        campaign1.setId(1L);
        Campaign campaign2 = new Campaign();
        campaign2.setId(campaign1.getId());
        assertThat(campaign1).isEqualTo(campaign2);
        campaign2.setId(2L);
        assertThat(campaign1).isNotEqualTo(campaign2);
        campaign1.setId(null);
        assertThat(campaign1).isNotEqualTo(campaign2);
    }
}
