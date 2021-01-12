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
                rs.getLong(ID.getValue()),
                rs.getString(NAME.getValue()),
                rs.getString(DESCRIPTION.getValue()),
                rs.getBigDecimal(PRICE.getValue()),
                rs.getInt(DURATION.getValue()),
                LocalDateTime.of(rs.getDate(CREATE_DATE.getValue()).toLocalDate(),
                        rs.getTime(CREATE_DATE.getValue()).toLocalTime()),
                LocalDateTime.of(rs.getDate(LAST_UPDATE_DATE.getValue()).toLocalDate(),
                        rs.getTime(LAST_UPDATE_DATE.getValue()).toLocalTime()));

        return giftCertificate;
    }
}
