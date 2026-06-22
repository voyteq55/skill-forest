package com.wburda.skillforest.backend.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class LessonDTO {
    UUID id;
    UUID courseId;
    UUID previousLessonId;
    String title;
    String content;
}
