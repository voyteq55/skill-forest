package com.wburda.skillforest.backend.entities;

import com.wburda.skillforest.backend.entities.enums.EnrolledLessonStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "enrolled_lessons")
public class EnrolledLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_enrollment_id", nullable = false)
    private CourseEnrollment courseEnrollment;

    @Enumerated(EnumType.STRING)
    @Column(name = "enrolled_lesson_status", nullable = false)
    private EnrolledLessonStatus enrolledLessonStatus;

}