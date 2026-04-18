package com.example.learnermanagementsystem.Controller;

import com.example.learnermanagementsystem.DTO.ApiErrorResponse;
import com.example.learnermanagementsystem.DTO.LearnerDTO;
import com.example.learnermanagementsystem.DTO.request.CreateLearnerRequest;
import com.example.learnermanagementsystem.Exception.DuplicateEmailException;
import com.example.learnermanagementsystem.Exception.LearnerNotFoundException;
import com.example.learnermanagementsystem.Service.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/learners")
@Validated
public class LearnerController {

    @Autowired
    private LearnerService learnerService;

    @PostMapping
    public ResponseEntity<LearnerDTO> createLearner(@Valid @RequestBody CreateLearnerRequest request) {
        LearnerDTO created = learnerService.createLearner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{learnerId}")
    public ResponseEntity<LearnerDTO> getLearnerById(@PathVariable @Positive(message = "learnerId must be positive") Long learnerId) {
        return ResponseEntity.ok(learnerService.getLearnerById(learnerId));
    }

    @GetMapping
    public ResponseEntity<List<LearnerDTO>> listLearners(
            @RequestParam(required = false) @Size(max = 255, message = "name filter must be at most 255 characters") String name) {
        return ResponseEntity.ok(learnerService.listLearners(Optional.ofNullable(name)));
    }

    @ExceptionHandler(LearnerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLearnerNotFound(LearnerNotFoundException ex,
                                                                 HttpServletRequest request) {
        return RestErrorBodies.status(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateEmail(DuplicateEmailException ex,
                                                                 HttpServletRequest request) {
        return RestErrorBodies.status(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableJson(HttpMessageNotReadableException ex,
                                                                 HttpServletRequest request) {
        return RestErrorBodies.unreadableJson(ex, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                               HttpServletRequest request) {
        return RestErrorBodies.typeMismatch(ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleBeanValidation(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {
        return RestErrorBodies.beanValidation(ex, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
                                                                      HttpServletRequest request) {
        return RestErrorBodies.constraintViolation(ex, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex,
                                                                HttpServletRequest request) {
        return RestErrorBodies.dataIntegrity(ex, request);
    }
}
