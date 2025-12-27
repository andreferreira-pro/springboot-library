package com.ferreira.libraryapi.controller.common;

import com.ferreira.libraryapi.controller.dto.ErroCampo;
import com.ferreira.libraryapi.controller.dto.ErroResposta;
import com.ferreira.libraryapi.exceptions.CampoValidoException;
import com.ferreira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.ferreira.libraryapi.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        log.error("Erro de validação: {}",e.getMessage());

        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErroCampo> listaErros = fieldErrors.
                stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){

        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitadaException (OperacaoNaoPermitidaException e){

        return ErroResposta.repostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoValidoException.class)
    public ErroResposta handleCampoInvalidException(CampoValidoException e){

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException e){

        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handlerErrosNaoTratados(RuntimeException e){

        log.error("Erro inesperado", e);

        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ("Ocorreu um erro inesperado. Entre em contato com o administrador"),
                List.of()
        );
    }
}
