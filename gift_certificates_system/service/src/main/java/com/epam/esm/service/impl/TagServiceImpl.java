package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.Page;
import com.epam.esm.validator.DtoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final ModelMapper modelMapper;
    private final TagDao tagDao;
    private final DtoValidator<TagDto> tagValidator;
    private final DtoValidator<PageDto> pageValidator;

    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao, DtoValidator<TagDto> tagValidator, DtoValidator<PageDto> pageValidator) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
        this.pageValidator = pageValidator;
    }

    @Transactional
    @Override
    public long add(TagDto tagDto) {
        tagValidator.validate(tagDto);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Optional<Tag> existingTag = tagDao.findByName(tag.getName());

        if (!existingTag.isPresent()) {
            Tag addedTag = tagDao.add(tag);
            return addedTag.getId();
        } else {
            throw new IncorrectParameterException(ExceptionKey.TAG_EXISTS, tag.getName());
        }
    }

    @Override
    public List<TagDto> findAll(PageDto pageDto) {
        pageValidator.validate(pageDto);
        Page page = modelMapper.map(pageDto, Page.class);
        return tagDao.findAll(page).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        Optional<Tag> foundTag = tagDao.findById(id);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id)));
    }

    @Transactional
    @Override
    public void remove(long id) {
        Optional<Tag> tagOptional = tagDao.findById(id);
        Tag tag = tagOptional.orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id)));

        tagDao.removeFromTableGiftCertificateHasTag(tag.getId());
        tagDao.remove(tag);
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        Optional<Tag> foundTag = tagDao.findByName(name);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class));
    }

    @Override
    public TagDto findMostPopularTagFromUserWithMaxPurchases() {
        Optional<Tag> foundTag = tagDao.findMostPopularTagFromUserWithMaxPurchases();
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.TAG_DOES_NOT_EXIST));
    }

    @Override
    public long findTotalRecords() {
        return tagDao.findTotalRecords();
    }
}
