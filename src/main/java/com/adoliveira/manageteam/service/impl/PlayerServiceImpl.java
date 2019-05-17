package com.adoliveira.manageteam.service.impl;

import com.adoliveira.manageteam.service.PlayerService;
import com.adoliveira.manageteam.domain.Player;
import com.adoliveira.manageteam.repository.PlayerRepository;
import com.adoliveira.manageteam.service.dto.PlayerDTO;
import com.adoliveira.manageteam.service.mapper.PlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Player.
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    /**
     * Save a player.
     *
     * @param playerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlayerDTO save(PlayerDTO playerDTO) {
        log.debug("Request to save Player : {}", playerDTO);
        Player player = playerMapper.toEntity(playerDTO);
        player = playerRepository.save(player);
        return playerMapper.toDto(player);
    }

    /**
     * Get all the players.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> findAll() {
        log.debug("Request to get all Players");
        return playerRepository.findAll().stream()
            .map(playerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one player by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerDTO> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id)
            .map(playerMapper::toDto);
    }

    /**
     * Delete the player by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}