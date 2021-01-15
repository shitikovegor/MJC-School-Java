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
    void removeGiftCertificateHasTag(long id);

    /**
     * Find by certificate id list of tags.
     *
     * @param id the id of gift_certificate
     * @return the list of tags
     */
    List<Tag> findByCertificateId(long id);
}
