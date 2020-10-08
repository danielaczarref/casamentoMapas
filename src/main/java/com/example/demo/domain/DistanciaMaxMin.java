package com.example.demo.domain;

public class DistanciaMaxMin {

    public double maiorDistancia = 0;

    public double[] dist = new double[2500];

    public int cont = 0;

    private double razao;

    public double getMax() {
        return this.maiorDistancia;
    }

    public void setMaiorDistancia(double valor) {
        this.maiorDistancia = valor;
    }

    public void addDist(double valor) {
        if (cont >= 1000) {
            System.out.println("Erro! Aumente o tamanho da conexão");
        }
        this.dist[cont] = valor;
        this.cont += 1;
    }


    public void zeraCont() {
        this.cont = 0;
    }

    public void zeraDist() {
        for (int i=0; i<cont; i++) {
            this.dist[cont] = 0;
        }
    }

    public void zeraMaiorDistancia() {
        this.maiorDistancia = 0;
    }

    public void zeraRazao() {
        this.razao = 0;
    }

    public void setRazao() {
        this.razao = this.maiorDistancia/2;
    }

    public double getRazao() {
        return this.razao;
    }

    public void distCalc(Canto[] edges, Canto[] edges2, int where, int where2) {

        double maior1 = 0;
        double maior2 = 0;
//        edges2[0] = new Canto();
        for(int i = 0 ; i <= where-1 ; i++){
            for(int j=i+1 ; j <=where-1 ; j++){

                if(edges[i].getMelhores(i).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()) > getMax()){
                    setMaiorDistancia(edges[i].getMelhores(i).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()));
                }

                addDist(edges[i].getMelhores(i).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()));
            }
        }
        maior1 = getMax();
        System.out.println("\nmaior 1: " + maior1);
        zeraMaiorDistancia();
        zeraDist();
        zeraCont();
        for(int i = 0 ; i <= where2-1 ; i++){
            for(int j=i+1 ; j <=where2-1 ; j++){

                if(edges2[i].getMelhores(i).calcDist(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY()) > getMax()){
                    setMaiorDistancia(edges2[i].getMelhores(i).calcDist(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY()));
                }
                addDist(edges[i].getMelhores(i).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()));
            }
        }
        maior2 = getMax();
        zeraMaiorDistancia();
        zeraDist();
        zeraCont();
        if (maior1 > maior2){
            setMaiorDistancia(maior2);
        }else{
            setMaiorDistancia(maior1);
        }
        System.out.println("\nmaior 1: " + maior1);
        System.out.println("\nmaior 2: "+ maior2);
        System.out.println("\n maior distancia: " + maiorDistancia);
        setRazao();
        System.out.println("\nsaindo aqui, razao: " + getRazao());
    }
}
