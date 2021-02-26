package com.epam.esm.dao.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

/**
 * Class {@code OrderAuditListener} uses to audit order creation operation.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class OrderAuditListener {

    /**
     * Before create order audit.
     *
     * @param order the order
     */
    @PrePersist
    public void beforeCreateOrder(Order order) {
        order.setPurchaseDate(LocalDateTime.now());
    }
}
