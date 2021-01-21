package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDaoImplTest {
    private GiftCertificateDao giftCertificateDao;
    private EmbeddedDatabase database;


    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db_script/gift_certificate_create_table.sql")
                .addScript("db_script/gift_certificate_insert_data.sql")
                .addScript("db_script/gift_certificate_has_tag_create_table.sql")
                .addScript("db_script/gift_certificate_has_tag_insert_data.sql")
                .build();
        JdbcTemplate template = new JdbcTemplate(database);
        giftCertificateDao = new GiftCertificateDaoImpl(template, new GiftCertificateMapper());

    }

    @AfterEach
    void tearDown() {
        database.shutdown();
        giftCertificateDao = null;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Title");
        certificate.setDescription("Description");
        certificate.setPrice(new BigDecimal(100.5));
        certificate.setDuration(2);
        certificate.setCreateDate(LocalDateTime.of(2021, 1, 3, 10, 0, 0));
        certificate.setLastUpdateDate(LocalDateTime.of(2021, 1, 5, 10, 0, 0));

        GiftCertificate actual = giftCertificateDao.add(certificate);
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldReturnValidId() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Title");
        certificate.setDescription("Description");
        certificate.setPrice(new BigDecimal(100.5));
        certificate.setDuration(2);
        certificate.setCreateDate(LocalDateTime.of(2021, 1, 3, 10, 0, 0));
        certificate.setLastUpdateDate(LocalDateTime.of(2021, 1, 5, 10, 0, 0));

        GiftCertificate actual = giftCertificateDao.add(certificate);
        assertNotEquals(0, actual.getId());
    }

    @Test
    void findAllCorrectDataShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateDao.findAll();
        int actual = certificates.size();
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate expected = new GiftCertificate();
        expected.setId(3);
        expected.setName("dinner in cafe");
        expected.setDescription("New Year dinner");
        expected.setPrice(new BigDecimal(50.99).setScale(2, RoundingMode.DOWN));
        expected.setDuration(10);
        expected.setCreateDate(LocalDateTime.of(2020, 12, 31, 23, 59, 0));
        expected.setLastUpdateDate(LocalDateTime.of(2021, 12, 31, 23, 59, 59));

        Optional<GiftCertificate> actualOptional = giftCertificateDao.findById(3);
        GiftCertificate actual = actualOptional.orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNotExistingTagShouldReturnEmptyValue() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(8);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void updateCorrectDataShouldReturnTrue() {
        GiftCertificate certificateForUpdate = new GiftCertificate(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59),
                new ArrayList<>());
        GiftCertificate actual = giftCertificateDao.update(certificateForUpdate);
        assertEquals(certificateForUpdate, actual);
    }

    @Test
    void removeCorrectDataShouldReturnTrue() {
        boolean actual = giftCertificateDao.remove(3);
        assertTrue(actual);
    }

    @Test
    void removeNotExistingDataShouldReturnFalse() {
        boolean actual = giftCertificateDao.remove(15);
        assertFalse(actual);
    }
}