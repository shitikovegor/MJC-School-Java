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
public interface OrderService extends BaseService<OrderDto> {
    /**
     * Remove order by id.
     *
     * @param id the id of order to remove
     */
    void remove(long id);

    /**
     * Findlist of orders by user id.
     *
     * @param userId the user id
     * @return the list of orders
     */
    List<OrderDto> findByUserId(long userId, PageDto pageDto);
}
