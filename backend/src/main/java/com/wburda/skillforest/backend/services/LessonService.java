package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.LessonDTO;
import com.wburda.skillforest.backend.dto.LessonRequestDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.Lesson;
import com.wburda.skillforest.backend.exceptions.BadRequestException;
import com.wburda.skillforest.backend.exceptions.ResourceNotFoundException;
import com.wburda.skillforest.backend.mappers.LessonMapper;
import com.wburda.skillforest.backend.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<LessonDTO> getAllCourseLessons(UUID id) {
        Course course = courseService.findCourse(id);
        courseService.validateCourseOwnership(course);
        return course.getLessons().stream().map(lessonMapper::toLessonDTO).toList();
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
            validateLessonsWithSameCourseId(courseId, previousLesson.getCourse().getId());
            lesson.setPrevious_lesson(previousLesson);
        }

        lesson.setTitle(lessonRequestDTO.getTitle());
        lesson.setContent(lessonRequestDTO.getContent());

        lessonRepository.save(lesson);

        return lessonMapper.toLessonDTO(lesson);
    }

    public LessonDTO updateLesson(LessonRequestDTO lessonRequestDTO, UUID courseId, UUID lessonId) {
        Lesson lesson = findLesson(lessonId);
        validateLessonsWithSameCourseId(lesson.getCourse().getId(), courseId);
        courseService.validateCourseOwnership(lesson.getCourse());

        UUID previousLessonId = lessonRequestDTO.getPreviousLessonId();
        if (previousLessonId == null) {
            lesson.setPrevious_lesson(null);
        } else {
            Lesson previousLesson = findLesson(previousLessonId);
            validateLessonsWithSameCourseId(courseId, previousLesson.getCourse().getId());
            lesson.setPrevious_lesson(previousLesson);
        }

        lesson.setTitle(lessonRequestDTO.getTitle());
        lesson.setContent(lessonRequestDTO.getContent());

        lessonRepository.save(lesson);

        return lessonMapper.toLessonDTO(lesson);
    }

    public void deleteLesson(UUID lessonId) {
        Lesson lesson = findLesson(lessonId);
        courseService.validateCourseOwnership(lesson.getCourse());
        lessonRepository.delete(lesson);
    }

    Lesson findLesson(UUID id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

    void validateLessonsWithSameCourseId(UUID id1, UUID id2){
        if (!id1.equals(id2)) {
            throw new BadRequestException("Invalid course");
        }
    }
}
