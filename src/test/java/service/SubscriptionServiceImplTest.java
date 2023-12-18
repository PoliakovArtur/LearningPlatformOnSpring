package service;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Subscription;
import org.example.repositories.SubscriptionRepository;
import org.example.services.CourseService;
import org.example.services.StudentService;
import org.example.services.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static entity_factory.EntitiesForTests.FULL_COURSE;
import static entity_factory.EntitiesForTests.ID;
import static entity_factory.EntitiesForTests.INVALID_SUBSCRIPTION_ID;
import static entity_factory.EntitiesForTests.FULL_STUDENT;
import static entity_factory.EntitiesForTests.SUBSCRIPTION;
import static entity_factory.EntitiesForTests.VALID_SUBSCRIPTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionServiceImplTest {
    private SubscriptionServiceImpl subscriptionService;
    private CourseService courseService;
    private StudentService studentService;
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setUp() {
        subscriptionRepository = mock(SubscriptionRepository.class);
        courseService = mock(CourseService.class);
        studentService = mock(StudentService.class);
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, courseService, studentService);
    }

    @Test
    void save_shouldSaveSubscription() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.findById(VALID_SUBSCRIPTION_ID)).thenReturn(Optional.empty());
        subscriptionService.save(VALID_SUBSCRIPTION_ID);
        verify(subscriptionRepository).save(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void save_tryToSaveExistsSubscription() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.findById(VALID_SUBSCRIPTION_ID)).thenReturn(Optional.of(SUBSCRIPTION));
        assertThrows(BadRequestException.class, () -> subscriptionService.save(VALID_SUBSCRIPTION_ID));
    }

    @Test
    void save_tryToSaveSubscriptionWithNonExistStudentId() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> subscriptionService.save(VALID_SUBSCRIPTION_ID));
    }

    @Test
    void save_tryToSaveInvalidSubscription() {
        assertThrows(BadRequestException.class, () -> subscriptionService.save(INVALID_SUBSCRIPTION_ID));
    }

    @Test
    void update_shouldUpdateSubscription() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.update(VALID_SUBSCRIPTION_ID)).thenReturn(true);
        subscriptionService.update(VALID_SUBSCRIPTION_ID);
        verify(subscriptionRepository).update(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void update_shouldThrowNotFoundException() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.update(VALID_SUBSCRIPTION_ID)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> subscriptionService.update(VALID_SUBSCRIPTION_ID));
    }

    @Test
    void findById_shouldFindSubscription() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.findById(VALID_SUBSCRIPTION_ID)).thenReturn(Optional.of(SUBSCRIPTION));
        Subscription actual = subscriptionService.findById(VALID_SUBSCRIPTION_ID);
        assertEquals(SUBSCRIPTION, actual);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.findById(VALID_SUBSCRIPTION_ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subscriptionService.findById(VALID_SUBSCRIPTION_ID));
    }

    @Test
    void findById_tryToFindWithInvalidId() {
        assertThrows(BadRequestException.class, () -> subscriptionService.findById(INVALID_SUBSCRIPTION_ID));
    }

    @Test
    void findAll_shouldFindSubscriptions() {
        List<Subscription> expected = List.of(SUBSCRIPTION);
        when(subscriptionRepository.findAll()).thenReturn(expected);
        List<Subscription> actual = subscriptionService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldThrowNotFoundException() {
        when(subscriptionRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> subscriptionService.findAll());
    }

    @Test
    void deleteById_shouldDeleteSubscription() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.deleteById(VALID_SUBSCRIPTION_ID)).thenReturn(true);
        subscriptionService.deleteById(VALID_SUBSCRIPTION_ID);
        verify(subscriptionRepository).deleteById(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void deleteById_shouldThrowNotFoundException() {
        when(courseService.findById(ID)).thenReturn(FULL_COURSE);
        when(studentService.findById(ID)).thenReturn(FULL_STUDENT);
        when(subscriptionRepository.deleteById(VALID_SUBSCRIPTION_ID)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> subscriptionService.deleteById(VALID_SUBSCRIPTION_ID));
    }

    @Test
    void deleteById_tryToDeleteWithInvalidId() {
        assertThrows(BadRequestException.class, () -> subscriptionService.deleteById(INVALID_SUBSCRIPTION_ID));
    }
 }
