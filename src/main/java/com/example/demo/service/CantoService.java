package com.example.demo.service;

import com.example.demo.domain.Canto;
import com.example.demo.repository.CantoDAO;
import com.example.demo.repository.DistanciaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CantoService {

    @Autowired
    private DistanciaService distanciaService;

//    public void adicionarDistancia(int x1, int y1, int x2, int y2) {
//        distanciaDAO.addDistancia(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
//    }
//
//    public double calcDistancia(int x1, int y1, int x2, int y2) {
//        return distanciaDAO.calcDistancia(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
//    }

    //sqrt(pow(x1 - x2,2) + pow(y1 - y2,2));

    public int addDistanciaMax(Canto[] canto, double dist, int where) {
        int i, j;
        System.out.println("\n Maior distância: " + dist);
        for (i=0; i<= where; i++) {
            for (j = i+1; j <= where; j++) {
                DistanciaService distancia = new DistanciaService();
                double result = distancia.calcDistancia(canto[j].getX(),canto[j].getY(),canto[i].getX(),canto[i].getY());
                canto[i].melhores[0].setDistancia(result);
                if(canto[i].melhores[0].getDistancia() <= (dist) && canto[i].quantMelhores < 100
                && canto[i].melhores[0].getDistancia() > 1.1) {

                    distanciaService.adicionarDistancia(canto[j].getX(), canto[j].getY(), canto[i].getX(), canto[i].getY());
                    canto[i].melhores[canto[i].quantMelhores].getDistancia();

                    canto[j].setPosMelhores(canto[j].quantMelhores, i);

                    if(canto[j].quantMelhores < 100-1)
                        canto[j].quantMelhores = canto[j].quantMelhores + 1;
                }
            }
        }
        return 1;
    }

    public void addAnguloCantos(Canto[] canto, int tam) {
        for (int z = 0; z < tam; z++) {
            for (int i = 0; i < canto[z].quantMelhores ; i++) {
                for (int j = 0; j < canto[z].quantMelhores ; j++ ){
//                    canto[z].setAngulo(canto[z].getMelhores(i), i, canto[z].getMelhores(j), j);
                }
            }
        }
    }


}
