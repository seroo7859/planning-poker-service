package de.bht.planningpoker.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.bht.planningpoker.auth.jwt.exception.JwtGenerationException;
import de.bht.planningpoker.auth.jwt.exception.JwtValidationException;
import de.bht.planningpoker.service.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMsg> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMsg> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.FORBIDDEN, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMsg> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorMsg> handleBindException(BindException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMsg> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Build message
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = errors.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        // Build response
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST, request, message, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMsg> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.METHOD_NOT_ALLOWED, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMsg> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMsg> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorMsg> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.EXPECTATION_FAILED, request, "File to large", ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorMsg> handleMultipartException(MultipartException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR, request, "Failed to upload file", ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(JwtGenerationException.class)
    public ResponseEntity<ErrorMsg> handleJwtGenerationException(JwtGenerationException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.UNAUTHORIZED, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorMsg> handleJwtValidationException(JwtValidationException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.UNAUTHORIZED, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ErrorMsg> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.UNAUTHORIZED, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorMsg> handleSessionNotFoundException(SessionNotFoundException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(BacklogNotFoundException.class)
    public ResponseEntity<ErrorMsg> handleBacklogNotFoundException(BacklogNotFoundException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(BacklogItemNotFoundException.class)
    public ResponseEntity<ErrorMsg> handleBacklogItemNotFoundException(BacklogItemNotFoundException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorMsg> handleOperationNotAllowedException(OperationNotAllowedException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.FORBIDDEN, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMsg> handleServiceException(ServiceException ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
        return buildResponseEntity(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
        return buildResponseEntity(errorMsg);
    }

    private ResponseEntity<ErrorMsg> buildResponseEntity(ErrorMsg errorMsg) {
        return new ResponseEntity<>(errorMsg, HttpStatus.valueOf(errorMsg.getStatus()));
    }

    @Value
    @AllArgsConstructor
    private static class ErrorMsg {

        Integer status;
        String path;
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp;
        String message;
        String exception;

        public ErrorMsg(HttpStatus status, HttpServletRequest request) {
            this(status, request, new RuntimeException("Unexpected error"));
        }

        public ErrorMsg(HttpStatus status, HttpServletRequest request, Throwable th) {
            this(status.value(), request.getRequestURI(), LocalDateTime.now(), th.getMessage(), th.getClass().getName());
        }

        public ErrorMsg(HttpStatus status, HttpServletRequest request, String message, Throwable th) {
            this(status.value(), request.getRequestURI(), LocalDateTime.now(), message, th.getClass().getName());
        }
    }
}
