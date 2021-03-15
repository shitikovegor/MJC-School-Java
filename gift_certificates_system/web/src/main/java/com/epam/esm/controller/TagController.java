package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.PageCollection;
import com.epam.esm.util.PageFormatter;
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
     * @param size the size
     * @return the tags
     */
    @GetMapping
    public ResponseEntity<PageCollection<TagDto>> getTags(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size) {
        PageDto pageDto = new PageDto(size, page, tagService.findTotalRecords());
        List<TagDto> tags = tagService.findAll(pageDto);
        PageCollection<TagDto> collection = new PageCollection<>(tags, pageDto.getTotalRecords());
        tags.forEach(this::addRelationship);
        addPageRelationship(collection, pageDto);
        return ResponseEntity.ok(collection);
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
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Delete tag by.
     *
     * @param id the tag id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) {
        tagService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets tag by id.
     *
     * @return the tag by id
     */
    @GetMapping("/most_popular")
    public TagDto getMostPopularTagFromUserWithMaxPurchases() {
        return tagService.findMostPopularTagFromUserWithMaxPurchases();
    }

    private void addRelationship(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel("delete"));
    }

    private void addPageRelationship(PageCollection<TagDto> tagCollection, PageDto pageDto) {
        PageFormatter formatter = new PageFormatter();
        int lastPage = formatter.calculateLastPage(pageDto);
        if (pageDto.getPageNumber() < lastPage) {
            tagCollection.add(linkTo(methodOn(TagController.class)
                    .getTags(formatter.calculateNextPage(pageDto), pageDto.getSize()))
                    .withRel("next_page"));
        }
        if (pageDto.getPageNumber() > 1) {
            tagCollection.add(linkTo(methodOn(TagController.class)
                    .getTags(formatter.calculatePrevPage(pageDto), pageDto.getSize()))
                    .withRel("previous_page"));
        }
        tagCollection.add(linkTo(methodOn(TagController.class)
                .getTags(PageDto.FIRST_PAGE, pageDto.getSize()))
                .withRel("first_page"));
        tagCollection.add(linkTo(methodOn(TagController.class)
                .getTags(lastPage, pageDto.getSize()))
                .withRel("last_page"));
    }
}
