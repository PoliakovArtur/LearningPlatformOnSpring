CREATE TABLE teachers
(
    id     int unsigned NOT NULL AUTO_INCREMENT,
    name   varchar(45),
    salary int unsigned,
    age    int unsigned,
    PRIMARY KEY (id)
);
CREATE TABLE courses
(
    id          int unsigned NOT NULL AUTO_INCREMENT,
    name        varchar(500),
    type        enum ('DESIGN', 'PROGRAMMING', 'MARKETING', 'MANAGEMENT'),
    description varchar(500),
    teacher_id  int unsigned,
    price       int unsigned,
    PRIMARY KEY (id),
    KEY teacher_idx (teacher_id),
    CONSTRAINT teacher FOREIGN KEY (teacher_id) REFERENCES teachers (id)
 ON DELETE SET NULL
);
CREATE TABLE students
(
    id                int unsigned NOT NULL AUTO_INCREMENT,
    name              varchar(45),
    age               int unsigned,
    registration_date date,
    PRIMARY KEY (id)
);
CREATE TABLE subscriptions
(
    student_id        int unsigned,
    course_id         int unsigned,
    subscription_date date,
    UNIQUE KEY unq (student_id, course_id),
    KEY course_idx (course_id),
    CONSTRAINT course FOREIGN KEY (course_id) REFERENCES courses (id)
 ON DELETE SET NULL,
    CONSTRAINT student FOREIGN KEY (student_id) REFERENCES students (id)
 ON DELETE SET NULL
);