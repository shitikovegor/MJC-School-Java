package com.epam.esm.util;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

/**
 * Class {@code PageCollection} represents data of DTO objects and total records of objects in database.
 * Uses in body of Response entity.
 *
 * @param <T> the type parameter
 * @author Egor Shitikov
 * @version 1.0
 */
public class PageCollection<T extends Object> extends RepresentationModel<PageCollection<T>> {

    private List<T> items;
    private long total;

    public PageCollection(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PageCollection<?> that = (PageCollection<?>) o;
        return total == that.total &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), items, total);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageCollection{");
        sb.append("items=").append(items);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
