package com.epam.esm.entity;

/**
 * Class {@code GiftCertificateQueryParameters} represents a class with
 * parameters for generation query
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class GiftCertificateQueryParameters {

    /**
     * Enum {@code SortType} represents values of sort by type.
     *
     * @author Egor Shitikov
     * @version 1.0
     */
    public enum SortType {
        /**
         * Name sort type.
         */
        NAME("name"),
        /**
         * Create date sort type.
         */
        CREATE_DATE("createDate");

        private String queryValue;

        SortType(String queryValue) {
            this.queryValue = queryValue;
        }

        /**
         * Gets query value.
         *
         * @return the query value
         */
        public String getQueryValue() {
            return queryValue;
        }
    }

    /**
     * Enum {@code SortOrder} represents values of sorting order.
     *
     * @author Egor Shitikov
     * @version 1.0
     */
    public enum SortOrder {
        /**
         * Asc sort order.
         */
        ASC,
        /**
         * Desc sort order.
         */
        DESC;
    }

    private String tagName;
    private String name;
    private String description;
    private SortType sortType;
    private SortOrder sortOrder;

    /**
     * Instantiates a new Gift certificate query parameters.
     */
    public GiftCertificateQueryParameters() {
    }

    /**
     * Instantiates a new Gift certificate query parameters.
     *
     * @param tagName     the tag name
     * @param name        the name
     * @param description the description
     * @param sortType    the sort type
     * @param sortOrder   the sort order
     */
    public GiftCertificateQueryParameters(String tagName, String name, String description, SortType sortType, SortOrder sortOrder) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
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

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
