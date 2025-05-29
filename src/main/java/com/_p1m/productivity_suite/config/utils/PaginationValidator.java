package com._p1m.productivity_suite.config.utils;

import com._p1m.productivity_suite.config.exceptions.BadRequestException;

public class PaginationValidator {

    public static void validatePageAndSize(final int page, final int size) {
        if (page < 1) {
            throw new BadRequestException("Page must be greater than 0");
        }
        if (size < 1) {
            throw new BadRequestException("Size must be greater than 0");
        }
    }
}
