package com.wburda.skillforest.backend.controllers;

import com.wburda.skillforest.backend.dto.LessonDTO;
import com.wburda.skillforest.backend.dto.LessonRequestDTO;
import com.wburda.skillforest.backend.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(LessonRequestDTO lessonRequestDTO) {
        LessonDTO newLesson = lessonService.createNewLesson(lessonRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLesson);
    }

}
