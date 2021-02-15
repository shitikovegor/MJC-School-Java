package com.epam.esm.dao.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.entity.Role;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
    private static final String ROLE_FIND_BY_NAME = "SELECT r FROM Role r WHERE name = ?1";

    @Override
    public List<Role> findAll(Page page) {
        throw new UnsupportedOperationException("Method find all for Role is unsupported.");
    }

    @Override
    public Optional<Role> findById(long id) {
        throw new UnsupportedOperationException("Method find by id for Role is unsupported.");
    }

    @Override
    public void remove(Role entity) {
        throw new UnsupportedOperationException("Method remove for Role is unsupported.");
    }

    @Override
    public Optional<Role> findByName(String name) {
        Query query = entityManager.createQuery(ROLE_FIND_BY_NAME, Role.class);
        query.setParameter(1, name);
        return query.getResultList().stream().findFirst();
    }
}
