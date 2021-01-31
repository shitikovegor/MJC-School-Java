package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code TagService} describes business logic operations
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
    List<TagDto> findAll(PageDto pageDto);

    /**
     * Find tag by name.
     *
     * @param name the name
     * @return the optional of found tag DTO
     */
    Optional<TagDto> findByName(String name);
}
