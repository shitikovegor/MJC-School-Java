package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
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

    @Override
    public TagDto add(TagDto tagDto) {
        TagValidator.validate(tagDto);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Optional<Tag> existingTag = tagDao.findByName(tag.getName());
        Tag addedTag = existingTag.orElseGet(() -> tagDao.add(tag));

        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        TagValidator.validateId(id);
        Optional<Tag> foundTag = tagDao.findById(id);
        return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found."));
    }

    @Transactional
    @Override
    public void remove(long id) {
        TagValidator.validateId(id);
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.remove(id);
    }
}
