package pl.kg.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class LicenceService {

    private final LicenceRepository licenceRepository;
    private final UserRepository userRepository;

    public LicenceService(LicenceRepository licenceRepository, UserRepository userRepository) {
        this.licenceRepository = licenceRepository;
        this.userRepository = userRepository;
    }

    public void createFreeLicence(long userId, LocalDate startDate) {
        createLicence(userId, startDate, LicenceType.FREE);
    }

    public void createPremiumLicence(long userId, LocalDate startDate) {
        createLicence(userId, startDate, LicenceType.PREMIUM);
    }

    public void createProLicence(long userId, LocalDate startDate) {
        createLicence(userId, startDate, LicenceType.PRO);
    }

    private void createLicence(long userId, LocalDate startDate, LicenceType licenceType) {
        User user = userRepository.getOne(userId);
        Licence licence = new Licence(user, licenceType, startDate);
        licenceRepository.save(licence);
    }

    public void changeLicence(long userId, LicenceType targetLicenceType, LocalDate startDate) {
        User user = userRepository.getOne(userId);
        validateUser(user);
        Licence licence = user.getLicence();
        validateLicence(licence, userId);
        licence.changeLicence(targetLicenceType, startDate);
        licenceRepository.save(licence);
    }

    private void validateUser(User user) {
        if (UserStatus.INATIVE.equals(user.getStatus())) {
            throw new IllegalArgumentException("Can not change Licence Type of INACTIVE user. User id: " + user.getId());
        }
    }

    private void validateLicence(Licence licence, long userId) {
        if (isNull(licence)) {
            throw new IllegalArgumentException("Can not change licence if it doesn't exist. User id: " + userId);
        }
    }

    public void removeLicence(long userId) {
        User user = userRepository.getOne(userId);
        Licence licence = user.getLicence();
        if (isNull(licence)) {
            return;
        }
        user.setLicence(null);
        licenceRepository.delete(licence);
    }
}
