package com.epam.esm.util;

import com.epam.esm.dto.PageDto;

/**
 * Class {@code PageFormatter} uses to calculate pages for links.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class PageFormatter {

    public PageFormatter(){}
    /**
     * Calculate previous page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
    public int calculatePrevPage(PageDto pageDto) {
        return pageDto.getPageNumber() - 1;
    }

    /**
     * Calculate next page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
    public int calculateNextPage(PageDto pageDto) {
        return pageDto.getPageNumber() + 1;
    }

    /**
     * Calculate last page int.
     *
     * @param pageDto the page dto
     * @return the int
     */
    public int calculateLastPage(PageDto pageDto) {
        long endPage;
        if (pageDto.getTotalRecords() % pageDto.getSize() == 0) {
            endPage = pageDto.getTotalRecords() / pageDto.getSize();
        } else {
            endPage = pageDto.getTotalRecords() / pageDto.getSize() + 1;
        }
        return (int) endPage;
    }
}
