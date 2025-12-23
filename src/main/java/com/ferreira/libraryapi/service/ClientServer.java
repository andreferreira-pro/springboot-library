package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.model.Client;
import com.ferreira.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServer {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    public Client salver(Client client) {

        var senhaCriptografada = passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);

        return clientRepository.save(client);
    }

    public Client obterPorId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
