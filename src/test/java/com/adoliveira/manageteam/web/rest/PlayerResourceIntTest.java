package com.adoliveira.manageteam.web.rest;

import com.adoliveira.manageteam.ManageTeamApp;

import com.adoliveira.manageteam.domain.Player;
import com.adoliveira.manageteam.repository.PlayerRepository;
import com.adoliveira.manageteam.service.PlayerService;
import com.adoliveira.manageteam.service.dto.PlayerDTO;
import com.adoliveira.manageteam.service.mapper.PlayerMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the PlayerResource REST controller.
 *
 * @see PlayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageTeamApp.class)
public class PlayerResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_HIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ANNEE_ARRIVEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE_ARRIVEE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_POIDS = 1D;
    private static final Double UPDATED_POIDS = 2D;

    private static final Integer DEFAULT_TAILLE = 1;
    private static final Integer UPDATED_TAILLE = 2;

    private static final Integer DEFAULT_NUM_MAILLOT = 1;
    private static final Integer UPDATED_NUM_MAILLOT = 2;

    private static final String DEFAULT_NUM_LICENCE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_LICENCE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG_PROFILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG_PROFILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMG_PROFILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_PROFILE_CONTENT_TYPE = "image/png";

    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private PlayerMapper playerMapper;
    

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlayerMockMvc;

    private Player player;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerResource playerResource = new PlayerResource(playerService);
        this.restPlayerMockMvc = MockMvcBuilders.standaloneSetup(playerResource)
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
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .anneeArrivee(DEFAULT_ANNEE_ARRIVEE)
            .poids(DEFAULT_POIDS)
            .taille(DEFAULT_TAILLE)
            .numMaillot(DEFAULT_NUM_MAILLOT)
            .numLicence(DEFAULT_NUM_LICENCE)
            .imgProfile(DEFAULT_IMG_PROFILE)
            .imgProfileContentType(DEFAULT_IMG_PROFILE_CONTENT_TYPE);
        return player;
    }

    @Before
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);
        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPlayer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPlayer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPlayer.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testPlayer.getAnneeArrivee()).isEqualTo(DEFAULT_ANNEE_ARRIVEE);
        assertThat(testPlayer.getPoids()).isEqualTo(DEFAULT_POIDS);
        assertThat(testPlayer.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testPlayer.getNumMaillot()).isEqualTo(DEFAULT_NUM_MAILLOT);
        assertThat(testPlayer.getNumLicence()).isEqualTo(DEFAULT_NUM_LICENCE);
        assertThat(testPlayer.getImgProfile()).isEqualTo(DEFAULT_IMG_PROFILE);
        assertThat(testPlayer.getImgProfileContentType()).isEqualTo(DEFAULT_IMG_PROFILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // Create the Player with an existing ID
        player.setId(1L);
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setFirstName(null);

        // Create the Player, which fails.
        PlayerDTO playerDTO = playerMapper.toDto(player);

        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setLastName(null);

        // Create the Player, which fails.
        PlayerDTO playerDTO = playerMapper.toDto(player);

        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc.perform(get("/api/players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].anneeArrivee").value(hasItem(DEFAULT_ANNEE_ARRIVEE.toString())))
            .andExpect(jsonPath("$.[*].poids").value(hasItem(DEFAULT_POIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE)))
            .andExpect(jsonPath("$.[*].numMaillot").value(hasItem(DEFAULT_NUM_MAILLOT)))
            .andExpect(jsonPath("$.[*].numLicence").value(hasItem(DEFAULT_NUM_LICENCE.toString())))
            .andExpect(jsonPath("$.[*].imgProfileContentType").value(hasItem(DEFAULT_IMG_PROFILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imgProfile").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_PROFILE))));
    }
    

    @Test
    @Transactional
    public void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.anneeArrivee").value(DEFAULT_ANNEE_ARRIVEE.toString()))
            .andExpect(jsonPath("$.poids").value(DEFAULT_POIDS.doubleValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE))
            .andExpect(jsonPath("$.numMaillot").value(DEFAULT_NUM_MAILLOT))
            .andExpect(jsonPath("$.numLicence").value(DEFAULT_NUM_LICENCE.toString()))
            .andExpect(jsonPath("$.imgProfileContentType").value(DEFAULT_IMG_PROFILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imgProfile").value(Base64Utils.encodeToString(DEFAULT_IMG_PROFILE)));
    }
    @Test
    @Transactional
    public void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        // Disconnect from session so that the updates on updatedPlayer are not directly saved in db
        em.detach(updatedPlayer);
        updatedPlayer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .anneeArrivee(UPDATED_ANNEE_ARRIVEE)
            .poids(UPDATED_POIDS)
            .taille(UPDATED_TAILLE)
            .numMaillot(UPDATED_NUM_MAILLOT)
            .numLicence(UPDATED_NUM_LICENCE)
            .imgProfile(UPDATED_IMG_PROFILE)
            .imgProfileContentType(UPDATED_IMG_PROFILE_CONTENT_TYPE);
        PlayerDTO playerDTO = playerMapper.toDto(updatedPlayer);

        restPlayerMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPlayer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPlayer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPlayer.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testPlayer.getAnneeArrivee()).isEqualTo(UPDATED_ANNEE_ARRIVEE);
        assertThat(testPlayer.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testPlayer.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testPlayer.getNumMaillot()).isEqualTo(UPDATED_NUM_MAILLOT);
        assertThat(testPlayer.getNumLicence()).isEqualTo(UPDATED_NUM_LICENCE);
        assertThat(testPlayer.getImgProfile()).isEqualTo(UPDATED_IMG_PROFILE);
        assertThat(testPlayer.getImgProfileContentType()).isEqualTo(UPDATED_IMG_PROFILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlayerMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Get the player
        restPlayerMockMvc.perform(delete("/api/players/{id}", player.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Player.class);
        Player player1 = new Player();
        player1.setId(1L);
        Player player2 = new Player();
        player2.setId(player1.getId());
        assertThat(player1).isEqualTo(player2);
        player2.setId(2L);
        assertThat(player1).isNotEqualTo(player2);
        player1.setId(null);
        assertThat(player1).isNotEqualTo(player2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerDTO.class);
        PlayerDTO playerDTO1 = new PlayerDTO();
        playerDTO1.setId(1L);
        PlayerDTO playerDTO2 = new PlayerDTO();
        assertThat(playerDTO1).isNotEqualTo(playerDTO2);
        playerDTO2.setId(playerDTO1.getId());
        assertThat(playerDTO1).isEqualTo(playerDTO2);
        playerDTO2.setId(2L);
        assertThat(playerDTO1).isNotEqualTo(playerDTO2);
        playerDTO1.setId(null);
        assertThat(playerDTO1).isNotEqualTo(playerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(playerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(playerMapper.fromId(null)).isNull();
    }
}
