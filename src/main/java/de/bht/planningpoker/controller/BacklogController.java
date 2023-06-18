package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.*;
import de.bht.planningpoker.controller.mapper.BacklogMapper;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import de.bht.planningpoker.service.BacklogService;
import de.bht.planningpoker.service.dto.BacklogExport;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BacklogController implements BacklogResource {

    private final BacklogService service;
    private final BacklogMapper mapper;

    @Override
    public ResponseEntity<BacklogDto> upload(String sessionId, MultipartFile file) throws ServiceException {
        Backlog backlog = service.importBacklog(sessionId, file);
        return ResponseEntity.ok(mapper.mapToDto(backlog));
    }

    @Override
    public ResponseEntity<Resource> download(String sessionId) throws ServiceException {
        BacklogExport backlogExport = service.exportBacklog(sessionId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + backlogExport.getFileName() + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(backlogExport.getResource());
    }

    @Override
    public ResponseEntity<BacklogDto> getSingle(String sessionId) throws ServiceException {
        Backlog backlog = service.getBacklog(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(backlog));
    }

    @Override
    public ResponseEntity<BacklogDto> rename(String sessionId, BacklogRenameDto dto) throws ServiceException {
        Backlog renamedBacklog = service.renameBacklog(sessionId, dto.getName());
        return ResponseEntity.ok(mapper.mapToDto(renamedBacklog));
    }

    @Override
    public void clear(String sessionId) throws ServiceException {
        service.clearBacklog(sessionId);
    }

    @Override
    public ResponseEntity<BacklogDto.BacklogItemDto> getItem(String sessionId, String number) throws ServiceException {
        BacklogItem backlogItem = service.getBacklogItem(sessionId, number);
        return ResponseEntity.ok(mapper.mapToDto(backlogItem));
    }

    @Override
    public ResponseEntity<BacklogDto.BacklogItemDto> addItem(String sessionId, BacklogItemAddDto dto) throws ServiceException {
        BacklogItem backlogItem = service.addBacklogItem(sessionId, mapper.mapToModel(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapToDto(backlogItem));
    }

    @Override
    public void removeItem(String sessionId, String number) throws ServiceException {
        service.removeBacklogItem(sessionId, number);
    }

    @Override
    public ResponseEntity<BacklogDto.BacklogItemDto> updateItem(String sessionId, String number, BacklogItemUpdateDto dto) throws ServiceException {
        BacklogItem backlogItem = service.updateBacklogItem(sessionId, number, mapper.mapToModel(dto));
        return ResponseEntity.ok(mapper.mapToDto(backlogItem));
    }

    @Override
    public ResponseEntity<List<BacklogDto.BacklogItemDto>> moveItem(String sessionId, String number, BacklogItemMoveDto dto) throws ServiceException {
        List<BacklogItem> backlogItems = service.moveBacklogItem(sessionId, number, dto.getNewIndex());
        return ResponseEntity.ok(mapper.mapToDto(backlogItems));
    }
}
