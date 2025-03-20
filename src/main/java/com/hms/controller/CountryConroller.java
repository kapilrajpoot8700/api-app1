package com.hms.controller;

import com.hms.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryConroller {


    //http://localhost:8080/api/v1/countryPrivate field 'algorithmKey
    @GetMapping("/addCountry")
    public AppUser addCountry(
            @AuthenticationPrincipal AppUser user
            ){
    return user;}
}
