package entity_factory;

import org.example.model.Course;
import org.example.model.CourseType;
import org.example.model.Student;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.model.Teacher;

import java.time.LocalDateTime;
import java.util.List;

public class EntitiesForTests {

    public final static LocalDateTime FIXED_DATE = LocalDateTime.of(2023, 12, 23, 22, 22, 22);
    public final static Long ID = 1L;
    public final static SubscriptionKey VALID_SUBSCRIPTION_ID = new SubscriptionKey(ID, ID);
    public final static SubscriptionKey INVALID_SUBSCRIPTION_ID = new SubscriptionKey();

    public final static Student STUDENT = new Student(ID, "Петров Александр", 30, FIXED_DATE);
    public final static Student EMPTY_STUDENT = new Student();
    public final static Student STUDENT_WITH_NAME = new Student();

    public final static Teacher TEACHER = new Teacher(ID, "Вареньев Алишер", 30000L, 30);
    public final static Teacher EMPTY_TEACHER = new Teacher();
    public final static Teacher TEACHER_WITH_NAME = new Teacher();

    public final static Course  COURSE = new Course(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", TEACHER,100000L);
    public final static Course FULL_COURSE_WITH_ID_TEACHER = new Course(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", new Teacher(),100000L);
    public final static Course EMPTY_COURSE = new Course();
    public final static Course COURSE_WITH_NAME = new Course();

    public final static Subscription SUBSCRIPTION = new Subscription(FIXED_DATE, VALID_SUBSCRIPTION_ID);

    static {
        SUBSCRIPTION.setSubscriptionDate(FIXED_DATE);
        INVALID_SUBSCRIPTION_ID.setCourseId(ID);
        STUDENT_WITH_NAME.setName("Петров Александр");
        TEACHER_WITH_NAME.setName("Вареньев Алишер");
        COURSE_WITH_NAME.setName("Программирование на Java-Script");
        FULL_COURSE_WITH_ID_TEACHER.getTeacher().setId(ID);
        COURSE.setStudents(List.of(STUDENT));
        SUBSCRIPTION.setStudent(STUDENT);
        SUBSCRIPTION.setCourse(COURSE);
    }

    private EntitiesForTests() {}

}
