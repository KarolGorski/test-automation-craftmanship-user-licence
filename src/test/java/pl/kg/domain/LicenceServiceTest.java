package pl.kg.domain;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static pl.kg.domain.LicenceTestConsts.GOOD_CHANGED_LICENCE_START_DATE;
import static pl.kg.domain.LicenceTestConsts.LICENCE_START_DATE;
import static pl.kg.domain.UserTestConsts.FIRST_NAME;
import static pl.kg.domain.UserTestConsts.LAST_NAME;
import static pl.kg.domain.UserTestConsts.USER_DATE_START;
import static pl.kg.domain.UserTestConsts.USER_ID;
import static pl.kg.domain.UserTestFactory.createUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;


public class LicenceServiceTest {

    private User user;

    @Mock
    LicenceRepository licenceRepositoryMock;

    @Mock
    UserRepository userRepositoryMock;

    @Captor
    ArgumentCaptor<Licence> accountArgumentCaptor;

    LicenceService licenceService;

    @Before
    public void before() {
        initMocks(this);
        licenceService = new LicenceService(licenceRepositoryMock, userRepositoryMock);
        user = createUser(USER_ID, FIRST_NAME, LAST_NAME, USER_DATE_START);
        mockUserLoad(user);
    }

    private void mockUserLoad(User user) {
        when(userRepositoryMock.getOne(USER_ID)).thenReturn(user);
    }

    @Test
    public void shouldCreateNewFreeLicence() {
        licenceService.createFreeLicence(USER_ID, LICENCE_START_DATE);

        verify(licenceRepositoryMock).save(accountArgumentCaptor.capture());
        Licence capturedLicence = accountArgumentCaptor.getValue();
        assertNotNull(capturedLicence);
    }

    @Test
    public void shouldCreateNewPremiumLicence() {
        licenceService.createPremiumLicence(USER_ID, LICENCE_START_DATE);

        verify(licenceRepositoryMock).save(accountArgumentCaptor.capture());
        Licence capturedLicence = accountArgumentCaptor.getValue();
        assertNotNull(capturedLicence);
    }

    @Test
    public void shouldCreateNewProLicence() {
        licenceService.createProLicence(USER_ID, LICENCE_START_DATE);

        verify(licenceRepositoryMock).save(accountArgumentCaptor.capture());
        Licence capturedLicence = accountArgumentCaptor.getValue();
        assertNotNull(capturedLicence);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_inactiveUser() {
        user.deactivateUser();
        Licence licence = new Licence(user, LicenceType.FREE, LICENCE_START_DATE);
        user.setLicence(licence);

        licenceService.changeLicence(user.getId(), LicenceType.PRO, GOOD_CHANGED_LICENCE_START_DATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_noLicence() {
        Licence licence = null;
        user.setLicence(licence);

        licenceService.changeLicence(user.getId(), LicenceType.PRO, GOOD_CHANGED_LICENCE_START_DATE);
    }
}