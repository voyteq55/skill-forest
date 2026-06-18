CREATE TABLE enrolled_lessons
(
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    lesson_id UUID NOT NULL,
    course_enrollment_id UUID NOT NULL,
    enrolled_lesson_status VARCHAR(255) NOT NULL,
    CONSTRAINT pk_enrolled_lessons PRIMARY KEY (id)
);

ALTER TABLE enrolled_lessons
    ADD CONSTRAINT FK_ENROLLED_LESSONS_ON_COURSE_ENROLLMENT FOREIGN KEY (course_enrollment_id) REFERENCES course_enrollments (id);

ALTER TABLE enrolled_lessons
    ADD CONSTRAINT FK_ENROLLED_LESSONS_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES lessons (id);