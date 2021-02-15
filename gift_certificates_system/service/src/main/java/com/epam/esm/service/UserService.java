package com.epam.esm.service;

import com.epam.esm.dto.AuthenticationDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * Interface {@code UserService} describes business logic operations
 * for working with users.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public interface UserService {
    /**
     * Register user.
     *
     * @param userDto the user DTO
     * @return the id of added user
     */
    long register(UserDto userDto);

    /**
     * Find user DTO by id.
     *
     * @param id the id
     * @return the found user DTO
     */
    UserDto findById(long id);

    /**
     * Find all users.
     *
     * @return the list of tags DTO
     */
    List<UserDto> findAll(PageDto pageDto);

    /**
     * Find user by username.
     *
     * @return the user DTO
     */
    UserDto findByUsername(String username);

    /**
     * Authenticate user user dto.
     *
     * @param authenticationDto the authentication dto
     * @return the user dto
     */
    UserDto authenticateUser(AuthenticationDto authenticationDto);
}
