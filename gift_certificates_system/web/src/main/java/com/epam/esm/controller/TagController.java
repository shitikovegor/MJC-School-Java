package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.add(tagDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) {
        tagService.remove(id);
    }
}
