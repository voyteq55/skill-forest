package com.wburda.skillforest.backend.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class StudentDTO {
    UUID id;
    String name;
}
