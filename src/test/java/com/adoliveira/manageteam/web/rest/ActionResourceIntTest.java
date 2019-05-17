package com.adoliveira.manageteam.web.rest;

import com.adoliveira.manageteam.ManageTeamApp;

import com.adoliveira.manageteam.domain.Action;
import com.adoliveira.manageteam.repository.ActionRepository;
import com.adoliveira.manageteam.service.ActionService;
import com.adoliveira.manageteam.service.dto.ActionDTO;
import com.adoliveira.manageteam.service.mapper.ActionMapper;
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
import java.util.List;
import java.util.ArrayList;

import static com.adoliveira.manageteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adoliveira.manageteam.domain.enumeration.TypeAction;
/**
 * Test class for the ActionResource REST controller.
 *
 * @see ActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageTeamApp.class)
public class ActionResourceIntTest {

    private static final TypeAction DEFAULT_TYPE_ACTION = TypeAction.GOAL;
    private static final TypeAction UPDATED_TYPE_ACTION = TypeAction.CARDYELOW;

    private static final Double DEFAULT_MINUTE = 1D;
    private static final Double UPDATED_MINUTE = 2D;

    private static final Boolean DEFAULT_PROLONGATION = false;
    private static final Boolean UPDATED_PROLONGATION = true;

    private static final String DEFAULT_COMMNTARY = "AAAAAAAAAA";
    private static final String UPDATED_COMMNTARY = "BBBBBBBBBB";

    @Autowired
    private ActionRepository actionRepository;


    @Autowired
    private ActionMapper actionMapper;
    

    @Autowired
    private ActionService actionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActionMockMvc;

    private Action action;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionResource actionResource = new ActionResource(actionService);
        this.restActionMockMvc = MockMvcBuilders.standaloneSetup(actionResource)
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
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .typeAction(DEFAULT_TYPE_ACTION)
            .minute(DEFAULT_MINUTE)
            .prolongation(DEFAULT_PROLONGATION)
            .commntary(DEFAULT_COMMNTARY);
        return action;
    }

    @Before
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTypeAction()).isEqualTo(DEFAULT_TYPE_ACTION);
        assertThat(testAction.getMinute()).isEqualTo(DEFAULT_MINUTE);
        assertThat(testAction.isProlongation()).isEqualTo(DEFAULT_PROLONGATION);
        assertThat(testAction.getCommntary()).isEqualTo(DEFAULT_COMMNTARY);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);
        ActionDTO actionDTO = actionMapper.toDto(action);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setTypeAction(null);

        // Create the Action, which fails.
        ActionDTO actionDTO = actionMapper.toDto(action);

        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setMinute(null);

        // Create the Action, which fails.
        ActionDTO actionDTO = actionMapper.toDto(action);

        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc.perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeAction").value(hasItem(DEFAULT_TYPE_ACTION.toString())))
            .andExpect(jsonPath("$.[*].minute").value(hasItem(DEFAULT_MINUTE.doubleValue())))
            .andExpect(jsonPath("$.[*].prolongation").value(hasItem(DEFAULT_PROLONGATION.booleanValue())))
            .andExpect(jsonPath("$.[*].commntary").value(hasItem(DEFAULT_COMMNTARY.toString())));
    }
    

    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.typeAction").value(DEFAULT_TYPE_ACTION.toString()))
            .andExpect(jsonPath("$.minute").value(DEFAULT_MINUTE.doubleValue()))
            .andExpect(jsonPath("$.prolongation").value(DEFAULT_PROLONGATION.booleanValue()))
            .andExpect(jsonPath("$.commntary").value(DEFAULT_COMMNTARY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .typeAction(UPDATED_TYPE_ACTION)
            .minute(UPDATED_MINUTE)
            .prolongation(UPDATED_PROLONGATION)
            .commntary(UPDATED_COMMNTARY);
        ActionDTO actionDTO = actionMapper.toDto(updatedAction);

        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTypeAction()).isEqualTo(UPDATED_TYPE_ACTION);
        assertThat(testAction.getMinute()).isEqualTo(UPDATED_MINUTE);
        assertThat(testAction.isProlongation()).isEqualTo(UPDATED_PROLONGATION);
        assertThat(testAction.getCommntary()).isEqualTo(UPDATED_COMMNTARY);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Get the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Action.class);
        Action action1 = new Action();
        action1.setId(1L);
        Action action2 = new Action();
        action2.setId(action1.getId());
        assertThat(action1).isEqualTo(action2);
        action2.setId(2L);
        assertThat(action1).isNotEqualTo(action2);
        action1.setId(null);
        assertThat(action1).isNotEqualTo(action2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionDTO.class);
        ActionDTO actionDTO1 = new ActionDTO();
        actionDTO1.setId(1L);
        ActionDTO actionDTO2 = new ActionDTO();
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
        actionDTO2.setId(actionDTO1.getId());
        assertThat(actionDTO1).isEqualTo(actionDTO2);
        actionDTO2.setId(2L);
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
        actionDTO1.setId(null);
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actionMapper.fromId(null)).isNull();
    }
}
