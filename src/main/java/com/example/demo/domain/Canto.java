package com.example.demo.domain;


import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class Canto {

    private int x = 0;

    private int x2 = 0;

    public double distance = 0;

    private int y = 0;

    public int xCanto1 = 0;
    public int yCanto1 = 0;

    public int xCanto2 = 0;
    public int yCanto2 = 0;

    private int y2 = 0;

    public int quantMelhores = 0;

    List<Integer> lista = new ArrayList<Integer>();

    public Distancia[] melhores = new Distancia[100];

    public Distancia[] melhores2 = new Distancia[100];

    private int[] posMelhores = new int[100];

    private double[][] angulo = new double[100][100];

    public Distancia getMelhores(int pos) {
        return this.melhores[pos] = new Distancia();
    }

    public int getX() {
        return this.x;
    }

    public void salvarDistancia(double distancia) {
        this.distance = distancia;
    }

    public double pegarDistancia() {
        return this.distance;
    }

    public void salvarXCanto1(int x) {
        this.xCanto1 = x;
    }

    public int getxCanto1() {
        return this.xCanto1;
    }

    public void salvarYCanto1(int y) {
        this.yCanto1 = y;
    }

    public int getyCanto1(){
        return this.yCanto1;
    }

    public void salvarXCanto2(int x) {
        this.xCanto2 = x;
    }

    public int getxCanto2() {
        return this.xCanto2;
    }

    public void salvaryCanto2(int y) {
        this.yCanto2 = y;
    }

    public int getyCanto2() {
        return this.yCanto2;
    }

    public int getY() {
        return this.y;
    }

    public void setList(int pos, int valor) {
        this.lista.add(0, valor);
    }

    public List getList() {
        return this.lista;
    }

    public Integer getListAt(int pos) {
        return lista.get(pos);
    }

    public int getQuantMelhores() {
        return this.quantMelhores;
    }

    public int getPosMelhores(int melhor) {
        return this.posMelhores[melhor];
    }

    public double getAngulo(int p1, int p2) {
        return this.angulo[p1][p2];
    }

    public void setX(int valor) {
//        canto.add(where, valor);
        this.x = valor;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setQuantMelhores(int quantMelhores) {
        this.quantMelhores = quantMelhores;
    }

    public void setPosMelhores(int melhor, int where) {
        this.posMelhores[melhor] = where;
    }

    public int addDistanciaMax(Canto[] edges, double dist,int where) {
        System.out.println("\nedges: " + edges);
        System.out.println("\n dist: " + dist);
        System.out.println("\nwhere: " + where);
        int i,j;

        System.out.println("\nMaior distancia: " + dist);
        for(i=0 ; i <= where ; i++){
            for(j=i+1 ; j <=where ; j++){
//                edges[i] = new Canto();
//                edges[j] = new Canto();
                if(edges[i].getMelhores(0).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()) <= (dist) && edges[i].getQuantMelhores() < 100
                        && edges[i].getMelhores(0).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()) > 1.1){

                    edges[i].getMelhores(edges[i].getQuantMelhores()).addDistancia(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY());
                    edges[i].setPosMelhores(edges[i].getQuantMelhores(),j);
                    System.out.println("\nmais um teste: " + edges[i].getQuantMelhores());
                    //edges[i].setMelhorespai(i);
                    if(edges[i].getQuantMelhores() < 100-1)
                        edges[i].setQuantMelhores(edges[i].getQuantMelhores() + 1);


                    edges[j].getMelhores(edges[j].getQuantMelhores()).addDistancia(edges[i].getX(),edges[i].getY(),edges[j].getX(),edges[j].getY());
                    edges[j].setPosMelhores(edges[j].getQuantMelhores(),i);
                    if(edges[j].getQuantMelhores() < 100-1)
                        edges[j].setQuantMelhores(edges[j].getQuantMelhores() + 1);
                }

            }
        }
        edges[where].addAnguloCantos1(edges, where);
//        edges2[where2-1].addAnguloCantos(edges2, where2-1);
        return 1;
    }

    public void zeraQuantMelhores() {
        this.quantMelhores = 0;
    }

