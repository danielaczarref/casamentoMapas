package com.example.demo.DTO;

import com.example.demo.domain.Cidade;
import com.example.demo.domain.TipoLocal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DadosLocalDTO implements Serializable{

        @NotEmpty(message = "Descrição do local não informada!")
        private String descricaoLocal;

        private long cepLocal;

        @NotEmpty(message = "Informe o id de uma cidade")
        private Long idCidade;

        @NotEmpty (message = "Rua não informada!")
        private String logradouroLocal;

        private int numeroLogradouro;

        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "dd/MM/yyy HH:mm")
        private Date dataLocal;

        @NotEmpty(message = "Informe um tipo de local")
        private Long idTipoLocal;
    }
