package com.todoapp.todo.configuration;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.persistence.entity.User;
import com.todoapp.todo.services.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:very-long-and-secure-secret-key-1234567891011121314151617181920}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        //secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserRequestDto userDto) {
        Date now = new Date();
        // token valid only one hour
        Date validity = new Date(now.getTime() + 3_600_000);
        return JWT.create()
            .withIssuer(userDto.getUsername())
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .sign(Algorithm.HMAC256(secretKey));
    }
    // kan bruges som input til en AuthenticationManager til at give de credentials brugeren giver til auth
    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
        .build();

        DecodedJWT decoded = verifier.verify(token);

        UserRequestDto userDto = userService.getUserByUsername(decoded.getIssuer());

        // returnere en Authentication objekt (spring security abstraktion). 
        // objektet vliver lagt i securitycontext og bruges i resten af spring security
        return new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());
    }

}
