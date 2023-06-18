package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.BacklogDto;
import de.bht.planningpoker.controller.dto.BacklogItemAddDto;
import de.bht.planningpoker.controller.dto.BacklogItemUpdateDto;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BacklogMapper {

    @Valid BacklogDto mapToDto(Backlog backlog);

    @Valid List<BacklogDto.BacklogItemDto> mapToDto(List<BacklogItem> backlogItems);

    @Valid BacklogDto.BacklogItemDto mapToDto(BacklogItem backlogItem);

    BacklogItem mapToModel(@Valid BacklogItemUpdateDto backlogItemUpdateDto);

    BacklogItem mapToModel(@Valid BacklogItemAddDto backlogItemAddDto);

}
