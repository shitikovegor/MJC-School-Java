package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.util.GiftCertificatesQueryCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String GIFT_CERTIFICATE_INSERT = "INSERT INTO gift_certificate (name, description, price, " +
            "duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GIFT_CERTIFICATE_FIND_ALL = "SELECT id, name, description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate";
    private static final String GIFT_CERTIFICATE_FIND_BY_ID = "SELECT id, name, description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String GIFT_CERTIFICATE_UPDATE = "UPDATE gift_certificate SET name = ?, " +
            "description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";
    private static final String GIFT_CERTIFICATE_REMOVE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String GIFT_CERTIFICATE_HAS_TAG_INSERT = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id_fk, tag_id_fk) VALUES (?, ?)";
    private static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "gift_certificate_id_fk = ?";
    private static final String GIFT_CERTIFICATE_FIND_BY_PARAMETERS = "SELECT gift_certificate.id, gift_certificate.name, " +
            "gift_certificate.description, gift_certificate.price, gift_certificate.duration, gift_certificate.create_date, " +
            "gift_certificate.last_update_date FROM gift_certificate " +
            "LEFT JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id_fk " +
            "LEFT JOIN tag ON gift_certificate_has_tag.tag_id_fk = tag.id ";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final SessionFactory sessionFactory;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper,
                                  SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        return (GiftCertificate) sessionFactory.getCurrentSession().merge(giftCertificate);
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
        String queryParameters = GiftCertificatesQueryCreator.createQuery(giftCertificateQueryParameters);
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_PARAMETERS + queryParameters, giftCertificateMapper);
    }
}
