package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.StudentCourseEnrollmentDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.CourseEnrollment;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.exceptions.CourseAccessDeniedException;
import com.wburda.skillforest.backend.mappers.CourseMapper;
import com.wburda.skillforest.backend.repositories.CourseEnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseEnrollmentService {
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final UserService userService;
    private final CourseMapper courseMapper;
    private final CourseService courseService;

    public CourseEnrollmentService(CourseEnrollmentRepository courseEnrollmentRepository, UserService userService, CourseMapper courseMapper, CourseService courseService) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.userService = userService;
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }

    public List<StudentCourseEnrollmentDTO> getAllStudentCourseEnrollmentForCurrentStudent() {
        Student currentStudent = userService.getCurrentlyLoggedStudent();
        List<CourseEnrollment> courseEnrollments = courseEnrollmentRepository.findByStudent(currentStudent);
        return courseEnrollments.stream().map(courseMapper::toStudentCourseEnrollmentDTO).toList();
    }

    public void enrollStudentInCourse(UUID courseId) {
        Course course = courseService.findCourse(courseId);
        validateCourseCanBeEnrolled(course);

        Student currentStudent = userService.getCurrentlyLoggedStudent();
        Optional<CourseEnrollment> existingCourseEnrollment = course.getCourseEnrollments().stream()
                .filter(enrollment -> enrollment.getStudent().equals(currentStudent))
                .findFirst();

        if (existingCourseEnrollment.isEmpty()) {
            CourseEnrollment newCourseEnrollment = new CourseEnrollment();
            newCourseEnrollment.setStudent(currentStudent);
            newCourseEnrollment.setCourse(course);
            courseEnrollmentRepository.save(newCourseEnrollment);
        } else {
            System.out.println("already enrolled!");
        }
    }

    void validateCourseEnrollmentForCurrentStudent(Course course) {
        Student currentStudent = userService.getCurrentlyLoggedStudent();
        Optional<CourseEnrollment> existingCourseEnrollment = course.getCourseEnrollments().stream()
                .filter(enrollment -> enrollment.getStudent().equals(currentStudent))
                .findFirst();
        if (existingCourseEnrollment.isEmpty()) {
            throw new CourseAccessDeniedException("No permissions to access course");
        }
    }

    private void validateCourseCanBeEnrolled(Course course) {
        if (!course.isShareable()) {
            throw new CourseAccessDeniedException("Course is not public");
        }
    }
}
