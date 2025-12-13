package com.ferreira.libraryapi.service;

import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.AutorRepository;
import com.ferreira.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository  autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void atualizacoSemAtualizar(){

        var id = UUID.fromString("b7e522d8-b58a-4a46-a9b7-f2c1d374b530");
        var livro = livroRepository.findById(id).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2025,12,06));

    }

    @Transactional
    public void executar(){

        //Salvar autor
        Autor autorLivro = new Autor();

        autorLivro.setNome("Teste José Marcelo");
        autorLivro.setNacionalidade("Italiano");
        autorLivro.setDataNascimento(LocalDate.of(1991,11,06));

        autorRepository.save(autorLivro);

        //Salvar livro
        Livro livro = new Livro();

        livro.setIsbn("2121-3030");
        livro.setPreco(BigDecimal.valueOf(6200));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Livro do José Marcelo");
        livro.setDataPublicacao(LocalDate.of(1913,4,15));

        livro.setAutor(autorLivro);

        livroRepository.save(livro);

        if(autorLivro.getNome().equals("Teste José Marcelo")){
            throw new RuntimeException("Rollback!");
        }

    }

}
