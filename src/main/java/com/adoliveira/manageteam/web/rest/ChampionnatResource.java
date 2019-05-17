package com.adoliveira.manageteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adoliveira.manageteam.service.ChampionnatService;
import com.adoliveira.manageteam.web.rest.errors.BadRequestAlertException;
import com.adoliveira.manageteam.web.rest.util.HeaderUtil;
import com.adoliveira.manageteam.service.dto.ChampionnatDTO;
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
 * REST controller for managing Championnat.
 */
@RestController
@RequestMapping("/api")
public class ChampionnatResource {

    private final Logger log = LoggerFactory.getLogger(ChampionnatResource.class);

    private static final String ENTITY_NAME = "championnat";

    private final ChampionnatService championnatService;

    public ChampionnatResource(ChampionnatService championnatService) {
        this.championnatService = championnatService;
    }

    /**
     * POST  /championnats : Create a new championnat.
     *
     * @param championnatDTO the championnatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new championnatDTO, or with status 400 (Bad Request) if the championnat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/championnats")
    @Timed
    public ResponseEntity<ChampionnatDTO> createChampionnat(@Valid @RequestBody ChampionnatDTO championnatDTO) throws URISyntaxException {
        log.debug("REST request to save Championnat : {}", championnatDTO);
        if (championnatDTO.getId() != null) {
            throw new BadRequestAlertException("A new championnat cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        ChampionnatDTO result = championnatService.save(championnatDTO);
        return ResponseEntity.created(new URI("/api/championnats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /championnats : Updates an existing championnat.
     *
     * @param championnatDTO the championnatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated championnatDTO,
     * or with status 400 (Bad Request) if the championnatDTO is not valid,
     * or with status 500 (Internal Server Error) if the championnatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/championnats")
    @Timed
    public ResponseEntity<ChampionnatDTO> updateChampionnat(@Valid @RequestBody ChampionnatDTO championnatDTO) throws URISyntaxException {
        log.debug("REST request to update Championnat : {}", championnatDTO);
        if (championnatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        ChampionnatDTO result = championnatService.save(championnatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, championnatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /championnats : get all the championnats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of championnats in body
     */
    @GetMapping("/championnats")
    @Timed
    public List<ChampionnatDTO> getAllChampionnats(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Championnats");
        return championnatService.findAll();
    }

    /**
     * GET  /championnats/:id : get the "id" championnat.
     *
     * @param id the id of the championnatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the championnatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/championnats/{id}")
    @Timed
    public ResponseEntity<ChampionnatDTO> getChampionnat(@PathVariable Long id) {
        log.debug("REST request to get Championnat : {}", id);
        Optional<ChampionnatDTO> championnatDTO = championnatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(championnatDTO);
    }

    /**
     * DELETE  /championnats/:id : delete the "id" championnat.
     *
     * @param id the id of the championnatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/championnats/{id}")
    @Timed
    public ResponseEntity<Void> deleteChampionnat(@PathVariable Long id) {
        log.debug("REST request to delete Championnat : {}", id);
        championnatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
