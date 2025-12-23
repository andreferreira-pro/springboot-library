package com.ferreira.libraryapi.security;

import com.ferreira.libraryapi.model.Client;
import com.ferreira.libraryapi.service.ClientServer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientServer clientServer;

    private final TokenSettings tokenSettings;

    private final ClientSettings clientSettings;

    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
    return null;
    }


    @Override
    public RegisteredClient findByClientId(String clientId) {

        Client client = clientServer.obterPorId(clientId);

            if(client == null){
                return null;
            }

            return RegisteredClient
                    .withId(client.getId().toString())
                    .clientId(client.getClientId())
                    .clientSecret(client.getClientSecret())
                    .redirectUri(client.getRedirectURI())
                    .scope(client.getScope())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .tokenSettings(tokenSettings)
                    .clientSettings(clientSettings)
                    .build();

    }
}

