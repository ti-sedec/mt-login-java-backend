package br.gov.mt.mtloginjavabackend.security.user;

import br.gov.mt.mtloginjavabackend.security.role.MyRole;
import br.gov.mt.mtloginjavabackend.security.role.MyRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MtUserService {

    private final MtUserRepository mtUserRepository;
    private final MyRoleService myRoleService;

    public Optional<MtUser> returnLoggedUser(Principal principal) {
        JwtUserDto dto = new JwtUserDto();
        BeanUtils.copyProperties(principal, dto);
        Optional<MtUser> optionalMtUser = this.findByEmail(dto.getEmail());
        return optionalMtUser;
    }

    public Optional<MtUser> findByEmail(String username) {
        return mtUserRepository
                .findByEmail(username);
    }

    public List<MtUser> getAllUsers() {
        return mtUserRepository.findAll();
    }

    public MtUser findByIdOrThrowBadRequest(Long id) {
        return mtUserRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário de id:" + id + "não encontrado"));
    }

    @Transactional
    public void deactivateUser(Long id, Principal principal) {
        MtUser loggedUser = new MtUser();
        BeanUtils.copyProperties(principal, loggedUser);

        MtUser userToDeactivate = this.findByIdOrThrowBadRequest(id);
        String loggedUserCpf = loggedUser
                .getCpf()
                .replace("-", "")
                .replace(".", "");
        if (loggedUserCpf
                .equals(userToDeactivate.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel desativar o usuário na sessão");
        }
        userToDeactivate.setAtivo(false);
    }

    @Transactional
    public void alterarRoles(MtUpdateRolesDto dto) {
        MtUser userToUpdate = this.findByIdOrThrowBadRequest(dto.id());
        List<MyRole> newRoles = new ArrayList<>();
        dto
                .roles()
                .forEach((item) -> newRoles.add(myRoleService.findByNomeOrElseThrowNotFound(item)));
        userToUpdate.setRoles(newRoles);
    }

    @Transactional
    public MtUser save(MtUser user) {
        return mtUserRepository.save(user);
    }
}
