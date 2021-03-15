package com.epam.esm.validator.impl;

import com.epam.esm.dto.PageDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.DtoValidator;
import org.springframework.stereotype.Component;

@Component
public class PageValidator implements DtoValidator<PageDto> {

    private static final int MIN_NUMBER = 1;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    @Override
    public void validate(PageDto pageDto) {
        fillEmptyFields(pageDto);
        if (pageDto.getTotalRecords() == 0) {
            throw new ResourceNotFoundException(ExceptionKey.USER_ORDERS_NOT_FOUND);
        }
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

    private void fillEmptyFields(PageDto pageDto) {
        if (pageDto.getPageNumber() == null) {
            pageDto.setPageNumber(DEFAULT_PAGE);
        }
        if (pageDto.getSize() == null) {
            pageDto.setSize(DEFAULT_SIZE);
        }
    }

    private int calculateLastPage(PageDto pageDto) {
        long endPage;
        if (pageDto.getTotalRecords() % pageDto.getSize() == 0) {
            endPage = pageDto.getTotalRecords() / pageDto.getSize();
        } else {
            endPage = pageDto.getTotalRecords() / pageDto.getSize() + 1;
        }
        return (int) endPage;
    }
}
