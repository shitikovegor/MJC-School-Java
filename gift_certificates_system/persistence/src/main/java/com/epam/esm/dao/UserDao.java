package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * Interface {@code UserDao} represents an extended abstract behavior
 * for working with the User entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Find by username optional.
     *
     * @param username the username to search
     * @return the optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Find total records in database.
     *
     * @return the int
     */
    int findTotalRecords();

    /**
     * Update user.
     *
     * @param user to update
     * @return the updated user
     */
    User update(User user);
}
