package br.gov.mt.mtloginjavabackend.security.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
@Getter
public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {

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

    public CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
        sub = jwt.getClaim("sub").toString();
        email_verified = jwt.getClaim("email_verified");
        name = jwt.getClaim("name").toString();
        cpf = jwt.getClaim("preferred_username").toString().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        preferred_username = jwt.getClaim("preferred_username").toString();
        given_name = jwt.getClaim("given_name").toString();
        dataNascimento = LocalDate.parse(jwt
                .getClaims()
                .get("dataNascimento")
                .toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        family_name = jwt.getClaim("family_name").toString();
        email = jwt.getClaim("email").toString();
        nomeMae = jwt.getClaim("nomeMae").toString();
    }
}