package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final ModelMapper modelMapper;
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(ModelMapper modelMapper, GiftCertificateDao giftCertificateDao, TagService tagService) {
        this.modelMapper = modelMapper;
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
    }

    @Transactional
    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        checkTags(giftCertificateDto);
        GiftCertificateValidator.validate(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);
        giftCertificateDao.addGiftCertificateHasTag(addedGiftCertificate);

        return modelMapper.map(addedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificateValidator.validateId(id);
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);

        return foundGiftCertificate.map(this::mapAndSetTags)
        .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.GIFT_CERTIFICATE_NOT_FOUND.getKey(),
                String.valueOf(id)));
    }

    @Transactional
    @Override
    public void remove(long id) {
        GiftCertificateValidator.validateId(id);
        giftCertificateDao.removeGiftCertificateHasTag(id);
        giftCertificateDao.remove(id);
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificateDto = findById(giftCertificateDto.getId());
        fillUpdatedFields(giftCertificateDto, foundGiftCertificateDto);
        GiftCertificateValidator.validate(foundGiftCertificateDto);
        checkTags(foundGiftCertificateDto);

        GiftCertificate foundGiftCertificate = modelMapper.map(foundGiftCertificateDto, GiftCertificate.class);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        giftCertificateDao.removeGiftCertificateHasTag(updatedGiftCertificate.getId());
        giftCertificateDao.addGiftCertificateHasTag(updatedGiftCertificate);

        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> findCertificates(GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        GiftCertificateQueryParameters parameters = modelMapper.map(giftCertificateQueryParametersDto,
                GiftCertificateQueryParameters.class);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByQueryParameters(parameters);
        return giftCertificates.stream()
                .map(this::mapAndSetTags)
                .collect(Collectors.toList());
    }

    private void checkTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();

        if (giftCertificateDto.getTags() != null) {
            tags = giftCertificateDto.getTags().stream()
                    .map(tagDto -> tagService.add(tagDto))
                    .collect(Collectors.toList());
        }
        giftCertificateDto.setTags(tags);
    }

    private void fillUpdatedFields(GiftCertificateDto updatedGiftCertificateDto,
                                   GiftCertificateDto foundGiftCertificateDto) {
        if (updatedGiftCertificateDto.getName() != null) {
            foundGiftCertificateDto.setName(updatedGiftCertificateDto.getName());
        }
        if (updatedGiftCertificateDto.getDescription() != null) {
            foundGiftCertificateDto.setDescription(updatedGiftCertificateDto.getDescription());
        }
        if (updatedGiftCertificateDto.getPrice() != null) {
            foundGiftCertificateDto.setPrice(updatedGiftCertificateDto.getPrice());
        }
        if (updatedGiftCertificateDto.getDuration() != 0) {
            foundGiftCertificateDto.setDuration(updatedGiftCertificateDto.getDuration());
        }
        if (updatedGiftCertificateDto.getTags() != null) {
            foundGiftCertificateDto.setTags(updatedGiftCertificateDto.getTags());
        }
        foundGiftCertificateDto.setLastUpdateDate(LocalDateTime.now());
    }

    private GiftCertificateDto mapAndSetTags(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        giftCertificateDto.setTags(tagService.findByCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;
    }
}
