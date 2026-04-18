package com.example.learnermanagementsystem.Service;

import com.example.learnermanagementsystem.DTO.CourseDTO;
import com.example.learnermanagementsystem.DTO.request.CreateCourseRequest;
import com.example.learnermanagementsystem.Exception.CourseNotFoundException;
import com.example.learnermanagementsystem.Exception.DuplicateCourseCodeException;
import com.example.learnermanagementsystem.Entity.Course;
import com.example.learnermanagementsystem.Repository.CourseRepository;
import com.example.learnermanagementsystem.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public CourseDTO createCourse(CreateCourseRequest request) {
        String code = request.getCourseCode().trim().toUpperCase();
        if (courseRepository.existsByCourseCode(code)) {
            throw new DuplicateCourseCodeException("A course with this code already exists");
        }
        Course saved = courseRepository.save(CourseMapper.toEntity(request));
        return CourseMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .map(CourseMapper::toDto)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> listCourses() {
        return courseRepository.findAll().stream()
                .map(CourseMapper::toDto)
                .toList();
    }
}
