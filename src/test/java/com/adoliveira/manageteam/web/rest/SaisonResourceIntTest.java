package com.adoliveira.manageteam.web.rest;

import com.adoliveira.manageteam.ManageTeamApp;

import com.adoliveira.manageteam.domain.Saison;
import com.adoliveira.manageteam.repository.SaisonRepository;
import com.adoliveira.manageteam.service.SaisonService;
import com.adoliveira.manageteam.service.dto.SaisonDTO;
import com.adoliveira.manageteam.service.mapper.SaisonMapper;
import com.adoliveira.manageteam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

import static com.adoliveira.manageteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SaisonResource REST controller.
 *
 * @see SaisonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageTeamApp.class)
public class SaisonResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SaisonRepository saisonRepository;


    @Autowired
    private SaisonMapper saisonMapper;
    

    @Autowired
    private SaisonService saisonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaisonMockMvc;

    private Saison saison;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaisonResource saisonResource = new SaisonResource(saisonService);
        this.restSaisonMockMvc = MockMvcBuilders.standaloneSetup(saisonResource)
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
    public static Saison createEntity(EntityManager em) {
        Saison saison = new Saison()
            .nom(DEFAULT_NOM)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return saison;
    }

    @Before
    public void initTest() {
        saison = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaison() throws Exception {
        int databaseSizeBeforeCreate = saisonRepository.findAll().size();

        // Create the Saison
        SaisonDTO saisonDTO = saisonMapper.toDto(saison);
        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saisonDTO)))
            .andExpect(status().isCreated());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeCreate + 1);
        Saison testSaison = saisonList.get(saisonList.size() - 1);
        assertThat(testSaison.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSaison.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testSaison.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createSaisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saisonRepository.findAll().size();

        // Create the Saison with an existing ID
        saison.setId(1L);
        SaisonDTO saisonDTO = saisonMapper.toDto(saison);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = saisonRepository.findAll().size();
        // set the field null
        saison.setNom(null);

        // Create the Saison, which fails.
        SaisonDTO saisonDTO = saisonMapper.toDto(saison);

        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saisonDTO)))
            .andExpect(status().isBadRequest());

        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaisons() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        // Get all the saisonList
        restSaisonMockMvc.perform(get("/api/saisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saison.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    

    @Test
    @Transactional
    public void getSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        // Get the saison
        restSaisonMockMvc.perform(get("/api/saisons/{id}", saison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saison.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSaison() throws Exception {
        // Get the saison
        restSaisonMockMvc.perform(get("/api/saisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        int databaseSizeBeforeUpdate = saisonRepository.findAll().size();

        // Update the saison
        Saison updatedSaison = saisonRepository.findById(saison.getId()).get();
        // Disconnect from session so that the updates on updatedSaison are not directly saved in db
        em.detach(updatedSaison);
        updatedSaison
            .nom(UPDATED_NOM)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        SaisonDTO saisonDTO = saisonMapper.toDto(updatedSaison);

        restSaisonMockMvc.perform(put("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saisonDTO)))
            .andExpect(status().isOk());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeUpdate);
        Saison testSaison = saisonList.get(saisonList.size() - 1);
        assertThat(testSaison.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSaison.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testSaison.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingSaison() throws Exception {
        int databaseSizeBeforeUpdate = saisonRepository.findAll().size();

        // Create the Saison
        SaisonDTO saisonDTO = saisonMapper.toDto(saison);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaisonMockMvc.perform(put("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        int databaseSizeBeforeDelete = saisonRepository.findAll().size();

        // Get the saison
        restSaisonMockMvc.perform(delete("/api/saisons/{id}", saison.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saison.class);
        Saison saison1 = new Saison();
        saison1.setId(1L);
        Saison saison2 = new Saison();
        saison2.setId(saison1.getId());
        assertThat(saison1).isEqualTo(saison2);
        saison2.setId(2L);
        assertThat(saison1).isNotEqualTo(saison2);
        saison1.setId(null);
        assertThat(saison1).isNotEqualTo(saison2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaisonDTO.class);
        SaisonDTO saisonDTO1 = new SaisonDTO();
        saisonDTO1.setId(1L);
        SaisonDTO saisonDTO2 = new SaisonDTO();
        assertThat(saisonDTO1).isNotEqualTo(saisonDTO2);
        saisonDTO2.setId(saisonDTO1.getId());
        assertThat(saisonDTO1).isEqualTo(saisonDTO2);
        saisonDTO2.setId(2L);
        assertThat(saisonDTO1).isNotEqualTo(saisonDTO2);
        saisonDTO1.setId(null);
        assertThat(saisonDTO1).isNotEqualTo(saisonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saisonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saisonMapper.fromId(null)).isNull();
    }
}
