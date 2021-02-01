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

    private String[] tagNames;
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
     * @param tagNames     the tag names
     * @param name        the name
     * @param description the description
     * @param sortType    the sort type
     * @param sortOrder   the sort order
     */
    public GiftCertificateQueryParametersDto(String[] tagNames, String name, String description, SortType sortType,
                                             SortOrder sortOrder) {
        this.tagNames = tagNames;
        this.name = name;
        this.description = description;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
    }

    public String[] getTagNames() {
        return tagNames;
    }

    public void setTagNames(String[] tagNames) {
        this.tagNames = tagNames;
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
