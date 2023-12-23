package org.example.services.impl;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.repositories.SubscriptionRepository;
import org.example.services.CourseService;
import org.example.services.StudentService;
import org.example.services.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, CourseService courseService, StudentService studentService) {
        this.subscriptionRepository = subscriptionRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public void save(SubscriptionKey id) {
        checkId(id);
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        subscription.ifPresent(s -> {
            throw new BadRequestException(String.format("Подписка с id %s уже есть", id));
        });
        subscriptionRepository.save(id);
    }

    @Override
    public void update(SubscriptionKey id) {
        checkId(id);
        boolean isUpdated = subscriptionRepository.update(id);
        if(!isUpdated) throw new NotFoundException("Подписка не найдена");
    }

    @Override
    public Subscription findById(SubscriptionKey id) {
        checkId(id);
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подписка не найдена"));
        subscription.setCourse(courseService.findById(id.getCourseId()));
        subscription.setStudent(studentService.findById(id.getStudentId()));
        return subscription;
    }

    @Override
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
                ex.printStackTrace();
            }
        }
        if(subscriptions.isEmpty()) throw new NotFoundException("На данный момент нет ни одной подписки");
        return subscriptions;
    }

    @Override
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
