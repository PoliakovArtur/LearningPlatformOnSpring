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

    final static LocalDateTime FIXED_DATE = LocalDateTime.of(2023, 12, 23, 22, 22, 22);


    public final static Long ID = 1L;
    public final static SubscriptionKey VALID_SUBSCRIPTION_ID = new SubscriptionKey(ID, ID);
    public final static SubscriptionKey INVALID_SUBSCRIPTION_ID = new SubscriptionKey();

    public final static Student FULL_STUDENT = new Student(ID, "Петров Александр", 30, FIXED_DATE);
    public final static Student STUDENT_WITHOUT_ID = new Student("Петров Александр", 30);
    public final static Student EMPTY_STUDENT = new Student();
    public final static Student NOT_FULL_STUDENT = new Student();

    public final static Teacher FULL_TEACHER = new Teacher(ID, "Вареньев Алишер", 30000L, 30);
    public final static Teacher TEACHER_WITHOUT_ID = new Teacher( "Вареньев Алишер", 30000L, 30);
    public final static Teacher EMPTY_TEACHER = new Teacher();
    public final static Teacher NOT_FULL_TEACHER = new Teacher();

    public final static Course FULL_COURSE = new Course(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", FULL_TEACHER,100000L);
    public final static Course FULL_COURSE_WITH_ID_TEACHER = new Course(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", new Teacher(),100000L);
    public final static Course COURSE_WITHOUT_ID = new Course("Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", FULL_TEACHER,100000L);
    public final static Course EMPTY_COURSE = new Course();
    public final static Course NOT_FULL_COURSE = new Course();

    public final static Subscription SUBSCRIPTION = new Subscription(FIXED_DATE, VALID_SUBSCRIPTION_ID);


    static {
        SUBSCRIPTION.setSubscriptionDate(FIXED_DATE);
        INVALID_SUBSCRIPTION_ID.setCourseId(ID);
        NOT_FULL_STUDENT.setName("Петров Александр");
        NOT_FULL_TEACHER.setName("Вареньев Алишер");
        NOT_FULL_COURSE.setName("Программирование на Java-Script");
        FULL_COURSE_WITH_ID_TEACHER.getTeacher().setId(ID);
        FULL_COURSE.setStudents(List.of(FULL_STUDENT));
        SUBSCRIPTION.setStudent(FULL_STUDENT);
        SUBSCRIPTION.setCourse(FULL_COURSE);
    }

    private EntitiesForTests() {}

}
