package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.GiftCertificateQueryParameters;
import com.epam.esm.util.Page;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.PageValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public long add(GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator.validate(giftCertificateDto);
        findAndSetTags(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificate addedGiftCertificate = giftCertificateDao.add(giftCertificate);

        return addedGiftCertificate.getId();
    }

    @Override
    public GiftCertificateDto findById(long id) {
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate.map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.GIFT_CERTIFICATE_NOT_FOUND,
                        String.valueOf(id)));
    }

    @Transactional
    @Override
    public void remove(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = giftCertificateOptional.orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id)));
        giftCertificateDao.remove(giftCertificate);
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificate = findById(giftCertificateDto.getId());
        GiftCertificateValidator.validate(giftCertificateDto);
        giftCertificateDto.setCreateDate(foundGiftCertificate.getCreateDate());
        findAndSetTags(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(giftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Transactional
    @Override
    public GiftCertificateDto updatePart(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificateDto = findById(giftCertificateDto.getId());
        fillUpdatedFields(giftCertificateDto, foundGiftCertificateDto);
        GiftCertificateValidator.validate(foundGiftCertificateDto);
        findAndSetTags(foundGiftCertificateDto);
        GiftCertificate foundGiftCertificate = modelMapper.map(foundGiftCertificateDto, GiftCertificate.class);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> findCertificates(GiftCertificateQueryParametersDto giftCertificateQueryParametersDto,
                                                     PageDto pageDto) {
        GiftCertificateQueryParameters parameters = modelMapper.map(giftCertificateQueryParametersDto,
                GiftCertificateQueryParameters.class);
        pageDto.setTotalRecords(giftCertificateDao.findTotalRecordsByQueryParameters(parameters));
        PageValidator.validatePage(pageDto);
        Page page = modelMapper.map(pageDto, Page.class);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByQueryParameters(parameters, page);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    private void findAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = giftCertificateDto.getTags().stream()
                .distinct()
                .map(tagDto -> tagService.findByName(tagDto.getName())
                        .orElseGet(() -> new TagDto(tagService.add(tagDto), tagDto.getName())))
                .collect(Collectors.toList());

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
        if (!updatedGiftCertificateDto.getTags().isEmpty()) {
            foundGiftCertificateDto.setTags(updatedGiftCertificateDto.getTags());
        }
    }
}
