package com.epam.esm.creation_util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class UserGenerator {
    private final UserService userService;

    @Autowired
    public UserGenerator(UserService userService) {
        this.userService = userService;
    }

    public void addUsersToDatabase(int count, List<String> emails) {
        IntStream.range(0, count).forEach(i -> {
            UserDto user = new UserDto();
            user.setEmail(emails.get(i));
            userService.add(user);
        });
    }
}
