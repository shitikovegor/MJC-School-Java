package com.epam.esm.service;

/**
 * Interface {@code BaseService} describes base business logic operations.
 *
 * @param <T> the type parameter
 * @author Egor Shitikov
 * @version 1.0
 */
public interface BaseService<T> {
    /**
     * Add object.
     *
     * @param t the t
     * @return the id of added object
     */
    long add(T t);

    /**
     * Find object by id.
     *
     * @param id the id
     * @return the found object
     */
    T findById(long id);

    /**
     * Remove object by id.
     *
     * @param id the id of object to remove
     */
    void remove(long id);
}
