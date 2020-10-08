package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
public class ClientService {

    @Autowired
    private S3Service s3Service;

    public URI uploadArquivoMapa(MultipartFile multipartFile) {
        return s3Service.enviarArquivo(multipartFile);
    }
}
