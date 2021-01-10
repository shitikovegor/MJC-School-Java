package com.epam.esm.dao;

public class SqlQuery {
    private SqlQuery(){
    }

    public static final String GIFT_CERTIFICATE_INSERT = "INSERT INTO gift_certificate (name, description, price, " +
            "duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String GIFT_CERTIFICATE_FIND_ALL = "SELECT id, name, description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate";

    public static final String GIFT_CERTIFICATE_FIND_BY_ID = "SELECT id, name, description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate WHERE id = ?";

    public static final String GIFT_CERTIFICATE_UPDATE = "UPDATE gift_certificate SET name = ?, " +
            "description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";

    public static final String GIFT_CERTIFICATE_REMOVE = "DELETE FROM gift_certificate WHERE id = ?";

    public static final String TAG_INSERT = "INSERT INTO tag (name) VALUES (?)";

    public static final String TAG_FIND_ALL = "SELECT id, name FROM tag";

    public static final String TAG_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = ?";

    public static final String TAG_REMOVE = "DELETE FROM tag WHERE id = ?";

    public static final String TAG_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";

    public static final String GIFT_CERTIFICATE_HAS_TAG_INSERT = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id_fk, tag_id_fk) VALUES (?, ?)";

    public static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "gift_certificate_id_fk = ?";
}
