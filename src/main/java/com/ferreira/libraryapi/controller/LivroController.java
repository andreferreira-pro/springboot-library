package com.ferreira.libraryapi.controller;


import com.ferreira.libraryapi.controller.dto.CadastroLivroDTO;
import com.ferreira.libraryapi.controller.dto.ErroResposta;
import com.ferreira.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.ferreira.libraryapi.controller.mappers.LivroMapper;
import com.ferreira.libraryapi.exceptions.RegistroDuplicadoException;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;

    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dtoBody) {

        Livro livro = livroMapper.toEntity(dtoBody);

        livroService.salvar(livro);

        URI uriLivroCadastrado = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(uriLivroCadastrado).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarLivro(
            @PathVariable("id") String id) {

        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
