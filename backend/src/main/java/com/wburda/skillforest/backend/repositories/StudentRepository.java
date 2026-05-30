package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
