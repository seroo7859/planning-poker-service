package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogItemRepository extends JpaRepository<BacklogItem, Long> {
    List<BacklogItem> findAllByBacklog(Backlog backlog);
}
