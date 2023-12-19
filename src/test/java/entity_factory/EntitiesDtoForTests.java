package entity_factory;

import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.dto.subscription_dto.CourseForSubscriptionDTO;
import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.model.CourseType;

import java.util.List;

import static entity_factory.EntitiesForTests.*;

public class EntitiesDtoForTests {

    public final static TeacherDTO TEACHER_DTO = new TeacherDTO(ID, "Вареньев Алишер", 30000L, 30);
    public final static TeacherDTO NOT_FULL_TEACHER_DTO = new TeacherDTO();
    public final static TeacherDTO TEACHER_DTO_WITHOUT_ID = new TeacherDTO("Вареньев Алишер", 30000L, 30);

    public final static StudentDTO STUDENT_DTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);
    public final static StudentDTO STUDENT_INCOMING_DTO = new StudentDTO( "Петров Александр", 30);
    public final static StudentDTO NOT_FULL_STUDENT_DTO = new StudentDTO();
    public final static StudentDTO STUDENT_DTO_WITHOUT_ID = new StudentDTO("Петров Александр", 30, FIXED_DATE);

    public final static CourseIncomingDTO COURSE_INCOMING_DTO = new CourseIncomingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", ID,100000L);
    public final static CourseIncomingDTO COURSE_INCOMING_DTO_WITHOUT_ID = new CourseIncomingDTO( "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", ID,100000L);
    public final static CourseOutgoingDTO COURSE_OUTGOING_DTO = new CourseOutgoingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", TEACHER_DTO, List.of(STUDENT_DTO),100000L);

    public final static SubscriptionIncomingDTO SUBSCRIPTION_INCOMING_DTO = new SubscriptionIncomingDTO(ID, ID);
    public final static SubscriptionIncomingDTO INVALID_SUBSCRIPTION_INCOMING_DTO = new SubscriptionIncomingDTO(ID, null);

    public final static CourseForSubscriptionDTO COURSE_FOR_SUBSCRIPTION_DTO = new CourseForSubscriptionDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  100000L);

    public final static SubscriptionOutGoingDTO SUBSCRIPTION_OUT_GOING_DTO = new SubscriptionOutGoingDTO(FIXED_DATE, STUDENT_DTO, COURSE_FOR_SUBSCRIPTION_DTO);

    static {
        NOT_FULL_TEACHER_DTO.setAge(30);
        NOT_FULL_STUDENT_DTO.setAge(30);
    }

    private EntitiesDtoForTests() {}
}
