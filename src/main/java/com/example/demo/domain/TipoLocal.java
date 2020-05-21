package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TipoLocal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tipo_local;
    private String descricao_tipo_local;
}
