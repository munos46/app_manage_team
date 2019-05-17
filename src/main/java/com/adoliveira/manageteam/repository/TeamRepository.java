package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Team;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "select distinct team from Team team left join fetch team.managers left join fetch team.players",
        countQuery = "select count(distinct team) from Team team")
    Page<Team> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct team from Team team left join fetch team.managers left join fetch team.players")
    List<Team> findAllWithEagerRelationships();

    @Query("select team from Team team left join fetch team.managers left join fetch team.players where team.id =:id")
    Optional<Team> findOneWithEagerRelationships(@Param("id") Long id);

}
