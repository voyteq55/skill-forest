package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.dto.CourseDTO;
import com.wburda.skillforest.backend.dto.CourseRequestDTO;
import com.wburda.skillforest.backend.entities.Course;
import com.wburda.skillforest.backend.entities.Teacher;
import com.wburda.skillforest.backend.exceptions.BadRequestException;
import com.wburda.skillforest.backend.exceptions.CourseAccessDeniedException;
import com.wburda.skillforest.backend.exceptions.ResourceNotFoundException;
import com.wburda.skillforest.backend.mappers.CourseMapper;
import com.wburda.skillforest.backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final UserService userService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    public CourseService(CourseMapper courseMapper, UserService userService, CourseRepository courseRepository) {
        this.courseMapper = courseMapper;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<CourseDTO> getAllCoursesForCurrentTeacher() {
        Teacher currentTeacher = userService.getCurrentlyLoggedTeacher();
        List<Course> courses = courseRepository.findByCreatedBy(currentTeacher);
        return courses.stream().map(courseMapper::toCourseDTO).toList();
    }

    public CourseDTO createNewCourse(CourseRequestDTO courseRequestDTO) {
        Course newCourse = new Course();
        newCourse.setName(courseRequestDTO.getName());
        newCourse.setCreatedBy(userService.getCurrentlyLoggedTeacher());
        courseRepository.save(newCourse);
        return courseMapper.toCourseDTO(newCourse);
    }

    public CourseDTO updateCourse(UUID id, CourseRequestDTO courseRequestDTO) {
        Course course = findCourse(id);
        validateCourseOwnership(course);
        course.setName(courseRequestDTO.getName());
        courseRepository.save(course);
        return courseMapper.toCourseDTO(course);
    }

    public void deleteCourse(UUID id) {
        Course course = findCourse(id);
        validateCourseOwnership(course);
        courseRepository.delete(course);
    }

    public String getShareableLink(UUID id) {
        Course course = findCourse(id);
        validateCourseOwnership(course);
        String shareableLink = frontendUrl + "/join/" + id;

        if (!course.isShareable()) {
            course.setShareable(true);
            courseRepository.save(course);
        }

        return shareableLink;
    }

    Course findCourse(UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    void validateCourseOwnership(Course course) {
        Teacher currentTeacher = userService.getCurrentlyLoggedTeacher();
        if (!course.getCreatedBy().equals(currentTeacher)) {
            throw new CourseAccessDeniedException("No permissions to access the course");
        }
    }

}

