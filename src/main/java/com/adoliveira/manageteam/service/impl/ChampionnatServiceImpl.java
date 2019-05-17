package com.adoliveira.manageteam.service.impl;

import com.adoliveira.manageteam.service.ChampionnatService;
import com.adoliveira.manageteam.domain.Championnat;
import com.adoliveira.manageteam.repository.ChampionnatRepository;
import com.adoliveira.manageteam.service.dto.ChampionnatDTO;
import com.adoliveira.manageteam.service.mapper.ChampionnatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Championnat.
 */
@Service
@Transactional
public class ChampionnatServiceImpl implements ChampionnatService {

    private final Logger log = LoggerFactory.getLogger(ChampionnatServiceImpl.class);

    private final ChampionnatRepository championnatRepository;

    private final ChampionnatMapper championnatMapper;

    public ChampionnatServiceImpl(ChampionnatRepository championnatRepository, ChampionnatMapper championnatMapper) {
        this.championnatRepository = championnatRepository;
        this.championnatMapper = championnatMapper;
    }

    /**
     * Save a championnat.
     *
     * @param championnatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChampionnatDTO save(ChampionnatDTO championnatDTO) {
        log.debug("Request to save Championnat : {}", championnatDTO);
        Championnat championnat = championnatMapper.toEntity(championnatDTO);
        championnat = championnatRepository.save(championnat);
        return championnatMapper.toDto(championnat);
    }

    /**
     * Get all the championnats.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChampionnatDTO> findAll() {
        log.debug("Request to get all Championnats");
        return championnatRepository.findAllWithEagerRelationships().stream()
            .map(championnatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Championnat with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ChampionnatDTO> findAllWithEagerRelationships(Pageable pageable) {
        return championnatRepository.findAllWithEagerRelationships(pageable).map(championnatMapper::toDto);
    }
    

    /**
     * Get one championnat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChampionnatDTO> findOne(Long id) {
        log.debug("Request to get Championnat : {}", id);
        return championnatRepository.findOneWithEagerRelationships(id)
            .map(championnatMapper::toDto);
    }

    /**
     * Delete the championnat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Championnat : {}", id);
        championnatRepository.deleteById(id);
    }
}
