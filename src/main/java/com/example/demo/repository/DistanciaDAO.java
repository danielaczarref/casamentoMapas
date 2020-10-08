package com.example.demo.repository;

import com.example.demo.domain.Distancia;
import org.springframework.context.annotation.Bean;


public interface DistanciaDAO {
    void addDistancia(double sqrt);
    double calcDistancia(double sqrt);
}
