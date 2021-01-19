package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

class GiftCertificateServiceImplTest {
    private GiftCertificateService giftCertificateService;
    private GiftCertificateDao giftCertificateDao;
    private ModelMapper modelMapper;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        giftCertificateDao = mock(GiftCertificateDao.class);
        tagService = mock((TagServiceImpl.class));
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        giftCertificateService = new GiftCertificateServiceImpl(modelMapper, giftCertificateDao, tagService);
    }

    @AfterEach
    void tearDown() {
        giftCertificateService = null;
        giftCertificateDao = null;
        modelMapper = null;
        tagService = null;
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificateDtoId() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59), new ArrayList<>());
        GiftCertificate giftCertificate = new GiftCertificate(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59), new ArrayList<>());
        when(tagService.findByName(anyString())).thenReturn(Optional.empty());
        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).addToTableGiftCertificateHasTag(any(GiftCertificate.class));
        assertEquals(giftCertificateDto.getId(), giftCertificateService.add(giftCertificateDto));
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(1323432, "Квадроциклы",
                "Активный отдых", new BigDecimal(1000000), 15,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 10, 15, 0, 0), new ArrayList<>());
        when(giftCertificateDao.add(any())).thenReturn(null);
        assertThrows(IncorrectParameterException.class, () -> giftCertificateService.add(giftCertificateDto));
    }

    @Test
    void findCertificatesCorrectDataShouldReturnTagDtoList() {
        GiftCertificate giftCertificate1 = new GiftCertificate(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        GiftCertificate giftCertificate2 = new GiftCertificate(56, "rest in hotel",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());
        List<GiftCertificate> giftCertificates = List.of(giftCertificate1, giftCertificate2);

        GiftCertificateDto giftCertificateDto1 = new GiftCertificateDto(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        GiftCertificateDto giftCertificateDto2 = new GiftCertificateDto(56, "rest in hotel",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());
        List<GiftCertificateDto> giftCertificatesDto = List.of(giftCertificateDto1, giftCertificateDto2);

        when(giftCertificateDao.findByQueryParameters(any(GiftCertificateQueryParameters.class)))
                .thenReturn(giftCertificates);
        when(tagService.findByCertificateId(anyLong())).thenReturn(new ArrayList<>());
        GiftCertificateQueryParametersDto parametersDto =
                new GiftCertificateQueryParametersDto("re", "in", "i",
                        GiftCertificateQueryParametersDto.SortType.NAME,
                        GiftCertificateQueryParametersDto.SortOrder.ASC);
        assertEquals(giftCertificatesDto, giftCertificateService.findCertificates(parametersDto));
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificateDto() {
        GiftCertificate giftCertificate = new GiftCertificate(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        when(giftCertificateDao.findById(3)).thenReturn(Optional.of(giftCertificate));
        when(tagService.findByCertificateId(anyLong())).thenReturn(new ArrayList<>());
        GiftCertificateDto expected = new GiftCertificateDto(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        long id = 3L;
        assertEquals(expected, giftCertificateService.findById(id));
    }

    @Test
    void findByIdCorrectDataShouldThrowException() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        when(tagService.findByCertificateId(anyLong())).thenReturn(new ArrayList<>());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
    }

    @Test
    void findByIdIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(tagService.findByCertificateId(anyLong())).thenReturn(new ArrayList<>());
        long id = -2L;
        assertThrows(IncorrectParameterException.class, () -> giftCertificateService.findById(id));
    }

    @Test
    void removeCorrectDataShouldNotThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate(2, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        doNothing().when(giftCertificateDao).removeFromTableGiftCertificateHasTag(anyLong());
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.remove(anyLong())).thenReturn(true);
        long id = 2L;
        assertDoesNotThrow(() -> giftCertificateService.remove(id));
    }

    @Test
    void removeIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate(2, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), new ArrayList<>());
        doNothing().when(giftCertificateDao).removeFromTableGiftCertificateHasTag(anyLong());
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.remove(anyLong())).thenReturn(true);
        long id = -2L;
        assertThrows(IncorrectParameterException.class, () -> giftCertificateService.remove(id));
    }

    @Test
    void updateCorrectDataShouldReturnGiftCertificateDto() {
        GiftCertificate foundGiftCertificate = new GiftCertificate(56, "rest in hotel",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());
        GiftCertificate updatedGiftCertificate = new GiftCertificate(56, "new rest",
                "rest. In good place", new BigDecimal(180), 24,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());
        GiftCertificateDto giftCertificateForUpdate = new GiftCertificateDto(56, "new rest",
                "rest. In good place", new BigDecimal(180), 24,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());

        long id = 56L;
        when(giftCertificateDao.findById(id)).thenReturn(Optional.of(foundGiftCertificate));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(updatedGiftCertificate);
        doNothing().when(giftCertificateDao).removeFromTableGiftCertificateHasTag(id);
        doNothing().when(giftCertificateDao).addToTableGiftCertificateHasTag(foundGiftCertificate);
        assertEquals(giftCertificateForUpdate, giftCertificateService.update(giftCertificateForUpdate));
    }

    @Test
    void updateCorrectDataShouldThrowException() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(56, "new rest",
                "rest. In good place", new BigDecimal(180), 24,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());

        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(null);
        doNothing().when(giftCertificateDao).removeFromTableGiftCertificateHasTag(anyLong());
        doNothing().when(giftCertificateDao).addToTableGiftCertificateHasTag(any(GiftCertificate.class));
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.update(giftCertificateDto));
    }

    @Test
    void updateIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate(56, "rest in hotel",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());
        GiftCertificateDto incorrectGiftCertificate = new GiftCertificateDto(56, "new rest",
                "rest. In good place", new BigDecimal(180), -24,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), new ArrayList<>());

        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDao).removeFromTableGiftCertificateHasTag(anyLong());
        doNothing().when(giftCertificateDao).addToTableGiftCertificateHasTag(giftCertificate);
        assertThrows(IncorrectParameterException.class, () -> giftCertificateService.update(incorrectGiftCertificate));
    }
}