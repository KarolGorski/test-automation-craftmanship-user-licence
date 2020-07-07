package pl.kg.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kg.domain.LicenceTestConsts.FREE_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.LICENCE_START_DATE;
import static pl.kg.domain.LicenceTestConsts.PREMIUM_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.PRO_LICENCE_EXPIRY_DATE;
import static pl.kg.domain.LicenceTestConsts.PRO_LICENCE_STARTING_TOKENS;
import static pl.kg.domain.LicenceTestConsts.getExpectedFreeExpiryDate;
import static pl.kg.domain.LicenceTestConsts.getExpectedPremiumExpiryDate;
import static pl.kg.domain.UserTestConsts.FIRST_NAME;
import static pl.kg.domain.UserTestConsts.LAST_NAME;
import static pl.kg.domain.UserTestConsts.USER_DATE_START;
import static pl.kg.domain.UserTestConsts.USER_ID;
import static pl.kg.domain.UserTestFactory.createUser;

import org.junit.Before;
import org.junit.Test;

public class LicenceCreationTest {

    private User user;

    @Before
    public void before() {
        user = createUser(USER_ID, FIRST_NAME, LAST_NAME, USER_DATE_START);
    }

    @Test
    public void shouldCreateNewFreeLicence() {
        Licence licence = new Licence(user, LicenceType.FREE, LICENCE_START_DATE);

        assertNull(licence.getId());
        assertEquals(USER_ID, licence.getUserId());
        assertEquals(LICENCE_START_DATE, licence.getStartDate());
        assertEquals(getExpectedFreeExpiryDate(LICENCE_START_DATE), licence.getExpiryDate());
        assertEquals(LicenceType.FREE, licence.getLicenceType());
        assertEquals(FREE_LICENCE_STARTING_TOKENS, licence.getTokens());
    }

    @Test
    public void shouldCreateNewPremiumLicence() {
        Licence licence = new Licence(user, LicenceType.PREMIUM, LICENCE_START_DATE);

        assertNull(licence.getId());
        assertEquals(USER_ID, licence.getUserId());
        assertEquals(LICENCE_START_DATE, licence.getStartDate());
        assertEquals(getExpectedPremiumExpiryDate(LICENCE_START_DATE), licence.getExpiryDate());
        assertEquals(LicenceType.PREMIUM, licence.getLicenceType());
        assertEquals(PREMIUM_LICENCE_STARTING_TOKENS, licence.getTokens());
    }

    @Test
    public void shouldCreateNewProLicence() {
        Licence licence = new Licence(user, LicenceType.PRO, LICENCE_START_DATE);

        assertNull(licence.getId());
        assertEquals(USER_ID, licence.getUserId());
        assertEquals(LICENCE_START_DATE, licence.getStartDate());
        assertEquals(PRO_LICENCE_EXPIRY_DATE, licence.getExpiryDate());
        assertEquals(LicenceType.PRO, licence.getLicenceType());
        assertEquals(PRO_LICENCE_STARTING_TOKENS, licence.getTokens());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnLicenceCreation_nullLicenceType() {
        Licence licence = new Licence(user, null, LICENCE_START_DATE);
    }

}
