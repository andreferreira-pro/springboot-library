package com.ferreira.libraryapi.controller.mappers;

import com.ferreira.libraryapi.controller.dto.CadastroLivroDTO;
import com.ferreira.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.ferreira.libraryapi.model.Livro;
import com.ferreira.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);



}
