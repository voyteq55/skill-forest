package com.wburda.skillforest.backend.dto;

import com.wburda.skillforest.backend.entities.enums.EnrolledLessonStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class EnrolledLessonDTO {
    UUID lessonId;
    UUID courseId;
    UUID previousLessonId;
    String title;
    EnrolledLessonStatus enrolledLessonStatus;
}
