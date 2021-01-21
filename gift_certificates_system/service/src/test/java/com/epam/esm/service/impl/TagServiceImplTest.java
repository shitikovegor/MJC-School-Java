package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

class TagServiceImplTest {
    private TagService tagService;
    private TagDao tagDao;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        tagDao = mock(TagDaoImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        tagService = new TagServiceImpl(modelMapper, tagDao);
    }

    @AfterEach
    void tearDown() {
        tagService = null;
        tagDao = null;
        modelMapper = null;
    }

    @Test
    void addCorrectDataShouldReturnTagDtoId() {
        TagDto tagDto = new TagDto(24, "Food");
        Tag tag = new Tag(24, "Food");
        when(tagDao.findByName("Food")).thenReturn(Optional.empty());
        when(tagDao.add(tag)).thenReturn(tag);
        long expected = 24L;
        assertEquals(expected, tagService.add(tagDto));
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        TagDto tagDto = new TagDto(24, "Food!@!");
        when(tagDao.findByName(any())).thenReturn(Optional.empty());
        when(tagDao.add(any())).thenReturn(null);
        assertThrows(IncorrectParameterException.class, () -> tagService.add(tagDto));
    }

    @Test
    void findAllCorrectDataShouldReturnTagDtoList() {
        Tag tag1 = new Tag(1, "Food");
        Tag tag2 = new Tag(23, "Rest");
        List<Tag> tags = List.of(tag1, tag2);

        TagDto tagDto1 = new TagDto(1, "Food");
        TagDto tagDto2 = new TagDto(23, "Rest");
        List<TagDto> tagsDto = List.of(tagDto1, tagDto2);

        when(tagDao.findAll()).thenReturn(tags);
        assertEquals(tagsDto, tagService.findAll());
    }

    @Test
    void findByIdCorrectDataShouldReturnTagDto() {
        Tag tag = new Tag(2, "Rest");
        when(tagDao.findById(2)).thenReturn(Optional.of(tag));
        TagDto expected = new TagDto(2, "Rest");
        long id = 2L;
        assertEquals(expected, tagService.findById(id));
    }

    @Test
    void findByIdCorrectDataShouldThrowException() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
    }

    @Test
    void findByIdIncorrectDataShouldThrowException() {
        Tag tag = new Tag(2, "Rest");
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag));
        long id = -2L;
        assertThrows(IncorrectParameterException.class, () -> tagService.findById(id));
    }

    @Test
    void removeCorrectDataShouldNotThrowException() {
        Tag tag = new Tag(1L, "Food");
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag));
        when(tagDao.remove(anyLong())).thenReturn(true);
        long id = 2L;
        assertDoesNotThrow(() -> tagService.remove(id));
    }

    @Test
    void removeIncorrectDataShouldThrowIncorrectException() {
        Tag tag = new Tag(1L, "Food");
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag));
        when(tagDao.remove(anyLong())).thenReturn(true);
        doNothing().when(tagDao).removeFromTableGiftCertificateHasTag(anyLong());
        long id = -2L;
        assertThrows(IncorrectParameterException.class, () -> tagService.remove(id));
    }

    @Test
    void removeIncorrectDataShouldThrowResourceNotFoundException() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        when(tagDao.remove(anyLong())).thenReturn(false);
        doNothing().when(tagDao).removeFromTableGiftCertificateHasTag(anyLong());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> tagService.remove(id));
    }

    @Test
    void findByCertificateIdCorrectDataShouldReturnListOfTags() {
        List<Tag> tags = List.of(new Tag(2, "Rest"),
                new Tag(3, "Sea"));
        when(tagDao.findByCertificateId(2)).thenReturn(tags);
        long id = 2L;
        List<TagDto> expected = List.of(new TagDto(2, "Rest"),
                new TagDto(3, "Sea"));
        assertEquals(expected, tagService.findByCertificateId(id));
    }

    @Test
    void findByNameCorrectDataShouldReturnTagDto() {
        Tag tag = new Tag(2, "Rest");
        when(tagDao.findByName("Rest")).thenReturn(Optional.of(tag));
        Optional<TagDto> expected = Optional.of(new TagDto(2, "Rest"));
        String actualName = "Rest";
        assertEquals(expected, tagService.findByName(actualName));
    }

    @Test
    void findByNameIncorrectDataShouldThrowException() {
        Tag tag = new Tag(2, "Rest");
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(tag));
        String incorrectName = "<\\name_incorrect";
        assertThrows(IncorrectParameterException.class, () -> tagService.findByName(incorrectName));
    }
}