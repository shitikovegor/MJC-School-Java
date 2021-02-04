package com.epam.esm.dao.impl;

import com.epam.esm.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T add(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }
}
