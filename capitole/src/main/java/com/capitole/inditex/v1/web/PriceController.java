package com.capitole.inditex.v1.web;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.service.PriceService;
import com.capitole.inditex.v1.validation.PriceValidator;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class PriceController {

    private final PriceService service;
    private final PriceValidator validator;
    private final PriceAdapter adapter;

    public PriceController(PriceService service, PriceValidator validator, PriceAdapter adapter) {
        this.service = service;
        this.validator = validator;
        this.adapter = adapter;
    }

    @GetMapping(value = "/ping",
    produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong");
    }

    @PostMapping(value = "/price",
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> retrievePriceData(final @Valid @RequestBody ProductRetrievalRequest retrievalRequest,
                                                    final BindingResult errors){
        if (errors.hasErrors()){
            //throw exception
        }

        //get from db


        //is priority

        //service logic

        //adapter

        return new ResponseEntity<>(HttpStatus.OK);



    }
}
