package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificatesQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

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

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(GIFT_CERTIFICATE_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setObject(5, giftCertificate.getCreateDate());
            ps.setObject(6, giftCertificate.getLastUpdateDate());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        giftCertificate.setId(id);

        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL, giftCertificateMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_ID, giftCertificateMapper, id).stream().findFirst();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(GIFT_CERTIFICATE_UPDATE, giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());

        return giftCertificate;
    }

    @Override
    public boolean remove(long id) {
        int result = jdbcTemplate.update(GIFT_CERTIFICATE_REMOVE, id);
        return result != 0;
    }

    @Override
    public void addToTableGiftCertificateHasTag(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificate.getId();
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(GIFT_CERTIFICATE_HAS_TAG_INSERT, giftCertificateId, tag.getId()));
    }

    @Override
    public void removeFromTableGiftCertificateHasTag(long id) {
        jdbcTemplate.update(GIFT_CERTIFICATE_HAS_TAG_REMOVE, id);
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        String queryParameters = GiftCertificatesQueryCreator.createQuery(giftCertificateQueryParameters);
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_PARAMETERS + queryParameters, giftCertificateMapper);
    }
}
