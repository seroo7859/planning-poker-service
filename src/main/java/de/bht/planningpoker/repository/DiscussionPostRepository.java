package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscussionPostRepository extends JpaRepository<DiscussionPost, Long> {
    List<DiscussionPost> findAllByDiscussion(Discussion discussion);
}
