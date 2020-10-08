package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.sound.sampled.Line;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotEmpty(message = "Nome do usuário não informado!")
    private String nomeUsuario;

    @NotEmpty(message = "Email do usuário não informado!")
    @Email
    private String emailUsuario;

    @NotEmpty(message = "Senha do usuário não informada!")
    private String senhaUsuario;

    @OneToMany(mappedBy = "usuario")
    private List<InfoMapa> infoMapas = new ArrayList<>();
}
