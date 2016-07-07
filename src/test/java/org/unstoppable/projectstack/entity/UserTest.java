package org.unstoppable.projectstack.entity;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@mail.com";
    private static final LocalDate REGISTRATION_DATE = LocalDate.now();

    @Test
    public void equals() throws Exception {
        User user1 = createUser();
        User user2 = createUser();
        assertEquals(user1, user2);
        assertTrue(user1.hashCode() == user2.hashCode());
    }

    private User createUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setRegistrationDate(REGISTRATION_DATE);
        return user;
    }

}