package com.ferreira.libraryapi.controller;


import com.ferreira.libraryapi.controller.dto.CadastroLivroDTO;
import com.ferreira.libraryapi.controller.dto.ErroResposta;
import com.ferreira.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.ferreira.libraryapi.controller.mappers.LivroMapper;
import com.ferreira.libraryapi.exceptions.RegistroDuplicadoException;
import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {

        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletarLivro(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisaPorParametros(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicaco
            ){

        var pesquisar = livroService.pesquisarBySpecification(isbn, titulo, nomeAutor, genero, anoPublicaco);

        var resultadoPesquisaLivros = pesquisar
                .stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultadoPesquisaLivros);
    }
}
