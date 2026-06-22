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

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseDTO>> getCoursesForCurrentTeacher() {
        return ResponseEntity.ok(courseService.getAllCoursesForCurrentTeacher());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createNewCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseDTO createdCourse = courseService.createNewCourse(courseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID id, @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseRequestDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/share")
    public ResponseEntity<Map<String, String>> getShareableLink(@PathVariable UUID id) {
        String shareableLink = courseService.getShareableLink(id);
        return ResponseEntity.ok(Map.of("shareableLink", shareableLink));
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<List<LessonDTO>> getCourseLessons(@PathVariable UUID id) {
        return ResponseEntity.ok(lessonService.getAllCourseLessons(id));
    }

    @PostMapping("/{id}/lessons")
    public ResponseEntity<LessonDTO> createLesson(LessonRequestDTO lessonRequestDTO, @PathVariable UUID id) {
        LessonDTO newLesson = lessonService.createNewLesson(lessonRequestDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLesson);
    }

    @PutMapping("/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<LessonDTO> updateLesson(LessonRequestDTO lessonRequestDTO, @PathVariable UUID course_id, @PathVariable UUID lesson_id) {
        LessonDTO updatedLesson = lessonService.updateLesson(lessonRequestDTO, course_id, lesson_id);
        return ResponseEntity.ok(updatedLesson);
    }

}
