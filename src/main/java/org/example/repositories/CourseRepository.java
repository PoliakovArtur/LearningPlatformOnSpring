package org.example.repositories;

import org.example.model.Course;
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
public class CourseRepository {

    private Session session;
    private CriteriaBuilder criteriaBuilder;

    public CourseRepository(Session session) {
        this.session = session;
        this.criteriaBuilder = session.getCriteriaBuilder();
    }

    public void save(Course course) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(course);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            throw new InternalError();
        }
    }

    public boolean update(Course course) {
        Transaction transaction = session.beginTransaction();
        int updateRows = 0;
        try {
            CriteriaUpdate<Course> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Course.class);
            Root<Course> root = criteriaUpdate.from(Course.class);
            Long id = course.getId();
            Map<String, Object> notNullFields = new HashMap<>(8, 1.0f);
            if(course.getDescription() != null) notNullFields.put("description", course.getDescription());
            if(course.getTeacher() != null) notNullFields.put("teacher", course.getTeacher());
            if(course.getName() != null) notNullFields.put("name", course.getName());
            if(course.getPrice() != null) notNullFields.put("price", course.getPrice());
            if(course.getType() != null) notNullFields.put("type", course.getType().name());

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

    public Optional<Course> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Course course = null;
        try {
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
            Root<Course> root = criteriaQuery.from(Course.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            course = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
        return Optional.ofNullable(course);
    }

    public List<Course> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Course> courses = Collections.emptyList();
        try {
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
            Root<Course> root = criteriaQuery.from(Course.class);
            criteriaQuery.select(root);
            courses = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return courses;
    }

    public boolean deleteById(Long id) {
        Transaction transaction = session.beginTransaction();
        int deleteRows = 0;
        try {
            CriteriaDelete<Course> criteriaDelete = criteriaBuilder.createCriteriaDelete(Course.class);
            Root<Course> root = criteriaDelete.from(Course.class);
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
