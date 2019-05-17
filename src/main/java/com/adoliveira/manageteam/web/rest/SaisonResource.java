package com.adoliveira.manageteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adoliveira.manageteam.service.SaisonService;
import com.adoliveira.manageteam.web.rest.errors.BadRequestAlertException;
import com.adoliveira.manageteam.web.rest.util.HeaderUtil;
import com.adoliveira.manageteam.service.dto.SaisonDTO;
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
 * REST controller for managing Saison.
 */
@RestController
@RequestMapping("/api")
public class SaisonResource {

    private final Logger log = LoggerFactory.getLogger(SaisonResource.class);

    private static final String ENTITY_NAME = "saison";

    private final SaisonService saisonService;

    public SaisonResource(SaisonService saisonService) {
        this.saisonService = saisonService;
    }

    /**
     * POST  /saisons : Create a new saison.
     *
     * @param saisonDTO the saisonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saisonDTO, or with status 400 (Bad Request) if the saison has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saisons")
    @Timed
    public ResponseEntity<SaisonDTO> createSaison(@Valid @RequestBody SaisonDTO saisonDTO) throws URISyntaxException {
        log.debug("REST request to save Saison : {}", saisonDTO);
        if (saisonDTO.getId() != null) {
            throw new BadRequestAlertException("A new saison cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        SaisonDTO result = saisonService.save(saisonDTO);
        return ResponseEntity.created(new URI("/api/saisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saisons : Updates an existing saison.
     *
     * @param saisonDTO the saisonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saisonDTO,
     * or with status 400 (Bad Request) if the saisonDTO is not valid,
     * or with status 500 (Internal Server Error) if the saisonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saisons")
    @Timed
    public ResponseEntity<SaisonDTO> updateSaison(@Valid @RequestBody SaisonDTO saisonDTO) throws URISyntaxException {
        log.debug("REST request to update Saison : {}", saisonDTO);
        if (saisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        SaisonDTO result = saisonService.save(saisonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saisonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saisons : get all the saisons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of saisons in body
     */
    @GetMapping("/saisons")
    @Timed
    public List<SaisonDTO> getAllSaisons() {
        log.debug("REST request to get all Saisons");
        return saisonService.findAll();
    }

    /**
     * GET  /saisons/:id : get the "id" saison.
     *
     * @param id the id of the saisonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saisonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/saisons/{id}")
    @Timed
    public ResponseEntity<SaisonDTO> getSaison(@PathVariable Long id) {
        log.debug("REST request to get Saison : {}", id);
        Optional<SaisonDTO> saisonDTO = saisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saisonDTO);
    }

    /**
     * DELETE  /saisons/:id : delete the "id" saison.
     *
     * @param id the id of the saisonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saisons/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaison(@PathVariable Long id) {
        log.debug("REST request to delete Saison : {}", id);
        saisonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
