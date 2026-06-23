package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.EnrolledLessonDTO;
import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.services.CourseEnrollmentService;
import com.wburda.skillforest.backend.services.EnrolledLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;
    private final EnrolledLessonService enrolledLessonService;

    @Autowired
    public EnrollmentController(CourseEnrollmentService courseEnrollmentService, EnrolledLessonService enrolledLessonService) {
        this.courseEnrollmentService = courseEnrollmentService;
        this.enrolledLessonService = enrolledLessonService;
    }

    @GetMapping
    public ResponseEntity<List<StudentCourseEnrollmentDTO>> getCourseEnrollmentsForCurrentStudent() {
        return ResponseEntity.ok(courseEnrollmentService.getAllStudentCourseEnrollmentForCurrentStudent());
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<EnrolledLessonDTO>> getCourseEnrolledLessonsForCurrentStudent(@PathVariable UUID courseId) {
        return ResponseEntity.ok(enrolledLessonService.getAllEnrolledLessons(courseId));
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable UUID courseId) {
        courseEnrollmentService.enrollStudentInCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Map<String, String>> getEnrolledLessonContent(@PathVariable UUID courseId, @PathVariable UUID lessonId) {
        return ResponseEntity.ok(Map.of("content", enrolledLessonService.getEnrolledLessonContent(courseId, lessonId)));
    }
}
