package br.gov.mt.mtloginjavabackend.security.role;

public record MyRoleDto(
        Long id,
        String nome,
        String descricao
) {
    public MyRoleDto(MyRole myRole) {
        this(
                myRole.getId(),
                myRole.getNome(),
                myRole.getDescricao()
        );
    }
}
