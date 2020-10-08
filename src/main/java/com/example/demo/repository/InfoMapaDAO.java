package com.example.demo.repository;
import com.example.demo.domain.DadosLocal;
import com.example.demo.domain.InfoMapa;
import com.example.demo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoMapaDAO extends JpaRepository<InfoMapa, Long>{
    List<InfoMapa> findAllByDadosLocal(DadosLocal dadosLocal);
    List<InfoMapa> findAllByUsuario(Usuario usuario);
    List<InfoMapa> findByUsuarioAndDadosLocal(Usuario usuario, DadosLocal dadosLocal);
}
