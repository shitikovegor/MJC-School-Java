package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.entity.GiftCertificateQueryParameters.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public GiftCertificateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        sessionFactory.getCurrentSession().persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from GiftCertificate").list();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.get(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return (GiftCertificate) sessionFactory.getCurrentSession().merge(giftCertificate);
    }

    @Override
    public void remove(GiftCertificate giftCertificate) {
        sessionFactory.getCurrentSession().delete(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        CriteriaQuery<GiftCertificate> query = generateCriteriaQuery(giftCertificateQueryParameters);
        TypedQuery<GiftCertificate> typedQuery = sessionFactory.getCurrentSession().createQuery(query);
        return typedQuery.getResultList();
    }

    private CriteriaQuery generateCriteriaQuery(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        String tagName = giftCertificateQueryParameters.getTagName();
        if (tagName != null) {
            ListJoin<GiftCertificate, Tag> tags = root.join(GiftCertificate_.tags, JoinType.LEFT);
            predicates.add(builder.equal(tags.get(Tag_.NAME), tagName));
        }
        String name = giftCertificateQueryParameters.getName();
        if (name != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.NAME), "%" + name + "%"));
        }
        String description = giftCertificateQueryParameters.getDescription();
        if (description != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.DESCRIPTION), "%" + description + "%"));
        }
        query.select(root).where(predicates.toArray(Predicate[]::new));

        SortType sortType = giftCertificateQueryParameters.getSortType();
        SortOrder sortOrder = giftCertificateQueryParameters.getSortOrder();
        if (sortType == null || sortOrder == null) {
            query.orderBy(builder.asc(root.get(GiftCertificate_.CREATE_DATE)));
        } else if (sortOrder == SortOrder.ASC) {
            query.orderBy(builder.asc(root.get(sortType.getQueryValue())));
        } else {
            query.orderBy(builder.desc(root.get(sortType.getQueryValue())));
        }
        return query;
    }
}
