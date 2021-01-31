package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;

import java.util.List;

/**
 * Interface {@code GiftCertificateService} describes business logic operations
 * for working with gift-certificates.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    /**
     * Update gift certificate.
     *
     * @param giftCertificateDto the gift certificate DTO
     * @return the gift certificate DTO
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);

    /**
     * Find gift-certificates list by parameters.
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters DTO
     * @return the list of gift-certificates DTO
     */
    List<GiftCertificateDto> findCertificates(GiftCertificateQueryParametersDto giftCertificateQueryParametersDto,
                                              PageDto pageDto);
}
