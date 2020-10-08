package com.example.demo.service;

import com.example.demo.domain.InfoCasamento;
import com.example.demo.repository.InfoCasamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoCasamentoService {

    @Autowired
    private InfoCasamentoDAO infoCasamentoDAO;

//    public void salvarCasamento(int pontoXMap1, int pontoYMap1, int pontoXMap2, int pontoYMap2, String cornerWindows, String dstNormScaled,
//                                String cornerWindows2, String dstNormScaled2, int idInfoMapa, int idInfoMapa2) {
//        infoCasamentoDAO.save(new InfoCasamento(null, pontoXMap1, pontoYMap1, pontoXMap2, pontoYMap2, cornerWindows, dstNormScaled,
//                cornerWindows2, dstNormScaled2, idInfoMapa, idInfoMapa2));
//    }
}
