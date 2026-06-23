package com.wburda.skillforest.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CourseAccessDeniedException extends RuntimeException {
    public CourseAccessDeniedException(String message) {
        super(message);
    }
}