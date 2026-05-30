package com.wburda.skillforest.backend.config.dev;

import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.entities.Teacher;
import com.wburda.skillforest.backend.entities.User;
import com.wburda.skillforest.backend.repositories.StudentRepository;
import com.wburda.skillforest.backend.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public DataSeeder(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void run(String... args) {
        seedStudents();
        seedTeachers();
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
}
