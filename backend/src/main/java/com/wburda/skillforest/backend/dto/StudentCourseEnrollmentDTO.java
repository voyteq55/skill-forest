package com.wburda.skillforest.backend.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class StudentCourseEnrollmentDTO {
    UUID courseEnrollmentId;
    String courseName;
    String courseTeacherName;
    Instant validFrom;
    Instant validTo;
}
