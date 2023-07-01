package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.EstimationRoundDto;
import de.bht.planningpoker.controller.dto.EstimationSummaryDto;
import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import de.bht.planningpoker.model.EstimationSummary;
import de.bht.planningpoker.model.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EstimationMapperImpl implements EstimationMapper {

    @Override
    public EstimationRoundDto mapToDto(EstimationRound estimationRound) {
        if (Objects.isNull(estimationRound)) {
            return null;
        }
        return EstimationRoundDto.builder()
                .backlogItemNumber(estimationRound.getBacklogItem().getNumber())
                .startedAt(estimationRound.getStartedAt())
                .finishedAt(estimationRound.getFinishedAt())
                .estimations(mapToDto(estimationRound.getEstimations()))
                .build();
    }

    @Override
    public List<EstimationRoundDto.EstimationDto> mapToDto(List<Estimation> estimations) {
        return estimations.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public EstimationRoundDto.EstimationDto mapToDto(Estimation estimation) {
        return EstimationRoundDto.EstimationDto.builder()
                .estimationValue(estimation.getEstimationValue())
                .estimator(estimation.getCreatedBy().getUsername())
                .createdAt(estimation.getCreatedAt())
                .updatedAt(estimation.getUpdatedAt())
                .build();
    }

    @Override
    public EstimationSummaryDto mapToDto(EstimationSummary estimationSummary) {
        if (Objects.isNull(estimationSummary)) {
            return null;
        }
        return EstimationSummaryDto.builder()
                .backlogItemNumber(estimationSummary.getBacklogItem().getNumber())
                .startedAt(estimationSummary.getStartedAt())
                .finishedAt(estimationSummary.getFinishedAt())
                .duration(formatDuration(estimationSummary.getDuration()))
                .totalEstimators(estimationSummary.getTotalEstimators())
                .numberOfEstimators(estimationSummary.getNumberOfEstimators())
                .consensusReached(estimationSummary.isConsensusReached())
                .recommendation(estimationSummary.getRecommendation())
                .estimationResults(mapToDto(estimationSummary.getEstimationResults()))
                .build();
    }

    @Override
    public Set<EstimationSummaryDto.EstimationResultDto> mapToDto(Set<EstimationSummary.EstimationResult> estimationResults) {
        return estimationResults.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public EstimationSummaryDto.EstimationResultDto mapToDto(EstimationSummary.EstimationResult estimationResult) {
        final Set<String> estimators = estimationResult.getEstimators()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());

        return EstimationSummaryDto.EstimationResultDto.builder()
                .estimationValue(estimationResult.getEstimationValue())
                .estimators(estimators)
                .build();
    }

    private String formatDuration(Duration duration) {
        return String.format("%02d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }

}
