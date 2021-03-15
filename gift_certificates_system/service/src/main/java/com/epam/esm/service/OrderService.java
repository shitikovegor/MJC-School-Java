package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;

import java.util.List;

/**
 * Interface {@code OrderService} describes base business logic operations
 * for working with orders.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface OrderService {

    /**
     * Add order.
     *
     * @param orderDto the order DTO
     * @return the id of added order
     */
    long add(OrderDto orderDto);

    /**
     * Find order DTO by id.
     *
     * @param id the id
     * @return the found order
     */
    OrderDto findById(long id);

    /**
     * Remove order by id.
     *
     * @param id the id of order to remove
     */
    void remove(long id);

    /**
     * Find list of orders by user id.
     *
     * @param userId the user id
     * @return the list of orders
     */
    List<OrderDto> findByUserId(long userId, PageDto pageDto);

    /**
     * Find total orders by user id.
     *
     * @param userId the user id
     * @return the long
     */
    long findTotalRecordsByUserId(long userId);
}
