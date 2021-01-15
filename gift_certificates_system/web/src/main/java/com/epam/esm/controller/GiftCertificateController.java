package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.dto.TagDto;
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

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates
            (@RequestParam(required = false) String tagName,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) TypeSort typeSort,
             @RequestParam(required = false) OrderSort orderSort) {
        GiftCertificateQueryParametersDto giftCertificateQueryParametersDto = new GiftCertificateQueryParametersDto();
        giftCertificateQueryParametersDto.setTagName(tagName);
        giftCertificateQueryParametersDto.setName(name);
        giftCertificateQueryParametersDto.setDescription(description);
        giftCertificateQueryParametersDto.setTypeSort(typeSort);
        giftCertificateQueryParametersDto.setOrderSort(orderSort);

        return giftCertificateService.findCertificates(giftCertificateQueryParametersDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id){
        return giftCertificateService.findById(id);
    }

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

    @PutMapping
    public GiftCertificateDto updateGiftCertificateById(@RequestBody GiftCertificateDto giftCertificateDto){
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id){
        giftCertificateService.remove(id);
    }
}
