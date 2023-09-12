package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Discussion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    boolean existsBySessionPublicId(String sessionId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "posts", "posts.author" }
    )
    Optional<Discussion> findBySessionPublicId(String sessionId);

}
