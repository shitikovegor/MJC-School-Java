package com.epam.esm.entity;

public class GiftCertificateQueryParameters {

    public enum TypeSort {
        NAME("gift_certificate.name"),
        CREATE_DATE("gift_certificate.create_date");

        private String queryValue;

        TypeSort(String queryValue) {
            this.queryValue = queryValue;
        }

        public String getQueryValue() {
            return queryValue;
        }
    }

    public enum OrderSort {
        ASC(" ASC"),
        DESC(" DESC");

        private String queryValue;

        OrderSort(String queryValue) {
            this.queryValue = queryValue;
        }

        public String getQueryValue() {
            return queryValue;
        }
    }

    private String tagName;
    private String name;
    private String description;
    private TypeSort typeSort;
    private OrderSort orderSort;

    public GiftCertificateQueryParameters() {
    }

    public GiftCertificateQueryParameters(String tagName, String name, String description, TypeSort typeSort, OrderSort orderSort) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.typeSort = typeSort;
        this.orderSort = orderSort;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeSort getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(TypeSort typeSort) {
        this.typeSort = typeSort;
    }

    public OrderSort getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(OrderSort orderSort) {
        this.orderSort = orderSort;
    }
}
