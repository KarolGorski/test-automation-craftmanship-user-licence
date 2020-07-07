package pl.kg.domain;

import java.time.LocalDate;

import pl.kg.domain.utils.TestUtils;

class UserTestConsts {
    static final long USER_ID = TestUtils.generateId();
    static final String FIRST_NAME = "Jan";
    static final String LAST_NAME = "Kowalski";
    static final LocalDate USER_DATE_START = LocalDate.parse("2020-01-01");
}
