package com.epam.esm.creation_util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component
public class DataCreationExecutor implements ApplicationRunner {
    private final UserGenerator userGenerator;
    private final TagGenerator tagGenerator;
    private final GiftCertificateGenerator giftCertificateGenerator;
    private final OrderGenerator orderGenerator;
    private final UserUpdater userUpdater;
    private final KeycloakPasswordSetter keycloakPasswordSetter;

    @Autowired
    public DataCreationExecutor(UserGenerator userGenerator, TagGenerator tagGenerator,
                                GiftCertificateGenerator giftCertificateGenerator, OrderGenerator orderGenerator, UserUpdater userUpdater, KeycloakPasswordSetter keycloakPasswordSetter) {
        this.userGenerator = userGenerator;
        this.tagGenerator = tagGenerator;
        this.giftCertificateGenerator = giftCertificateGenerator;
        this.orderGenerator = orderGenerator;
        this.userUpdater = userUpdater;
        this.keycloakPasswordSetter = keycloakPasswordSetter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application run!!!");
        List<String> emails = DataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_db/user_emails.txt");
        List<String> tagNames = DataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_db/tag_names.txt");
        List<String> certificateNames = DataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_db/certificate_names.txt");
        List<String> certificateDescriptions = DataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_db/certificate_descriptions.txt");
        List<String> userNames = DataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_db/z_names_users.txt");

        Map<String, String> userPaswords = keycloakPasswordSetter.resetUsersPasswords();
        DataReader.writePasswordFile(userPaswords);
//        userUpdater.updateUsers(1000, emails, userNames);
//        userGenerator.addUsersToDatabase(1000, emails);
//        tagGenerator.addTagsToDatabase(50, tagNames);
//        giftCertificateGenerator.addGiftCertificatesToDatabase(10000, certificateNames, certificateDescriptions,
//                tagNames);
//        orderGenerator.addOrdersToDatabase(15000);
    }
}
