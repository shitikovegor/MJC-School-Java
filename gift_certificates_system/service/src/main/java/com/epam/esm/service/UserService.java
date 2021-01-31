package com.epam.esm.service;

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
public interface UserService extends BaseService<UserDto> {
    /**
     * Find all users.
     *
     * @return the list of tags DTO
     */
    List<UserDto> findAll(PageDto pageDto);

    /**
     * Find user by email.
     *
     * @return the user DTO
     */
    UserDto findByEmail(String email);
}
