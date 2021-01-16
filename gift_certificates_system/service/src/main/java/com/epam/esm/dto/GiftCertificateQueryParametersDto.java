package com.epam.esm.dto;

/**
 * Class {@code GiftCertificateQueryParametersDto} represents data of
 * parameters for generation query
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class GiftCertificateQueryParametersDto {

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
        NAME,
        /**
         * Create date sort type.
         */
        CREATE_DATE;
    }

    /**
     * Enum {@code SortOrder} represents values of sort order.
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
     * Instantiates a new Gift certificate query parameters DTO.
     */
    public GiftCertificateQueryParametersDto() {
    }

    /**
     * Instantiates a new Gift certificate query parameters DTO.
     *
     * @param tagName     the tag name
     * @param name        the name
     * @param description the description
     * @param sortType    the sort type
     * @param sortOrder   the sort order
     */
    public GiftCertificateQueryParametersDto(String tagName, String name, String description, SortType sortType, SortOrder sortOrder) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
    }

    /**
     * Gets tag name.
     *
     * @return the tag name
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets tag name.
     *
     * @param tagName the tag name
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets sort type.
     *
     * @return the sort type
     */
    public SortType getSortType() {
        return sortType;
    }

    /**
     * Sets sort type.
     *
     * @param sortType the sort type
     */
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
