package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.DeckDto;
import de.bht.planningpoker.model.Card;
import de.bht.planningpoker.model.Deck;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface DeckMapper {

    Deck mapToModel(@Valid DeckDto deckDto);

    List<Card> mapToModel(@Valid List<DeckDto.DeckCardDto> deckCardsDto);

    Card mapToModel(@Valid DeckDto.DeckCardDto deckCardDto);

    @Valid DeckDto mapToDto(Deck deck);

    @Valid List<DeckDto.DeckCardDto> mapToDto(List<Card> cards);

    @Valid DeckDto.DeckCardDto mapToDto(Card card);

}
