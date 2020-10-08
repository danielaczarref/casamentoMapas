package com.example.demo;

import com.example.demo.domain.Cidade;
import com.example.demo.domain.Estado;
import com.example.demo.repository.CidadeDAO;
import com.example.demo.repository.EstadoDAO;
import com.example.demo.service.Estrutura2Service;
import com.example.demo.service.EstruturaService;
import com.example.demo.service.S3Service;
import nu.pattern.OpenCV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.ws.transport.http.HttpUrlConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

//    @Autowired
//    private S3Service s3Service;

    @Autowired
    private EstadoDAO estadoDAO;

    @Autowired
    private CidadeDAO cidadeDAO;

    @Autowired
    private Estrutura2Service estrutura2Service;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DemoApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
//        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
        });

        Estado ma =  new Estado(null, "MA", null);
        estadoDAO.save(ma);

        cidadeDAO.saveAll(Arrays.asList(
                new Cidade(null, "São Luís", ma),
                new Cidade(null, "Imperatriz", ma),
                new Cidade(null, "Barreirinhas", ma)
        ));

        estadoDAO.saveAll(   Arrays.asList(
                new Estado(null, "BA", null),
                new Estado(null, "CE", null),
                new Estado(null, "PA", null)
        ));

        OpenCV.loadShared();

        estrutura2Service.defineParametros("teste", 1L, "teste", 2L);

//        estruturaService.defineParametros("teste", 1L, "teste", 2L);

//        URL url = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa5.bmp");
//
//        URL url2 = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa6.bmp");
//
//        estruturaService.defineParametros("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa5.bmp", "https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa6.bmp");

//        s3Service.uploadFile("/home/daniela/Documents/PIBITI/Higo/demo/src/main/resources/static/mapa8.bmp");
    }
}
