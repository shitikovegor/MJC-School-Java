package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.util.List;

/**
 * Interface {@code GiftCertificateDao} represents an extended abstract behavior
 * for working with the GiftCertificate entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    /**
     * Update entity.
     *
     * @param entity to update
     * @return the updated entity
     */
    GiftCertificate update (GiftCertificate entity);

    /**
     * Find gift certificates by query parameters.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @return the list of gift certificates
     */
    List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters);
}
