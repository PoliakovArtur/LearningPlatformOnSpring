package org.example.repositories.impl;

import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    private final Session session;
    private final CriteriaBuilder criteriaBuilder;

    public TeacherRepositoryImpl(Session session) {
        this.session = session;
        this.criteriaBuilder = session.getCriteriaBuilder();
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
            throw new InternalError();
        }
    }

    @Override
    public boolean update(Teacher teacher) {
        Transaction transaction = session.beginTransaction();
        int updateRows = 0;
        try {
            CriteriaUpdate<Teacher> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Teacher.class);
            Root<Teacher> root = criteriaUpdate.from(Teacher.class);
            Long id = teacher.getId();
            Map<String, Object> notNullFields = new HashMap<>(4, 1.0f);

            if(teacher.getAge() != null) notNullFields.put("age", teacher.getAge());
            if(teacher.getName() != null) notNullFields.put("name", teacher.getName());
            if(teacher.getSalary() != null) notNullFields.put("salary", teacher.getSalary());

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
    public Optional<Teacher> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Teacher teacher = null;
        try {
            CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            teacher = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Teacher> teachers = Collections.emptyList();
        try {
            CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);
            criteriaQuery.select(root);
            teachers = session.createQuery(criteriaQuery).getResultList();
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
        int deleteRows = 0;
        try {
            CriteriaDelete<Teacher> criteriaDelete = criteriaBuilder.createCriteriaDelete(Teacher.class);
            Root<Teacher> root = criteriaDelete.from(Teacher.class);
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
