package com.adoliveira.manageteam.web.rest;

import com.adoliveira.manageteam.ManageTeamApp;

import com.adoliveira.manageteam.domain.Championnat;
import com.adoliveira.manageteam.repository.ChampionnatRepository;
import com.adoliveira.manageteam.service.ChampionnatService;
import com.adoliveira.manageteam.service.dto.ChampionnatDTO;
import com.adoliveira.manageteam.service.mapper.ChampionnatMapper;
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
import java.util.List;
import java.util.ArrayList;

import static com.adoliveira.manageteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChampionnatResource REST controller.
 *
 * @see ChampionnatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageTeamApp.class)
public class ChampionnatResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT_VICTOIRE = 1;
    private static final Integer UPDATED_POINT_VICTOIRE = 2;

    private static final Integer DEFAULT_POINT_NULL = 1;
    private static final Integer UPDATED_POINT_NULL = 2;

    private static final Integer DEFAULT_POINT_DEFAITE = 1;
    private static final Integer UPDATED_POINT_DEFAITE = 2;

    private static final Integer DEFAULT_POINT_FORFAIT = 1;
    private static final Integer UPDATED_POINT_FORFAIT = 2;

    @Autowired
    private ChampionnatRepository championnatRepository;
    @Mock
    private ChampionnatRepository championnatRepositoryMock;

    @Autowired
    private ChampionnatMapper championnatMapper;
    
    @Mock
    private ChampionnatService championnatServiceMock;

    @Autowired
    private ChampionnatService championnatService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChampionnatMockMvc;

    private Championnat championnat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChampionnatResource championnatResource = new ChampionnatResource(championnatService);
        this.restChampionnatMockMvc = MockMvcBuilders.standaloneSetup(championnatResource)
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
    public static Championnat createEntity(EntityManager em) {
        Championnat championnat = new Championnat()
            .nom(DEFAULT_NOM)
            .pointVictoire(DEFAULT_POINT_VICTOIRE)
            .pointNull(DEFAULT_POINT_NULL)
            .pointDefaite(DEFAULT_POINT_DEFAITE)
            .pointForfait(DEFAULT_POINT_FORFAIT);
        return championnat;
    }

    @Before
    public void initTest() {
        championnat = createEntity(em);
    }

    @Test
    @Transactional
    public void createChampionnat() throws Exception {
        int databaseSizeBeforeCreate = championnatRepository.findAll().size();

        // Create the Championnat
        ChampionnatDTO championnatDTO = championnatMapper.toDto(championnat);
        restChampionnatMockMvc.perform(post("/api/championnats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(championnatDTO)))
            .andExpect(status().isCreated());

        // Validate the Championnat in the database
        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeCreate + 1);
        Championnat testChampionnat = championnatList.get(championnatList.size() - 1);
        assertThat(testChampionnat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testChampionnat.getPointVictoire()).isEqualTo(DEFAULT_POINT_VICTOIRE);
        assertThat(testChampionnat.getPointNull()).isEqualTo(DEFAULT_POINT_NULL);
        assertThat(testChampionnat.getPointDefaite()).isEqualTo(DEFAULT_POINT_DEFAITE);
        assertThat(testChampionnat.getPointForfait()).isEqualTo(DEFAULT_POINT_FORFAIT);
    }

    @Test
    @Transactional
    public void createChampionnatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = championnatRepository.findAll().size();

        // Create the Championnat with an existing ID
        championnat.setId(1L);
        ChampionnatDTO championnatDTO = championnatMapper.toDto(championnat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChampionnatMockMvc.perform(post("/api/championnats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(championnatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Championnat in the database
        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = championnatRepository.findAll().size();
        // set the field null
        championnat.setNom(null);

        // Create the Championnat, which fails.
        ChampionnatDTO championnatDTO = championnatMapper.toDto(championnat);

        restChampionnatMockMvc.perform(post("/api/championnats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(championnatDTO)))
            .andExpect(status().isBadRequest());

        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChampionnats() throws Exception {
        // Initialize the database
        championnatRepository.saveAndFlush(championnat);

        // Get all the championnatList
        restChampionnatMockMvc.perform(get("/api/championnats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(championnat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].pointVictoire").value(hasItem(DEFAULT_POINT_VICTOIRE)))
            .andExpect(jsonPath("$.[*].pointNull").value(hasItem(DEFAULT_POINT_NULL)))
            .andExpect(jsonPath("$.[*].pointDefaite").value(hasItem(DEFAULT_POINT_DEFAITE)))
            .andExpect(jsonPath("$.[*].pointForfait").value(hasItem(DEFAULT_POINT_FORFAIT)));
    }
    
    public void getAllChampionnatsWithEagerRelationshipsIsEnabled() throws Exception {
        ChampionnatResource championnatResource = new ChampionnatResource(championnatServiceMock);
        when(championnatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restChampionnatMockMvc = MockMvcBuilders.standaloneSetup(championnatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChampionnatMockMvc.perform(get("/api/championnats?eagerload=true"))
        .andExpect(status().isOk());

        verify(championnatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllChampionnatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChampionnatResource championnatResource = new ChampionnatResource(championnatServiceMock);
            when(championnatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restChampionnatMockMvc = MockMvcBuilders.standaloneSetup(championnatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChampionnatMockMvc.perform(get("/api/championnats?eagerload=true"))
        .andExpect(status().isOk());

            verify(championnatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChampionnat() throws Exception {
        // Initialize the database
        championnatRepository.saveAndFlush(championnat);

        // Get the championnat
        restChampionnatMockMvc.perform(get("/api/championnats/{id}", championnat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(championnat.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.pointVictoire").value(DEFAULT_POINT_VICTOIRE))
            .andExpect(jsonPath("$.pointNull").value(DEFAULT_POINT_NULL))
            .andExpect(jsonPath("$.pointDefaite").value(DEFAULT_POINT_DEFAITE))
            .andExpect(jsonPath("$.pointForfait").value(DEFAULT_POINT_FORFAIT));
    }
    @Test
    @Transactional
    public void getNonExistingChampionnat() throws Exception {
        // Get the championnat
        restChampionnatMockMvc.perform(get("/api/championnats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChampionnat() throws Exception {
        // Initialize the database
        championnatRepository.saveAndFlush(championnat);

        int databaseSizeBeforeUpdate = championnatRepository.findAll().size();

        // Update the championnat
        Championnat updatedChampionnat = championnatRepository.findById(championnat.getId()).get();
        // Disconnect from session so that the updates on updatedChampionnat are not directly saved in db
        em.detach(updatedChampionnat);
        updatedChampionnat
            .nom(UPDATED_NOM)
            .pointVictoire(UPDATED_POINT_VICTOIRE)
            .pointNull(UPDATED_POINT_NULL)
            .pointDefaite(UPDATED_POINT_DEFAITE)
            .pointForfait(UPDATED_POINT_FORFAIT);
        ChampionnatDTO championnatDTO = championnatMapper.toDto(updatedChampionnat);

        restChampionnatMockMvc.perform(put("/api/championnats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(championnatDTO)))
            .andExpect(status().isOk());

        // Validate the Championnat in the database
        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeUpdate);
        Championnat testChampionnat = championnatList.get(championnatList.size() - 1);
        assertThat(testChampionnat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testChampionnat.getPointVictoire()).isEqualTo(UPDATED_POINT_VICTOIRE);
        assertThat(testChampionnat.getPointNull()).isEqualTo(UPDATED_POINT_NULL);
        assertThat(testChampionnat.getPointDefaite()).isEqualTo(UPDATED_POINT_DEFAITE);
        assertThat(testChampionnat.getPointForfait()).isEqualTo(UPDATED_POINT_FORFAIT);
    }

    @Test
    @Transactional
    public void updateNonExistingChampionnat() throws Exception {
        int databaseSizeBeforeUpdate = championnatRepository.findAll().size();

        // Create the Championnat
        ChampionnatDTO championnatDTO = championnatMapper.toDto(championnat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChampionnatMockMvc.perform(put("/api/championnats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(championnatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Championnat in the database
        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChampionnat() throws Exception {
        // Initialize the database
        championnatRepository.saveAndFlush(championnat);

        int databaseSizeBeforeDelete = championnatRepository.findAll().size();

        // Get the championnat
        restChampionnatMockMvc.perform(delete("/api/championnats/{id}", championnat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Championnat> championnatList = championnatRepository.findAll();
        assertThat(championnatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Championnat.class);
        Championnat championnat1 = new Championnat();
        championnat1.setId(1L);
        Championnat championnat2 = new Championnat();
        championnat2.setId(championnat1.getId());
        assertThat(championnat1).isEqualTo(championnat2);
        championnat2.setId(2L);
        assertThat(championnat1).isNotEqualTo(championnat2);
        championnat1.setId(null);
        assertThat(championnat1).isNotEqualTo(championnat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChampionnatDTO.class);
        ChampionnatDTO championnatDTO1 = new ChampionnatDTO();
        championnatDTO1.setId(1L);
        ChampionnatDTO championnatDTO2 = new ChampionnatDTO();
        assertThat(championnatDTO1).isNotEqualTo(championnatDTO2);
        championnatDTO2.setId(championnatDTO1.getId());
        assertThat(championnatDTO1).isEqualTo(championnatDTO2);
        championnatDTO2.setId(2L);
        assertThat(championnatDTO1).isNotEqualTo(championnatDTO2);
        championnatDTO1.setId(null);
        assertThat(championnatDTO1).isNotEqualTo(championnatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(championnatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(championnatMapper.fromId(null)).isNull();
    }
}
