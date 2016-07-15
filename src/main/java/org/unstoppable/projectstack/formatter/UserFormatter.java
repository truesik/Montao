package org.unstoppable.projectstack.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

import java.text.ParseException;
import java.util.Locale;

@Service
public class UserFormatter implements Formatter<User> {
    @Autowired
    private UserService userService;

    @Override
    public User parse(String text, Locale locale) throws ParseException {
        return userService.getByUsername(text);
    }

    @Override
    public String print(User object, Locale locale) {
        return object != null ? object.getUsername() : "";
    }
}
