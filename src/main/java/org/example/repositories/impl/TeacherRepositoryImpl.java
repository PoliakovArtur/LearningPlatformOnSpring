package org.example.repositories.impl;

import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    private final Session session;

    public TeacherRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public void save(Teacher teacher) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(teacher);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public boolean update(Teacher toUpdate) {
        Transaction transaction = session.beginTransaction();
        boolean isUpdate = false;
        try {
            Teacher teacher = session.get(Teacher.class, toUpdate.getId());

            Integer age = toUpdate.getAge();
            String name = toUpdate.getName();
            Long salary = toUpdate.getSalary();

            if(age != null) teacher.setAge(age);
            if(name != null) teacher.setName(name);
            if(salary != null) teacher.setSalary(salary);

            transaction.commit();
            isUpdate = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isUpdate;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Teacher teacher = null;
        try {
            teacher = session.get(Teacher.class, id);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Teacher> teachers = Collections.emptyList();
        try {
            teachers = session.createQuery("FROM Teacher", Teacher.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return teachers;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;
        try {
            Teacher teacher = session.get(Teacher.class, id);
            if(teacher == null) return false;
            session.remove(teacher);
            transaction.commit();
            isDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isDeleted;
    }
}
