CREATE TABLE students
(
    user_id UUID NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (user_id)
);

CREATE TABLE teachers
(
    user_id UUID NOT NULL,
    CONSTRAINT pk_teachers PRIMARY KEY (user_id)
);

CREATE TABLE users
(
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_ID FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE teachers
    ADD CONSTRAINT FK_TEACHERS_ON_ID FOREIGN KEY (user_id) REFERENCES users (id);