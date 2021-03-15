package com.epam.esm.dto;

import java.util.Objects;

/**
 * Class {@code {PageDto}} represents data of pagination parameters.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class PageDto {

    public static final int FIRST_PAGE = 1;

    private Integer size;
    private Integer pageNumber;
    private Long totalRecords;

    /**
     * Instantiates a new Page DTO.
     */
    public PageDto() {
    }

    /**
     * Instantiates a new Page dto.
     *
     * @param size         the size
     * @param pageNumber   the page number
     * @param totalRecords the total records
     */
    public PageDto(Integer size, Integer pageNumber, Long totalRecords) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.totalRecords = totalRecords;
    }

    /**
     * Instantiates a new Page dto.
     *
     * @param size       the size
     * @param pageNumber the page number
     */
    public PageDto(Integer size, Integer pageNumber) {
        this.size = size;
        this.pageNumber = pageNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageDto pageDto = (PageDto) o;
        return Objects.equals(size, pageDto.size) &&
                Objects.equals(pageNumber, pageDto.pageNumber) &&
                Objects.equals(totalRecords, pageDto.totalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, pageNumber, totalRecords);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDto{");
        sb.append("size=").append(size);
        sb.append(", pageNumber=").append(pageNumber);
        sb.append(", totalRecords=").append(totalRecords);
        sb.append('}');
        return sb.toString();
    }
}
