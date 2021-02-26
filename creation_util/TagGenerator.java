package com.epam.esm.creation_util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class TagGenerator {
    private final TagService tagService;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public TagGenerator(TagService tagService) {
        this.tagService = tagService;
    }

    public void addTagsToDatabase(int count, List<String> tagNames) {
        IntStream.range(0, count).forEach(i -> {
            TagDto tag = new TagDto();
            tag.setName(tagNames.get(i));
            try {
                tagService.add(tag);
            } catch (IncorrectParameterException e) {
                logger.info("Tag " + tag.getName() + "exists");
            }
        });
    }
}
