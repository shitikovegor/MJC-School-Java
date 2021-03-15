package com.epam.esm.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Class {@code TagDto} represents data of gift-certificate.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    private String name;

    /**
     * Instantiates a new Tag DTO.
     */
    public TagDto() {
    }

    /**
     * Instantiates a new Tag DTO.
     *
     * @param id   the id
     * @param name the name
     */
    public TagDto(long id, String name) {
        this.id = id;
        this.name = StringUtils.trim(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.trim(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagDto tagDto = (TagDto) o;
        return id == tagDto.id &&
                Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
