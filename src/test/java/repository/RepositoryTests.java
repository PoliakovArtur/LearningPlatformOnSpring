package repository;

import config.TestRepositoriesConfig;
import org.example.model.Course;
import org.example.model.CourseType;
import org.example.model.Student;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
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

    private final static Long ID = 1L;

    private Student student = new Student(ID, "Петров Александр", 30);

    private Teacher teacher = new Teacher(ID, "Вареньев Алишер", 30000L, 30);

    private Teacher teacherWithId = new Teacher(ID);

    private Course course = new Course(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", teacherWithId,100000L);

    private SubscriptionKey subscriptionKey = new SubscriptionKey(ID, ID);

    private Subscription subscription;

    @Test
    @Order(1)
    public void findByIdTest() {
        saveEntities();
        assertEquals(teacher, teacherRepository.findById(ID).get());
        assertEquals(course, courseRepository.findById(ID).get());
        assertEquals(student, studentRepository.findById(ID).get());
        subscription = subscriptionRepository.findById(subscriptionKey).get();
        assertNotNull(subscription.getSubscriptionDate());
    }

    private void saveEntities() {
        teacherRepository.save(teacher);
        studentRepository.save(student);
        courseRepository.save(course);
        subscriptionRepository.save(subscriptionKey);
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
        assertTrue(subscriptionRepository.update(subscriptionKey));

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
        assertTrue(subscriptionRepository.deleteById(subscriptionKey));
        assertTrue(courseRepository.deleteById(ID));
        assertTrue(studentRepository.deleteById(ID));
        assertTrue(teacherRepository.deleteById(ID));

        assertTrue(studentRepository.findById(ID).isEmpty());
        assertTrue(teacherRepository.findById(ID).isEmpty());
        assertTrue(courseRepository.findById(ID).isEmpty());
        assertTrue(subscriptionRepository.findById(subscriptionKey).isEmpty());
    }
}
