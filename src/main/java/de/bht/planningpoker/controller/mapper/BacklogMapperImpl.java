package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.BacklogDto;
import de.bht.planningpoker.controller.dto.BacklogItemAddDto;
import de.bht.planningpoker.controller.dto.BacklogItemUpdateDto;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BacklogMapperImpl implements BacklogMapper {

    @Override
    public BacklogDto mapToDto(Backlog backlog) {
        return BacklogDto.builder()
                .name(backlog.getName())
                .items(mapToDto(backlog.getItems()))
                .build();
    }

    @Override
    public List<BacklogDto.BacklogItemDto> mapToDto(List<BacklogItem> backlogItems) {
        return backlogItems.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public BacklogDto.BacklogItemDto mapToDto(BacklogItem backlogItem) {
        return BacklogDto.BacklogItemDto.builder()
                .number(backlogItem.getNumber())
                .title(backlogItem.getTitle())
                .description(backlogItem.getDescription())
                .estimation(backlogItem.getEstimation())
                .priority(backlogItem.getPriority())
                .build();
    }

    @Override
    public BacklogItem mapToModel(BacklogItemUpdateDto backlogItemUpdateDto) {
        return BacklogItem.builder()
                .title(backlogItemUpdateDto.getTitle())
                .description(backlogItemUpdateDto.getDescription())
                .estimation(backlogItemUpdateDto.getEstimation())
                .priority(backlogItemUpdateDto.getPriority())
                .build();
    }

    @Override
    public BacklogItem mapToModel(BacklogItemAddDto backlogItemAddDto) {
        return BacklogItem.builder()
                .title(backlogItemAddDto.getTitle())
                .description(backlogItemAddDto.getDescription())
                .estimation(Strings.EMPTY)
                .priority(Strings.EMPTY)
                .build();
    }

}
