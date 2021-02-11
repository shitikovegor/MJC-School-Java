package com.epam.esm.validator;

import com.epam.esm.dto.PageDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

public class PageValidator {
    private static int MIN_NUMBER = 1;
    private static int DEFAULT_PAGE = 1;
    private static int DEFAULT_SIZE = 5;

    private PageValidator(){}

    /**
     * Validate page.
     *
     * @param pageDto the page dto
     */
    public static void validatePage(PageDto pageDto) {
        fillEmptyFields(pageDto);
        if (pageDto.getPageNumber() < MIN_NUMBER) {
            throw new IncorrectParameterException(ExceptionKey.INCORRECT_PAGE_FORMAT, String.valueOf(pageDto.getPageNumber()));
        }
        if (pageDto.getSize() < MIN_NUMBER) {
            throw new IncorrectParameterException(ExceptionKey.INCORRECT_PAGE_FORMAT, String.valueOf(pageDto.getSize()));
        }
        if (pageDto.getPageNumber() > calculateLastPage(pageDto)) {
            throw new IncorrectParameterException(ExceptionKey.PAGE_DOES_NOT_EXIST, String.valueOf(pageDto.getPageNumber()));
        }
    }

    private static void fillEmptyFields(PageDto pageDto) {
        if (pageDto.getPageNumber() == 0) {
            pageDto.setPageNumber(DEFAULT_PAGE);
        }
        if (pageDto.getSize() == 0) {
            pageDto.setSize(DEFAULT_SIZE);
        }
    }

    private static int calculateLastPage(PageDto pageDto) {
        int endPage;
        if (pageDto.getTotalRecords() % pageDto.getSize() == 0) {
            endPage = pageDto.getTotalRecords() / pageDto.getSize();
        } else {
            endPage = pageDto.getTotalRecords() / pageDto.getSize() + 1;
        }
        return endPage;
    }
}
