package com.example.demo.DTO;

import com.example.demo.domain.Cidade;
import com.example.demo.domain.TipoLocal;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DadosLocalDTO implements Serializable{


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long idDadosLocal;

        @NotEmpty(message = "Descrição do local não informada!")
        private String descricaoLocal;

        private long cepLocal;

        @NotBlank(message = "Cidade não informada!")
        @OneToOne
        @MapsId
        private Cidade cidade;

        @NotEmpty (message = "Rua não informada!")
        private String logradouroLocal;

        private int numeroLogradouro;

        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "dd/MM/yyy HH:mm")
        private Date dataLocal;

        @ManyToOne
        private TipoLocal tipoLocal;
    }
