package com.example.demo.repository;

import com.example.demo.domain.InfoCasamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCasamentoDAO
        extends JpaRepository<InfoCasamento, Long>
{

}
