package pl.kg.domain;

import static org.junit.Assert.assertEquals;
import static pl.kg.domain.LicenceTestConsts.BAD_CHANGED_LICENCE_START_DATE;
import static pl.kg.domain.LicenceTestConsts.FREE_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.GOOD_CHANGED_LICENCE_START_DATE;
import static pl.kg.domain.LicenceTestConsts.LICENCE_START_DATE;
import static pl.kg.domain.LicenceTestConsts.PREMIUM_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.PRO_LICENCE_EXPIRY_DATE;
import static pl.kg.domain.LicenceTestConsts.PRO_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.getExpectedFreeExpiryDate;
import static pl.kg.domain.LicenceTestConsts.getExpectedPremiumExpiryDate;
import static pl.kg.domain.LicenceType.FREE;
import static pl.kg.domain.LicenceType.PREMIUM;
import static pl.kg.domain.LicenceType.PRO;
import static pl.kg.domain.UserTestConsts.FIRST_NAME;
import static pl.kg.domain.UserTestConsts.LAST_NAME;
import static pl.kg.domain.UserTestConsts.USER_DATE_START;
import static pl.kg.domain.UserTestConsts.USER_ID;
import static pl.kg.domain.UserTestFactory.createUser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LicenceChangeTest {

    private User user;

    @Parameterized.Parameter
    public String testName;

    @Parameterized.Parameter(value = 1)
    public LicenceType startingLicenceType;

    @Parameterized.Parameter(value = 2)
    public LicenceType targetLicenceType;

    @Parameterized.Parameter(value = 3)
    public LicenceType expectedLicenceType;

    @Parameterized.Parameter(value = 4)
    public long expectedTokensAmount;

    @Parameterized.Parameter(value = 5)
    public LocalDate targetStartDate;

    @Parameterized.Parameter(value = 6)
    public LocalDate expectedStartDate;

    @Parameterized.Parameter(value = 7)
    public LocalDate expectedExpiryDate;

    @Before
    public void before() {
        user = createUser(USER_ID, FIRST_NAME, LAST_NAME, USER_DATE_START);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        "Free to Premium. Should change.",
                        FREE,
                        PREMIUM,
                        PREMIUM,
                        PREMIUM_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        getExpectedPremiumExpiryDate(GOOD_CHANGED_LICENCE_START_DATE)
                },
                {
                        "Free to Pro. Should change.",
                        FREE,
                        PRO,
                        PRO,
                        PRO_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        PRO_LICENCE_EXPIRY_DATE
                },
                {
                        "Premium to Free. Should change.",
                        PREMIUM,
                        FREE,
                        FREE,
                        PREMIUM_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        getExpectedFreeExpiryDate(GOOD_CHANGED_LICENCE_START_DATE)
                },
                {
                        "Premium to Pro. Should change.",
                        PREMIUM,
                        PRO,
                        PRO,
                        PREMIUM_LICENCE_STARTING_TOKENS + PRO_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        PRO_LICENCE_EXPIRY_DATE
                },
                {
                        "Pro to Free. Should change.",
                        PRO,
                        FREE,
                        FREE,
                        PRO_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        getExpectedFreeExpiryDate(GOOD_CHANGED_LICENCE_START_DATE)
                },
                {
                        "Pro to Premium. Should change.",
                        PRO,
                        PREMIUM,
                        PREMIUM,
                        PRO_LICENCE_STARTING_TOKENS + PREMIUM_LICENCE_STARTING_TOKENS,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        GOOD_CHANGED_LICENCE_START_DATE,
                        getExpectedPremiumExpiryDate(GOOD_CHANGED_LICENCE_START_DATE)
                },
                {
                        "Free to Pro. Should not change. Bad date.",
                        FREE,
                        PRO,
                        FREE,
                        FREE_LICENCE_STARTING_TOKENS,
                        BAD_CHANGED_LICENCE_START_DATE,
                        LICENCE_START_DATE,
                        getExpectedFreeExpiryDate(LICENCE_START_DATE)
                },
                {
                        "Premium to Pro. Should not change. Bad date.",
                        PREMIUM,
                        PRO,
                        PREMIUM,
                        PREMIUM_LICENCE_STARTING_TOKENS,
                        BAD_CHANGED_LICENCE_START_DATE,
                        LICENCE_START_DATE,
                        getExpectedPremiumExpiryDate(LICENCE_START_DATE)
                },
                {
                        "Pro to Free. Should not change. Bad date.",
                        PRO,
                        FREE,
                        PRO,
                        PRO_LICENCE_STARTING_TOKENS,
                        BAD_CHANGED_LICENCE_START_DATE,
                        LICENCE_START_DATE,
                        PRO_LICENCE_EXPIRY_DATE
                }
        });
    }

    @Test
    public void shouldChangeLicence() {
        Licence licence = new Licence(user, startingLicenceType, LICENCE_START_DATE);

        licence.changeLicence(targetLicenceType, targetStartDate);

        assertEquals(expectedLicenceType, licence.getLicenceType());
        assertEquals(expectedStartDate, licence.getStartDate());
        assertEquals(expectedTokensAmount, licence.getTokens());
        assertEquals(expectedExpiryDate, licence.getExpiryDate());
    }
}
