package com.example.demo.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class DadosLocal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDadosLocal;

    @NotEmpty (message = "Descrição do local não informada!")
    private String descricaoLocal;

    private long cepLocal;


    @NotBlank(message = "Cidade não informada!")
    @OneToOne
    private Cidade cidade;

    @NotEmpty (message = "Rua não informada!")
    private String logradouroLocal;

    private int numeroLogradouro;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyy HH:mm")
    private Date dataLocal;

    @ManyToOne
    private TipoLocal tipoLocal;

    @OneToOne (mappedBy = "dadosLocal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InfoMapa infoMapa;


   }
