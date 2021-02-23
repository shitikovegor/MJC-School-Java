package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

/**
 * Class {@code UserDto} represents data of user.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class UserRegistrationDto extends RepresentationModel<UserRegistrationDto> {
    private long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    /**
     * Instantiates a new User DTO.
     */
    public UserRegistrationDto() {
    }

    /**
     * Instantiates a new Registration dto.
     *
     * @param id              the id
     * @param username        the username
     * @param password        the password
     * @param confirmPassword the confirm password
     * @param firstName       the first name
     * @param lastName        the last name
     */
    public UserRegistrationDto(long id, String username, String password, String confirmPassword, String firstName,
                               String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
        UserRegistrationDto userDto = (UserRegistrationDto) o;
        return id == userDto.id &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(confirmPassword, userDto.confirmPassword) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, password, confirmPassword, firstName, lastName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegistrationDto{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", confirmPassword='").append(confirmPassword).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName);
        sb.append('}');
        return sb.toString();
    }
}
