package com.ferreira.libraryapi.controller;

import com.ferreira.libraryapi.controller.dto.AutorDTO;
import com.ferreira.libraryapi.controller.mappers.AutorMapper;
import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") //localhost:8080/autores
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO,
                                       Authentication authentication) {

        log.info("Cadastrando novo autor: {}", autorDTO.nome());

        Autor autor = autorMapper.toEntity(autorDTO);

        autorService.salvar(autor);

        URI uriAutorSalvo = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(uriAutorSalvo).build();

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
    })
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
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado"),
    })
    public ResponseEntity<Void> deletarAutorPorId(@PathVariable("id") String id){

        log.info("Deletando Autor por Id: {}", id);

        UUID idEntity = UUID.fromString(id);

        Optional<Autor> optionalAutor = autorService.obterAutorPorId(idEntity);

        if (optionalAutor.isEmpty()){
            return ResponseEntity.notFound().build();

        }

        autorService.deletarAutorPorId(optionalAutor.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
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
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
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
