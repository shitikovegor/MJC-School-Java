package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code TagService} Base service describes business logic operations
 * for working with tags.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface TagService extends BaseService<TagDto> {
    /**
     * Find all tags.
     *
     * @return the list of tags DTO
     */
    List<TagDto> findAll();

    /**
     * Find tag by name.
     *
     * @param name the name
     * @return the optional of found tag DTO
     */
    Optional<TagDto> findByName(String name);
}
