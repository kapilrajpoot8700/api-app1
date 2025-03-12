package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserRepository;
import com.hms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private AppUserRepository appUserRepository;

    private UserService userService;
    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(
            @RequestBody AppUser user
    ) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());

        if (opUsername.isPresent()) {
            return new ResponseEntity<>("user alredy taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (opEmail.isPresent()) {
            return new ResponseEntity<>("email alredy taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(6));
        user.setPassword(hashpw);
        AppUser save = appUserRepository.save(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto dto) {
        TokenDto token=  userService.verifyLogin(dto);
        if (token!=null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("username/password invalid",HttpStatus.FORBIDDEN);
        }
    }

























}