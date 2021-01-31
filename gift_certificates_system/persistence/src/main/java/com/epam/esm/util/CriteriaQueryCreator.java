package com.epam.esm.util;

import com.epam.esm.entity.*;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code CriteriaQueryCreator} creates criteria query for search certificates.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class CriteriaQueryCreator {
    /**
     * Generate criteria query by search parameters criteria query.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @param builder                        the CriteriaBuilder
     * @return the criteria query
     */
    public static CriteriaQuery generateCriteriaQueryBySearchParameters
            (GiftCertificateQueryParameters giftCertificateQueryParameters, CriteriaBuilder builder) {
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        String tagName = giftCertificateQueryParameters.getTagName();
        if (tagName != null) {
            ListJoin<GiftCertificate, Tag> tags = root.join(GiftCertificate_.tags, JoinType.LEFT);
            predicates.add(builder.equal(tags.get(Tag_.NAME), tagName));
        }
        String name = giftCertificateQueryParameters.getName();
        if (name != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.NAME), "%" + name + "%"));
        }
        String description = giftCertificateQueryParameters.getDescription();
        if (description != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.DESCRIPTION), "%" + description + "%"));
        }
        query.select(root).where(predicates.toArray(Predicate[]::new));

        GiftCertificateQueryParameters.SortType sortType = giftCertificateQueryParameters.getSortType();
        GiftCertificateQueryParameters.SortOrder sortOrder = giftCertificateQueryParameters.getSortOrder();
        if (sortType == null || sortOrder == null) {
            query.orderBy(builder.asc(root.get(GiftCertificate_.CREATE_DATE)));
        } else if (sortOrder == GiftCertificateQueryParameters.SortOrder.ASC) {
            query.orderBy(builder.asc(root.get(sortType.getQueryValue())));
        } else {
            query.orderBy(builder.desc(root.get(sortType.getQueryValue())));
        }
        return query;
    }
}
