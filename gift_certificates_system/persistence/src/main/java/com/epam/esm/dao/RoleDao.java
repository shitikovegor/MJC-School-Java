package com.epam.esm.dao;

import com.epam.esm.entity.Role;

import java.util.Optional;

/**
 * Interface {@code RoleDao} represents an extended abstract behavior
 * for working with the Role entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface RoleDao extends BaseDao<Role>{

    /**
     * Find by name optional.
     *
     * @param name the name to search
     * @return the optional
     */
    Optional<Role> findByName(String name);
}
