package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.DeckDto;
import de.bht.planningpoker.model.Card;
import de.bht.planningpoker.model.Deck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeckMapperImpl implements DeckMapper {

    @Override
    public Deck mapToModel(DeckDto deckDto) {
        return Deck.builder()
                .name(deckDto.getName())
                .cards(mapToModel(deckDto.getCards()))
                .build();
    }

    @Override
    public List<Card> mapToModel(List<DeckDto.DeckCardDto> deckCardsDto) {
        return deckCardsDto.stream()
                .map(this::mapToModel)
                .toList();
    }

    @Override
    public Card mapToModel(DeckDto.DeckCardDto deckCardDto) {
        final DeckDto.DeckCardColorsDto deckCardColorsDto = deckCardDto.getColors();
        return Card.builder()
                .label(deckCardDto.getValue())
                .value(deckCardDto.getValue())
                .frontTextColor(removeHashtag(deckCardColorsDto.getFrontTextColor()))
                .backTextColor(removeHashtag(deckCardColorsDto.getBackTextColor()))
                .backgroundColor(removeHashtag(deckCardColorsDto.getBackgroundColor()))
                .hoverColor(removeHashtag(deckCardColorsDto.getHoverColor()))
                .build();
    }

    private String removeHashtag(String hexadecimalColorValue) {
        if (hexadecimalColorValue.startsWith("#")) {
            return hexadecimalColorValue.substring(1);
        }
        return hexadecimalColorValue;
    }

    private String addHashtag(String hexadecimalColorValue) {
        if (hexadecimalColorValue.startsWith("#")) {
            return hexadecimalColorValue;
        }
        return "#" + hexadecimalColorValue;
    }

    @Override
    public DeckDto mapToDto(Deck deck) {
        return DeckDto.builder()
                .name(deck.getName())
                .cards(mapToDto(deck.getCards()))
                .build();
    }

    @Override
    public List<DeckDto.DeckCardDto> mapToDto(List<Card> cards) {
        return cards.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public DeckDto.DeckCardDto mapToDto(Card card) {
        final DeckDto.DeckCardColorsDto deckCardColorsDto = DeckDto.DeckCardColorsDto.builder()
                .frontTextColor(addHashtag(card.getFrontTextColor()))
                .backTextColor(addHashtag(card.getBackTextColor()))
                .backgroundColor(addHashtag(card.getBackgroundColor()))
                .hoverColor(addHashtag(card.getHoverColor()))
                .build();

        return DeckDto.DeckCardDto.builder()
                .value(card.getValue())
                .colors(deckCardColorsDto)
                .build();
    }

}
