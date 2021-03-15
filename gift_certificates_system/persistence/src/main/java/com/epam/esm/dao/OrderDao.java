package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;

import java.util.List;

/**
 * Interface {@code OrderDao} describes CRUD operations for working with orders.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface OrderDao extends BaseDao<Order> {

    /**
     * Find orders by user id list.
     *
     * @param id   the id
     * @param page the page
     * @return the list
     */
    List<Order> findOrdersByUserId(long id, Page page);

    /**
     * Find total records in database.
     *
     * @return the long
     */
    long findTotalRecords();

    /**
     * Find total records in database by user Id.
     *
     * @return the long
     */
    long findTotalRecordsByUserId(long userId);
}
