package com.example.demo.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InfoMapa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfoMapa;

    private String observacaoMapa;

    @NotEmpty(message = "Não há nenhum mapa anexado")
    private String urlImage;

    @NotEmpty(message = "Salvar informações do nome do arquivo")
    private String fileName;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private DadosLocal dadosLocal;

    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

}
