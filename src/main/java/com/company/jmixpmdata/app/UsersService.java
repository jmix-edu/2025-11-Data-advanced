package com.company.jmixpmdata.app;

import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<User> getUsersNotInProject(Project project, int first, int max) {
        return entityManager.createQuery("SELECT u FROM User u " +
                "WHERE u.id NOT IN " +
                "(SELECT u1.id FROM User u1 " +
                "INNER JOIN u1.projects pul " +
                "where pul.id = ?1)", User.class)
                .setParameter(1, project.getId())
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }
}