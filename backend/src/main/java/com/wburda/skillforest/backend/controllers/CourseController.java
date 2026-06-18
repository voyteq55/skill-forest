package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/my-enrollments")
    public ResponseEntity<List<StudentCourseEnrollmentDTO>> getCourseEnrollmentsForCurrentStudent() {
        return ResponseEntity.ok(courseService.getAllStudentCourseEnrollmentForCurrentStudent());
    }
}
