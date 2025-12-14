package com.ferreira.libraryapi.validator;

import com.ferreira.libraryapi.exceptions.CampoValidoException;
import com.ferreira.libraryapi.exceptions.RegistroDuplicadoException;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_OBRIGATORIO_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro){

        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatorioNul(livro)){
            throw new CampoValidoException("preço", "Para livros a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNul(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_OBRIGATORIO_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){

        var livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }

}
