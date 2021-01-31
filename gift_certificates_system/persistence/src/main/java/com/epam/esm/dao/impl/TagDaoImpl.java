package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String TAG_FIND_BY_NAME = "SELECT t FROM Tag t WHERE name = ?1";
    private static final String TAG_FIND_ALL = "SELECT t FROM Tag t";
    private static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "tag_id_fk = ?";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(TAG_FIND_ALL)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.of(entityManager.find(Tag.class, id));
    }

    @Override
    public void remove(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Query query = entityManager.createQuery(TAG_FIND_BY_NAME);
        query.setParameter(1, name);
        return query.getResultStream().findFirst();
    }

    @Override
    public void removeFromTableGiftCertificateHasTag(long id) {
        Query query = entityManager.createNativeQuery(GIFT_CERTIFICATE_HAS_TAG_REMOVE);
        query.setParameter(1, id).executeUpdate();
    }
}
