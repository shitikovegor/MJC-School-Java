package com.epam.esm.util;

import com.epam.esm.entity.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class {@code CriteriaQueryCreator} creates criteria query for search certificates.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class CriteriaQueryCreator {
    private static final String PERCENT = "%";
    /**
     * Generate criteria query by search parameters criteria query.
     *
     * @param giftCertificateQueryParameters the gift certificate query parameters
     * @param builder                        the CriteriaBuilder
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> generateCriteriaQueryBySearchParameters
            (GiftCertificateQueryParameters giftCertificateQueryParameters, CriteriaBuilder builder) {
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.addAll(addTagNames(giftCertificateQueryParameters, builder, root));
        predicates.addAll(addName(giftCertificateQueryParameters, builder, root));
        predicates.addAll(addDescription(giftCertificateQueryParameters, builder, root));

        query.select(root).where(predicates.toArray(Predicate[]::new));
        setSortType(giftCertificateQueryParameters, builder, root, query);
        return query;
    }

    public CriteriaQuery<Long> generateCountCriteriaQueryBySearchParameters
            (GiftCertificateQueryParameters giftCertificateQueryParameters, CriteriaBuilder builder) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.addAll(addTagNames(giftCertificateQueryParameters, builder, root));
        predicates.addAll(addName(giftCertificateQueryParameters, builder, root));
        predicates.addAll(addDescription(giftCertificateQueryParameters, builder, root));

        query.select(builder.count(root)).where(predicates.toArray(Predicate[]::new));
        return query;
    }

    private List<Predicate> addTagNames(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                        CriteriaBuilder builder, Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        String[] tagNames = giftCertificateQueryParameters.getTagNames();
        if (tagNames != null) {
            predicates.addAll(Arrays.stream(tagNames)
                    .map(tagName -> builder.equal(root.join(GiftCertificate_.tags).get(Tag_.NAME), tagName))
                    .collect(Collectors.toList()));
        }
        return predicates;
    }

    private List<Predicate> addName(GiftCertificateQueryParameters giftCertificateQueryParameters,
                              CriteriaBuilder builder, Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        String name = giftCertificateQueryParameters.getName();
        if (name != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.NAME), PERCENT + name + PERCENT));
        }
        return predicates;
    }

    private List<Predicate> addDescription(GiftCertificateQueryParameters giftCertificateQueryParameters,
                                    CriteriaBuilder builder, Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        String description = giftCertificateQueryParameters.getDescription();
        if (description != null) {
            predicates.add(builder.like(root.get(GiftCertificate_.DESCRIPTION), PERCENT + description + PERCENT));
        }
        return predicates;
    }

    private void setSortType(GiftCertificateQueryParameters giftCertificateQueryParameters,
                             CriteriaBuilder builder, Root<GiftCertificate> root,
                             CriteriaQuery<GiftCertificate> query) {
        GiftCertificateQueryParameters.SortType sortType = giftCertificateQueryParameters.getSortType();
        GiftCertificateQueryParameters.SortOrder sortOrder = giftCertificateQueryParameters.getSortOrder();
        if (sortType != null) {
            if (sortOrder != null && sortOrder == GiftCertificateQueryParameters.SortOrder.DESC) {
                query.orderBy(builder.desc(root.get(sortType.getQueryValue())));
            } else {
                query.orderBy(builder.asc(root.get(sortType.getQueryValue())));
            }
        } else {
            query.orderBy(builder.asc(root.get(GiftCertificate_.CREATE_DATE)));
        }
    }
}
