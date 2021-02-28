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

    private int size;
    private int pageNumber;
    private long totalRecords;

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
    public PageDto(int size, int pageNumber, long totalRecords) {
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
    public PageDto(int size, int pageNumber) {
        this.size = size;
        this.pageNumber = pageNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
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
        return size == pageDto.size &&
                pageNumber == pageDto.pageNumber &&
                totalRecords == pageDto.totalRecords;
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
