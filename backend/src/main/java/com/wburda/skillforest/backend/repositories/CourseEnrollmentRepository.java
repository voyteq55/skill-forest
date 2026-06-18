package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.CourseEnrollment;
import com.wburda.skillforest.backend.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, UUID> {
    List<CourseEnrollment> findByStudent(Student student);
}
