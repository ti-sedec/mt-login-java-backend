package br.gov.mt.mtloginjavabackend.security.user;

import br.gov.mt.mtloginjavabackend.security.role.MyRoleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class MtUserController {

    private final MtUserService mtUserService;
    private final MyRoleService myRoleService;

    @GetMapping
    public ResponseEntity getLoggedUser(Principal principal) {
        Optional<MtUser> optionalMtUser = mtUserService.returnLoggedUser(principal);
        return optionalMtUser
                .map(MtUserDto::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping("/adm")
    public ResponseEntity createNewAdmUser(@RequestBody @Valid PersistAdmUserDto dto) {

        MtUser user = MtUser
                .builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .emailVerified(true)
                .dataNascimento(dto.dataNascimento())
                .ativo(true)
                .roles(List.of(myRoleService
                        .findByNomeOrElseThrowNotFound("ROLE_ADMIN")))
                .build();

        mtUserService.save(user);

        URI uri = URI.create("/user/" + user.getId());

        return ResponseEntity
                .created(uri)
                .build();
    }

    @GetMapping("/get-all-users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(mtUserService
                .getAllUsers()
                .stream()
                .map(MtUserDto::new)
                .collect(Collectors.toList()));
    }

    @PutMapping("/desativar-usuario/{idUsuario}")
    public ResponseEntity deactivateUsuarioById(@PathVariable Long idUsuario, Principal principal) {
        mtUserService.deactivateUser(idUsuario, principal);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/alterar-roles")
    public ResponseEntity alterarRolesAoUsuario(@RequestBody @Valid MtUpdateRolesDto dto) {
        mtUserService.alterarRoles(dto);
        return ResponseEntity
                .noContent()
                .build();
    }
}
