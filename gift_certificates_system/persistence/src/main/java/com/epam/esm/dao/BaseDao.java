package com.epam.esm.dao;

import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code BaseDao} describes CRUD operations for working with database.
 *
 * @param <T> the type parameter
 * @author Egor Shitikov
 * @version 1.0
 */
public interface BaseDao<T> {
    /**
     * Add entity.
     *
     * @param entity the entity to add
     * @return the added entity
     */
    T add (T entity);

    /**
     * Find all entities.
     *
     * @return the list
     */
    List<T> findAll(Page page);

    /**
     * Find by id optional.
     *
     * @param id the id to search
     * @return the optional found
     */
    Optional<T> findById(long id);

    /**
     * Remove entity.
     *
     * @param entity the entity to delete
     */
    void remove (T entity);
}
