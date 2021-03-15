package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;
    private Page page;
    private GiftCertificateQueryParameters parameters;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @BeforeAll
    void setUp() {
        page = new Page(5, 1);
        parameters = new GiftCertificateQueryParameters();
        parameters.setName("i");
        parameters.setDescription("or");
        parameters.setTagNames(new String[]{"sport"});
    }

    @AfterAll
    void tearDown() {
        page = null;
        parameters = null;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("rest in hotel one");
        giftCertificate.setDescription("rest in good place");
        giftCertificate.setPrice(new BigDecimal(150.45).setScale(2, RoundingMode.CEILING));
        giftCertificate.setDuration(4);
        giftCertificate.setTags(List.of(new Tag(4L, "sport")));
        GiftCertificate actual = giftCertificateDao.add(giftCertificate);

        assertNotNull(actual);
    }

    @Test
    void findAllCorrectDataShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateDao.findAll(page);
        int actual = certificates.size();
        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(3);
        giftCertificate.setName("dinner in cafe");
        giftCertificate.setDescription("New Year dinner or breakfast");
        giftCertificate.setPrice(new BigDecimal(50.99).setScale(2, RoundingMode.DOWN));
        giftCertificate.setDuration(10);
        giftCertificate.setCreateDate(LocalDateTime.of(2020, 12, 31, 23, 59, 0));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 12, 31, 23, 59, 59));
        giftCertificate.setTags(List.of(new Tag(4L, "sport")));
        Optional<GiftCertificate> actualOptional = giftCertificateDao.findById(3);
        GiftCertificate actual = actualOptional.orElse(null);

        assertEquals(giftCertificate, actual);
    }

    @Test
    void findByIdNotExistingTagShouldReturnEmptyValue() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(8);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void updateCorrectDataShouldReturnTrue() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("rest in SPA");
        giftCertificate.setDescription("rest in good place");
        giftCertificate.setPrice(new BigDecimal(250));
        giftCertificate.setDuration(40);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 1, 1, 10, 0, 0));
        GiftCertificate actual = giftCertificateDao.update(giftCertificate);
        assertEquals(giftCertificate.getName(), actual.getName());
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnSeveralCertificates() {
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(parameters, page);
        int expectedSize = 2;

        assertEquals(expectedSize, actual.size());
    }
}