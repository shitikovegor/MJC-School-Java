package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            (@RequestBody GiftCertificateQueryParametersDto giftCertificateQueryParametersDto) {
        return giftCertificateService.findCertificates(giftCertificateQueryParametersDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id){
        return giftCertificateService.findById(id);
    }

    @PostMapping
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    @PutMapping
    public GiftCertificateDto updateGiftCertificateById(@RequestBody GiftCertificateDto giftCertificateDto){
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable long id){
        giftCertificateService.remove(id);
    }
}
