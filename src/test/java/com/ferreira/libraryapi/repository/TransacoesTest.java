package com.ferreira.libraryapi.repository;

import com.ferreira.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimples(){
        transacaoService.executar();

    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacoSemAtualizar();

    }

}
