package br.gov.mt.mtloginjavabackend.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MyRoleService {

    private final MyRoleRepository myRoleRepository;

    public MyRole findByNomeOrElseThrowNotFound(String nome) {
        return myRoleRepository
                .findByNome(nome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada"));
    }
}
