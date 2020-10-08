package com.example.demo.resources;

import com.example.demo.domain.Usuario;
import com.example.demo.repository.UsuarioDAO;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping (value = "/usuario")
public class UsuarioResource {
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public List<Usuario> getListUsuario() {
        return usuarioDAO.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usuario) {
        usuarioDAO.save(usuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/{usuarioId}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable long usuarioId) {
        return usuarioService.updateUsuario(usuarioId, usuario);
    }

}
