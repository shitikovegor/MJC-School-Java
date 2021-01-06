package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
    void addPositiveTest() {
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
    void addReturnedIdTest() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Title");
        certificate.setDescription("Description");
        certificate.setPrice(new BigDecimal(100.5));
        certificate.setDuration(2);
        certificate.setCreateDate(LocalDateTime.of(2021, 1, 3, 10, 0, 0));
        certificate.setLastUpdateDate(LocalDateTime.of(2021, 1, 5, 10, 0, 0));

        GiftCertificate actual = giftCertificateDao.add(certificate);
        assertTrue(actual.getId() != 0);
    }

    @Test
    void findAllPositiveTest() {
        List<GiftCertificate> certificates = giftCertificateDao.findAll();
        int actual = certificates.size();
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        GiftCertificate expected = new GiftCertificate(
                3, "dinner in cafe", "New Year dinner",
                new BigDecimal(50.99).setScale(2, RoundingMode.DOWN), 1,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59));

        Optional<GiftCertificate> actualOptional = giftCertificateDao.findById(3);
        GiftCertificate actual = actualOptional.orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(8);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void updateTest() {
        GiftCertificate certificateForUpdate = new GiftCertificate(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59));
        boolean actual = giftCertificateDao.update(certificateForUpdate);
        assertTrue(actual);
    }

    @Test
    void removePositiveTest() {
        boolean actual = giftCertificateDao.remove(3);
        assertTrue(actual);
    }

    @Test
    void removeNegativeTest() {
        boolean actual = giftCertificateDao.remove(15);
        assertFalse(actual);
    }
}