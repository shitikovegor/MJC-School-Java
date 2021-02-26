package com.epam.esm.creation_util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.security.service.KeycloakAdminClientService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserUpdater {
    private final UserServiceImpl userService;
    private final KeycloakAdminClientService keycloakService;

    @Autowired
    public UserUpdater(UserServiceImpl userService, KeycloakAdminClientService keycloakService) {
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    static String generatePassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(4, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public void updateUsers(int count, List<String> emails, List<String> usersData) throws IOException {
        List<UserRegistrationDto> users = new ArrayList<>();
        IntStream.range(0, count).forEach(i -> {
            String userData = usersData.get(i);
            String[] firsNameLastName = userData.split(" ");
            UserDto foundUser = userService.findByUsername(emails.get(i));
            foundUser.setFirstName(firsNameLastName[0]);
            foundUser.setLastName(firsNameLastName[1]);


            userService.update(foundUser);

            UserRegistrationDto registration = new UserRegistrationDto();
            registration.setUsername(foundUser.getUsername());
            registration.setFirstName(foundUser.getFirstName());
            registration.setLastName(foundUser.getLastName());

            String password = generatePassword();

            registration.setPassword(password);
            registration.setConfirmPassword(password);

//            keycloakService.addUserToAuthServer(registration);
            users.add(registration);
        });
        DataReader.writePasswordFile(users);
    }
}
