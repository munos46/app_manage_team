package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Game;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select distinct game from Game game left join fetch game.manages left join fetch game.players left join fetch game.actions",
        countQuery = "select count(distinct game) from Game game")
    Page<Game> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct game from Game game left join fetch game.manages left join fetch game.players left join fetch game.actions")
    List<Game> findAllWithEagerRelationships();

    @Query("select game from Game game left join fetch game.manages left join fetch game.players left join fetch game.actions where game.id =:id")
    Optional<Game> findOneWithEagerRelationships(@Param("id") Long id);

}
