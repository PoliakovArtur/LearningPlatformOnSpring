package org.example.repositories.impl;

import org.example.model.Course;
import org.example.model.CourseType;
import org.example.model.Teacher;
import org.example.repositories.CourseRepository;
import org.example.repositories.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final Session session;
    private final TeacherRepository teacherRepository;

    public CourseRepositoryImpl(Session session, TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.session = session;
    }

    @Override
    public boolean save(Course course) {
        Transaction transaction = session.beginTransaction();
        boolean isSaved = false;
        try {
            Optional<Teacher> teacher = teacherRepository.findById(course.getTeacher().getId());
            course.setTeacher(teacher.orElseThrow());
            session.save(course);
            transaction.commit();
            isSaved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isSaved;
    }

    @Override
    public boolean update(Course toUpdate) {
        Transaction transaction = session.beginTransaction();
        boolean isUpdate = false;
        try {
            Course course = session.get(Course.class, toUpdate.getId());
            String name = toUpdate.getName();
            Long price = toUpdate.getPrice();
            String description = toUpdate.getDescription();
            CourseType courseType = toUpdate.getType();
            Teacher teacher = toUpdate.getTeacher();

            if(teacher != null) {
                Optional<Teacher> optTeacher = teacherRepository.findById(teacher.getId());
                course.setTeacher(optTeacher.get());
            }

            if(name != null) course.setName(name);
            if(price != null) course.setPrice(price);
            if(description != null) course.setDescription(description);
            if(courseType != null) course.setType(courseType);
            transaction.commit();
            isUpdate = true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        }
        return isUpdate;
    }

    @Override
    public Optional<Course> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Course course = null;
        try {
            course = session.get(Course.class, id);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Course> courses = Collections.emptyList();
        try {
            courses = session.createQuery("FROM Course", Course.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return courses;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;
        try {
            Course course = session.get(Course.class, id);
            if(course == null) return false;
            session.remove(course);
            transaction.commit();
            isDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isDeleted;
    }
}
