package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code TagService} describes business logic operations
 * for working with tags.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface TagService {
    /**
     * Add tag.
     *
     * @param tagDto the t
     * @return the id of added tag
     */
    long add(TagDto tagDto);

    /**
     * Find tag DTO by id.
     *
     * @param id the id
     * @return the found tag DTO
     */
    TagDto findById(long id);

    /**
     * Remove tag by id.
     *
     * @param id the id of tag to remove
     */
    void remove(long id);

    /**
     * Find all tags.
     *
     * @param pageDto the page DTO
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

    /**
     * Find most popular tag from user with max purchases tag dto.
     *
     * @return the tag dto
     */
    TagDto findMostPopularTagFromUserWithMaxPurchases();

    /**
     * Find total tags records.
     *
     * @return the long
     */
    long findTotalRecords();
}
