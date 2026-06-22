package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.CourseDTO;
import com.wburda.skillforest.backend.dto.CourseRequestDTO;
import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseDTO>> getCoursesForCurrentTeacher() {
        return ResponseEntity.ok(courseService.getAllCoursesForCurrentTeacher());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createNewCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseDTO createdCourse = courseService.createNewCourse(courseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

}
