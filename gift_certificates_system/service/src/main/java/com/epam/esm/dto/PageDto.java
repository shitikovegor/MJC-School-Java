package com.epam.esm.dto;

import java.util.Objects;

/**
 * Class {@code {PageDto}} represents data of pagination parameters.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class PageDto {
    private int size;
    private int pageNumber;

    /**
     * Instantiates a new Page DTO.
     */
    public PageDto() {
    }

    /**
     * Instantiates a new Page DTO.
     *
     * @param size the size
     * @param pageNumber the page
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageDto page1 = (PageDto) o;
        return size == page1.size &&
                pageNumber == page1.pageNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, pageNumber);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append("size=").append(size);
        sb.append(", page=").append(pageNumber);
        sb.append('}');
        return sb.toString();
    }
}
