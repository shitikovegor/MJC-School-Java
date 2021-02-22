package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.PageCollection;
import com.epam.esm.util.PageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code UserController} uses to work with users information.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the tag service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets users.
     *
     * @param page the page number
     * @param size the size
     * @return the users
     */
    @GetMapping
    public ResponseEntity<PageCollection<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(size, page);
        List<UserDto> users = userService.findAll(pageDto);
        PageCollection<UserDto> collection = new PageCollection<>(users, pageDto.getTotalRecords());
        users.forEach(this::addRelationship);
        addPageRelationship(collection, pageDto);
        return ResponseEntity.ok(collection);
    }

    /**
     * Gets user by id.
     *
     * @param id the user id
     * @return the user DTO
     */
    @PreAuthorize("hasRole('admin') or #id == principal.userId")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        UserDto userDto = userService.findById(id);
        addRelationship(userDto);
        return userDto;
    }

    private void addRelationship(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }

    private void addPageRelationship(PageCollection<UserDto> userCollection, PageDto pageDto) {
        PageFormatter formatter = new PageFormatter();
        int lastPage = formatter.calculateLastPage(pageDto);
        if (pageDto.getPageNumber() < lastPage) {
            userCollection.add(linkTo(methodOn(UserController.class)
                    .getUsers(formatter.calculateNextPage(pageDto), pageDto.getSize()))
                    .withRel("next_page"));
        }
        if (pageDto.getPageNumber() > 1) {
            userCollection.add(linkTo(methodOn(UserController.class)
                    .getUsers(formatter.calculatePrevPage(pageDto), pageDto.getSize()))
                    .withRel("previous_page"));
        }
        userCollection.add(linkTo(methodOn(UserController.class)
                .getUsers(PageDto.FIRST_PAGE, pageDto.getSize()))
                .withRel("first_page"));
        userCollection.add(linkTo(methodOn(UserController.class)
                .getUsers(lastPage, pageDto.getSize()))
                .withRel("last_page"));
    }
}
