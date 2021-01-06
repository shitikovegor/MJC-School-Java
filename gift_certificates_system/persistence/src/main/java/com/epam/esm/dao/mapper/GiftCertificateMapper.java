package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.epam.esm.dao.ColumnName.*;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate(
                rs.getLong(ID),
                rs.getString(NAME),
                rs.getString(DESCRIPTION),
                rs.getBigDecimal(PRICE),
                rs.getInt(DURATION),
                LocalDateTime.of(rs.getDate(CREATE_DATE).toLocalDate(),
                        rs.getTime(CREATE_DATE).toLocalTime()),
                LocalDateTime.of(rs.getDate(LAST_UPDATE_DATE).toLocalDate(),
                        rs.getTime(LAST_UPDATE_DATE).toLocalTime()));

        return giftCertificate;
    }
}
