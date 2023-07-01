package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.EstimationRoundDto;
import de.bht.planningpoker.controller.dto.EstimationSummaryDto;
import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import de.bht.planningpoker.model.EstimationSummary;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

@Validated
public interface EstimationMapper {

    @Valid EstimationRoundDto mapToDto(EstimationRound estimationRound);

    @Valid List<EstimationRoundDto.EstimationDto> mapToDto(List<Estimation> estimations);

    @Valid EstimationRoundDto.EstimationDto mapToDto(Estimation estimation);

    @Valid EstimationSummaryDto mapToDto(EstimationSummary estimationSummary);

    @Valid Set<EstimationSummaryDto.EstimationResultDto> mapToDto(Set<EstimationSummary.EstimationResult> estimationResults);

    @Valid EstimationSummaryDto.EstimationResultDto mapToDto(EstimationSummary.EstimationResult estimationResult);

}
