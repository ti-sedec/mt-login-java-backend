package br.gov.mt.mtloginjavabackend.security.user;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @GetMapping("/ping")
    public String ping(Principal principal){
        JwtUserDto dto = new JwtUserDto();
        BeanUtils.copyProperties(principal, dto);
        return "Pong!";
    }
}
