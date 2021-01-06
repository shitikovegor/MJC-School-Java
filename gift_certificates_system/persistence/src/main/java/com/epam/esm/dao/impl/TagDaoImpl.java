package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.SqlQuery.*;

@Repository
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;
    private TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(TAG_INSERT);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        long id = (long) keyHolder.getKey();
        tag.setId(id);

        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, tagMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(TAG_FIND_BY_ID, tagMapper, id).stream().findFirst();
    }

    @Override
    public boolean update(Tag tag) {
        int result = jdbcTemplate.update(TAG_UPDATE, tag.getName(), tag.getId());
        return result != 0;
    }

    @Override
    public boolean remove(long id) {
        int result = jdbcTemplate.update(TAG_REMOVE, id);
        return result != 0;
    }
}
