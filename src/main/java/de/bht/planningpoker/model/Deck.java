package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Column(nullable = false, length = 32)
    private String name;

    @UniqueElements(message = "Cards must not contain duplicates")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "deck_card",
        joinColumns = @JoinColumn(name = "deck_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id", unique = true))
    @OrderColumn(name="list_index")
    private List<@Valid Card> cards = new ArrayList<>();

    @NotNull(message = "CreatedAt may not be null")
    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull(message = "UpdatedAt may not be null")
    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Card getCard(String cardValue) {
        return cards
                .stream()
                .filter(card -> cardValue.equalsIgnoreCase(card.getValue()))
                .findFirst()
                .orElse(null);
    }

    public boolean hasCard(String cardValue) {
        return cards
                .stream()
                .anyMatch(card -> cardValue.equalsIgnoreCase(card.getValue()));
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public int getSize() {
        return cards.size();
    }

}
