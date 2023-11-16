package com.capitole.inditex.v1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class PriceController {

    @GetMapping(value = "/ping",
    produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong");
    }
}
