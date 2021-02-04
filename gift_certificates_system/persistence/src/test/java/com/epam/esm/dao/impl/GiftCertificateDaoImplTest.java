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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
class GiftCertificateDaoImplTest {
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate3;
    private static Page page;
    private static GiftCertificateQueryParameters parameters;
    private GiftCertificateDao giftCertificateDao;


    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }


    @BeforeAll
    static void setUp() {
        giftCertificate1 = new GiftCertificate();
        giftCertificate1.setName("rest in hotel one");
        giftCertificate1.setDescription("rest in good place");
        giftCertificate1.setPrice(new BigDecimal(150.45).setScale(2, RoundingMode.CEILING));
        giftCertificate1.setDuration(4);
        giftCertificate1.setTags(List.of(new Tag(4L, "sport")));
        giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(1L);
        giftCertificate2.setName("rest in SPA");
        giftCertificate2.setDescription("rest in good place");
        giftCertificate2.setPrice(new BigDecimal(250));
        giftCertificate2.setDuration(40);
        giftCertificate2.setCreateDate(LocalDateTime.of(2021, 1, 1, 10, 0, 0));
        giftCertificate3 = new GiftCertificate();
        giftCertificate3.setId(3);
        giftCertificate3.setName("dinner in cafe");
        giftCertificate3.setDescription("New Year dinner or breakfast");
        giftCertificate3.setPrice(new BigDecimal(50.99).setScale(2, RoundingMode.DOWN));
        giftCertificate3.setDuration(10);
        giftCertificate3.setCreateDate(LocalDateTime.of(2020, 12, 31, 23, 59, 0));
        giftCertificate3.setLastUpdateDate(LocalDateTime.of(2021, 12, 31, 23, 59, 59));
        giftCertificate3.setTags(List.of(new Tag(4L, "sport")));
        page = new Page(5, 1);
        parameters = new GiftCertificateQueryParameters();
        parameters.setName("i");
        parameters.setDescription("or");
        parameters.setTagNames(new String[]{"sport"});
    }

    @AfterAll
    static void tearDown() {
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificate3 = null;
        page = null;
        parameters = null;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate actual = giftCertificateDao.add(giftCertificate1);

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
        Optional<GiftCertificate> actualOptional = giftCertificateDao.findById(3);
        GiftCertificate actual = actualOptional.orElse(null);

        assertEquals(giftCertificate3, actual);
    }

    @Test
    void findByIdNotExistingTagShouldReturnEmptyValue() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(8);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void updateCorrectDataShouldReturnTrue() {
        GiftCertificate actual = giftCertificateDao.update(giftCertificate2);
        assertEquals(giftCertificate2.getName(), actual.getName());
    }

    @Test
    void findByQueryParametersCorrectDataShouldReturnSeveralCertificates() {
        List<GiftCertificate> actual = giftCertificateDao.findByQueryParameters(parameters, page);
        int expectedSize = 2;

        assertEquals(expectedSize, actual.size());
    }
}