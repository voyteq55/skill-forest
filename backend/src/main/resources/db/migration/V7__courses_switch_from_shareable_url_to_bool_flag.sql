ALTER TABLE courses
    DROP CONSTRAINT uc_courses_shareable_url,
    DROP COLUMN shareable_url,
    ADD is_shareable BOOLEAN NOT NULL DEFAULT FALSE;