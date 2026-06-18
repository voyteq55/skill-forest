package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.CourseDTO;
import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.CourseEnrollment;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.entities.Teacher;
import com.wburda.skillforest.backend.mappers.CourseMapper;
import com.wburda.skillforest.backend.repositories.CourseEnrollmentRepository;
import com.wburda.skillforest.backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    @Autowired
    public CourseService(CourseMapper courseMapper, CourseEnrollmentRepository courseEnrollmentRepository, UserService userService, CourseRepository courseRepository) {
        this.courseMapper = courseMapper;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<StudentCourseEnrollmentDTO> getAllStudentCourseEnrollmentForCurrentStudent() {
        Student currentStudent = userService.getCurrentlyLoggedStudent();
        List<CourseEnrollment> courseEnrollments = courseEnrollmentRepository.findByStudent(currentStudent);
        return courseEnrollments.stream().map(courseMapper::toStudentCourseEnrollmentDTO).toList();
    }

    public List<CourseDTO> getAllCoursesForCurrentTeacher() {
        Teacher currentTeacher = userService.getCurrentlyLoggedTeacher();
        List<Course> courses = courseRepository.findByCreatedBy(currentTeacher);
        return courses.stream().map(courseMapper::toCourseDTO).toList();
    }
}

