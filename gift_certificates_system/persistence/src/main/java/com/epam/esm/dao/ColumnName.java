package com.epam.esm.dao;

public enum ColumnName {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("create_date"),
    LAST_UPDATE_DATE("last_update_date");

    private String value;

    ColumnName(java.lang.String value) {
        this.value = value;
    }

    public java.lang.String getValue() {
        return value;
    }
}
