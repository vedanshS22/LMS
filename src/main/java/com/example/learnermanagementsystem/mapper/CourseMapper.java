package com.example.learnermanagementsystem.mapper;

import com.example.learnermanagementsystem.DTO.CourseDTO;
import com.example.learnermanagementsystem.DTO.request.CreateCourseRequest;
import com.example.learnermanagementsystem.Entity.Course;

public final class CourseMapper {

    private CourseMapper() {
    }

    public static Course toEntity(CreateCourseRequest request) {
        Course course = new Course();
        course.setCourseCode(request.getCourseCode().trim().toUpperCase());
        course.setCourseName(request.getCourseName().trim());
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            course.setDescription(request.getDescription().trim());
        }
        return course;
    }

    public static CourseDTO toDto(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseName(course.getCourseName());
        dto.setDescription(course.getDescription());
        return dto;
    }
}
