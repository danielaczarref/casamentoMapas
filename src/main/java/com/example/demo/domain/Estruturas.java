package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.Serializable;
import java.util.List;

import static org.opencv.imgproc.Imgproc.*;

@Getter
@Setter
@Component
public class Estruturas {

//    public Mat src;
//    private Mat src2;
//    private Mat src_gray;
//    private Mat src2_gay;
//    private Mat kernel;
//    private Mat kernel2;
//    private Mat resultMat;
    private double[][] angulo = new double[100][100];
    String source_window = "Source image 1";
    String corners_window = "Corners detected 1";

    String source_window2 = "Source image 2";
    String corners_window2 = "Corners detected 2";

    String corners_window3 = "Intersecao";
    String corners_window4 = "Uniao";

    public double getAngulo(int p1, int p2) {
        return this.angulo[p1][p2];
    }

//    public void setSrc(BufferedImage bufferedImage) {
//        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
//        this.src = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
//        this.src.put(0, 0, pixels1);
//    }
//
//    public Mat getSrc() {
//        return src;
//    }
//
//    public void setSrc2(BufferedImage bufferedImage) {
//        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
//        this.src2 = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
//        this.src2.put(0, 0, pixels1);
//    }

    public void getMatch(Canto[] edges, int tam, Canto[] edges2, int tam2, int[][] strong_match, DistanciaMaxMin dist, Mat src, Mat src2, Mat src2_gay, Mat src_gray, double melhorMatch, Mat transMat, int melhorDireto) {

        System.out.println("\nMaior distancia: " + dist);
        for(int i=0 ; i <= tam ; i++){
            for(int j=i+1 ; j <=tam ; j++){
                if(edges[i].getMelhores(0).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()) <= (dist.getRazao()) && edges[i].getQuantMelhores() < 100
                        && edges[i].getMelhores(0).calcDist(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()) > 1.1){

                    edges[i].getMelhores(edges[i].getQuantMelhores()).addDistancia(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY());
                    edges[i].salvarDistancia(edges[i].getMelhores(edges[i].getQuantMelhores()).addDistancia(edges[j].getX(),edges[j].getY(),edges[i].getX(),edges[i].getY()));
                    edges[i].setPosMelhores(edges[i].getQuantMelhores(),j);
                    if(edges[i].getQuantMelhores() < 100-1)
                        edges[i].setQuantMelhores(edges[i].getQuantMelhores() + 1);


                    edges[j].getMelhores(edges[j].getQuantMelhores()).addDistancia(edges[i].getX(),edges[i].getY(),edges[j].getX(),edges[j].getY());
                    edges[j].salvarDistancia(edges[j].getMelhores(edges[j].getQuantMelhores()).addDistancia(edges[i].getX(),edges[i].getY(),edges[j].getX(),edges[j].getY()));
                    edges[j].setPosMelhores(edges[j].getQuantMelhores(),i);
                    if(edges[j].getQuantMelhores() < 100-1)
                        edges[j].setQuantMelhores(edges[j].getQuantMelhores() + 1);
                }

            }
        }
        edges[tam].addAnguloCantos1(edges, tam);

        for(int i=0 ; i <= tam2 ; i++){
            for(int j=i+1 ; j <=tam2 ; j++){
                if(edges2[i].getMelhores(0).calcDist(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY()) <= (dist.getRazao()) && edges2[i].getQuantMelhores() < 100
                        && edges2[i].getMelhores(0).calcDist(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY()) > 1.1){

                    edges2[i].getMelhores(edges2[i].getQuantMelhores()).addDistancia(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY());
                    edges2[i].salvarDistancia(edges2[i].getMelhores(edges2[i].getQuantMelhores()).addDistancia(edges2[j].getX(),edges2[j].getY(),edges2[i].getX(),edges2[i].getY()));
                    edges2[i].setPosMelhores(edges2[i].getQuantMelhores(),j);
                    if(edges2[i].getQuantMelhores() < 100-1)
                        edges2[i].setQuantMelhores(edges2[i].getQuantMelhores() + 1);


                    edges2[j].getMelhores(edges2[j].getQuantMelhores()).addDistancia(edges2[i].getX(),edges2[i].getY(),edges2[j].getX(),edges2[j].getY());
                    edges2[j].salvarDistancia(edges2[j].getMelhores(edges2[j].getQuantMelhores()).addDistancia(edges2[i].getX(),edges2[i].getY(),edges2[j].getX(),edges2[j].getY()));
                    edges2[j].setPosMelhores(edges2[j].getQuantMelhores(),i);
                    if(edges2[j].getQuantMelhores() < 100-1)
                        edges2[j].setQuantMelhores(edges2[j].getQuantMelhores() + 1);
                }

            }
        }

