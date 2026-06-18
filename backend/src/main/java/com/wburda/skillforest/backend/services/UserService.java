package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.StudentDTO;
import com.wburda.skillforest.backend.dto.TeacherDTO;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.mappers.UserMapper;
import com.wburda.skillforest.backend.repositories.StudentRepository;
import com.wburda.skillforest.backend.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(StudentRepository studentRepository, TeacherRepository teacherRepository, UserMapper userMapper) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userMapper = userMapper;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(userMapper::toStudentDTO)
                .toList();
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(userMapper::toTeacherDTO)
                .toList();
    }

    public Student getCurrentlyLoggedStudent() {
        // TODO: add auth instead of this temporary placeholder
        return studentRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No users"));
    }
}
