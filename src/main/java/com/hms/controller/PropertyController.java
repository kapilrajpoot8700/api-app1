package com.hms.controller;

import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    @GetMapping("/search-hotels")
    public ResponseEntity<?> searchHotels(@RequestParam String name){
        List<Property> properties = propertyRepository.searchHotel(name);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }



}
