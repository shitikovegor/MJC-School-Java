package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService extends BaseService<TagDto> {
    List<TagDto> findAll();
    List<TagDto> findByCertificateId(long id);
}
