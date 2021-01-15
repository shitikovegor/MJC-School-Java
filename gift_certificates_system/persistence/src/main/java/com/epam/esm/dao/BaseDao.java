package com.epam.esm.dao;

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
    List<T> findAll();

    /**
     * Find by id optional.
     *
     * @param id the id to search
     * @return the optional found
     */
    Optional<T> findById(long id);

    /**
     * Update entity.
     *
     * @param entity to update
     * @return the updated entity
     */
    T update (T entity);

    /**
     * Remove entity by id.
     *
     * @param id the id of entity to delete
     * @return the boolean
     */
    boolean remove (long id);
}
