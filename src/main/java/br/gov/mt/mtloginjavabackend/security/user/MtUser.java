package br.gov.mt.mtloginjavabackend.security.user;

import br.gov.mt.mtloginjavabackend.security.role.MyRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MtUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nome;
    @Email
    @NotEmpty
    private String email;
    @CPF
    @NotEmpty
    private String cpf;
    @NotNull
    private Boolean emailVerified;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Boolean ativo;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Size(min = 1)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mt_user_has_my_role",
            joinColumns = @JoinColumn(name = "mt_user_id"),
            inverseJoinColumns = @JoinColumn(name = "my_role_id"))
    private List<MyRole> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (MyRole uhr : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(uhr
                    .getNome()));
        }
        return authorities;
    }
}
