package br.gov.mt.mtloginjavabackend.security.user;

import br.gov.mt.mtloginjavabackend.security.role.MyRole;
import br.gov.mt.mtloginjavabackend.security.role.MyRoleDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record MtUserDto(
        Long id,
        String nome,
        String email,
        String cpf,
        Boolean emailVerified,
        LocalDate dataNascimento,
        Boolean ativo,
        List<MyRoleDto> roles
) {
    public MtUserDto(MtUser mtUser) {
        this(
                mtUser.getId(),
                mtUser.getNome(),
                mtUser.getEmail(),
                mtUser.getCpf(),
                mtUser.getEmailVerified(),
                mtUser.getDataNascimento(),
                mtUser.getAtivo(),
                mtUser
                        .getRoles()
                        .stream()
                        .map(MyRoleDto::new)
                        .collect(Collectors.toList())
        );
    }
}
