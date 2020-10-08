package com.example.demo.service;

import com.example.demo.domain.Usuario;
import com.example.demo.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public ResponseEntity<Void> updateUsuario(long usuarioId, Usuario usuario){
        if (usuarioDAO.findById(usuarioId).isPresent()) {
            usuario.setIdUsuario(usuarioId);
            usuarioDAO.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
