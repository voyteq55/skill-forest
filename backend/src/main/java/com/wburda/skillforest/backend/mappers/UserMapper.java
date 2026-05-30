package com.wburda.skillforest.backend.mappers;

import com.wburda.skillforest.backend.dto.StudentDTO;
import com.wburda.skillforest.backend.dto.TeacherDTO;
import com.wburda.skillforest.backend.entities.Student;
import com.wburda.skillforest.backend.entities.Teacher;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public StudentDTO toStudentDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getUser_id())
                .name(student.getUser().getName())
                .build();
    }

    public TeacherDTO toTeacherDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getUser_id())
                .name(teacher.getUser().getName())
                .build();
    }
}
