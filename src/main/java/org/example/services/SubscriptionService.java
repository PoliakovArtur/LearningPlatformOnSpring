package org.example.services;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private CourseService courseService;
    private StudentService studentService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CourseService courseService, StudentService studentService) {
        this.subscriptionRepository = subscriptionRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public void save(SubscriptionKey id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        subscription.ifPresent(s -> {
            throw new BadRequestException(String.format("Подписка с id %s уже есть", id));
        });
        subscriptionRepository.save(id);
    }

    public void update(SubscriptionKey id) {
        checkId(id);
        subscriptionRepository.update(id);
    }

    public Subscription findById(SubscriptionKey id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подписка не найдена"));
        subscription.setCourse(courseService.findById(id.getCourseId()));
        subscription.setStudent(studentService.findById(id.getStudentId()));
        return subscription;
    }

    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        for(Subscription subscription : subscriptionRepository.findAll()) {
            try {
                SubscriptionKey id = subscription.getId();
                Long courseId = id.getCourseId();
                Long studentId = id.getStudentId();
                Course course = courseService.findById(courseId);
                Student student = studentService.findById(studentId);
                subscription.setCourse(course);
                subscription.setStudent(student);
                subscriptions.add(subscription);
            } catch (Exception ex) {
                System.err.printf("DB is not consistency! Subscription %s is invalid\n", subscription);
            }
        }
        if(subscriptions.isEmpty()) throw new NotFoundException("На данный момент нет ни одной подписки");
        return subscriptions;
    }

    public void deleteById(SubscriptionKey id) {
        checkId(id);
        boolean isDeleted = subscriptionRepository.deleteById(id);
        if(!isDeleted) throw new NotFoundException("Подписка не найдена");
    }

    private void checkId(SubscriptionKey id) {
        Long courseId = id.getCourseId();
        Long studentId = id.getStudentId();
        if(courseId == null || studentId == null)
            throw new BadRequestException("Неверно передан id");
        courseService.findById(id.getCourseId());
        studentService.findById(id.getStudentId());
    }
}
