package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    void addGiftCertificateHasTag(GiftCertificate giftCertificate);

    void removeGiftCertificateHasTag(long id);

    List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters);
}
