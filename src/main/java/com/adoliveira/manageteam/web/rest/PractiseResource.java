package com.adoliveira.manageteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adoliveira.manageteam.service.PractiseService;
import com.adoliveira.manageteam.web.rest.errors.BadRequestAlertException;
import com.adoliveira.manageteam.web.rest.util.HeaderUtil;
import com.adoliveira.manageteam.service.dto.PractiseDTO;
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
 * REST controller for managing Practise.
 */
@RestController
@RequestMapping("/api")
public class PractiseResource {

    private final Logger log = LoggerFactory.getLogger(PractiseResource.class);

    private static final String ENTITY_NAME = "practise";

    private final PractiseService practiseService;

    public PractiseResource(PractiseService practiseService) {
        this.practiseService = practiseService;
    }

    /**
     * POST  /practises : Create a new practise.
     *
     * @param practiseDTO the practiseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new practiseDTO, or with status 400 (Bad Request) if the practise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/practises")
    @Timed
    public ResponseEntity<PractiseDTO> createPractise(@Valid @RequestBody PractiseDTO practiseDTO) throws URISyntaxException {
        log.debug("REST request to save Practise : {}", practiseDTO);
        if (practiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new practise cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        PractiseDTO result = practiseService.save(practiseDTO);
        return ResponseEntity.created(new URI("/api/practises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /practises : Updates an existing practise.
     *
     * @param practiseDTO the practiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated practiseDTO,
     * or with status 400 (Bad Request) if the practiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the practiseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/practises")
    @Timed
    public ResponseEntity<PractiseDTO> updatePractise(@Valid @RequestBody PractiseDTO practiseDTO) throws URISyntaxException {
        log.debug("REST request to update Practise : {}", practiseDTO);
        if (practiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        PractiseDTO result = practiseService.save(practiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, practiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /practises : get all the practises.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of practises in body
     */
    @GetMapping("/practises")
    @Timed
    public List<PractiseDTO> getAllPractises(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Practises");
        return practiseService.findAll();
    }

    /**
     * GET  /practises/:id : get the "id" practise.
     *
     * @param id the id of the practiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the practiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/practises/{id}")
    @Timed
    public ResponseEntity<PractiseDTO> getPractise(@PathVariable Long id) {
        log.debug("REST request to get Practise : {}", id);
        Optional<PractiseDTO> practiseDTO = practiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(practiseDTO);
    }

    /**
     * DELETE  /practises/:id : delete the "id" practise.
     *
     * @param id the id of the practiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/practises/{id}")
    @Timed
    public ResponseEntity<Void> deletePractise(@PathVariable Long id) {
        log.debug("REST request to delete Practise : {}", id);
        practiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
