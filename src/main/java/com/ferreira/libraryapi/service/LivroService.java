package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ferreira.libraryapi.repository.specs.LivroSpecs.*;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {

       return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){

        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisarBySpecification(String isbn, String nomeAutor, String titulo,
                                                GeneroLivro genero, Integer anoPublicacao){

        Specification<Livro> livroSpecification = Specification.where(
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(isbn != null){
            //query = query and isbn = :isbn
            livroSpecification = livroSpecification.and(isbnEqual(isbn));
        }

        if(titulo != null){

            livroSpecification = livroSpecification.and(tituloLike(titulo));
        }

        if(genero != null){

            livroSpecification = livroSpecification.and(generoEqual(genero));
        }

        if(anoPublicacao != null){

            livroSpecification = livroSpecification.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){

            livroSpecification = livroSpecification.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(livroSpecification);
    }
}
