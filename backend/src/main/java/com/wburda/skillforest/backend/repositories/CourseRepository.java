package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByCreatedBy(Teacher createdBy);
}
