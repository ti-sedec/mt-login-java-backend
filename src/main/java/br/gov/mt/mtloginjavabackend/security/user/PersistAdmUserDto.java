package br.gov.mt.mtloginjavabackend.security.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersistAdmUserDto(
        @NotEmpty
        String nome,
        @NotEmpty
        String cpf,
        @NotEmpty
        String email,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento
) {
}
