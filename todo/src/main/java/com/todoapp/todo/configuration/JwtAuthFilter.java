package com.todoapp.todo.configuration;

import java.io.IOException;
import org.springframework.http.HttpHeaders;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;



// -------------------  Servlet ------------
// JtwAuthFilter er et filter der bliver tilføjet til web Servlet container (Tomcat) filterchain, 
// som er en række af filtre som hver request udenfor gennemgår for at ungå diverse attacks
// spring tilføjer ikke de ekstre tilføjet filtre direkte ind i filterchain, men laver en 
// delegated filter proxy der redirecter alle requests til et spring security filter chain
// -----------------------------------------

// -------------------  Bearer -------------
// Bearer er en form for token beskrevet efter RFC 6750 standarden
// -----------------------------------------


@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            //hvis der er en auth header
            if (header != null) {
                String[] elements = header.split("");
                // forventer formatet Authorization: Bearer <token>
                if (elements.length == 2 && "Bearer".equals(elements[0])) {
                    try {
                        //lægger resultatet i SecurityContextHolder så Spring Security "ved" at brugeren er logget ind
                        SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateToken(elements[1])
                        );
                        // tømmer SecurityContextHolder igen hvis auth fejler
                    } catch (RuntimeException e) {
                        SecurityContextHolder.clearContext();
                        throw e;
                    }
                }
            }
            filterChain.doFilter(request, response);

        }

}
