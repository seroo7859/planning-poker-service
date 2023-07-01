package de.bht.planningpoker.service;

import de.bht.planningpoker.event.publisher.BacklogPublisher;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.repository.BacklogRepository;
import de.bht.planningpoker.service.dto.BacklogExport;
import de.bht.planningpoker.service.exception.*;
import de.bht.planningpoker.service.util.BacklogCSVHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BacklogServiceImpl implements BacklogService {

    private final BacklogRepository repository;
    private final BacklogPublisher publisher;
    private final UserService userService;

    @Override
    public Backlog importBacklog(String sessionId, MultipartFile file) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Import backlog on session " + sessionId + " is not allowed");
            }

            if (file.isEmpty() || !BacklogCSVHelper.hasCSVFormat(file)) {
                throw new BacklogImportFailedException(sessionId);
            }

            Backlog importedBacklog = BacklogCSVHelper.csvToBacklog(file);
            if (Objects.isNull(importedBacklog) || importedBacklog.isEmpty()) {
                throw new BacklogImportFailedException(sessionId);
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));

            String backlogName = importedBacklog.getName().trim().replaceAll("_", " ").replaceFirst("[0-9]{14}$", Strings.EMPTY).trim();
            backlog.setName(backlogName);

            if (backlog.isEmpty()) {
                backlog.addItems(importedBacklog.getItems());
            } else {
                for (BacklogItem importedBacklogItem : importedBacklog.getItems()) {
                    if (backlog.hasItem(importedBacklogItem.getNumber())) {
                        backlog.setItem(importedBacklogItem.getNumber(), importedBacklogItem);
                        continue;
                    }
                    backlog.addItem(importedBacklogItem);
                }
            }

            repository.save(backlog);
            publisher.publishBacklogImportedEvent(backlog);
            return backlog;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

    @Override
    public BacklogExport exportBacklog(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Export backlog on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));

            Resource resource = BacklogCSVHelper.backlogToCsv(backlog);
            if (!resource.exists() || !resource.isReadable()) {
                throw new BacklogExportFailedException(sessionId);
            }

            String fileName = backlog.getName().trim().replaceAll("\\s", "_") + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + ".csv";
            return new BacklogExport(fileName, resource);
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Backlog getBacklog(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Get backlog on session " + sessionId + " is not allowed");
            }

            Optional<Backlog> backlogFound = repository.findBySessionPublicId(sessionId);
            return backlogFound.orElseThrow(() -> new BacklogNotFoundException(sessionId));
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Backlog renameBacklog(String sessionId, String newName) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(newName) || newName.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Rename backlog on session " + sessionId + " is not allowed");
            }

            Optional<Backlog> backlogFound = repository.findBySessionPublicId(sessionId);
            backlogFound.ifPresent(backlog -> {
                if (!newName.equals(backlog.getName())) {
                    backlog.setName(newName);
                    repository.save(backlog);
                    publisher.publishBacklogRenamedEvent(backlog);
                }
            });
            return backlogFound.orElseThrow(() -> new BacklogNotFoundException(sessionId));
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @Override
    public void clearBacklog(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Remove all backlog items on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));

            backlog.clear();
            repository.save(backlog);
            publisher.publishBacklogClearedEvent(backlog);
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.DELETE_ERROR, e);
        }
    }

    @Override
    public BacklogItem getBacklogItem(String sessionId, String backlogItemNumber) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Get backlog item on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));
            if (!backlog.hasItem(backlogItemNumber)) {
                throw new BacklogItemNotFoundException(backlogItemNumber);
            }

            return backlog.getItem(backlogItemNumber);
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public BacklogItem addBacklogItem(String sessionId, BacklogItem backlogItem) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(backlogItem)) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Add backlog item on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));

            backlog.addItem(backlogItem);
            repository.save(backlog);
            publisher.publishBacklogItemAddedEvent(backlogItem);
            return backlogItem;
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

    @Override
    public void removeBacklogItem(String sessionId, String backlogItemNumber) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(backlogItemNumber) || backlogItemNumber.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Remove backlog item on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));
            if (!backlog.hasItem(backlogItemNumber)) {
                throw new BacklogItemNotFoundException(backlogItemNumber);
            }

            BacklogItem backlogItem = backlog.getItem(backlogItemNumber);
            backlog.removeItem(backlogItem.clone());
            repository.save(backlog);
            publisher.publishBacklogItemRemovedEvent(backlogItem);
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.DELETE_ERROR, e);
        }
    }

    @Override
    public BacklogItem updateBacklogItem(String sessionId, String backlogItemNumber, BacklogItem newBacklogItem) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(backlogItemNumber) || backlogItemNumber.isBlank() || Objects.isNull(newBacklogItem)) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Update backlog item on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));
            if (!backlog.hasItem(backlogItemNumber)) {
                throw new BacklogItemNotFoundException(backlogItemNumber);
            }

            backlog.setItem(backlogItemNumber, newBacklogItem);
            repository.save(backlog);

            BacklogItem backlogItem = backlog.getItem(backlogItemNumber);
            publisher.publishBacklogItemUpdatedEvent(backlogItem);
            return backlogItem;
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.DELETE_ERROR, e);
        }
    }

    @Override
    public List<BacklogItem> moveBacklogItem(String sessionId, String backlogItemNumber, Integer newIndex) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(backlogItemNumber) || backlogItemNumber.isBlank() || Objects.isNull(newIndex)) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Move backlog item on session " + sessionId + " is not allowed");
            }

            Backlog backlog = repository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new BacklogNotFoundException(sessionId));
            if (!backlog.hasItem(backlogItemNumber)) {
                throw new BacklogItemNotFoundException(backlogItemNumber);
            }

            BacklogItem backlogItem = backlog.getItem(backlogItemNumber);
            int currentIndex = backlog.getItemIndex(backlogItem.getNumber());
            if (currentIndex < newIndex) {
                for (int index = currentIndex; index < newIndex; index++) {
                    Collections.swap(backlog.getItems(), index, index + 1);
                }
            } else if (currentIndex > newIndex) {
                for (int index = currentIndex; index > newIndex; index--) {
                    Collections.swap(backlog.getItems(), index, index - 1);
                }
            } else {
                return backlog.getItems();
            }

            repository.save(backlog);
            publisher.publishBacklogItemMovedEvent(backlogItem);
            return backlog.getItems();
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

}
