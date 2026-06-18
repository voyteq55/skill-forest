ALTER TABLE course_enrollments
    ALTER COLUMN id SET DEFAULT gen_random_uuid();