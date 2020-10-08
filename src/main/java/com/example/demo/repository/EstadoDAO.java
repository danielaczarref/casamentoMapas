package com.example.demo.repository;

import com.example.demo.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDAO extends JpaRepository<Estado, Long> {
}
