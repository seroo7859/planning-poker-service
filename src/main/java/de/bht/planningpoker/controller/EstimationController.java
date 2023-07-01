package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.*;
import de.bht.planningpoker.controller.mapper.EstimationMapper;
import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import de.bht.planningpoker.model.EstimationSummary;
import de.bht.planningpoker.service.EstimationService;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EstimationController implements EstimationResource {

    private final EstimationService service;
    private final EstimationMapper mapper;

    @Override
    public ResponseEntity<EstimationRoundDto> startRound(String sessionId, EstimationRoundStartDto dto) throws ServiceException {
        EstimationRound estimationRound = service.startEstimationRound(sessionId, dto.getBacklogItemNumber());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapToDto(estimationRound));
    }

    @Override
    public ResponseEntity<EstimationRoundDto> nextRound(String sessionId) throws ServiceException {
        EstimationRound estimationRound = service.nextEstimationRound(sessionId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapToDto(estimationRound));
    }

    @Override
    public ResponseEntity<EstimationRoundDto> finishRound(String sessionId) throws ServiceException {
        EstimationRound estimationRound = service.finishEstimationRound(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(estimationRound));
    }

    @Override
    public ResponseEntity<EstimationRoundDto> getInfo(String sessionId) throws ServiceException {
        EstimationRound estimationRound = service.getCurrentEstimationRound(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(estimationRound));
    }

    @Override
    public ResponseEntity<EstimationSummaryDto> getSummary(String sessionId) throws ServiceException {
        EstimationRound estimationRound = service.getCurrentEstimationRound(sessionId);
        EstimationSummary estimationSummary = estimationRound.getSummary();
        return ResponseEntity.ok(mapper.mapToDto(estimationSummary));
    }

    @Override
    public ResponseEntity<EstimationRoundDto.EstimationDto> giveEstimation(String sessionId, EstimationGiveDto dto) throws ServiceException {
        Estimation estimation = service.giveAnEstimation(sessionId, dto.getEstimationValue());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapToDto(estimation));
    }

}
