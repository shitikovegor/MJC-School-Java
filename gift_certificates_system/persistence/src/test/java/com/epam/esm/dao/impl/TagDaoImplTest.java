package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
class TagDaoImplTest {
    private static Page page;
    private static Tag tag1;
    private static Tag tag2;
    private static Tag tag3;
    private TagDao tagDao;

    @Autowired
    public TagDaoImplTest(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeAll
    static void setUp() {
        tag1 = new Tag();
        tag1.setName("New tag");
        tag2 = new Tag(3L, "food");
        tag3 = new Tag(1L, "rest");
        page = new Page(5, 1);
    }

    @AfterAll
    static void tearDown() {
        page = null;
        tag1 = null;
        tag2 = null;
        tag3 = null;
    }

    @Test
    void addCorrectDataShouldReturnTag() {
        Tag actual = tagDao.add(tag1);

        assertNotNull(actual);
    }

    @Test
    void findAllCorrectDataShouldReturnListOfTags() {
        List<Tag> tags = tagDao.findAll(page);
        int actual = tags.size();
        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnTag() {
        Optional<Tag> actualOptional = tagDao.findById(3);
        Tag actual = actualOptional.orElse(null);

        assertEquals(tag2, actual);
    }

    @Test
    void findByIdNotExistingTagShouldReturnEmptyValue() {
        Optional<Tag> actual = tagDao.findById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findByNameCorrectDataShouldReturnTag() {
        Optional<Tag> actualOptional = tagDao.findByName("rest");
        Tag actual = actualOptional.orElse(null);

        assertEquals(tag3, actual);
    }

    @Test
    void findMostPopularTagFromUserWithMaxPurchasesShouldReturnMostPopularTag() {
        Optional<Tag> actualOptional = tagDao.findMostPopularTagFromUserWithMaxPurchases();
        Tag actual = actualOptional.orElse(null);

        assertEquals(tag3, actual);
    }
}