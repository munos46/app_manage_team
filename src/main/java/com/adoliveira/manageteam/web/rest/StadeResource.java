package com.adoliveira.manageteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adoliveira.manageteam.service.StadeService;
import com.adoliveira.manageteam.web.rest.errors.BadRequestAlertException;
import com.adoliveira.manageteam.web.rest.util.HeaderUtil;
import com.adoliveira.manageteam.service.dto.StadeDTO;
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
 * REST controller for managing Stade.
 */
@RestController
@RequestMapping("/api")
public class StadeResource {

    private final Logger log = LoggerFactory.getLogger(StadeResource.class);

    private static final String ENTITY_NAME = "stade";

    private final StadeService stadeService;

    public StadeResource(StadeService stadeService) {
        this.stadeService = stadeService;
    }

    /**
     * POST  /stades : Create a new stade.
     *
     * @param stadeDTO the stadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stadeDTO, or with status 400 (Bad Request) if the stade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stades")
    @Timed
    public ResponseEntity<StadeDTO> createStade(@Valid @RequestBody StadeDTO stadeDTO) throws URISyntaxException {
        log.debug("REST request to save Stade : {}", stadeDTO);
        if (stadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stade cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        StadeDTO result = stadeService.save(stadeDTO);
        return ResponseEntity.created(new URI("/api/stades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stades : Updates an existing stade.
     *
     * @param stadeDTO the stadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stadeDTO,
     * or with status 400 (Bad Request) if the stadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the stadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stades")
    @Timed
    public ResponseEntity<StadeDTO> updateStade(@Valid @RequestBody StadeDTO stadeDTO) throws URISyntaxException {
        log.debug("REST request to update Stade : {}", stadeDTO);
        if (stadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        StadeDTO result = stadeService.save(stadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stades : get all the stades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stades in body
     */
    @GetMapping("/stades")
    @Timed
    public List<StadeDTO> getAllStades() {
        log.debug("REST request to get all Stades");
        return stadeService.findAll();
    }

    /**
     * GET  /stades/:id : get the "id" stade.
     *
     * @param id the id of the stadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stades/{id}")
    @Timed
    public ResponseEntity<StadeDTO> getStade(@PathVariable Long id) {
        log.debug("REST request to get Stade : {}", id);
        Optional<StadeDTO> stadeDTO = stadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stadeDTO);
    }

    /**
     * DELETE  /stades/:id : delete the "id" stade.
     *
     * @param id the id of the stadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stades/{id}")
    @Timed
    public ResponseEntity<Void> deleteStade(@PathVariable Long id) {
        log.debug("REST request to delete Stade : {}", id);
        stadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
