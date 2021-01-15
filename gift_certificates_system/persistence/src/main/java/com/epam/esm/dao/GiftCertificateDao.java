package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.util.List;

/**
 * Interface {@code GiftCertificateDao} represents an extended abstract behavior
 * for working with the Tag entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    /**
     * Add gift certificate id to cross table.
     *
     * @param giftCertificate the gift certificate
     */
    void addGiftCertificateHasTag(GiftCertificate giftCertificate);

    /**
     * Remove gift certificates from cross table by gift certificate id.
     *
     * @param id the id
     */
    void removeGiftCertificateHasTag(long id);

    /**
     * Find gift certificates by query parameters.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @return the list of gift certificates
     */
    List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters);
}
