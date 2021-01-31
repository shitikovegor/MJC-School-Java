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
import com.epam.esm.validator.TagValidator;
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

    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
    }

    @Transactional
    @Override
    public long add(TagDto tagDto) {
        TagValidator.validateName(tagDto.getName());
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Optional<Tag> existingTag = tagDao.findByName(tag.getName());

        if (existingTag.isEmpty()) {
            Tag addedTag = tagDao.add(tag);
            return addedTag.getId();
        } else {
            throw new IncorrectParameterException(ExceptionKey.TAG_EXISTS.getKey(), tag.getName());
        }
    }

    @Override
    public List<TagDto> findAll(PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        return tagDao.findAll(page).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        Optional<Tag> foundTag = tagDao.findById(id);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(), String.valueOf(id)));
    }

    @Transactional
    @Override
    public void remove(long id) {
        Optional<Tag> tagOptional = tagDao.findById(id);
        Tag tag = tagOptional.orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(), String.valueOf(id)));

        tagDao.removeFromTableGiftCertificateHasTag(tag.getId());
        tagDao.remove(tag);
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        TagValidator.validateName(name);
        Optional<Tag> foundTag = tagDao.findByName(name);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class));
    }
}
