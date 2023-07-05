package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.DiscussionDto;
import de.bht.planningpoker.controller.dto.DiscussionPostCreateDto;
import de.bht.planningpoker.controller.dto.DiscussionStartDto;
import de.bht.planningpoker.controller.mapper.DiscussionMapper;
import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import de.bht.planningpoker.service.DiscussionService;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiscussionController implements DiscussionResource {

    private final DiscussionService service;
    private final DiscussionMapper mapper;

    @Override
    public ResponseEntity<DiscussionDto> getSingle(String sessionId) throws ServiceException {
        Discussion discussion = service.getDiscussion(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(discussion));
    }

    @Override
    public ResponseEntity<DiscussionDto> startDiscussion(String sessionId, DiscussionStartDto dto) throws ServiceException {
        Discussion discussion = service.startDiscussion(sessionId, dto.getTopic());
        return ResponseEntity.ok(mapper.mapToDto(discussion));
    }

    @Override
    public ResponseEntity<DiscussionDto> endDiscussion(String sessionId) throws ServiceException {
        Discussion discussion = service.endDiscussion(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(discussion));
    }

    @Override
    public ResponseEntity<DiscussionDto.DiscussionPostDto> createPost(String sessionId, DiscussionPostCreateDto dto) throws ServiceException {
        DiscussionPost discussionPost = service.createDiscussionPost(sessionId, mapper.mapToModel(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapToDto(discussionPost));
    }
}
