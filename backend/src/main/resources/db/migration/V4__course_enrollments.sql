CREATE TABLE course_enrollments
(
    id UUID NOT NULL,
    student_id UUID NOT NULL,
    course_id UUID NOT NULL,
    valid_from TIMESTAMPTZ,
    valid_to TIMESTAMPTZ,
    CONSTRAINT pk_course_enrollments PRIMARY KEY (id)
);

ALTER TABLE course_enrollments
    ADD CONSTRAINT FK_COURSE_ENROLLMENTS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE course_enrollments
    ADD CONSTRAINT FK_COURSE_ENROLLMENTS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (user_id);