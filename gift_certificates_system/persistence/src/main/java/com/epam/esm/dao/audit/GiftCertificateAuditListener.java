package com.epam.esm.dao.audit;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * Class {@code GiftCertificateAuditListener} uses to audit gift certificate creation
 * and updating operations.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class GiftCertificateAuditListener {

    /**
     * Before create gift certificate audit.
     *
     * @param giftCertificate the gift certificate
     */
    @PrePersist
    public void beforeCreateGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
    }

    /**
     * Before update gift certificate audit.
     *
     * @param giftCertificate the gift certificate
     */
    @PreUpdate
    public void beforeUpdateGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }
}
