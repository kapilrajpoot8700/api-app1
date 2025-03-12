package com.hms.services;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private JWTService jwtService;
    private AppUserRepository userRepository;

    public UserService(JWTService jwtService, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public TokenDto verifyLogin(LoginDto dto) {
        Optional<AppUser> user = userRepository.findByUsername(dto.getUsername());

        if (user.isPresent()){
            AppUser appUser = user.get();
          if(BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
              TokenDto tokenDto = new TokenDto();
              tokenDto.setType("JWT");
             String token= jwtService.generateToken(appUser.getUsername());
              tokenDto.setToken(token);
              return tokenDto;
          }else {return null;}
        }else {
            return null;
        }

    }
}

