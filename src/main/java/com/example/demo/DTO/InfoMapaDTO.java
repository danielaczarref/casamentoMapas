package com.example.demo.DTO;
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
public class InfoMapaDTO implements Serializable {
    private String observacaoMapa;

    @NotEmpty(message = "Não há nenhum mapa anexado")
    private String urlImage;

    @NotEmpty(message = "Nome do arquivo de imagem não pode ficar em branco")
    private String fileName;

    @NotEmpty(message = "Informe o id dos dados do mapa")
    private Long idDadosLocal;

    @NotEmpty(message = "Informe o id do usuário relacionado com o mapa")
    private Long idUsuario;
}
