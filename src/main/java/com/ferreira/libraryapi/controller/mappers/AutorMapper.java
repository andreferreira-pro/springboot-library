package com.ferreira.libraryapi.controller.mappers;

import com.ferreira.libraryapi.controller.dto.AutorDTO;
import com.ferreira.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);
}
