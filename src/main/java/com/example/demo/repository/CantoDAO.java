package com.example.demo.repository;

import com.example.demo.domain.Canto;
import com.example.demo.domain.Distancia;

public interface CantoDAO {
    Distancia getMelhores(int pos);
}
