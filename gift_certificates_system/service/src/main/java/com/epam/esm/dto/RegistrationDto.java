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
public class RegistrationDto extends RepresentationModel<RegistrationDto> {
    private long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private List<RoleDto> roles;

    /**
     * Instantiates a new User DTO.
     */
    public RegistrationDto() {
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
     * @param roles           the roles
     */
    public RegistrationDto(long id, String username, String password, String confirmPassword, String firstName,
                           String lastName, List<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
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
        RegistrationDto userDto = (RegistrationDto) o;
        return id == userDto.id &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(confirmPassword, userDto.confirmPassword) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, password, confirmPassword, firstName, lastName, roles);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegistrationDto{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", confirmPassword='").append(confirmPassword).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
