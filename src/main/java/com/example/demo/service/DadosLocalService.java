package com.example.demo.service;
import com.example.demo.DTO.DadosLocalDTO;
import com.example.demo.domain.Cidade;
import com.example.demo.domain.DadosLocal;
import com.example.demo.domain.TipoLocal;
import com.example.demo.repository.CidadeDAO;
import com.example.demo.repository.DadosLocalDAO;
import com.example.demo.repository.TipoLocalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DadosLocalService {
    @Autowired
    private DadosLocalDAO dadosLocalDAO;

    @Autowired
    private CidadeDAO cidadeDAO;

    @Autowired
    private TipoLocalDAO tipoLocalDAO;

    public String salvarDadosLocal(DadosLocalDTO dadosLocalDTO){
        Optional<Cidade> cidadeOptional = cidadeDAO.findById(dadosLocalDTO.getIdCidade());
        Cidade cidade;
        if(cidadeOptional.isPresent()) {
            cidade = cidadeOptional.get();
        } else {
            return "Id cidade inválido";
        }

        Optional<TipoLocal> tipoLocalOptional = tipoLocalDAO.findById(dadosLocalDTO.getIdTipoLocal());
        TipoLocal tipoLocal;
        if(tipoLocalOptional.isPresent()) {
            tipoLocal = tipoLocalOptional.get();
        } else {
            return "Id TipoLocal inválido";
        }

        dadosLocalDTO.setDataLocal(new Date());
        dadosLocalDAO.save(new DadosLocal(null, dadosLocalDTO.getDescricaoLocal(), dadosLocalDTO.getCepLocal(), cidade, dadosLocalDTO.getLogradouroLocal(),
                                            dadosLocalDTO.getNumeroLogradouro(), dadosLocalDTO.getDataLocal(), tipoLocal, null));
        return "Sucesso";
    }

    public DadosLocal getById(long idDadosLocal) {
        Optional<DadosLocal> result = dadosLocalDAO.findById(idDadosLocal);
        if (result.isPresent()){
            return result.get();
        }
        else {
            return null;
        }
    }

    public List<DadosLocal> getAllDadosLocal() {
        return dadosLocalDAO.findAll();
    }

    public DadosLocal getByCep(long cepDadosLocal) {
        return dadosLocalDAO.findDadosLocalByCepLocal(cepDadosLocal);
    }

    public DadosLocal getByCidade (long idcidade) {
        Cidade cidade = new Cidade();
        cidade.setIdCidade(idcidade);
        return dadosLocalDAO.findDadosLocalByCidade(cidade);
    }

    public ResponseEntity<Void> atualizarDadosLocal(long idDadosLocal, DadosLocal dadosLocal) {
        if (dadosLocalDAO.findById(idDadosLocal).isPresent()) {
            dadosLocal.setIdDadosLocal(idDadosLocal);
            dadosLocalDAO.save(dadosLocal);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<DadosLocal> getByTipoCidadeCep(long idTipoLocal, long idCidade, long cepDadosLocal) {
        if (tipoLocalDAO.findById(idTipoLocal).isPresent() && cidadeDAO.findById(idCidade).isPresent()) {
            TipoLocal tipoLocal = new TipoLocal();
            tipoLocal.setIdTipoLocal(idTipoLocal);
            Cidade cidade = new Cidade();
            cidade.setIdCidade(idCidade);
            if (dadosLocalDAO.findAllByTipoLocal(tipoLocal).size() > 0 && dadosLocalDAO.findAllByCidade(cidade).size() > 0) {
                return dadosLocalDAO.findAllByTipoLocalAndCidadeAndCepLocal(tipoLocal, cidade, cepDadosLocal);
            } else {
                return null;
            }
        }
        return null;
    }

    public List<DadosLocal> getByTipoLocal(long idTipoLocal) {
        TipoLocal tipoLocal = new TipoLocal();
        tipoLocal.setIdTipoLocal(idTipoLocal);
        return dadosLocalDAO.findAllByTipoLocal(tipoLocal);
    }



}
