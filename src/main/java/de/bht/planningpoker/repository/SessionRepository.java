package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.User;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Modifying
    @Query("UPDATE Session s SET s.createdBy = :user WHERE s.id = :id")
    void updateCreatedBy(@Param(value = "id") Long id, @Param(value = "user") User user);

    int deleteByPublicId(String publicId);

    int deleteByPublicIdAndCreatedBy(String publicId, User createdBy);

    boolean existsByPublicId(String publicId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "team", "deck", "backlog", "discussion" }
    )
    Optional<Session> findByPublicId(String publicId);

    default Optional<Session> findOneByPublicId(String sessionId) {
        Optional<Session> sessionFound = findByPublicId(sessionId);
        sessionFound.ifPresent(this::initializeProxy);
        return sessionFound;
    }

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "team", "deck", "backlog", "discussion" }
    )
    Optional<Session> findByPublicIdAndTeamMembers(String publicId, User user);

    default Optional<Session> findByPublicIdAndUser(String publicId, User user) {
        Optional<Session> sessionFound = findByPublicIdAndTeamMembers(publicId, user);
        sessionFound.ifPresent(this::initializeProxy);
        return sessionFound;
    }

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "team", "deck", "backlog", "discussion" }
    )
    @Override
    List<Session> findAllById(Iterable<Long> ids);

    /**
     * Fetch sessions with pagination and no N+1 complexity.
     * Source: https://stackoverflow.com/a/71595502
     *
     * @param pageable
     * @return
     */
    default Page<Session> findAllSessions(Pageable pageable) {
        // Fetch entities with pagination and without @EntityGraph
        Page<Session> paginatedSession = findAll(pageable);
        List<Long> ids = paginatedSession.getContent()
                .stream()
                .map(Session::getId)
                .toList();

        // Fetch entities with @EntityGraph from previous returned ids and re-sort entities to preserve order
        List<Session> sessions = findAllById(ids).stream()
                .peek(session -> {
                    initializeProxy(session);
                    session.getEstimationRounds()
                            .stream()
                            .limit(Math.max(0, session.getEstimationRoundCount() - 1))      // ignore current estimation round
                            .forEach(estimationRound -> Hibernate.initialize(estimationRound.getEstimations()));
                })
                .sorted(Comparator.comparing(session -> ids.indexOf(session.getId())))
                .toList();
        return new PageImpl<Session>(sessions, pageable, paginatedSession.getTotalElements());
    }

    default void initializeProxy(Session session) {
        Hibernate.initialize(session.getTeam().getMembers());
        Hibernate.initialize(session.getDeck().getCards());
        Hibernate.initialize(session.getBacklog().getItems());
        Hibernate.initialize(session.getDiscussion().getPosts());
        Hibernate.initialize(session.getCurrentEstimationRound());
        session.getCurrentEstimationRound().ifPresent(estimationRound -> {
            Hibernate.initialize(estimationRound.getEstimations());
        });
    }

}
