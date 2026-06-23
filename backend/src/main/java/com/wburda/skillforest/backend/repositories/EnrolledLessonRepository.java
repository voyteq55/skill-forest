package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.EnrolledLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrolledLessonRepository extends JpaRepository<EnrolledLesson, UUID> {
}
