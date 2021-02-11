package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.CriteriaQueryCreator;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends BaseDaoImpl<GiftCertificate> implements GiftCertificateDao {
    private static final String GIFT_CERTIFICATE_FIND_ALL = "SELECT g FROM GiftCertificate g";

    @Override
    public List<GiftCertificate> findAll(Page page) {
        return entityManager.createQuery(GIFT_CERTIFICATE_FIND_ALL)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                                       Page page) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query =
                new CriteriaQueryCreator().generateCriteriaQueryBySearchParameters(giftCertificateQueryParameters,
                        builder);
        return entityManager.createQuery(query)
                .setFirstResult((page.getPageNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public int findTotalRecordsByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query =
                new CriteriaQueryCreator().generateCountCriteriaQueryBySearchParameters(giftCertificateQueryParameters,
                        builder);
        return entityManager.createQuery(query).getSingleResult().intValue();
    }
}
