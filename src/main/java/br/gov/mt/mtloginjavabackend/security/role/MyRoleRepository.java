package br.gov.mt.mtloginjavabackend.security.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyRoleRepository extends JpaRepository<MyRole, Long> {
    Optional<MyRole> findByNome(String roleUser);
}
