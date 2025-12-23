package com.ferreira.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(

        UUID id,

        @NotBlank(message = "campo obrigatório")
        @Size(max = 100,min = 2,message = "campo fora do padrão")
        @Schema(name = "nome")
        String nome,

        @NotNull(message = "campo obrigatório")
        @Past(message = "data de nascimento não poser ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório")
        @Size(max = 50, min = 2, message = "campo fora do padrão")
        @Schema(name = "nacionalidade")
        String nacionalidade) {

}
