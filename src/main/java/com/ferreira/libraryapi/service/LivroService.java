package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
}
