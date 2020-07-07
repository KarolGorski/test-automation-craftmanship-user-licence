package pl.kg.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LicenceRepository licenceRepository;

    public UserService(UserRepository userRepository, LicenceRepository licenceRepository) {
        this.userRepository = userRepository;
        this.licenceRepository = licenceRepository;
    }

    public void deactivateUser(long userId) {
        User user = userRepository.getOne(userId);
        user.deactivateUser();
        user.getLicence().setExpiryDate(LocalDate.now());
        userRepository.save(user);
    }

    public void reactivateUser(long userId) {
        User user = userRepository.getOne(userId);
        user.activateUser();
        userRepository.save(user);
    }
}
