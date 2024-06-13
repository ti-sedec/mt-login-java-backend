package br.gov.mt.mtloginjavabackend.security.config;

import br.gov.mt.mtloginjavabackend.security.role.MyRoleService;
import br.gov.mt.mtloginjavabackend.security.user.MtUserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwksUri}")
    private String jwksUri;

    @Value("#{'${allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;

    private final MtUserService mtUserService;
    private final MyRoleService myRoleService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                r -> r
                        .jwt((auth2Jwt) -> auth2Jwt
                                .jwkSetUri(jwksUri)
                                .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter(mtUserService, myRoleService))
                        )
        );

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/user/get-all-users")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/desativar-usuario/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/alterar-roles")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/adm")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                );

        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedOrigins(allowedOrigins.toArray(new String[0]))
                        .allowedHeaders("*");
            }
        };
    }
}