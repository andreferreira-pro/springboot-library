package com.ferreira.libraryapi.repository;

import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.model.GeneroLivro;
import com.ferreira.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository  livroRepository;

    @Test
    public void salvarTest(){

        Autor autor = new Autor();

        autor.setNome("Fernando");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,25));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){

        var id = UUID.fromString("99a482f8-dac8-45bf-9ffb-381135e43b3c");

        Optional<Autor> autorBanco = autorRepository.findById(id);

        if(autorBanco.isPresent()) {

            Autor autorExistente = autorBanco.get();

            System.out.println("Dados Atuais do Autor no Banco:");
            System.out.println(autorExistente);

            autorExistente.setDataNascimento(LocalDate.of(1950,2,21));

            autorRepository.save(autorExistente);
            System.out.println("Autor Atualizado: " + autorExistente);
        }
    }

    @Test
    public void ListarTest(){

        List<Autor> autorList = autorRepository.findAll();
        autorList.forEach(System.out::println);
    }

    @Test
    public void countAutor(){
        System.out.println("Quantidade de autores no banco: " + autorRepository.count());
    }

    @Test
    public void deleteByIdTeste(){

        var id = UUID.fromString("5f50b588-3ad9-4ddd-8ace-742f40d3829a");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTeste(){

        var id = UUID.fromString("99a482f8-dac8-45bf-9ffb-381135e43b3c");
        Optional<Autor> autorBanco = autorRepository.findById(id);

        if(autorBanco.isPresent()) {

            Autor autorExistente = autorBanco.get();

            autorRepository.delete(autorExistente);
            System.out.println("Autor Deletado: " + autorExistente.getNome());

        }
    }

    @Test
    void salvarAutoresComLivroTeste(){

        Autor autor = new Autor();
        autor.setNome("Diego");
        autor.setNacionalidade("Canadense");
        autor.setDataNascimento(LocalDate.of(1989,06,28));

        Livro livro = new Livro();
        livro.setIsbn("2122-3030");
        livro.setPreco(BigDecimal.valueOf(1500));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Livro de Misterio");
        livro.setDataPublicacao(LocalDate.of(1990,4,15));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("2114-3030");
        livro2.setPreco(BigDecimal.valueOf(2500));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Livro de Misterio 2");
        livro2.setDataPublicacao(LocalDate.of(2000,11,15));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
//        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void buscarLivrosPorAutor(){

        UUID id = UUID.fromString("66591fa6-3981-4cf0-8148-6e7210ac840b");
        Autor autor = autorRepository.findById(id).get();

        List<Livro> livros = livroRepository.findByIdAutor(autor);

        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);
    }


}