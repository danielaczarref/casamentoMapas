package com.example.demo.service;

import com.example.demo.domain.Distancia;
import com.example.demo.repository.DistanciaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanciaService {

//    @Autowired
    private Distancia distancia;

    public void adicionarDistancia(int x1, int y1, int x2, int y2) {
        distancia.setX(x1);
        distancia.setY(y1);
        double dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        distancia.setDistancia(dist);
    }

    public double calcDistancia(int x1, int y1, int x2, int y2) {
        return (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }
}
