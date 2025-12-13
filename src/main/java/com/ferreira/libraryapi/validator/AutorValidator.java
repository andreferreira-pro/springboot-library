package com.ferreira.libraryapi.validator;

import com.ferreira.libraryapi.exceptions.RegistroDuplicadoException;
import com.ferreira.libraryapi.model.Autor;
import com.ferreira.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {


    AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar (Autor autor) {

        if (existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }

    }

    private boolean existeAutorCadastrado (Autor autor) {

        Optional<Autor> autorEntity = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) {

            return autorEntity.isPresent();
        }

        return !autor.getId().equals(autorEntity.get().getId()) && autorEntity.isPresent();
    }
}
