package com.example.demo.resources;

import com.example.demo.domain.TipoLocal;
import com.example.demo.service.TipoLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping (value = "/tipoLocal")
public class TipoLocalResource {
    @Autowired
    private TipoLocalService tipoLocalService;

    @GetMapping
    public ResponseEntity<List<TipoLocal>> getAllTipoLocals(){

        return ResponseEntity.ok().body(tipoLocalService.getAllTipoLocal());
    }

    @GetMapping(value = "/{idTipoLocal}")
    public ResponseEntity<TipoLocal> getTipoLocalById(@PathVariable long idTipoLocal) {
        return tipoLocalService.getById(idTipoLocal);
    }

    @PostMapping
    public ResponseEntity<Void> salvarTipoLocal(@Valid @RequestBody TipoLocal tipoLocal) {
        tipoLocalService.salvarTipoLocal(tipoLocal);
        return ResponseEntity.ok().build();
    }
}
