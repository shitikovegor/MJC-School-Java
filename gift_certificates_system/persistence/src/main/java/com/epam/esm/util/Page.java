package com.epam.esm.util;

import java.util.Objects;

/**
 * Class {@code {Page}} consists parameters which use pagination selection from database.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class Page {
    private int size;
    private int pageNumber;
    private int totalRecords;

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    /**
     * Instantiates a new Page.
     *
     * @param size         the size
     * @param pageNumber   the page number
     * @param totalRecords the total records
     */
    public Page(int size, int pageNumber, int totalRecords) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.totalRecords = totalRecords;
    }

    /**
     * Instantiates a new Page.
     *
     * @param size       the size
     * @param pageNumber the page number
     */
    public Page(int size, int pageNumber) {
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

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
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
        Page page = (Page) o;
        return size == page.size &&
                pageNumber == page.pageNumber &&
                totalRecords == page.totalRecords;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, pageNumber, totalRecords);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append("size=").append(size);
        sb.append(", pageNumber=").append(pageNumber);
        sb.append(", totalRecords=").append(totalRecords);
        sb.append('}');
        return sb.toString();
    }
}
