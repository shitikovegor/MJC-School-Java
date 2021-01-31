package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.SortOrder;
import static com.epam.esm.dto.GiftCertificateQueryParametersDto.SortType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
     * Gets gift certificates.
     *
     * @param tagName     the tag name
     * @param name        the name
     * @param description the description
     * @param sortType    the sort type
     * @param sortOrder   the sort order
     * @param pageNumber  the page number
     * @param size        the size
     * @return the gift certificates
     */
    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates
    (@RequestParam(required = false) String tagName,
     @RequestParam(required = false) String name,
     @RequestParam(required = false) String description,
     @RequestParam(required = false) SortType sortType,
     @RequestParam(required = false) SortOrder sortOrder,
     @RequestParam(required = false, defaultValue = "1") int pageNumber,
     @RequestParam(required = false, defaultValue = "5") int size) {
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto = new GiftCertificateQueryParametersDto();
        giftCertificateQueryParametersDto.setTagName(tagName);
        giftCertificateQueryParametersDto.setName(name);
        giftCertificateQueryParametersDto.setDescription(description);
        giftCertificateQueryParametersDto.setSortType(sortType);
        giftCertificateQueryParametersDto.setSortOrder(sortOrder);
        PageDto pageDto = new PageDto(size, pageNumber);

        List<GiftCertificateDto> giftCertificates =
                giftCertificateService.findCertificates(giftCertificateQueryParametersDto, pageDto);
        giftCertificates.forEach(this::addRelationship);
        return giftCertificates;
    }

    /**
     * Get gift-certificate by id.
     *
     * @param id the id
     * @return the gift-certificate DTO
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        GiftCertificateDto foundGiftCertificate = giftCertificateService.findById(id);
        addGiftCertificate(foundGiftCertificate);
        return foundGiftCertificate;
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
        addRelationship(giftCertificateDto);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Update gift-certificate.
     *
     * @param giftCertificateDto the gift-certificate DTO
     * @return the gift-certificate DTO
     */
    @PutMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto updatedGiftCertificate = giftCertificateService.update(giftCertificateDto);
        addGiftCertificate(updatedGiftCertificate);
        return updatedGiftCertificate;
    }

    /**
     * Delete gift-certificate by id.
     *
     * @param id the gift-certificate id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addRelationship(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.getTags().forEach(tagDto ->
                tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));

    }
}
