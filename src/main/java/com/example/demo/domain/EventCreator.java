package com.example.demo.domain;

import com.example.demo.service.DadosLocalService;
import com.example.demo.service.EstruturaService;
import com.example.demo.service.InfoMapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EventCreator {

    @Autowired
    private InfoMapaService infoMapaService;

    @Autowired
    private DadosLocalService dadosLocalService;

    @Autowired
    private EstruturaService estruturaService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void create() throws IOException {
        URL url1;
        List<Long> idDadosLocalList = new ArrayList<>();
        List<InfoMapa> infoMapaListAux = new ArrayList<>();
        List<InfoMapa> infoMapaListAux2 = new ArrayList<>();
        infoMapaListAux = infoMapaService.getAllInfoMapa();
        for (int i=0; i < infoMapaListAux.size(); i++){
              idDadosLocalList = Collections.singletonList(infoMapaListAux.get(i).getDadosLocal().getIdDadosLocal());
        }
        for (int j=0; j < idDadosLocalList.size(); j++) {
            infoMapaListAux2 = infoMapaService.getAllByDadosLocal(idDadosLocalList.get(j));
            for (int x=j; x < 2; x++) {
                estruturaService.defineParametros(infoMapaListAux2.get(0).getUrlImage(), infoMapaListAux2.get(0).getIdInfoMapa(), infoMapaListAux2.get(1).getUrlImage(), infoMapaListAux2.get(1).getIdInfoMapa());
            }
        }
    }
}
