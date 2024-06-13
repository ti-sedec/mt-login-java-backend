package br.gov.mt.mtloginjavabackend.security.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
public class JwtUserDto {
    private String sub;
    private Boolean email_verified;
    private String name;
    private String cpf;
    private String preferred_username;
    private String given_name;
    private LocalDate dataNascimento;
    private String family_name;
    private String email;
    private String nomeMae;
    private Collection<? extends GrantedAuthority> authorities;
}
