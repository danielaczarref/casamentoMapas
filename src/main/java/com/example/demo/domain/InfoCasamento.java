package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class InfoCasamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfoCasamento;

    @NotEmpty(message = "Adicionar informação de ponto X do mapa 1")
    private int pontoXMap1;

    @NotEmpty(message = "Adicionar informação de ponto Y do mapa 1")
    private int pontoYMap1;

    @NotEmpty(message = "Adicionar informação de ponto X do mapa 2")
    private int pontoXMap2;

    @NotEmpty(message = "Adicionar informação de ponto Y do mapa 2")
    private int pontoYMap2;

    @NotEmpty(message = "Campo corners window nulo")
    private String cornersWindow;

    @NotEmpty(message = "Matriz dst norm scaled nula")
    private String dstNormScaled;

    @NotEmpty(message = "Campo corners window2 nulo")
    private String cornersWindow2;

    @NotEmpty(message = "Matriz dst norm scaled2 nula")
    private String dstNormScaled2;

    @NotEmpty(message = "id das informações do mapa 1 está nulo")
    private int idInfoMapa1;

    @NotEmpty(message = "id das informações do mapa 2 está nulo")
    private int idInfoMapa2;
}
