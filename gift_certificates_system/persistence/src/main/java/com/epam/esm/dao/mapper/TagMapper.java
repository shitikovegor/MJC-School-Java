package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.ColumnName.ID;
import static com.epam.esm.dao.ColumnName.NAME;

/**
 * Component {@code TagMapper}  Tag mapper.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag(
                rs.getLong(ID.getValue()),
                rs.getString(NAME.getValue()));

        return tag;
    }
}
