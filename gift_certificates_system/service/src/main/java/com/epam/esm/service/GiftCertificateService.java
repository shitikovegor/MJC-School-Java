package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findCertificates(GiftCertificateQueryParametersDto giftCertificateQueryParametersDto);
}
