package com.example.demo.resources;
import com.example.demo.DTO.DadosLocalDTO;
import com.example.demo.domain.DadosLocal;
import com.example.demo.service.DadosLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping (value = "/dadosLocal")
public class DadosLocalResource {
    @Autowired
    private DadosLocalService dadosLocalService;

    @GetMapping
    public ResponseEntity<List<DadosLocal>> getAllDadosLocal() {
        return ResponseEntity.ok().body(dadosLocalService.getAllDadosLocal());
    }

    @GetMapping(value = "/{idDadosLocal}")
    public ResponseEntity<DadosLocal> getDadosLocalById(@PathVariable long idDadosLocal) {
        return ResponseEntity.ok().body(dadosLocalService.getById(idDadosLocal));
    }

    @GetMapping(value = "/cep/{cep}")
    public ResponseEntity<DadosLocal> getDadosLocalByCep (@PathVariable long cepDadosLocal) {
        return ResponseEntity.ok().body(dadosLocalService.getByCep(cepDadosLocal));
    }

    @GetMapping(value = "/cidade/{cidade}")
    public ResponseEntity<DadosLocal> getDadosLocalByCidade (@PathVariable long idCidade){
        return ResponseEntity.ok().body(dadosLocalService.getByCidade(idCidade));
    }

    @GetMapping(value = "/tipoLocal/{idTipoLocal}")
    public ResponseEntity<List<DadosLocal>> getDadosLocalByTipoLocal (@PathVariable long idTipoLocal) {
        return ResponseEntity.ok().body(dadosLocalService.getByTipoLocal(idTipoLocal));
    }

    @GetMapping(value="/result?idTipoLocal={idTipoLocal}&idCidade={idCidade}&cep={cepDadosLocal}")
    public ResponseEntity<List<DadosLocal>> getDadosLocalByTipoCidadeCep(@PathVariable long idTipoLocal, @PathVariable long idCidade, @PathVariable long cepDadosLocal ) {
        return ResponseEntity.ok().body(dadosLocalService.getByTipoCidadeCep(idTipoLocal, idCidade, cepDadosLocal));
    }

    @PostMapping
    public ResponseEntity<String> addDadosLocal(@Valid @RequestBody DadosLocalDTO dadosLocalDTO) {
        String response = dadosLocalService.salvarDadosLocal(dadosLocalDTO);
        if(response.equals("Sucesso")) {
            return ResponseEntity.ok().build();
        }
            return ResponseEntity.badRequest().body(response);


    }

    @PutMapping(value="/{idDadosLocal}")
    public ResponseEntity<?> atualizarDadosLocal(@Valid @RequestBody DadosLocal dadosLocal, @PathVariable long idDadosLocal ) {
        return dadosLocalService.atualizarDadosLocal(idDadosLocal, dadosLocal);
    }


}
