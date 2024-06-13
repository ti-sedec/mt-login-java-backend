package br.gov.mt.mtloginjavabackend.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MtUserRepository extends JpaRepository<MtUser, Long> {
    Optional<MtUser> findByEmail(String username);

}
