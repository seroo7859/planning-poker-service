package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
    List<Estimation> findAllByEstimationRound(EstimationRound estimationRound);
}
