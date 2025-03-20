package com.hms.configurtation;


import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
@Controller
public class JWTFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private AppUserRepository userRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
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

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(appUser, null,
                                Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //granting him access

            }

        }
    filterChain.doFilter(request,response);
    }
}
