package repository;

import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MySQLContainer;

@ExtendWith(MockitoExtension.class)
class TeacherRepositoryTests {

    private TeacherRepository teacherRepository;

    private static MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withInitScript("script.sql");

    @BeforeAll
    static void startContainer() {
        container.start();
    }

    @Test
    void testAdd() {
        Teacher teacher = new Teacher("w")
    }

    @AfterAll
    static void stopContainer() {
        container.stop();
    }
}
