package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.services.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;

    @Autowired
    public EnrollmentController(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentCourseEnrollmentDTO>> getCourseEnrollmentsForCurrentStudent() {
        return ResponseEntity.ok(courseEnrollmentService.getAllStudentCourseEnrollmentForCurrentStudent());
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable UUID courseId) {
        courseEnrollmentService.enrollStudentInCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
