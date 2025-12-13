package com.ferreira.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(

        UUID id,

        @NotBlank(message = "campo obrigatório")
        @Size(max = 100,min = 2,message = "campo fora do padrão")
        String nome,

        @NotNull(message = "campo obrigatório")
        @Past(message = "data de nascimento não poser ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório")
        @Size(max = 50, min = 2, message = "campo fora do padrão")
        String nacionalidade) {

}