//    public void setAngulo(int p1, int p1index, int p2, int p2index) {
//        double x1, y1, x2, y2, x0, y0;
//        float ang2;
//
//        x0 = (float)(x);
//        y0 = (float)(y);
//        x1 = (float)(p1);
//        y1 = (float)(p1);
//        x2 = (float)(p2.getX());
//        y2 = (float)(p2.getY());
//        System.out.println("\n informacoes distancia: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
//
//        if (p1index == p2index) {
//            this.angulo[p1index][p2index] = 0;
//            this.angulo[p2index][p1index] = 0;
////            System.out.println("Informação angulo: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 : " + angulo[p2index][p1index]);
//        }
//        else if (x1 == x2 && y1 == y2) {
//            this.angulo[p1index][p2index] = 0;
//            this.angulo[p2index][p1index] = 0;
////            System.out.println("Informação angulo: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 : " + angulo[p2index][p1index]);
//        }
//        else if (x == x1 && y == y1) {
//            this.angulo[p1index][p2index] = 0;
//            this.angulo[p2index][p1index] = 0;
////            System.out.println("Informação angulo: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 : " + angulo[p2index][p1index]);
//        }
//        else if (x == x2 && y == y2) {
//            this.angulo[p1index][p2index] = 0;
//            this.angulo[p2index][p1index] = 0;
////            System.out.println("Informação angulo: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 : " + angulo[p2index][p1index]);
//        }
//
//        x1 = x1-x0;
//        y1 = y1-y0;
//        x2 = x2-x0;
//        y2 = y2-y0;
//        double valor = (x1*x2 + y1*y2)/(Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2)) * Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)));
//        ang2 = (float)(Math.acos(valor)*180/3.14159265);
//
//        if (ang2 < 0.0001 || Float.isNaN(ang2)) {
//            this.angulo[p1index][p2index] = 0;
//            this.angulo[p2index][p1index] = 0;
////            System.out.println("Informação angulo update: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 update: " + angulo[p2index][p1index]);
//        } else {
//            this.angulo[p1index][p2index] = ang2;
//            this.angulo[p2index][p1index] = ang2;
////            System.out.println("Informação angulo update: " + angulo[p1index][p2index]);
////            System.out.println("Informação angulo 2 update: " + angulo[p2index][p1index]);
//        }
//
//    }

    public int addCantos(Canto[] canto, int where, int xx, int yy, int tam) {
        if (where >= tam) {
            System.out.println("Tamanho insuficiente: " + tam);
            return 0;
        }

        canto[where].setX(xx);
        canto[where].setY(yy);
        System.out.println("Informação de add cantos: X = " + canto[where].getX() + " Y = " + canto[where].getY());
        if (where == 0) {
            return 1;
        }
        return 1;
    }

    public void addAnguloCantos1(Canto[] edges, int tam) {
        System.out.println("chegou angulo cantos");
        for(int z = 0 ; z < tam ; z++){
            for(int i = 0 ; i < edges[z].getQuantMelhores() ; i++){
                for(int j = 0 ; j < edges[z].getQuantMelhores() ; j++){
//                    System.out.println("\no que vai setar os angulos:  " + edges[z].getMelhores(i) + "    ,  " + i + "   ,    " + edges[z].getMelhores(j) + "    e   " + j);
//                    edges[z].setAngulo(edges[z].getX(),i,edges[z].getY(),j);
                    edges[z].salvarXCanto1(edges[z].getX());
                    edges[z].salvarYCanto1(edges[z].getY());
                }
            }
        }
    }

    public void addAnguloCantos2(Canto[] edges, int tam) {
        System.out.println("chegou angulo cantos");
        for(int z = 0 ; z < tam ; z++){
            for(int i = 0 ; i < edges[z].getQuantMelhores() ; i++){
                for(int j = 0 ; j < edges[z].getQuantMelhores() ; j++){
//                    System.out.println("\no que vai setar os angulos:  " + edges[z].getMelhores(i) + "    ,  " + i + "   ,    " + edges[z].getMelhores(j) + "    e   " + j);
//                    edges[z].setAngulo(edges[z].getX(),i,edges[z].getY(),j);
                    edges[z].salvarXCanto2(edges[z].getX());
                    edges[z].salvaryCanto2(edges[z].getY());
                }
            }
        }
    }
}
