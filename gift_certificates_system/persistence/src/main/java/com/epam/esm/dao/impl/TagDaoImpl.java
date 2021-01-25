package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class TagDaoImpl implements TagDao {
    private static final String TAG_INSERT = "INSERT INTO tag (name) VALUES (?)";
    private static final String TAG_FIND_ALL = "SELECT id, name FROM tag";
    private static final String TAG_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private static final String TAG_REMOVE = "DELETE FROM tag WHERE id = ?";
    private static final String TAG_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    private static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "tag_id_fk = ?";
    private static final String TAG_FIND_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_certificate_has_tag LEFT JOIN" +
            " tag ON tag_id_fk = id WHERE gift_certificate_id_fk = ?";
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final SessionFactory sessionFactory;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Tag add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(TAG_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        tag.setId(id);

        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, tagMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.get(Tag.class, id));
//        return jdbcTemplate.query(TAG_FIND_BY_ID, tagMapper, id).stream().findFirst();
    }

    @Override
    public boolean remove(long id) {
        int result = jdbcTemplate.update(TAG_REMOVE, id);
        return result != 0;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(TAG_FIND_BY_NAME, tagMapper, name).stream().findFirst();
    }

    @Override
    public void removeFromTableGiftCertificateHasTag(long id) {
        jdbcTemplate.update(GIFT_CERTIFICATE_HAS_TAG_REMOVE, id);
    }

    @Override
    public List<Tag> findByCertificateId(long id) {
        return jdbcTemplate.query(TAG_FIND_BY_CERTIFICATE_ID, tagMapper, id);
    }
}
