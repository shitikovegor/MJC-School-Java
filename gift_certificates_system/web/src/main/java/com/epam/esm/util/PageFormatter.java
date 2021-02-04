package com.epam.esm.util;

import com.epam.esm.dto.PageDto;

/**
 * Class {@code PageFormatter} uses to calculate pages for links.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class PageFormatter {

    private PageFormatter(){}
    /**
     * Calculate previous page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
    public static int calculatePrevPage(PageDto pageDto) {
        return pageDto.getPageNumber() - 1;
    }

    /**
     * Calculate next page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
    public static int calculateNextPage(PageDto pageDto) {
        return pageDto.getPageNumber() + 1;
    }

    /**
     * Calculate last page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
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
