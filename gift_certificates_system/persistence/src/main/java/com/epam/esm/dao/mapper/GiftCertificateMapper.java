package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.epam.esm.dao.mapper.ColumnName.*;

/**
 * Component {@code GiftCertificateMapper} Gift certificate mapper.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ID.getValue()));
        giftCertificate.setName(rs.getString(NAME.getValue()));
        giftCertificate.setDescription(rs.getString(DESCRIPTION.getValue()));
        giftCertificate.setPrice(rs.getBigDecimal(PRICE.getValue()));
        giftCertificate.setDuration(rs.getInt(DURATION.getValue()));
        giftCertificate.setCreateDate(LocalDateTime.of(rs.getDate(CREATE_DATE.getValue()).toLocalDate(),
                rs.getTime(CREATE_DATE.getValue()).toLocalTime()));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(rs.getDate(LAST_UPDATE_DATE.getValue()).toLocalDate(),
                rs.getTime(LAST_UPDATE_DATE.getValue()).toLocalTime()));

        return giftCertificate;
    }
}
