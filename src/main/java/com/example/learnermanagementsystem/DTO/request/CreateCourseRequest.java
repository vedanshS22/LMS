package com.example.learnermanagementsystem.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCourseRequest {

    @NotBlank(message = "Course code is required")
    @Size(max = 64, message = "Course code must be at most 64 characters")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "Course code may only contain letters, digits, dot, underscore, or hyphen")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name must be at most 255 characters")
    private String courseName;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
