package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.EstimationRound;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimationRoundRepository extends JpaRepository<EstimationRound, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "estimations" }
    )
    Optional<EstimationRound> findBySessionPublicId(String sessionId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "estimations" }
    )
    Optional<EstimationRound> findBySessionPublicIdAndBacklogItemNumber(String sessionId, String backlogItemNumber);

}
