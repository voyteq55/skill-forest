package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.EnrolledLessonDTO;
import com.wburda.skillforest.backend.entities.*;
import com.wburda.skillforest.backend.entities.enums.EnrolledLessonStatus;
import com.wburda.skillforest.backend.mappers.LessonMapper;
import com.wburda.skillforest.backend.repositories.EnrolledLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrolledLessonService {
    private final CourseService courseService;
    private final CourseEnrollmentService courseEnrollmentService;
    private final UserService userService;
    private final LessonMapper lessonMapper;
    private final EnrolledLessonRepository enrolledLessonRepository;
    private final LessonService lessonService;

    @Autowired
    public EnrolledLessonService(CourseService courseService, CourseEnrollmentService courseEnrollmentService, UserService userService, LessonMapper lessonMapper, EnrolledLessonRepository enrolledLessonRepository, LessonService lessonService) {
        this.courseService = courseService;
        this.courseEnrollmentService = courseEnrollmentService;
        this.userService = userService;
        this.lessonMapper = lessonMapper;
        this.enrolledLessonRepository = enrolledLessonRepository;
        this.lessonService = lessonService;
    }

    public List<EnrolledLessonDTO> getAllEnrolledLessons(UUID courseId) {
        Course course = courseService.findCourse(courseId);
        courseEnrollmentService.validateCourseEnrollmentForCurrentStudent(course);
        Student currentStudent = userService.getCurrentlyLoggedStudent();
        CourseEnrollment courseEnrollment = course.getCourseEnrollments().stream()
                .filter(enrollment -> enrollment.getStudent().equals(currentStudent))
                .findFirst().orElseThrow();

        List<EnrolledLessonDTO> enrolledLessons = new ArrayList<>();
        for (Lesson lesson : course.getLessons()) {
            Optional<EnrolledLesson> optionalEnrolledLesson = lesson.getEnrolledLessons().stream()
                    .filter(enrLesson -> enrLesson.getCourseEnrollment().getStudent().equals(currentStudent))
                    .findFirst();

            if (optionalEnrolledLesson.isEmpty()) {
                EnrolledLesson newEnrolledLesson = new EnrolledLesson();
                newEnrolledLesson.setLesson(lesson);
                newEnrolledLesson.setCourseEnrollment(courseEnrollment);
                newEnrolledLesson.setEnrolledLessonStatus(EnrolledLessonStatus.NOT_STARTED);

                enrolledLessonRepository.save(newEnrolledLesson);
                enrolledLessons.add(lessonMapper.toEnrolledLessonDTO(newEnrolledLesson));
            } else {
                enrolledLessons.add(lessonMapper.toEnrolledLessonDTO(optionalEnrolledLesson.get()));
            }
        }

        return enrolledLessons;
    }

    public String getEnrolledLessonContent(UUID courseId, UUID lessonId) {
        validateEnrolledCourseAndLesson(courseId, lessonId);
        return lessonService.findLesson(lessonId).getContent();
    }

    public void updateEnrolledLessonStatus(UUID courseId, UUID lessonId, EnrolledLessonStatus status) {
        validateEnrolledCourseAndLesson(courseId, lessonId);
        Lesson lesson = lessonService.findLesson(lessonId);

        Student currentStudent = userService.getCurrentlyLoggedStudent();
        EnrolledLesson enrolledLesson = lesson.getEnrolledLessons().stream()
                .filter(enrLesson -> enrLesson.getCourseEnrollment().getStudent().equals(currentStudent))
                .findFirst().orElseThrow();

        enrolledLesson.setEnrolledLessonStatus(status);
        enrolledLessonRepository.save(enrolledLesson);
    }

    private void validateEnrolledCourseAndLesson(UUID courseId, UUID lessonId) {
        Course course = courseService.findCourse(courseId);
        courseEnrollmentService.validateCourseEnrollmentForCurrentStudent(course);
        Lesson lesson = lessonService.findLesson(lessonId);
        lessonService.validateLessonsWithSameCourseId(lesson.getCourse().getId(), courseId);
    }

}
