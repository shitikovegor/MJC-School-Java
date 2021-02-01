package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code TagController} uses to work with gift-certificate information.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Gets tags.
     *
     * @param page the page number
     * @param size       the size
     * @return the tags
     */
    @GetMapping
    public List<TagDto> getTags(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(size, page);
        List<TagDto> tags = tagService.findAll(pageDto);
        tags.forEach(this::addRelationship);
        return tags;
    }

    /**
     * Gets tag by id.
     *
     * @param id the tag id
     * @return the tag DTO
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable @Valid long id) {
        return tagService.findById(id);
    }

    /**
     * Add tag.
     *
     * @param tagDto the tag DTO
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<String> addTag(@RequestBody TagDto tagDto) {
        long tagId = tagService.add(tagDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tagId)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        addRelationship(tagDto);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Delete tag by.
     *
     * @param id the tag id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.remove(id);
    }

    private void addRelationship(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
    }

    @GetMapping("/most_popular")
    public TagDto getTagById() {
        return tagService.findMostPopularTagFromUserWithMaxPurchases();
    }
}
