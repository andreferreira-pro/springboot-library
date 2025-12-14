package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.LivroRepository;
import com.ferreira.libraryapi.validator.LivroValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {

        livroValidator.validar(livro);

       return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){

        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro){
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisarBySpecification(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ){

        Specification<Livro> livroSpecification = Specification.where(
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(isbn != null){
            //query = query and isbn = :isbn
            livroSpecification = livroSpecification.and(isbnEqual(isbn));
        }

        if(titulo != null){

            livroSpecification = livroSpecification.and(tituloLike(titulo));
        }

        if(nomeAutor != null){

            livroSpecification = livroSpecification.and(nomeAutorLike(nomeAutor));
        }

        if(genero != null){

            livroSpecification = livroSpecification.and(generoEqual(genero));
        }

        if(anoPublicacao != null){

            livroSpecification = livroSpecification.and(anoPublicacaoEqual(anoPublicacao));
        }

        Pageable pagepageRequest = PageRequest.of(pagina,tamanhoPagina);

        return livroRepository.findAll(livroSpecification, pagepageRequest);
    }

    public void atualizar(Livro livro) {

        if (livro.getId() == null){
            throw new IllegalArgumentException("Necessário que o livro já esteja cadastrado para ser atualizado");
        }

        livroValidator.validar(livro);

        livroRepository.save(livro);
    }


}
