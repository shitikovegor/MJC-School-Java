package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagDaoImplTest {

    private final TagDao tagDao;
    private Page page;
    private Tag tag;

    @Autowired
    public TagDaoImplTest(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeAll
    void setUp() {
        tag = new Tag(1L, "rest");
        page = new Page(5, 1);
    }

    @AfterAll
    void tearDown() {
        page = null;
        tag = null;
    }

    @Test
    void addCorrectDataShouldReturnTag() {
        Tag tag1 = new Tag();
        tag1.setName("New tag");
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
        Tag tag2 = new Tag(3L, "food");
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

        assertEquals(tag, actual);
    }

    @Test
    void findMostPopularTagFromUserWithMaxPurchasesShouldReturnMostPopularTag() {
        Optional<Tag> actualOptional = tagDao.findMostPopularTagFromUserWithMaxPurchases();
        Tag actual = actualOptional.orElse(null);

        assertEquals(tag, actual);
    }
}