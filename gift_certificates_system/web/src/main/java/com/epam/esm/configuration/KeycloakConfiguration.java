package com.epam.esm.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class {@code KeycloakConfiguration} contains spring configuration for keycloak authorization server.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Configuration
public class KeycloakConfiguration {

    @Value("${keycloak.serverUrl}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Value("${keycloak.adminUserName}")
    private String adminUserName;
    @Value("${keycloak.adminPassword}")
    private String adminPassword;

    /**
     * Bean {@code Keycloak} will configure keycloak
     *
     * @return the keycloak
     */
    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUserName)
                .password(adminPassword)
                .build();
    }
}
