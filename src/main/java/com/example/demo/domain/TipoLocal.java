package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TipoLocal {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTipoLocal;

    @NotEmpty (message = "Descrição do local não informada!")
    private String descricaoTipoLocal;

    @JsonIgnore
    @OneToMany (mappedBy = "tipoLocal")
    private List<DadosLocal> dadosLocals = new ArrayList<>();


}
