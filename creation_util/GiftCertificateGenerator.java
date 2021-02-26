package com.epam.esm.creation_util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class GiftCertificateGenerator {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateGenerator(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    public void addGiftCertificatesToDatabase(int count, List<String> names, List<String> descriptions,
                                       List<String> tagNames) {
        IntStream.range(0, count).forEach(i -> {
            GiftCertificateDto giftCertificate = new GiftCertificateDto();
            giftCertificate.setName(names.get(i));
            giftCertificate.setDescription(descriptions.get(i));
            giftCertificate.setPrice(new BigDecimal(RandomUtils.nextDouble(10.00, 2001.00)));
            giftCertificate.setDuration(RandomUtils.nextInt(3, 61));

            int tagsAmount = RandomUtils.nextInt(1, 5);
            List<TagDto> tags = new ArrayList<>();
            IntStream.range(0, tagsAmount).forEach(j -> {
                TagDto tag = new TagDto();
                tag.setName(tagNames.get(RandomUtils.nextInt(0, 1000)));
                tags.add(tag);
            });
            giftCertificate.setTags(tags);
            giftCertificateService.add(giftCertificate);
        });
    }
}
