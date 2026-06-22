package com.wburda.skillforest.backend.mappers;

import com.wburda.skillforest.backend.dto.LessonDTO;
import com.wburda.skillforest.backend.entities.Lesson;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LessonMapper {
    public LessonDTO toLessonDTO(Lesson lesson) {
        UUID previousLessonId = (lesson.getPrevious_lesson() != null) ? lesson.getPrevious_lesson().getId() : null;
        return LessonDTO.builder()
                .id(lesson.getId())
                .courseId(lesson.getCourse().getId())
                .previousLessonId(previousLessonId)
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .build();
    }
}
