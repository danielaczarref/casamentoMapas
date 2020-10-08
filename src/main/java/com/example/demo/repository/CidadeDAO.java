package com.example.demo.repository;

import com.example.demo.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeDAO extends JpaRepository<Cidade, Long> {
}
