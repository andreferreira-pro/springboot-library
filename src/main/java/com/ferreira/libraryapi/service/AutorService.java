package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.repository.AutorRepository;
import com.ferreira.libraryapi.repository.LivroRepository;
import com.ferreira.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){

        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor){

        if (autor.getId() == null){
            throw new IllegalArgumentException("Necessário que o autor já esteja cadastrado para ser atualizado");
        }

        autorValidator.validar(autor);

        autorRepository.save(autor);
    }

    public Optional<Autor> obterAutorPorId (UUID id){
        return autorRepository.findById(id);
    }

    public void deletarAutorPorId (Autor autor){

        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido deletar um autor que possui livros cadastrados");
        }

        autorRepository.delete(autor);
    }

    public List<Autor> listarAutores(String nome, String nacionalidade){

        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome,nacionalidade);
        }

        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        if (nome != null){
            return autorRepository.findByNome(nome);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){

        Autor autor = new Autor();

        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor,matcher);

        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){

        return livroRepository.existsByAutor(autor);
    }

}
