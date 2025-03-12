package com.hms.configurtation;


import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private AppUserRepository userRepository;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if(token != null&& token.startsWith("Bearer ")){
            String tokenVal = token.substring(8, token.length() - 1);
            String userName = jwtService.getUserName(tokenVal);
            Optional<AppUser> opUser = userRepository.findByUsername(userName);
            if (opUser.isPresent()){
                AppUser appUser = opUser.get();

            }

        }
    filterChain.doFilter(request,response);
    }
}
