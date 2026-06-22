package com.wburda.skillforest.backend.config.dev;

import com.wburda.skillforest.backend.entities.*;
import com.wburda.skillforest.backend.repositories.CourseEnrollmentRepository;
import com.wburda.skillforest.backend.repositories.CourseRepository;
import com.wburda.skillforest.backend.repositories.StudentRepository;
import com.wburda.skillforest.backend.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    public DataSeeder(StudentRepository studentRepository, TeacherRepository teacherRepository, CourseRepository courseRepository, CourseEnrollmentRepository courseEnrollmentRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
    }

    @Override
    public void run(String... args) {
        seedStudents();
        seedTeachers();
        seedCourses();
    }

    private void seedStudents() {
        if (studentRepository.count() > 0) {
            return;
        }

        User studentUser1 = new User();
        studentUser1.setName("tom123");
        Student student1 = new Student();
        student1.setUser(studentUser1);

        User studentUser2 = new User();
        studentUser2.setName("mark");
        Student student2 = new Student();
        student2.setUser(studentUser2);

        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("students seeded");
    }

    private void seedTeachers() {
        if (teacherRepository.count() > 0) {
            return;
        }

        User teacherUser1 = new User();
        teacherUser1.setName("mr bill");
        Teacher teacher1 = new Teacher();
        teacher1.setUser(teacherUser1);

        User teacherUser2 = new User();
        teacherUser2.setName("mrs smith");
        Teacher teacher2 = new Teacher();
        teacher2.setUser(teacherUser2);

        teacherRepository.saveAll(List.of(teacher1, teacher2));

        System.out.println("teachers seeded");
    }

    private void seedCourses() {
        if (courseRepository.count() > 0) {
            return;
        }

        List<Teacher> teachers = teacherRepository.findAll();
        List<Student> students = studentRepository.findAll();

        Course biologyCourse = new Course();
        biologyCourse.setName("Biology");
        biologyCourse.setCreatedBy(teachers.get(0));

        CourseEnrollment student1BiologyEnrollment = new CourseEnrollment();
        student1BiologyEnrollment.setCourse(biologyCourse);
        student1BiologyEnrollment.setStudent(students.get(0));
        student1BiologyEnrollment.setValidTo(Instant.now());

        Course chemistryCourse = new Course();
        chemistryCourse.setName("Chemistry");
        chemistryCourse.setCreatedBy(teachers.get(1));

        CourseEnrollment student1ChemistryEnrollment = new CourseEnrollment();
        student1ChemistryEnrollment.setCourse(chemistryCourse);
        student1ChemistryEnrollment.setStudent(students.get(0));

        CourseEnrollment student2ChemistryEnrollment = new CourseEnrollment();
        student2ChemistryEnrollment.setCourse(chemistryCourse);
        student2ChemistryEnrollment.setStudent(students.get(1));
        student2ChemistryEnrollment.setValidFrom(Instant.now());

        courseRepository.saveAll(List.of(biologyCourse, chemistryCourse));
        courseEnrollmentRepository.saveAll(List.of(student1BiologyEnrollment, student1ChemistryEnrollment, student2ChemistryEnrollment));

        System.out.println("courses seeded");
    }
}
