package repository;

import config.TestRepositoriesConfig;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Subscription;
import org.example.model.Teacher;
import org.example.repositories.CourseRepository;
import org.example.repositories.StudentRepository;
import org.example.repositories.SubscriptionRepository;
import org.example.repositories.TeacherRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static entity_factory.EntitiesForTests.COURSE;
import static entity_factory.EntitiesForTests.ID;
import static entity_factory.EntitiesForTests.STUDENT;
import static entity_factory.EntitiesForTests.SUBSCRIPTION;
import static entity_factory.EntitiesForTests.TEACHER;
import static entity_factory.EntitiesForTests.VALID_SUBSCRIPTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoriesConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTests {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private Student student;

    private Teacher teacher;

    private Course course;

    private Subscription subscription;

    @Test
    @Order(1)
    public void findByIdTest() {
        saveEntities();
        teacher = teacherRepository.findById(ID).get();
        student = studentRepository.findById(ID).get();
        course = courseRepository.findById(ID).get();
        subscription = subscriptionRepository.findById(VALID_SUBSCRIPTION_ID).get();

        assertEquals(TEACHER, teacher);
        assertEquals(COURSE, course);

        assertEquals(STUDENT.getId(), student.getId());
        assertEquals(STUDENT.getName(), student.getName());
        assertEquals(STUDENT.getAge(), student.getAge());
        assertNotNull(student.getRegistrationDate());

        assertEquals(SUBSCRIPTION.getId(), subscription.getId());
    }

    private void saveEntities() {
        teacherRepository.save(TEACHER);
        studentRepository.save(STUDENT);
        courseRepository.save(COURSE);
        subscriptionRepository.update(VALID_SUBSCRIPTION_ID);
    }

    @Test
    @Order(2)
    public void findAllTest() {
        assertEquals(List.of(teacher), teacherRepository.findAll());
        assertEquals(List.of(student), studentRepository.findAll());
        assertEquals(List.of(course), courseRepository.findAll());
        assertEquals(List.of(subscription), subscriptionRepository.findAll());
    }

    @Test
    @Order(3)
    public void updateByIdTest() {
        student.setName("Порогов Филат");
        teacher.setName("Пузырьков Пельмений");
        course.setDescription("Верстка HTML страниц");

        assertTrue(studentRepository.update(student));
        assertTrue(teacherRepository.update(teacher));
        assertTrue(courseRepository.update(course));

        Teacher changedTeacher = teacherRepository.findById(ID).get();
        Student changedStudent = studentRepository.findById(ID).get();
        Course changedCourse = courseRepository.findById(ID).get();

        assertEquals(teacher, changedTeacher);
        assertEquals(student, changedStudent);
        assertEquals(course, changedCourse);
    }

    @Test
    @Order(4)
    public void deleteByIdTest() {
        assertTrue(subscriptionRepository.deleteById(VALID_SUBSCRIPTION_ID));
        assertTrue(courseRepository.deleteById(ID));
        assertTrue(studentRepository.deleteById(ID));
        assertTrue(teacherRepository.deleteById(ID));

        assertTrue(studentRepository.findById(ID).isEmpty());
        assertTrue(teacherRepository.findById(ID).isEmpty());
        assertTrue(courseRepository.findById(ID).isEmpty());
        assertTrue(subscriptionRepository.findById(VALID_SUBSCRIPTION_ID).isEmpty());
    }

}
