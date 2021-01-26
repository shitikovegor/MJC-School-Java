package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String TAG_FIND_BY_NAME = "FROM Tag WHERE name = ?1";
    private static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "tag_id_fk = ?";

    private final SessionFactory sessionFactory;

    @Autowired
    public TagDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Tag add(Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Tag").list();
    }

    @Override
    public Optional<Tag> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.get(Tag.class, id));
    }

    @Override
    public void remove(Tag tag) {
        sessionFactory.getCurrentSession().remove(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(TAG_FIND_BY_NAME);
        query.setParameter(1, name);
        return query.stream().findFirst();
    }

    @Override
    public void removeFromTableGiftCertificateHasTag(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(GIFT_CERTIFICATE_HAS_TAG_REMOVE);
        query.setParameter(1, id).executeUpdate();
    }
}
