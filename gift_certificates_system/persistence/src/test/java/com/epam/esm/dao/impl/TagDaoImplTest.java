package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {
    private TagDao tagDao;
    private EmbeddedDatabase database;


    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db_script/tag_create_table.sql")
                .addScript("db_script/tag_insert_data.sql")
                .build();
        JdbcTemplate template = new JdbcTemplate(database);
        tagDao = new TagDaoImpl(template, new TagMapper());

    }

    @AfterEach
    void tearDown() {
        database.shutdown();
        tagDao = null;
    }

    @Test
    void addPositiveTest() {
        Tag tag = new Tag();
        tag.setName("New tag");
        Tag actual = tagDao.add(tag);
        assertNotNull(actual);
    }

    @Test
    void addReturnedIdTest() {
        Tag tag = new Tag();
        tag.setName("Title");
        Tag actual = tagDao.add(tag);
        assertTrue(actual.getId() != 0);
    }

    @Test
    void findAllPositiveTest() {
        List<Tag> tags = tagDao.findAll();
        int actual = tags.size();
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        Tag expected = new Tag(3, "food");

        Optional<Tag> actualOptional = tagDao.findById(3);
        Tag actual = actualOptional.orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<Tag> actual = tagDao.findById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void removePositiveTest() {
        boolean actual = tagDao.remove(3);
        assertTrue(actual);
    }

    @Test
    void removeNegativeTest() {
        boolean actual = tagDao.remove(15);
        assertFalse(actual);
    }
}