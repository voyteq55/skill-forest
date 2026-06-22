package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
