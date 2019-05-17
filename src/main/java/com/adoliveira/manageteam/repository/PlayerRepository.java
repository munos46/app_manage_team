package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Player;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
