package com.example.demo.service;
import com.example.demo.domain.DadosLocal;
import com.example.demo.domain.TipoLocal;
import com.example.demo.repository.TipoLocalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TipoLocalService {
    @Autowired
    private TipoLocalDAO tipoLocalDAO;

    public ResponseEntity<TipoLocal> getById(long idTipoLocal) {
        Optional<TipoLocal> result = tipoLocalDAO.findById(idTipoLocal);
        if(result.isPresent()){
            return ResponseEntity.ok().body(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public List<TipoLocal> getAllTipoLocal () {
        return tipoLocalDAO.findAll();
    }

    public void salvarTipoLocal(TipoLocal tipoLocal) {
        tipoLocalDAO.save(tipoLocal);
    }

}
