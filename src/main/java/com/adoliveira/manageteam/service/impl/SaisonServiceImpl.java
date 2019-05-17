package com.adoliveira.manageteam.service.impl;

import com.adoliveira.manageteam.service.SaisonService;
import com.adoliveira.manageteam.domain.Saison;
import com.adoliveira.manageteam.repository.SaisonRepository;
import com.adoliveira.manageteam.service.dto.SaisonDTO;
import com.adoliveira.manageteam.service.mapper.SaisonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Saison.
 */
@Service
@Transactional
public class SaisonServiceImpl implements SaisonService {

    private final Logger log = LoggerFactory.getLogger(SaisonServiceImpl.class);

    private final SaisonRepository saisonRepository;

    private final SaisonMapper saisonMapper;

    public SaisonServiceImpl(SaisonRepository saisonRepository, SaisonMapper saisonMapper) {
        this.saisonRepository = saisonRepository;
        this.saisonMapper = saisonMapper;
    }

    /**
     * Save a saison.
     *
     * @param saisonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SaisonDTO save(SaisonDTO saisonDTO) {
        log.debug("Request to save Saison : {}", saisonDTO);
        Saison saison = saisonMapper.toEntity(saisonDTO);
        saison = saisonRepository.save(saison);
        return saisonMapper.toDto(saison);
    }

    /**
     * Get all the saisons.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SaisonDTO> findAll() {
        log.debug("Request to get all Saisons");
        return saisonRepository.findAll().stream()
            .map(saisonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one saison by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SaisonDTO> findOne(Long id) {
        log.debug("Request to get Saison : {}", id);
        return saisonRepository.findById(id)
            .map(saisonMapper::toDto);
    }

    /**
     * Delete the saison by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Saison : {}", id);
        saisonRepository.deleteById(id);
    }
}
