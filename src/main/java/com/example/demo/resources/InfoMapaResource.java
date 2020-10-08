package com.example.demo.resources;
import com.example.demo.DTO.InfoMapaDTO;
import com.example.demo.domain.InfoMapa;
import com.example.demo.service.InfoMapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping (value = "/infoMapa")
public class InfoMapaResource {
    @Autowired
    private InfoMapaService infoMapaService;

    @GetMapping
    public ResponseEntity<List<InfoMapa>> getAllInfoMapa() {
        return ResponseEntity.ok().body(infoMapaService.getAllInfoMapa());
    }

    @GetMapping(value = "/{idInfoMapa}")
    public ResponseEntity<InfoMapa> getInfoById(@PathVariable long idInfoMapa) {
        return ResponseEntity.ok().body(infoMapaService.getById(idInfoMapa));
    }

    @GetMapping(value = "/idDadosLocal={idDadosLocal}")
    public ResponseEntity<List<InfoMapa>> getAllByDadosLocal(@PathVariable long idDadosLocal) {
        return ResponseEntity.ok().body(infoMapaService.getAllByDadosLocal(idDadosLocal));
    }

    @GetMapping(value = "/idUsuario={idUsuario}")
    public ResponseEntity<List<InfoMapa>> getAllByUser(@PathVariable long idUsuario) {
        return ResponseEntity.ok().body(infoMapaService.getAllByUser(idUsuario));
    }

    @GetMapping (value = "/?idUsuario={idUsuario}&idDadosLocal={idDadosLocal}")
    public ResponseEntity<List<InfoMapa>> getAllByUserAndDados(@PathVariable long idUsuario, @PathVariable long idDadosLocal) {
        return ResponseEntity.ok().body(infoMapaService.getAllByUserAndDados(idUsuario, idDadosLocal));
    }

    @PostMapping
    public ResponseEntity<String> salvarInfoMapa(@RequestParam(name="observacao") String observacao,
                                                 @RequestParam(name="idUsuario") long idUsuario,
                                                 @RequestParam(name="idDadosLocal") long idDadosLocal,
                                                 @RequestParam(name="file") MultipartFile multipartFile) {
        String response = infoMapaService.salvarInfoMapa(observacao, idUsuario, idDadosLocal, multipartFile);
        if (response.equals("Sucesso")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping(value = "/{idInfoMapa}")
    public ResponseEntity<?> atualizarInfoMapa(@Valid @RequestBody InfoMapa infoMapa, @PathVariable long idInfoMapa){
        infoMapaService.atualizarInfoMapa(idInfoMapa, infoMapa);
        return ResponseEntity.ok().build();
    }

 }
