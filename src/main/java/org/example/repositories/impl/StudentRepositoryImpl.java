package org.example.repositories.impl;

import org.example.model.Student;
import org.example.repositories.StudentRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final Session session;
    private final CriteriaBuilder criteriaBuilder;

    public StudentRepositoryImpl(Session session) {
        this.session = session;
        this.criteriaBuilder = session.getCriteriaBuilder();
    }

    @Override
    public void save(Student student) {
        Transaction transaction = session.beginTransaction();
        try {
            student.setRegistrationDate(LocalDateTime.now());
            session.save(student);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            throw new InternalError();
        }
    }

    @Override
    public boolean update(Student student) {
        Transaction transaction = session.beginTransaction();
        int updateRows = 0;
        try {
            CriteriaUpdate<Student> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Student.class);
            Root<Student> root = criteriaUpdate.from(Student.class);
            Long id = student.getId();
            Map<String, Object> notNullFields = new HashMap<>(4, 1.0f);
            if(student.getAge() != null) notNullFields.put("age", student.getAge());
            if(student.getName() != null) notNullFields.put("name", student.getName());

            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
            for(Map.Entry<String, Object> entry : notNullFields.entrySet()) {
                criteriaUpdate.set(entry.getKey(), entry.getValue());
            }
            updateRows = session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return updateRows > 0;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Student Student = null;
        try {
            CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            Student = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
        return Optional.ofNullable(Student);
    }

    @Override
    public List<Student> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Student> students = Collections.emptyList();
        try {
            CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.select(root);
            students = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return students;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = session.beginTransaction();
        int deleteRows = 0;
        try {
            CriteriaDelete<Student> criteriaDelete = criteriaBuilder.createCriteriaDelete(Student.class);
            Root<Student> root = criteriaDelete.from(Student.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
            deleteRows = session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return deleteRows > 0;
    }
}
