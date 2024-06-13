package br.gov.mt.mtloginjavabackend.security.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MtUpdateRolesDto(
        @NotNull
        Long id,
        @NotEmpty
        List<String> roles
) {
}
