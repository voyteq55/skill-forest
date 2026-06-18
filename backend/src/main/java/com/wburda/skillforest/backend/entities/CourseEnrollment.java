package com.wburda.skillforest.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "course_enrollments")
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "valid_from", columnDefinition = "TIMESTAMPTZ")
    private Instant validFrom;

    @Column(name = "valid_to", columnDefinition = "TIMESTAMPTZ")
    private Instant validTo;

    @OneToMany(mappedBy = "courseEnrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnrolledLesson> enrolledLessons = new LinkedHashSet<>();

}