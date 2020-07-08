package pl.kg.domain;

import java.time.LocalDate;

class LicenceTestConsts {
    static final LocalDate LICENCE_START_DATE = LocalDate.parse("2020-07-01");
    static final LocalDate GOOD_CHANGED_LICENCE_START_DATE = LocalDate.parse("2020-08-02");
    static final LocalDate BAD_CHANGED_LICENCE_START_DATE = LocalDate.parse("2020-07-15");
    static final LocalDate PRO_LICENCE_EXPIRY_DATE = null;
    static final long FREE_LICENCE_STARTING_TOKENS = 0;
    static final long PREMIUM_LICENCE_STARTING_TOKENS = 100;
    static final long PRO_LICENCE_STARTING_TOKENS = 10000;

    static LocalDate getExpectedFreeExpiryDate(LocalDate startDate) {
        return startDate.plusMonths(1);
    }

    static LocalDate getExpectedPremiumExpiryDate(LocalDate startDate) {
        return startDate.plusYears(1);
    }
}
