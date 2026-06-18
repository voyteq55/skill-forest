CREATE TABLE lessons
(
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    course_id UUID NOT NULL,
    previous_lesson_id UUID,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    CONSTRAINT pk_lessons PRIMARY KEY (id)
);

ALTER TABLE lessons
    ADD CONSTRAINT FK_LESSONS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE lessons
    ADD CONSTRAINT FK_LESSONS_ON_PREVIOUS_LESSON FOREIGN KEY (previous_lesson_id) REFERENCES lessons (id);