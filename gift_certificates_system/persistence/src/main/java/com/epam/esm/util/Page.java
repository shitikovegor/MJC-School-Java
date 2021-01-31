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

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    /**
     * Instantiates a new Page.
     *
     * @param size the size
     * @param pageNumber the page
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page page1 = (Page) o;
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
