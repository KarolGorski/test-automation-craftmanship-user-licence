package pl.kg.domain;

import java.time.LocalDate;

import org.springframework.test.util.ReflectionTestUtils;

public class UserTestFactory {

    private static final String ID_FIELD_NAME = "id";

    public static User createUser(Long userId, String firstName, String lastName, LocalDate dateStart) {
        User user = new User(firstName, lastName, dateStart, null);
        ReflectionTestUtils.setField(user, ID_FIELD_NAME, userId);
        return user;
    }
}
