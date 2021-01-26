package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code TagDao} represents an extended abstract behavior
 * for working with the Tag entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface TagDao extends BaseDao<Tag> {
    /**
     * Find by name optional.
     *
     * @param name the name to search
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Remove tags from cross table by tag id.
     *
     * @param id the id
     */
    void removeFromTableGiftCertificateHasTag(long id);
}
