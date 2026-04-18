package com.example.learnermanagementsystem.Controller;

import com.example.learnermanagementsystem.DTO.ApiErrorResponse;
import com.example.learnermanagementsystem.DTO.CourseDTO;
import com.example.learnermanagementsystem.DTO.request.CreateCourseRequest;
import com.example.learnermanagementsystem.Exception.CourseNotFoundException;
import com.example.learnermanagementsystem.Exception.DuplicateCourseCodeException;
import com.example.learnermanagementsystem.Service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@Validated
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        CourseDTO created = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(
            @PathVariable @Positive(message = "courseId must be positive") Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> listCourses() {
        return ResponseEntity.ok(courseService.listCourses());
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCourseNotFound(CourseNotFoundException ex,
                                                                 HttpServletRequest request) {
        return RestErrorBodies.status(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(DuplicateCourseCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateCourseCode(DuplicateCourseCodeException ex,
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
