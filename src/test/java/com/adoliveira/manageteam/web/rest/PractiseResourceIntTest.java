package com.adoliveira.manageteam.web.rest;

import com.adoliveira.manageteam.ManageTeamApp;

import com.adoliveira.manageteam.domain.Practise;
import com.adoliveira.manageteam.repository.PractiseRepository;
import com.adoliveira.manageteam.service.PractiseService;
import com.adoliveira.manageteam.service.dto.PractiseDTO;
import com.adoliveira.manageteam.service.mapper.PractiseMapper;
import com.adoliveira.manageteam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

import static com.adoliveira.manageteam.web.rest.TestUtil.sameInstant;
import static com.adoliveira.manageteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PractiseResource REST controller.
 *
 * @see PractiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageTeamApp.class)
public class PractiseResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PractiseRepository practiseRepository;
    @Mock
    private PractiseRepository practiseRepositoryMock;

    @Autowired
    private PractiseMapper practiseMapper;
    
    @Mock
    private PractiseService practiseServiceMock;

    @Autowired
    private PractiseService practiseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPractiseMockMvc;

    private Practise practise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PractiseResource practiseResource = new PractiseResource(practiseService);
        this.restPractiseMockMvc = MockMvcBuilders.standaloneSetup(practiseResource)
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
    public static Practise createEntity(EntityManager em) {
        Practise practise = new Practise()
            .date(DEFAULT_DATE);
        return practise;
    }

    @Before
    public void initTest() {
        practise = createEntity(em);
    }

    @Test
    @Transactional
    public void createPractise() throws Exception {
        int databaseSizeBeforeCreate = practiseRepository.findAll().size();

        // Create the Practise
        PractiseDTO practiseDTO = practiseMapper.toDto(practise);
        restPractiseMockMvc.perform(post("/api/practises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(practiseDTO)))
            .andExpect(status().isCreated());

        // Validate the Practise in the database
        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeCreate + 1);
        Practise testPractise = practiseList.get(practiseList.size() - 1);
        assertThat(testPractise.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPractiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = practiseRepository.findAll().size();

        // Create the Practise with an existing ID
        practise.setId(1L);
        PractiseDTO practiseDTO = practiseMapper.toDto(practise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPractiseMockMvc.perform(post("/api/practises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(practiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Practise in the database
        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = practiseRepository.findAll().size();
        // set the field null
        practise.setDate(null);

        // Create the Practise, which fails.
        PractiseDTO practiseDTO = practiseMapper.toDto(practise);

        restPractiseMockMvc.perform(post("/api/practises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(practiseDTO)))
            .andExpect(status().isBadRequest());

        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPractises() throws Exception {
        // Initialize the database
        practiseRepository.saveAndFlush(practise);

        // Get all the practiseList
        restPractiseMockMvc.perform(get("/api/practises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(practise.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    public void getAllPractisesWithEagerRelationshipsIsEnabled() throws Exception {
        PractiseResource practiseResource = new PractiseResource(practiseServiceMock);
        when(practiseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPractiseMockMvc = MockMvcBuilders.standaloneSetup(practiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPractiseMockMvc.perform(get("/api/practises?eagerload=true"))
        .andExpect(status().isOk());

        verify(practiseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPractisesWithEagerRelationshipsIsNotEnabled() throws Exception {
        PractiseResource practiseResource = new PractiseResource(practiseServiceMock);
            when(practiseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPractiseMockMvc = MockMvcBuilders.standaloneSetup(practiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPractiseMockMvc.perform(get("/api/practises?eagerload=true"))
        .andExpect(status().isOk());

            verify(practiseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPractise() throws Exception {
        // Initialize the database
        practiseRepository.saveAndFlush(practise);

        // Get the practise
        restPractiseMockMvc.perform(get("/api/practises/{id}", practise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(practise.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingPractise() throws Exception {
        // Get the practise
        restPractiseMockMvc.perform(get("/api/practises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePractise() throws Exception {
        // Initialize the database
        practiseRepository.saveAndFlush(practise);

        int databaseSizeBeforeUpdate = practiseRepository.findAll().size();

        // Update the practise
        Practise updatedPractise = practiseRepository.findById(practise.getId()).get();
        // Disconnect from session so that the updates on updatedPractise are not directly saved in db
        em.detach(updatedPractise);
        updatedPractise
            .date(UPDATED_DATE);
        PractiseDTO practiseDTO = practiseMapper.toDto(updatedPractise);

        restPractiseMockMvc.perform(put("/api/practises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(practiseDTO)))
            .andExpect(status().isOk());

        // Validate the Practise in the database
        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeUpdate);
        Practise testPractise = practiseList.get(practiseList.size() - 1);
        assertThat(testPractise.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPractise() throws Exception {
        int databaseSizeBeforeUpdate = practiseRepository.findAll().size();

        // Create the Practise
        PractiseDTO practiseDTO = practiseMapper.toDto(practise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPractiseMockMvc.perform(put("/api/practises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(practiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Practise in the database
        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePractise() throws Exception {
        // Initialize the database
        practiseRepository.saveAndFlush(practise);

        int databaseSizeBeforeDelete = practiseRepository.findAll().size();

        // Get the practise
        restPractiseMockMvc.perform(delete("/api/practises/{id}", practise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Practise> practiseList = practiseRepository.findAll();
        assertThat(practiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Practise.class);
        Practise practise1 = new Practise();
        practise1.setId(1L);
        Practise practise2 = new Practise();
        practise2.setId(practise1.getId());
        assertThat(practise1).isEqualTo(practise2);
        practise2.setId(2L);
        assertThat(practise1).isNotEqualTo(practise2);
        practise1.setId(null);
        assertThat(practise1).isNotEqualTo(practise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PractiseDTO.class);
        PractiseDTO practiseDTO1 = new PractiseDTO();
        practiseDTO1.setId(1L);
        PractiseDTO practiseDTO2 = new PractiseDTO();
        assertThat(practiseDTO1).isNotEqualTo(practiseDTO2);
        practiseDTO2.setId(practiseDTO1.getId());
        assertThat(practiseDTO1).isEqualTo(practiseDTO2);
        practiseDTO2.setId(2L);
        assertThat(practiseDTO1).isNotEqualTo(practiseDTO2);
        practiseDTO1.setId(null);
        assertThat(practiseDTO1).isNotEqualTo(practiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(practiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(practiseMapper.fromId(null)).isNull();
    }
}
