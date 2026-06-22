package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.LessonDTO;
import com.wburda.skillforest.backend.dto.LessonRequestDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.Lesson;
import com.wburda.skillforest.backend.mappers.LessonMapper;
import com.wburda.skillforest.backend.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseService courseService;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.courseService = courseService;
    }

    public LessonDTO createNewLesson(LessonRequestDTO lessonRequestDTO, UUID courseId) {
        Lesson lesson = new Lesson();

        Course course = courseService.findCourse(courseId);
        courseService.validateCourseOwnership(course);
        lesson.setCourse(course);

        UUID previousLessonId = lessonRequestDTO.getPreviousLessonId();
        if (previousLessonId == null) {
            lesson.setPrevious_lesson(null);
        } else {
            Lesson previousLesson = findLesson(previousLessonId);
            validateLessonsFromSameCourse(lesson, previousLesson);
            lesson.setPrevious_lesson(previousLesson);
        }

        lesson.setTitle(lessonRequestDTO.getTitle());
        lesson.setContent(lessonRequestDTO.getContent());

        lessonRepository.save(lesson);

        return lessonMapper.toLessonDTO(lesson);
    }

    Lesson findLesson(UUID id) {
        return lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    void validateLessonsFromSameCourse(Lesson lesson1, Lesson lesson2){
        if (!lesson1.getCourse().equals(lesson2.getCourse())) {
            throw new RuntimeException("Invalid previous lesson (from a different course)");
        }
    }
}
