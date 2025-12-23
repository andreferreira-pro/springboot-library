package com.ferreira.libraryapi.controller.mappers;

import com.ferreira.libraryapi.controller.dto.UsuarioDTO;
import com.ferreira.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);



}
