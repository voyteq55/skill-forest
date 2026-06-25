package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.StudentDTO;
import com.wburda.skillforest.backend.dto.TeacherDTO;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.entities.Teacher;
import com.wburda.skillforest.backend.entities.User;
import com.wburda.skillforest.backend.entities.enums.UserRole;
import com.wburda.skillforest.backend.exceptions.AuthenticationException;
import com.wburda.skillforest.backend.exceptions.InvalidRoleException;
import com.wburda.skillforest.backend.mappers.UserMapper;
import com.wburda.skillforest.backend.repositories.StudentRepository;
import com.wburda.skillforest.backend.repositories.TeacherRepository;
import com.wburda.skillforest.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(StudentRepository studentRepository, TeacherRepository teacherRepository, UserMapper userMapper, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
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
        User user = getCurrentUser();

        if (user.getStudent() == null) {
            throw new InvalidRoleException("User must have a 'Student' role to perform this operation");
        }

        return user.getStudent();
    }

    public Teacher getCurrentlyLoggedTeacher() {
        User user = getCurrentUser();

        if (user.getTeacher() == null) {
            throw new InvalidRoleException("User must have a 'Teacher' role to perform this operation");
        }

        return user.getTeacher();
    }

    public void assignUserRole(UserRole role) {
        User user = getCurrentUser();
        if (user.getStudent() != null || user.getTeacher() != null) {
            throw new InvalidRoleException("User already has an assigned role");
        }

        if (role == UserRole.STUDENT) {
            Student student = new Student();
            student.setUser(user);
            studentRepository.save(student);
        } else if (role == UserRole.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacherRepository.save(teacher);
        }
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AuthenticationException("No authenticated user found");
        }

        if (!(auth.getPrincipal() instanceof OAuth2User principal)) {
            throw new AuthenticationException("Current user is not an OAuth2User");
        }

        String googleSub = principal.getAttribute("sub");
        return userRepository.findByGoogleSub(googleSub).orElseThrow(() -> new AuthenticationException("User with sub not found"));
    }
}
