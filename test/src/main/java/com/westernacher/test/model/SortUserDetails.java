package com.westernacher.test.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum SortUserDetails {

    SORT_BY_FIRST_NAME_ASC(Sort.by(Sort.Direction.ASC, "firstName")),
    SORT_BY_FIRST_NAME_DESC(Sort.by(Sort.Direction.DESC, "firstName")),

    SORT_BY_LAST_NAME_ASC(Sort.by(Sort.Direction.ASC, "lastName")),
    SORT_BY_LAST_NAME_DESC(Sort.by(Sort.Direction.DESC, "lastName")),

    SORT_BY_EMAIL_ASC(Sort.by(Sort.Direction.ASC, "email")),
    SORT_BY_EMAIL_DESC(Sort.by(Sort.Direction.DESC, "email")),

    SORT_BY_BIRTH_DATE_ASC(Sort.by(Sort.Direction.ASC, "birthDate")),
    SORT_BY_BIRTH_DATE_DESC(Sort.by(Sort.Direction.DESC, "birthDate"));

    private final Sort sort;
}
