package com.epam.esm.util;

import com.epam.esm.dto.PageDto;

public class PageFormatter {
    public static int calculatePrevPage(PageDto pageDto) {
        return pageDto.getPageNumber() - 1;
    }

    public static int calculateNextPage(PageDto pageDto) {
        return pageDto.getPageNumber() + 1;
    }

    public static int calculateLastPage(PageDto pageDto) {
        int endPage;
        if (pageDto.getTotalRecords() % pageDto.getSize() == 0) {
            endPage = pageDto.getTotalRecords() / pageDto.getSize();
        } else {
            endPage = pageDto.getTotalRecords() / pageDto.getSize() + 1;
        }
        return endPage;
    }
}
