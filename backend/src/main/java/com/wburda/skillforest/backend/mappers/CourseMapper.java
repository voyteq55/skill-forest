package com.wburda.skillforest.backend.mappers;

import com.wburda.skillforest.backend.dto.CourseDTO;
import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.CourseEnrollment;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public StudentCourseEnrollmentDTO toStudentCourseEnrollmentDTO(CourseEnrollment courseEnrollment) {
        return StudentCourseEnrollmentDTO.builder()
                .courseEnrollmentId(courseEnrollment.getId())
                .courseName(courseEnrollment.getCourse().getName())
                .courseTeacherName(courseEnrollment.getCourse().getCreatedBy().getUser().getName())
                .validFrom(courseEnrollment.getValidFrom())
                .validTo(courseEnrollment.getValidTo())
                .build();
    }

    public CourseDTO toCourseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }
}
