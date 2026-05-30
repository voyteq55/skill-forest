package com.wburda.skillforest.backend.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class TeacherDTO {
    UUID id;
    String name;
}
