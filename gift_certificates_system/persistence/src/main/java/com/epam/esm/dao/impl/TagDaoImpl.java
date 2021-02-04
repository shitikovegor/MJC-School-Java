package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends BaseDaoImpl<Tag> implements TagDao {
    private static final String TAG_FIND_BY_NAME = "SELECT t FROM Tag t WHERE name = ?1";
    private static final String TAG_FIND_ALL = "SELECT t FROM Tag t";
    private static final String GIFT_CERTIFICATE_HAS_TAG_REMOVE = "DELETE FROM gift_certificate_has_tag WHERE " +
            "tag_id_fk = ?";
    private static final String TAG_FIND_POPULAR_TAG_BY_MAX_USER_PRICE =
            "WITH max_price AS (SELECT user.id AS user_id, SUM(gift_certificate_order.cost) AS user_sum_cost " +
                    "FROM gift_certificate_order LEFT JOIN user ON gift_certificate_order.user_id_fk = user.id " +
                    "GROUP BY user.id) " +
                    "SELECT t.id, t.name " +
                    "FROM gift_certificate_order gco LEFT JOIN user ON gco.user_id_fk = user.id " +
                    "LEFT JOIN gift_certificate gc on gco.gift_certificate_id_fk = gc.id " +
                    "LEFT JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id_fk " +
                    "INNER JOIN tag t on gcht.tag_id_fk = t.id WHERE user.id IN ( SELECT user_id " +
                    "FROM max_price HAVING MAX(user_sum_cost)) " +
                    "GROUP BY t.id, user.id " +
                    "ORDER BY COUNT(t.id) DESC LIMIT 1";
    private static final String TAG_TOTAL_RECORDS = "SELECT COUNT(*) FROM Tag";

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(TAG_FIND_ALL)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Query query = entityManager.createQuery(TAG_FIND_BY_NAME);
        query.setParameter(1, name);
        return query.getResultStream().findFirst();
    }

    @Override
    public void removeFromTableGiftCertificateHasTag(long id) {
        Query query = entityManager.createNativeQuery(GIFT_CERTIFICATE_HAS_TAG_REMOVE);
        query.setParameter(1, id).executeUpdate();
    }

    @Override
    public Optional<Tag> findMostPopularTagFromUserWithMaxPurchases() {
        Query query = entityManager.createNativeQuery(TAG_FIND_POPULAR_TAG_BY_MAX_USER_PRICE, Tag.class);
        return query.getResultStream().findFirst();
    }

    @Override
    public int findTotalRecords() {
        Long totalRecords = (Long) entityManager.createQuery(TAG_TOTAL_RECORDS).getSingleResult();
        return totalRecords.intValue();
    }
}
