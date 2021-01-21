package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.*;

/**
 * Class {@code GiftCertificateController} uses to work with gift-certificate information.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Gets list of gift-certificates by parameters.
     *
     * @param tagName     the tag name
     * @param name        the name
     * @param description the description
     * @param sortType    the sort type
     * @param sortOrder   the sort order
     * @return the list of gift-certificates
     */
    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates
            (@RequestParam(required = false) String tagName,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) SortType sortType,
             @RequestParam(required = false) SortOrder sortOrder) {
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto = new GiftCertificateQueryParametersDto();
        giftCertificateQueryParametersDto.setTagName(tagName);
        giftCertificateQueryParametersDto.setName(name);
        giftCertificateQueryParametersDto.setDescription(description);
        giftCertificateQueryParametersDto.setSortType(sortType);
        giftCertificateQueryParametersDto.setSortOrder(sortOrder);

        return giftCertificateService.findCertificates(giftCertificateQueryParametersDto);
    }

    /**
     * Get gift-certificate by id.
     *
     * @param id the id
     * @return the gift-certificate DTO
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id){
        return giftCertificateService.findById(id);
    }

    /**
     * Add gift-certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<String> addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        long tagId = giftCertificateService.add(giftCertificateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tagId)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Update gift-certificate.
     *
     * @param giftCertificateDto the gift-certificate DTO
     * @return the gift-certificate DTO
     */
    @PutMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto){
        return giftCertificateService.update(giftCertificateDto);
    }

    /**
     * Delete gift-certificate by id.
     *
     * @param id the gift-certificate id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id){
        giftCertificateService.remove(id);
    }
}
