package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.*;
import de.bht.planningpoker.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionMapperImpl implements SessionMapper {

    private final TeamMapper teamMapper;
    private final DeckMapper deckMapper;
    private final BacklogMapper backlogMapper;
    private final DiscussionMapper discussionMapper;
    private final EstimationMapper estimationMapper;

    @Override
    public SessionPageDto mapToDto(Page<Session> pagedSessions) {
        final SessionPageDto.SessionPageInfoDto info = SessionPageDto.SessionPageInfoDto.builder()
                .pageNumber(pagedSessions.getNumber())
                .pageSize(pagedSessions.getSize())
                .offset(pagedSessions.getPageable().getOffset())
                .totalPages(pagedSessions.getTotalPages())
                .totalItems(pagedSessions.getTotalElements())
                .numberOfItems(pagedSessions.getNumberOfElements())
                .build();

        final Function<List<Card>, String> deckCardsToCsv = cards -> cards.stream()
                .map(Card::getValue)
                .collect(Collectors.joining(", "));

        final List<SessionPageDto.SessionPageItemDto> items = pagedSessions.getContent()
                .stream()
                .map(session ->
                    SessionPageDto.SessionPageItemDto.builder()
                            .id(session.getPublicId())
                            .teamName(session.getTeam().getName())
                            .totalMembers(session.getTeam().getSize())
                            .activeMembers(session.getTeam().getActiveMembers().size())
                            .totalBacklogItems(session.getBacklog().getSize())
                            .estimatedBacklogItems(session.getBacklog().getEstimatedItems().size())
                            .totalDiscussionPosts(session.getDiscussion().getPostCount())
                            .totalEstimationRounds(session.getEstimationRoundCount())
                            .totalEstimations(session.getEstimationCount())
                            .deckName(session.getDeck().getName())
                            .deckCards(deckCardsToCsv.apply(session.getDeck().getCards()))
                            .createdAt(convertToDate(session.getCreatedAt()))
                            .build()
                )
                .toList();

        return SessionPageDto.builder()
                .info(info)
                .items(items)
                .build();
    }

    public SessionDto mapToDto(Session session) {
        return SessionDto.builder()
                .id(session.getPublicId())
                .team(teamMapper.mapToDto(session.getTeam()))
                .deck(deckMapper.mapToDto(session.getDeck()))
                .backlog(backlogMapper.mapToDto(session.getBacklog()))
                .discussion(discussionMapper.mapToDto(session.getDiscussion()))
                .estimationRound(estimationMapper.mapToDto(session.getCurrentEstimationRound().orElse(null)))
                .estimationSummary(estimationMapper.mapToDto(session.getCurrentEstimationRound().map(EstimationRound::getSummary).orElse(null)))
                .createdAt(convertToDate(session.getCreatedAt()))
                .build();
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Session mapToModel(SessionCreateDto sessionCreateDto) {
        final List<User> members = List.of(
            User.builder()
                .username(sessionCreateDto.getUsername())
                .active(false)
                .role(Role.MODERATOR)
                .build()
        );

        final Team team = Team.builder()
                .name(sessionCreateDto.getTeam())
                .members(members)
                .build();

        final Backlog backlog = Backlog.builder()
                .name("My Product Backlog")
                .items(Collections.emptyList())
                .build();

        final Discussion discussion = Discussion.builder()
                .topic("General")
                .active(false)
                .posts(Collections.emptyList())
                .build();

        return Session.builder()
                .publicId(UUID.randomUUID().toString())
                .team(team)
                .deck(deckMapper.mapToModel(sessionCreateDto.getDeck()))
                .backlog(backlog)
                .discussion(discussion)
                .estimationRounds(List.of())
                .build();
    }

    public User mapToModel(SessionJoinDto sessionJoinDto) {
        return User.builder()
                .username(sessionJoinDto.getUsername())
                .active(false)
                .role(sessionJoinDto.isSpectator() ? Role.SPECTATOR : Role.PARTICIPANT)
                .build();
    }

}
