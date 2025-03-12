package com.hms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryConroller {


    //http://localhost:8080/api/v1/country
    public String addCountry(){
    return "added";}
}
