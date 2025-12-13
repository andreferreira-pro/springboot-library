package com.ferreira.libraryapi.controller;

import com.ferreira.libraryapi.controller.dto.AutorDTO;
import com.ferreira.libraryapi.controller.mappers.AutorMapper;
import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") //localhost:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO){

        Autor autor = autorMapper.toEntity(autorDTO);

        autorService.salvar(autor);

        URI uriAutorSalvo = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(uriAutorSalvo).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterAutorPorId(@PathVariable("id") String id){

        UUID idEntity = UUID.fromString(id);

        return autorService
                .obterAutorPorId(idEntity)
                .map(autor ->{
                    AutorDTO autorDTO = autorMapper.toDto(autor);
                    return ResponseEntity.ok(autorDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAutorPorId(@PathVariable("id") String id){

        UUID idEntity = UUID.fromString(id);

        Optional<Autor> optionalAutor = autorService.obterAutorPorId(idEntity);

        if (optionalAutor.isEmpty()){
            return ResponseEntity.notFound().build();

        }

        autorService.deletarAutorPorId(optionalAutor.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisaPorNomeENacionalidade(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){

        List<Autor> listaAutores = autorService.pesquisaByExample(nome, nacionalidade);

        List<AutorDTO> listaAutoresDTO = listaAutores
                .stream()
                .map(autorMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaAutoresDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarAutorPorId (
            @PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO){

        UUID idEntity = UUID.fromString(id);

        Optional<Autor> optionalAutor = autorService.obterAutorPorId(idEntity);

        if (optionalAutor.isEmpty()){
            return ResponseEntity.notFound().build();

        }

        Autor autorEntity = optionalAutor.get();

        autorEntity.setNome(autorDTO.nome());
        autorEntity.setNacionalidade(autorDTO.nacionalidade());
        autorEntity.setDataNascimento(autorDTO.dataNascimento());

        autorService.atualizar(autorEntity);

        return ResponseEntity.noContent().build();

    }
}
