package com.wburda.skillforest.backend.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CourseDTO {
    UUID id;
    String name;
}
