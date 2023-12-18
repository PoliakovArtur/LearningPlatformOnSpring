package repository;

import config.TestRepositoriesConfig;
import org.example.model.Teacher;
import org.example.repositories.impl.TeacherRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoriesConfig.class)
public class TeacherRepositoryTests {

    @Autowired
    private TeacherRepositoryImpl teacherRepository;

    @Test
    void testAdd() throws SQLException {
        Teacher teacher = new Teacher("Василий Петров", 50000L, 43);
        teacherRepository.save(teacher);
        List<Teacher> teachers = teacherRepository.findAll();
        teachers.forEach(System.out::println);
    }
}
