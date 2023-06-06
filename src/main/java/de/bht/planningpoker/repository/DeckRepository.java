package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    Optional<Deck> findByName(String name);

    @Query("SELECT d FROM Deck d LEFT JOIN FETCH d.cards")
    List<Deck> findAllDecks();
}
