package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    private static final String ORDER_FIND_BY_USER_ID = "SELECT o FROM Order o WHERE user_id_fk = ?1";
    private static final String ORDER_TOTAL_RECORDS = "SELECT COUNT(*) FROM Order";
    private static final String ORDER_TOTAL_RECORDS_BY_USER_ID = "SELECT COUNT(*) FROM Order WHERE user_id_fk = ?1";
    private static final String ORDER_FIND_ALL = "select o from Order o";

    @Override
    public List<Order> findAll(Page page) {
        return entityManager.createQuery(ORDER_FIND_ALL)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findOrdersByUserId(long id, Page page) {
        Query query = entityManager.createQuery(ORDER_FIND_BY_USER_ID);
        query.setParameter(1, id);
        return query
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public long findTotalRecords() {
        Long totalRecords = (Long) entityManager.createQuery(ORDER_TOTAL_RECORDS).getSingleResult();
        return totalRecords.intValue();
    }

    @Override
    public long findTotalRecordsByUserId(long userId) {
        Query query = entityManager.createQuery(ORDER_TOTAL_RECORDS_BY_USER_ID);
        query.setParameter(1, userId);
        return ((Long) query.getSingleResult()).intValue();
    }
}
