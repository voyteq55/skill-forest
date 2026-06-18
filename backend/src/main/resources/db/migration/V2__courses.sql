CREATE TABLE courses
(
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_by_user_id UUID NOT NULL,
    shareable_url VARCHAR(255),
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

ALTER TABLE courses
    ADD CONSTRAINT uc_courses_shareable_url UNIQUE (shareable_url);

ALTER TABLE courses
    ADD CONSTRAINT FK_COURSES_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES teachers (user_id);