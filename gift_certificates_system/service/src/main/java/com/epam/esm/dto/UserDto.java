package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Class {@code UserDto} represents data of user.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class UserDto extends RepresentationModel<UserDto> {
    private long id;
    private String username;
    private String firstName;
    private String lastName;

    /**
     * Instantiates a new User DTO.
     */
    public UserDto() {
    }

    /**
     * Instantiates a new User dto.
     *
     * @param id        the id
     * @param username  the username
     * @param firstName the first name
     * @param lastName  the last name
     */
    public UserDto(long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, firstName, lastName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
