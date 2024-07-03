package br.gov.mt.mtloginjavabackend.security.config;

import br.gov.mt.mtloginjavabackend.security.role.MyRoleService;
import br.gov.mt.mtloginjavabackend.security.user.MtUser;
import br.gov.mt.mtloginjavabackend.security.user.MtUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, CustomJwtAuthenticationToken> {

    private final MtUserService userService;
    private final MyRoleService roleService;

    private MtUser retornaUserDoJwt(Jwt source){
        MtUser user = MtUser
                .builder()
                .nome(
                        source
                                .getClaims()
                                .get("name")
                                .toString()
                )
                .email(
                        source
                                .getClaims()
                                .get("email")
                                .toString()
                )
                .emailVerified(
                        Boolean.valueOf(source
                                .getClaims()
                                .get("email_verified")
                                .toString())
                )
                .cpf(
                        source
                                .getClaims()
                                .get("preferred_username")
                                .toString()
                )
                .dataNascimento(
                        LocalDate.parse(source
                                .getClaims()
                                .get("dataNascimento")
                                .toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
                .roles(List.of(roleService.findByNomeOrElseThrowNotFound("ROLE_USER")))
                .build();

        return user;
    }
    @Override
    public CustomJwtAuthenticationToken convert(Jwt source) {
        // VERIFICA SE USUARIO JA EXISTE NO BANCO
        Optional<MtUser> userOpt = userService
                .findByEmail(source
                        .getClaims()
                        .get("email")
                        .toString());

        MtUser user;

        if (userOpt.isEmpty()) {
            user = retornaUserDoJwt(source);
            user.setAtivo(true);
            userService.save(user);
        } else {
            user = userOpt.get();
            if(!user.getAtivo()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O usuário está desativado");
            }
            MtUser jwtUser = retornaUserDoJwt(source);
            jwtUser.setId(user.getId());
            if (!user.equals(jwtUser)) {
                //ATUALIZAR OS VALORES DE MTUSER
                BeanUtils.copyProperties(jwtUser, user);
                userService.save(user);
            }
        }

        return new CustomJwtAuthenticationToken(source, user.getAuthorities());
    }
}