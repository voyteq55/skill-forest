package com.wburda.skillforest.backend.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class LessonRequestDTO {
    UUID previousLessonId;
    String title;
    String content;
}
