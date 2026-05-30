package com.wburda.skillforest.backend.repositories;

import com.wburda.skillforest.backend.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
