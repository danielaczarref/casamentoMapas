package com.example.demo.repository;

import com.example.demo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario,Long> {
    Usuario findByEmailUsuario(String email);
    Usuario findByNomeUsuario(String nome);
}
