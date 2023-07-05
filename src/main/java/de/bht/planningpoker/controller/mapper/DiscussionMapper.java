package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.DiscussionDto;
import de.bht.planningpoker.controller.dto.DiscussionPostCreateDto;
import de.bht.planningpoker.model.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface DiscussionMapper {

    @Valid DiscussionDto mapToDto(Discussion discussion);

    @Valid List<DiscussionDto.DiscussionPostDto> mapToDto(List<DiscussionPost> posts);

    @Valid DiscussionDto.DiscussionPostDto mapToDto(DiscussionPost discussionPost);

    DiscussionPost mapToModel(@Valid DiscussionPostCreateDto discussionPostCreateDto);

}
