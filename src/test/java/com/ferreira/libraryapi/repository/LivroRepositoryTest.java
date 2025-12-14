package com.ferreira.libraryapi.repository;

import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTeste(){

        Livro livro = new Livro();
        livro.setIsbn("2525-2620");
        livro.setPreco(BigDecimal.valueOf(900));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1991,4,10));

        Autor autorLivro = autorRepository
                .findById(UUID.fromString("9ac4770f-e7c2-450b-acd6-a550ccda0966"))
                .orElse(null);

        livro.setAutor(autorLivro);

        livroRepository.save(livro);

    }

    @Test
    void salvarAutorELivroTeste(){

        Livro livro = new Livro();
        livro.setIsbn("2525-3030");
        livro.setPreco(BigDecimal.valueOf(1200));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Mais um Livro");
        livro.setDataPublicacao(LocalDate.of(1990,4,15));

        Autor autorLivro = new Autor();

        autorLivro.setNome("Thais");
        autorLivro.setNacionalidade("Portuguesa");
        autorLivro.setDataNascimento(LocalDate.of(1997,07,31));

        autorRepository.save(autorLivro);

        livro.setAutor(autorLivro);

        livroRepository.save(livro);

    }

    @Test
    void salvarCascadeTeste(){

        Livro livro = new Livro();
        livro.setIsbn("2525-3030");
        livro.setPreco(BigDecimal.valueOf(1200));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1991,4,15));

        Autor autorLivro = new Autor();

        autorLivro.setNome("André");
        autorLivro.setNacionalidade("Portuguesa");
        autorLivro.setDataNascimento(LocalDate.of(1991,11,06));

        livro.setAutor(autorLivro);

        livroRepository.save(livro);

    }

    @Test
    void atualizarLivroTeste() {

        Livro livrobanco = livroRepository
                .findById(UUID.fromString("b851b686-0ead-4256-8b23-1108c11d13a2"))
                .orElse(null);

        UUID id = UUID.fromString("21ba3dcf-3621-4163-bf0a-359f747c47e4");
        Autor novoAutor = autorRepository.findById(id).orElse(null);

        livrobanco.setAutor(novoAutor);

        livroRepository.save(livrobanco);
    }

    @Test
    void deletarTeste(){

        UUID id = UUID.fromString("b851b686-0ead-4256-8b23-1108c11d13a2");
        livroRepository.deleteById(id);
    }

    @Test
    void buscarLivrosPorAutorTeste(){

        UUID id = UUID.fromString("bd3f4c56-9220-4e79-ad96-fdf3f0a2fc72");
        Livro livroAutor = livroRepository.findById(id).orElse(null);
        System.out.println("Título Livro: " + livroAutor.getTitulo());
        System.out.println("Autor: " + livroAutor.getAutor().getNome());
    }


    @Test
    void pesquisaPorTituloTeste(){

        List<Livro> livros = livroRepository.findByTitulo("Livro de Misterio");
        livros.forEach(System.out::println);

    }

    @Test
    void pesquisaPorIsbnTeste(){

        Optional<Livro> livros = livroRepository.findByIsbn("2525-3030");
        livros.ifPresent(System.out::println);

    }

    @Test
    void pesquisaPorTituloAndPreco(){
        var tituloPesquisa = "Livro de Misterio 2";
        var precoPesquisa = BigDecimal.valueOf(2500);

        List<Livro> livros = livroRepository.findByTituloAndPreco(tituloPesquisa, precoPesquisa);
        livros.forEach(System.out::println);

    }

    @Test
    void pesquisaPorTituloOrIsbn(){
        var tituloPesquisa = "Livro de Misterio 2";
        var isbnPesquisa = "2122-3030";


        List<Livro> livros = livroRepository.findByTituloOrIsbn(tituloPesquisa,isbnPesquisa);
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var livros = livroRepository.listarTodosOrdenadoPorTituloePreco();
        livros.forEach(System.out::println);

    }

    @Test
    void listarAutoresDosLivrosComQueryJPQL(){
        var livros = livroRepository.listarAutoresDosLivros();
        livros.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivrosComQueryJPQL(){
        var livros = livroRepository.listarNomeDiferentesLivros();
        livros.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresCanadensesComQueryJPQL(){
        var livros = livroRepository.listarGenerosAutoresCanadenses();
        livros.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroComQueryJPQL(){
        var livros = livroRepository.findByGenero(GeneroLivro.MISTERIO,"preco");
        livros.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroComQueryJPQLPositionalParam(){
        var livros = livroRepository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO,"preco");
        livros.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroComQueryJPQL(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updadteDataPublicacaoComQueryJPQL(){
        livroRepository.updateLivroDataPublicacao(LocalDate.of(2020,10,15));
    }
}