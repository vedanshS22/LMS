package com.example.learnermanagementsystem.Controller;

import com.example.learnermanagementsystem.DTO.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Plain helpers for building {@link ApiErrorResponse} bodies. Exception handling and HTTP mapping
 * stay on each {@link org.springframework.web.bind.annotation.RestController} via
 * {@link org.springframework.web.bind.annotation.ExceptionHandler}.
 */
public final class RestErrorBodies {

    private RestErrorBodies() {
    }

    public static ResponseEntity<ApiErrorResponse> status(HttpStatus status, String message,
                                                          HttpServletRequest request,
                                                          Map<String, String> fieldErrors) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<ApiErrorResponse> beanValidation(MethodArgumentNotValidException ex,
                                                                  HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value");
        }
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for one or more fields",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ApiErrorResponse> constraintViolation(ConstraintViolationException ex,
                                                                       HttpServletRequest request) {
        Map<String, String> fieldErrors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for one or more parameters",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ApiErrorResponse> unreadableJson(HttpMessageNotReadableException ignored,
                                                                  HttpServletRequest request) {
        return status(HttpStatus.BAD_REQUEST, "Request body is missing or malformed JSON", request, null);
    }

    public static ResponseEntity<ApiErrorResponse> typeMismatch(MethodArgumentTypeMismatchException ex,
                                                                HttpServletRequest request) {
        String name = ex.getName() != null ? ex.getName() : "parameter";
        return status(HttpStatus.BAD_REQUEST, "Invalid value for '" + name + "'", request, null);
    }

    public static ResponseEntity<ApiErrorResponse> dataIntegrity(DataIntegrityViolationException ex,
                                                                 HttpServletRequest request) {
        String message = "The request could not be completed due to a data constraint";
        if (ex.getMostSpecificCause() != null && ex.getMostSpecificCause().getMessage() != null) {
            String cause = ex.getMostSpecificCause().getMessage();
            if (cause.contains("LEARNER_EMAIL") || cause.toLowerCase().contains("learner_email")) {
                message = "A learner with this email already exists";
            } else if (cause.contains("COURSE_CODE") || cause.toLowerCase().contains("course_code")) {
                message = "A course with this code already exists";
            }
        }
        return status(HttpStatus.CONFLICT, message, request, null);
    }
}
