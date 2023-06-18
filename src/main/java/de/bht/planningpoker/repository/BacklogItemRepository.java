package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, Long> {
    List<BacklogItem> findAllByBacklog(Backlog backlog);
}
