package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.DiscussionDto;
import de.bht.planningpoker.controller.dto.DiscussionPostCreateDto;
import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class DiscussionMapperImpl implements DiscussionMapper {

    @Override
    public DiscussionDto mapToDto(Discussion discussion) {
        return DiscussionDto.builder()
                .topic(discussion.getTopic())
                .active(discussion.isActive())
                .startedAt(discussion.getStartedAt())
                .endedAt(discussion.getEndedAt())
                .duration(formatDuration(discussion.getDuration()))
                .posts(mapToDto(discussion.getPosts()))
                .build();
    }

    @Override
    public List<DiscussionDto.DiscussionPostDto> mapToDto(List<DiscussionPost> posts) {
        return posts.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public DiscussionDto.DiscussionPostDto mapToDto(DiscussionPost discussionPost) {
        return DiscussionDto.DiscussionPostDto.builder()
                .content(discussionPost.getContent())
                .author(discussionPost.getAuthor().getUsername())
                .createdAt(discussionPost.getCreatedAt())
                .updatedAt(discussionPost.getUpdatedAt())
                .build();
    }

    @Override
    public DiscussionPost mapToModel(DiscussionPostCreateDto discussionPostCreateDto) {
        return DiscussionPost.builder()
                .content(discussionPostCreateDto.getContent())
                .build();
    }

    private String formatDuration(Duration duration) {
        return String.format("%02d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }

}
