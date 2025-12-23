package com.ferreira.libraryapi.controller;

import com.ferreira.libraryapi.controller.dto.UsuarioDTO;
import com.ferreira.libraryapi.controller.mappers.UsuarioMapper;
import com.ferreira.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salver(@RequestBody @Valid UsuarioDTO dto){

        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}
