package com.epam.esm.dto;

import java.util.Objects;

/**
 * Class {@code RoleDto} represents data of role.
 *
 * @author Egor Shitikov
 * @version 1.0
 */

public class RoleDto {
    long id;
    String name;

    /**
     * Instantiates a new Role DTO.
     */
    public RoleDto() {
    }

    /**
     * Instantiates a new Role DTO.
     *
     * @param id   the id
     * @param name the name
     */
    public RoleDto(long id, String name) {
        this.id = id;
        this.name = name;
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
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto role = (RoleDto) o;
        return id == role.id && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
