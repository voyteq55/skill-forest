package com.wburda.skillforest.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "previous_lesson_id")
    private Lesson previous_lesson;

    @Column(name = "title", nullable = false)
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}