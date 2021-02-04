package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private static final String USER_FIND_BY_EMAIL = "SELECT u FROM User u WHERE email = ?1";
    private static final String USER_FIND_ALL = "SELECT u FROM User u";
    private static final String USER_TOTAL_RECORDS = "SELECT COUNT(*) FROM User";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findAll(Page page) {
        return entityManager.createQuery(USER_FIND_ALL)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void remove(User user) {
        throw new UnsupportedOperationException("Method remove for User is unsupported.");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = entityManager.createQuery(USER_FIND_BY_EMAIL);
        query.setParameter(1, email);
        return query.getResultStream().findFirst();
    }

    @Override
    public int findTotalRecords() {
        Long totalRecords = (Long) entityManager.createQuery(USER_TOTAL_RECORDS).getSingleResult();
        return totalRecords.intValue();
    }
}
