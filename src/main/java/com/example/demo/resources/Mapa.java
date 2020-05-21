package com.example.demo.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mapa")

public class Mapa {

    @GetMapping(value="/retorna-mapa")
    public String getMapa() {
        return "Hello World";
    }

}
