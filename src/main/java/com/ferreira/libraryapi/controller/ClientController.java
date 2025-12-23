package com.ferreira.libraryapi.controller;

import com.ferreira.libraryapi.model.Client;
import com.ferreira.libraryapi.service.ClientServer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServer clientServer;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salver (@RequestBody Client client) {

        clientServer.salver(client);

    }
}
