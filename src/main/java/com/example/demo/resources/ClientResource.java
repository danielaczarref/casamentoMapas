package com.example.demo.resources;

import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value="/client")

public class ClientResource {

    @Autowired
    private ClientService clientService;

    @PostMapping(value="/mapa")
    public ResponseEntity<Void> uploadMapa(@RequestParam(name="file") MultipartFile file) {
        URI uri = clientService.uploadArquivoMapa(file);
        return ResponseEntity.created(uri).build();
    }

}
