package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.*;
import com.wburda.skillforest.backend.services.CourseService;
import com.wburda.skillforest.backend.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @Autowired
    public CourseController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/my-enrollments")
    public ResponseEntity<List<StudentCourseEnrollmentDTO>> getCourseEnrollmentsForCurrentStudent() {
        return ResponseEntity.ok(courseService.getAllStudentCourseEnrollmentForCurrentStudent());
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCoursesForCurrentTeacher() {
        return ResponseEntity.ok(courseService.getAllCoursesForCurrentTeacher());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createNewCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseDTO createdCourse = courseService.createNewCourse(courseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID courseId, @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(courseId, courseRequestDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/share")
    public ResponseEntity<Map<String, String>> getShareableLink(@PathVariable UUID courseId) {
        String shareableLink = courseService.getShareableLink(courseId);
        return ResponseEntity.ok(Map.of("shareableLink", shareableLink));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<LessonDTO>> getCourseLessons(@PathVariable UUID courseId) {
        return ResponseEntity.ok(lessonService.getAllCourseLessons(courseId));
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<LessonDTO> createLesson(LessonRequestDTO lessonRequestDTO, @PathVariable UUID courseId) {
        LessonDTO newLesson = lessonService.createNewLesson(lessonRequestDTO, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLesson);
    }

    @PutMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonDTO> updateLesson(LessonRequestDTO lessonRequestDTO, @PathVariable UUID courseId, @PathVariable UUID lessonId) {
        LessonDTO updatedLesson = lessonService.updateLesson(lessonRequestDTO, courseId, lessonId);
        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID courseId, @PathVariable UUID lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

}
