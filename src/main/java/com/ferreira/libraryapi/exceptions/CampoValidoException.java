package com.ferreira.libraryapi.exceptions;

import lombok.Getter;

public class CampoValidoException extends RuntimeException {

    @Getter
    private String campo;

    public CampoValidoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
