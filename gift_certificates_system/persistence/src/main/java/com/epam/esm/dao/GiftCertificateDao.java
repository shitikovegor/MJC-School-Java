package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    void addGiftCertificateHasTag(GiftCertificate giftCertificate);

    void removeGiftCertificateHasTag(long id);
}
