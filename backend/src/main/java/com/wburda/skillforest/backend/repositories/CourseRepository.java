package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