        edges2[tam2].addAnguloCantos2(edges2, tam2);


        Mat copy1 = new Mat();
        Mat copy2 = new Mat();

        src.copyTo(copy1);
        src2.copyTo(copy2);
        double min1 = -1, min2 = -1, min3 = -1, min4 = -1, min5 = -1, min6 = -1;
        int auxi = 0, auxj = 0,auxi2 = 0, auxj2 = 0,auxi3 = 0, auxj3 = 0,auxi4 = 0,auxj4 = 0,auxi5 = 0,auxj5 = 0,auxi6 = 0,auxj6 = 0;
        int[] max1i = new int[2];
        int[] max1j = new int[2];
        int[] max1i2 = new int[2];
        int[] max1j2 = new int[2];
        int[] max1i3 = new int[2];
        int[] max1j3 = new int[2];
        int[] max2i = new int[2];
        int[] max2j = new int[2];
        int[] max2i2 = new int[2];
        int[] max2j2 = new int[2];
        int[] max2i3 = new int[2];
        int[] max2j3 = new int[2];
        int[] num_matches = new int [tam];
        int flagFim = 0;

        int[][][] matches = new int [tam][1000][6];

        strong_match = new int[1000][1000];

        if (strong_match != null) {
            for(int i = 0 ; i < tam ; i++){
                num_matches[i] = 0;
                if(strong_match[i] != null){

                    for(int k = 0 ; k < tam2 ; k++){

                        strong_match[i][k] = 0;
                        for(int j = 0 ; j < edges[i].getQuantMelhores() ; j++){
                            for(int l = 0 ; l < edges2[k].getQuantMelhores() ; l++){
                                setAngulo(edges, edges2, i, k);
                                if(edges[i].pegarDistancia() > 1.1 && edges2[k].pegarDistancia() > 1.1)
                                    if((edges[i].getMelhores(j).getDistancia() >= edges2[k].getMelhores(l).getDistancia()-1.1)
                                            && (edges[i].pegarDistancia() <= edges2[k].pegarDistancia()+1.1)
                                            && (edges[i].pegarDistancia()-1.1 <= edges2[k].pegarDistancia())
                                            && (edges[i].pegarDistancia()+1.1 >= edges2[k].pegarDistancia())
                                    ){

                                        matches[i][num_matches[i]][0] = i;
                                        matches[i][num_matches[i]][1] = k;
                                        matches[i][num_matches[i]][2] = edges[i].getPosMelhores(j);
                                        matches[i][num_matches[i]][3] = edges2[k].getPosMelhores(l);
                                        matches[i][num_matches[i]][4] = j;
                                        matches[i][num_matches[i]][5] = l;
                                        num_matches[i]++;
                                    }
                            }
                        }
                    }
                }
            }
            for(int i = 0 ; i < tam ; i++){
                for(int k = 0 ; k < tam2 ; k++){
                    for(int z = 0 ; z < num_matches[i]-1 ; z++){
                        for(int m = z+1 ; m < num_matches[i] ; m++){
                            if(matches[i][z][4] != matches[i][m][4] && getAngulo(matches[i][z][2],matches[i][m][2]) > 1.1 && getAngulo(matches[i][z][3],matches[i][m][3])> 1.1) {
                                if ((getAngulo(matches[i][z][2], matches[i][m][2]) >= (getAngulo(matches[i][z][3], matches[i][m][3]) - 1.1))
                                        && (getAngulo(matches[i][z][2], matches[i][m][2]) <= (getAngulo(matches[i][z][3], matches[i][m][3]) + 1.1))
                                        && (getAngulo(matches[i][z][2], matches[i][m][2]) - 1.1 <= (getAngulo(matches[i][z][3], matches[i][m][3])))
                                        && (getAngulo(matches[i][z][2], matches[i][m][2]) + 1.1 >= (getAngulo(matches[i][z][3], matches[i][m][3])))
                                ) {
                                    strong_match[i][k] += 1;
                                    strong_match[i][k] += getMatch2(edges, tam, matches[i][z][2], edges2, tam2, matches[i][z][3], i, k, 1);
                                    strong_match[i][k] += getMatch2(edges, tam, matches[i][m][2], edges2, tam2, matches[i][m][3], i, k, 1);
                                    if (strong_match[i][k] >= 2) {
                                        if (min1 < strong_match[i][k]) {
                                            System.out.println("\n bora ver o resultado do min1: " + min1);
                                            min1 = strong_match[i][k];
                                            System.out.println("\n bora ver o resultado do min1 parte 2: " + min1);
                                            auxi = i;
                                            auxj = k;
                                            if (min2 < strong_match[i][k] && min1 > strong_match[i][k]) {
                                                min2 = strong_match[i][k];
                                                auxi2 = i;
                                                auxj2 = k;
                                            }
                                            if (min3 < strong_match[i][k] && min2 > strong_match[i][k]){
                                                min3 = strong_match[i][k];
                                                auxi3 = i;
                                                auxj3 = k;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("\nmin1 eh: " + min1);

            if (min1 >= 2) {
                System.out.println("\nMaior matching: " + min1);
            }

            for(int i = 0 ; i < tam ; i++){
                for(int j = 0 ; j < tam2 ; j++){
                    if(strong_match[i][j] > 2){
                        if(min4 < strong_match[i][j] && (edges[auxi].getX() != edges[i].getX() || edges[auxi].getY() != edges[i].getY())
                                && (edges2[auxj].getX() != edges2[j].getX() || edges2[auxj].getY() != edges2[j].getY()) && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) >= 18
                                && edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                            if((edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                    && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                    && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()))
                                    && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()))){
                                min4 = strong_match[i][j];
                                System.out.println("\n min4 fica: " + min4);
                                auxi4 = i;
                                auxj4 = j;
                            }
                        }
                    }
                    if(min2 >= 2)
                        if(strong_match[i][j] > 2){
                            if(min5 < strong_match[i][j] && (edges[auxi2].getX() != edges[i].getX() || edges[auxi2].getY() != edges[i].getY())
                                    && (edges2[auxj2].getX() != edges2[j].getX() || edges2[auxj2].getY() != edges2[j].getY()) && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) >= 18
                                    && edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                                if((edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                        && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                        && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()))
                                        && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()))){
                                    min5 = strong_match[i][j];
                                    auxi5 = i;
                                    auxj5 = j;
                                }
                            }
                        }
                    if(min3 >= 2)
                        if(strong_match[i][j] > 2){
                            if(min6 < strong_match[i][j] && (edges[auxi3].getX() != edges[i].getX() || edges[auxi3].getY() != edges[i].getY())
                                    && (edges2[auxj3].getX() != edges2[j].getX() || edges2[auxj3].getY() != edges2[j].getY()) && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) >= 18
                                    && edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                                if((edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                        && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                        && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()))
                                        && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()))){
                                    min6 = strong_match[i][j];
                                    auxi6 = i;
                                    auxj6 = j;
                                }
                            }
                        }
                }
            }

            max1i[0] = edges[auxi].getX();
            max1j[0] = edges[auxi].getY();
            max1i[1] = edges2[auxj].getX();
            max1j[1] = edges2[auxj].getY();
            if(min2 >= 2){
                max1i2[0] = edges[auxi2].getX();
                max1j2[0] = edges[auxi2].getY();
                max1i2[1] = edges2[auxj2].getX();
                max1j2[1] = edges2[auxj2].getY();
                if(min3 >= 2){
                    max1i3[0] = edges[auxi3].getX();
                    max1j3[0] = edges[auxi3].getY();
                    max1i3[1] = edges2[auxj3].getX();
                    max1j3[1] = edges2[auxj3].getY();
                }
            }

            circle(copy1, new Point(max1i[0], max1j[0]), 2, new Scalar(255, 255, 0), 2, 8, 0);
            circle(copy2, new Point(max1i[1], max1j[1]), 2, new Scalar(255, 255, 0), 2, 8, 0);

            System.out.println("\n primeiro ponto: " + min4);
            if (min4 >= 2) {
                System.out.println("\nMaior matching: " + min4);
                System.out.println("\nPonto branco mapa1: X = " + edges[auxi].getX() + " Y = " + edges[auxi].getY());
                System.out.println("\nPonto branco mapa2: X = " + edges2[auxj].getX() + " Y = " + edges2[auxj].getY());
                System.out.println("\nPonto preto mapa1: X = " + edges[auxi4].getX() + " Y = " + edges[auxi4].getY());
                System.out.println("\nPonto preto mapa2: X = " + edges2[auxj4].getX() + " Y = " + edges2[auxj4].getY());

                max2i[0] = edges[auxi4].getX();
                max2j[0] = edges[auxi4].getY();
                max2i[1] = edges2[auxj4].getX();
                max2j[1] = edges2[auxj4].getY();
                if (min5 >= 2) {
                    max2i2[0] = edges[auxi5].getX();
                    max2j2[0] = edges[auxi5].getY();
                    max2i2[1] = edges2[auxj5].getX();
                    max2j2[1] = edges2[auxj5].getY();
                    if (min6 >= 2) {
                        max2i3[0] = edges[auxi6].getX();
                        max2j3[0] = edges[auxi6].getY();
                        max2i3[1] = edges2[auxj6].getX();
                        max2j3[1] = edges2[auxj6].getY();
                    }
                }

                circle(copy1, new Point(max2i[0], max2j[0]), 2, new Scalar(0, 255, 255), 2, 8, 0);
                circle(copy2, new Point(max2i[1], max2j[1]), 2, new Scalar(0, 255, 255), 2, 8, 0);

                copy1.release();
                copy2.release();

                if (flagFim == 0) {
                    transformations(max1i[0], max1j[0], max1i[1], max1j[1], max2i[0], max2j[0], max2i[1], max2j[1], src2_gay, src_gray, transMat, melhorMatch, melhorDireto);
                    if (min5 >= 2) {
                        transformations(max1i2[0], max1j2[0], max1i2[1], max1j2[1], max2i2[0], max2j2[0], max2i2[1], max2j2[1], src2_gay, src_gray, transMat, melhorMatch, melhorDireto);
                        if (min6 >= 2) {
                            transformations(max1i3[0], max1j3[0], max1i3[1], max1j3[1], max2i3[0], max2j3[0], max2i3[1], max2j3[1], src2_gay, src_gray, transMat, melhorMatch, melhorDireto);
                        }
                    }
                } else {
                    System.out.println("Não ocorreram matchings suficientes no segunto ponto: " + min4);
                }

//                if (flagFim == 1 && melhorMatch > 0) {
//                    transformations(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3]);
//
//                    saveMatch(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3]);
//                }
            } else {
                System.out.println("Não ocorreram matchings suficientes no primeiro ponto: " + min1);
            }

        } //bem aqui
    }

    public int transformations(int canto11x, int canto11y, int canto12x, int canto12y, int canto21x, int canto21y, int canto22x, int canto22y, Mat src2_gay, Mat src_gray, Mat transMat, double melhorMatch, int melhorDireto) {
        double percent = 0, percentInter = 0, percentUni = 0;
        double[] percentt = new double[2];

        int canto11x2 = 0, canto11y2 = 0, canto12x2 = 0, canto12y2 = 0, canto21x2 = 0, canto21y2 = 0, canto22x2 = 0, canto22y2 = 0;

        canto11x2 = canto11x;
        canto11y2 = canto11y;
        canto12x2 = canto12x;
        canto12y2 = canto12y;
        canto21x2 = canto21x;
        canto21y2 = canto21y;
        canto22x2 = canto22x;
        canto22y2 = canto22y;
        int contaa1 = 0,contaa2 = 0,contaacop1 = 0,contaacop2 = 0;

        int direto = 0;
        int[] melhorCanto1 = {0, 0, 0, 0};
        int[] melhorCanto2 = {0, 0, 0, 0};

        double melhorAngle = 0;

        Mat copy1 = new Mat();
        src_gray.copyTo(copy1);

        Mat copy2 = new Mat();
        src2_gay.copyTo(copy2);

        Mat intersect = new Mat();
        Mat uniao = new Mat();

        Mat element = getStructuringElement(MORPH_RECT, new Size(3, 3), new Point(-1, -1));

        int shift1X = 0, shift1Y = 0, shift2X = 0, shift2Y = 0;
        double angle = 0;

        shift1X = canto11x - canto12x;
        shift1Y = canto11y - canto12y;

        Core.bitwise_not(copy2, copy2);

        MatOfDouble matOfDouble = new MatOfDouble();
        matOfDouble.put(2, 3, 1);

//        transMat = (1, 0, (float)shift1X, 0, 1, (float)shift1Y);

//        warpAffine(copy2, copy2, transMat, copy2.size());

        double x1 = 0,y1 = 0,x2 = 0,y2 = 0,x0 = 0,y0 = 0,x3 = 0,y3 = 0;

        canto12x = canto12x + shift1X;
        canto12y = canto12y + shift1Y;
        canto22x = canto22x + shift1X;
        canto22y = canto22y + shift1Y;

        x0 = (float)(canto11x);
        y0 = (float)(canto11y);
        x1 = (float)(canto12x);
        y1 = (float)(canto12y);
        x2 = (float)(canto21x);
        y2 = (float)(canto21y);
        x3 = (float)(canto22x);
        y3 = (float)(canto22y);

        x2 = x2-x0;
        y2 = y2-y0;
        x3 = x3-x1;
        y3 = y3-y1;

        double valor = (x3*x2 + y3*y2)/(Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2)) * Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)));
        angle = (float)Math.acos(valor) * 180/Math.PI;

        Mat rotateMatrix = new Mat();
        Mat copy22 = new Mat();

        erode(copy1, copy1, element);
        for (int i=0; i<2; i++) {
            angle = -1*angle;
            copy2.copyTo(copy22);

            rotateMatrix = getRotationMatrix2D(new Point(canto12x,canto12y), angle, 1);
            warpAffine(copy22, copy22, rotateMatrix, copy22.size());
            Core.bitwise_not(copy22,copy22);
            erode( copy22, copy22, element );
            Core.bitwise_or(copy1,copy22,intersect);
            Core.bitwise_and(copy1,copy22,uniao);

            percentInter = contapixelpreto(intersect);
            percentUni = contapixelpreto(uniao);
            percentt[i] = percentInter/percentUni;
        }

        if (percentt[0] > percentt[1]) {
            angle = -1*angle;
            percent = percentt[0];
            copy2.copyTo(copy22);
            rotateMatrix = getRotationMatrix2D(new Point(canto12x,canto12y), angle, 1);
            warpAffine(copy22, copy22, rotateMatrix, copy22.size());
            Core.bitwise_not(copy22,copy22);
            erode( copy22, copy22, element );
            Core.bitwise_or(copy1,copy22,intersect);
            Core.bitwise_and(copy1,copy22,uniao);
        } else {
            percent = percentt[1];
        }
        copy22.copyTo(copy2);
        System.out.println("\nO valor: " + valor);
        System.out.println("\nO x3: " + x3);
        System.out.println("\nO y3: " + y3);
        System.out.println("\nO x2: " + x2);
        System.out.println("\nO y2: " + y2);
        System.out.println("\nO angle: " + angle);

        atualizaMelhorMatch(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3], melhorDireto, direto, src_gray, src2_gay, melhorAngle, melhorMatch, transMat);
        if(percent > melhorMatch){
            melhorCanto1[0] = canto11x2;
            melhorCanto1[1] = canto11y2;
            melhorCanto1[2] = canto12x2;
            melhorCanto1[3] = canto12y2;
            melhorCanto2[0] = canto21x2;
            melhorCanto2[1] = canto21y2;
            melhorCanto2[2] = canto22x2;
            melhorCanto2[3] = canto22y2;
            melhorAngle = angle;
            melhorMatch = percent;
            melhorDireto = direto;
            contaacop1 = contaa1;
            contaacop2 = contaa2;
        }
        System.out.println("\nPercent: " + percent);
        System.out.println("\npercentInter: " + percentInter);
        System.out.println("\npercentUni: " + percentUni);

        if (direto == 0) {
            HighGui.namedWindow(corners_window2, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window2, copy1);

            HighGui.namedWindow(corners_window, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window, copy2);

            HighGui.namedWindow(corners_window3, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window4, uniao);
        } else {
            HighGui.namedWindow(corners_window2, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window2, copy2);

            HighGui.namedWindow(corners_window, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window, copy1);

            HighGui.namedWindow(corners_window3, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window3, intersect);

            HighGui.namedWindow(corners_window4, HighGui.WINDOW_AUTOSIZE);
            HighGui.imshow(corners_window4, uniao);
        }

        copy1.release();
        copy2.release();
        copy22.release();
        intersect.release();
        uniao.release();
        transMat.release();
        element.release();

        System.out.println("\nMelhor match: " + melhorMatch);
        System.out.println("\nConta 1: " + contaacop1);
        System.out.println("\nConta 2: " + contaacop2);

        return 1;
    }

    public double contapixelpreto(Mat imagem) {
        Mat newImage = new Mat();
        threshold(imagem, newImage, 100, 255, THRESH_BINARY);
        double valor = 0;
        for (int x = 0; x < newImage.rows(); x++){
            for (int y = 0; y < newImage.cols(); y++){
                if(Core.countNonZero(newImage) < 1  ){
                    valor += 1;
                }
            }
        }
        System.out.println("\nvalor pixel preto match: " + valor);
        return valor;
    }

    public int atualizaMelhorMatch(int canto11x, int canto11y, int canto12x, int canto12y, int canto21x, int canto21y, int canto22x, int canto22y, int melhorDireto, int direto, Mat src_gray, Mat src2_gray, double melhorAngle, double melhorMatch, Mat transMat) {
        double percent = 0,percentInter = 0, percentUni = 0;
        int canto11x2 = 0,canto11y2 = 0,canto12x2 = 0,canto12y2 = 0,canto21x2 = 0,canto21y2 = 0,canto22x2 = 0,canto22y2 = 0;
        canto11x2 = canto11x;
        canto11y2 = canto11y;
        canto12x2 = canto12x;
        canto12y2 = canto12y;
        canto21x2 = canto21x;
        canto21y2 = canto21y;
        canto22x2 = canto22x;
        canto22y2 = canto22y;

        Mat copy1 = new Mat();
        Mat copy2 = new Mat();
        if(melhorDireto == 0){
            if(direto == 0){
                src_gray.copyTo(copy1);
                src2_gray.copyTo(copy2);
            }else{
                src_gray.copyTo(copy2);
                src2_gray.copyTo(copy1);
            }
        }else{
            if(direto == 0){
                src_gray.copyTo(copy2);
                src2_gray.copyTo(copy1);
            }else{
                src_gray.copyTo(copy1);
                src2_gray.copyTo(copy2);
            }
        }
        Mat intersect = new Mat();
        Mat uniao = new Mat();
        Mat element = getStructuringElement(MORPH_RECT, new Size(3, 3), new Point(-1, -1));
        int shift1X = 0, shift1Y = 0, shift2X = 0, shift2Y = 0;
        double angle = 0;
        shift1X = canto11x - canto12x;
        shift1Y = canto11y - canto12y;
        Core.bitwise_not(copy2, copy2);
        transMat = new Mat();
        System.out.println("\naonde deu erro: " + copy2 + "     " + transMat + "       " + copy2.size());
        warpAffine(copy2,copy2,transMat,copy2.size());
        canto12x = canto12x + shift1X;
        canto12y = canto12y + shift1Y;
        canto22x = canto22x + shift1X;
        canto22y = canto22y + shift1Y;
        angle = melhorAngle;
        Mat rotateMatrix = new Mat();
        erode( copy1, copy1, element );
        rotateMatrix = getRotationMatrix2D(new Point(canto12x,canto12y), angle, 1);
        warpAffine(copy2, copy2, rotateMatrix, copy2.size());
        Core.bitwise_not(copy2,copy2);
        erode( copy2, copy2, element );
        Core.bitwise_or(copy1,copy2,intersect);
        Core.bitwise_and(copy1,copy2,uniao);
        percentInter = contapixelpreto(intersect);
        percentUni = contapixelpreto(uniao);
        percent = percentInter/percentUni;
        melhorMatch = percent;
        copy1.release();
        copy2.release();
        intersect.release();
        uniao.release();
        transMat.release();
        element.release();

        return 1;
    }


    public int getMatch2(Canto[] edges, int tam, int where, Canto[] edges2, int tam2, int where2, int i, int k,int deep) {
        int num_matches = 0;
        int[][] matches = new int [1000][4];
        int nivel_matches = 0;
        int value = 0;
        if (deep <= 1) {
            for(int j = 0 ; j < edges[where].getQuantMelhores() ; j++){
                for(int l = 0 ; l < edges2[where2].getQuantMelhores() ; l++){
                    if(edges[where].pegarDistancia() > 1.1 && edges2[where2].pegarDistancia() > 1.1) {
                        /*** verifica a semelhan�a se distancia entre pontos entre um mapa e outro s�o equivalentes variando apenas dentro de um ruido ***/
                        if ((edges[where].pegarDistancia() >= edges2[where2].pegarDistancia() - 1.1)
                                && (edges[where].pegarDistancia() <= edges2[where2].pegarDistancia() + 1.1)
                                && (edges[where].pegarDistancia() - 1.1 <= edges2[where2].pegarDistancia())
                        ) {

                            matches[num_matches][0] = edges[where].getPosMelhores(j);
                            matches[num_matches][1] = edges2[where2].getPosMelhores(l);
                            matches[num_matches][2] = j;
                            matches[num_matches][3] = l;
                            num_matches++;
                        }
                    }
                }
            }
            for(int z = 0 ; z < num_matches-1 ; z++){
                for(int m = z+1 ; m < num_matches ; m++){
                    if(matches[z][2] != matches[m][2]){
                        if(getAngulo(matches[z][0],matches[m][0]) > 1.1 && getAngulo(matches[z][1],matches[m][1]) > 1.1)
                            if((getAngulo(matches[z][0],matches[m][0]) >= (getAngulo(matches[z][1],matches[m][1])-1.1))
                                    && (getAngulo(matches[z][0],matches[m][0]) <= (getAngulo(matches[z][1],matches[m][1])+1.1))
                                    && (getAngulo(matches[z][0],matches[m][0])-1.1 <= (getAngulo(matches[z][1],matches[m][1])))
                                    && (getAngulo(matches[z][0],matches[m][0])+1.1 >= (getAngulo(matches[z][1],matches[m][1])))
                            ){
                                if((getAngulo(matches[z][0],matches[m][0]) >= (getAngulo(matches[z][1],matches[m][1])-1.1))
                                        && (getAngulo(matches[z][0],matches[m][0]) <= (getAngulo(matches[z][1],matches[m][1])+1.1))
                                        && (getAngulo(matches[z][0],matches[m][0])-1.1 <= (getAngulo(matches[z][1],matches[m][1])))
                                        && (getAngulo(matches[z][0],matches[m][0])+1.1 >= (getAngulo(matches[z][1],matches[m][1])))
                                ){

                                    value += 1;
                                }
                            }
                    }
                }
            }
            System.out.println("\nvalueee: " + value);
            return value;
        }
        return 0;
    }

    public void setAngulo(Canto[] edges, Canto[] edges2, int p1index, int p2index) {
        double x1, y1, x2, y2, x0, y0;
        float ang2;

        x0 = (float)(edges[p1index].getX());
        y0 = (float)(edges[p2index].getY());
        x1 = (float)(edges[p1index].getxCanto1());
        y1 = (float)(edges[p1index].getyCanto1());
        x2 = (float)(edges2[p2index].getxCanto2());
        y2 = (float)(edges2[p2index].getyCanto2());

        if (p1index == p2index) {
            this.angulo[p1index][p2index] = 0;
            this.angulo[p2index][p1index] = 0;
        }
        else if (x1 == x2 && y1 == y2) {
            this.angulo[p1index][p2index] = 0;
            this.angulo[p2index][p1index] = 0;
        }
        else if (x0 == x1 && y0 == y1) {
            this.angulo[p1index][p2index] = 0;
            this.angulo[p2index][p1index] = 0;
        }
        else if (x0 == x2 && y0 == y2) {
            this.angulo[p1index][p2index] = 0;
            this.angulo[p2index][p1index] = 0;
        }

        x1 = x1-x0;
        y1 = y1-y0;
        x2 = x2-x0;
        y2 = y2-y0;
        double valor = (x1*x2 + y1*y2)/(Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2)) * Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)));
        ang2 = (float)(Math.acos(valor)*180/3.14159265);

        if (ang2 < 0.0001 || Float.isNaN(ang2)) {
            this.angulo[p1index][p2index] = 0;
            this.angulo[p2index][p1index] = 0;
        } else {
            this.angulo[p1index][p2index] = ang2;
            this.angulo[p2index][p1index] = ang2;
        }

    }

}
