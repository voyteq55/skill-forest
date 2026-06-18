package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.entities.CourseEnrollment;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.mappers.CourseMapper;
import com.wburda.skillforest.backend.repositories.CourseEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final UserService userService;

    @Autowired
    public CourseService(CourseMapper courseMapper, CourseEnrollmentRepository courseEnrollmentRepository, UserService userService) {
        this.courseMapper = courseMapper;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.userService = userService;
    }

    public List<StudentCourseEnrollmentDTO> getAllStudentCourseEnrollmentForCurrentStudent() {
        Student currentStudent = userService.getCurrentlyLoggedStudent();
        List<CourseEnrollment> courseEnrollments = courseEnrollmentRepository.findByStudent(currentStudent);
        return courseEnrollments.stream().map(courseMapper::toStudentCourseEnrollmentDTO).toList();
    }
}

