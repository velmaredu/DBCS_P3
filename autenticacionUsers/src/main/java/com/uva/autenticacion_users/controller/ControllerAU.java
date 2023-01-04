package com.uva.autenticacion_users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.uva.autenticacion_users.exception.AuthException;
import com.uva.autenticacion_users.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/autentificacion")
public class ControllerAU {
    private static final String EMAIL_ID = "email";
    
    @Autowired JwtTokenUtil jwtUtil;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String autentificar(@RequestBody Map<String,String> datos){

        
        String uri = "http://localhost:8080/users/";
        String token = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> user = restTemplate.getForObject(uri + "?email="+datos.get(EMAIL_ID), Map.class);
        if (user == null) {
            throw new AuthException("No user found with email: " + datos.get(EMAIL_ID));
        }

        if(encoder.matches(datos.get("password"),user.get("password"))) token = jwtUtil.generateAccessToken(user.get("name"), user.get(EMAIL_ID), user.get("role"));
        else throw new AuthException("Datos incorrectos");

        return token;
    }
}
