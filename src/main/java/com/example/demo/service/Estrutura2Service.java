package com.example.demo.service;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.demo.domain.Canto;
import com.example.demo.domain.DistanciaMaxMin;
import com.example.demo.domain.Estruturas;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.opencv.imgproc.Imgproc.*;


@Service
public class Estrutura2Service {

    @Autowired
    Estruturas estruturas;


    int maxCorners = 30;
    int maxTrackbar = 100;
    Mat src, src2, src_gray, src2_gray;
    int thresh = 180;
    int max_thresh = 255;
    int threshold_type = 1;
    int apertureSize = 5;
    double qualityLevel = 0.2;
    double minDistance = 18;
    int blockSize = 7;
    boolean useHarrisDetector = true;
    double kk = 0.04;

    String source_window = "Source image 1";
    String corners_window = "Corners detected 1";

    String source_window2 = "Source image 2";
    String corners_window2 = "Corners detected 2";

    String corners_window3 = "Intersecao";
    String corners_window4 = "Uniao";

    Canto[] edges = new Canto[1000];
    Canto[] edges2 = new Canto[1000];

    int tam = 1000;
    int where = 0;
    int where2 = 0;
    int check = 10;
    int check2 = 10;
    char[] image1 = new char[80];
    char[] image2 = new char[80];

    DistanciaMaxMin dist = new DistanciaMaxMin();

    int[] melhorCanto1 = {0, 0, 0, 0};
    int[] melhorCanto2 = {0, 0, 0, 0};
    double melhorMatch = 0;
    double melhorAngle = 0;
    int flagFim = 0;

    int contaa1 = 0,contaa2 = 0,contaacop1 = 0,contaacop2 = 0;
    int robo1,robo2;
    int direto = 0;
    int melhorDireto = 0;
    int salto = 1;
    String mapaa;
    Mat transMat;
    int[][] strong_matches = new int[1000][1000];
    String img1;
    String img2;
    String num1;
    String num2;
    String robo1num;
    String robo2num;
    int largura, comprimento;
    int mapa = 10;
    Mat dst,dst2;
    Rect rect = new Rect();

    public void setSrc(BufferedImage bufferedImage) {
        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        this.src = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        this.src.put(0, 0, pixels1);
    }

    public void setSrc2(BufferedImage bufferedImage) {
        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        this.src2 = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        this.src2.put(0, 0, pixels1);
    }

    public void setSrc_gray() {
        this.src_gray = new Mat(new Size(0, 0), CvType.CV_64FC1);
    }

    public void setSrc2_gay() {
        this.src2_gray = new Mat(new Size(0, 0), CvType.CV_64FC1);
    }

    public void defineParametros(String urlName1, Long idInfoMapa1, String urlName2, Long idInfoMapa2) throws IOException {
        System.out.println("chegou aqui");
        URL url1 = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa5.bmp");
        URL url2 = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa5.bmp");

        BufferedImage bufferedImage1 = ImageIO.read(url1);
        BufferedImage bufferedImage2 = ImageIO.read(url2);

        robo1 = 4;
        robo2 = 5;
        System.out.println("Imagem 1: " + contaa1 + "\n");
        System.out.println("Imagem 2: " + contaa2 + "\n");

        mapaa = String.valueOf(mapa);
        num1 = String.valueOf(contaa1);
        num2 = String.valueOf(contaa2);
        robo1num = String.valueOf(robo1);
        robo2num = String.valueOf(robo2);

        setSrc(bufferedImage1);
        setSrc2(bufferedImage2);

        if (!src.empty() && !src2.empty()) {

            if (src.cols() >= src2.cols()) {
                largura = src.cols();
            } else if (src.cols() < src2.cols()) {
                largura = src2.cols();
            }
            if (src.rows() >= src2.rows()) {
                comprimento = src.rows();
            } else if (src.rows() < src2.rows()) {
                comprimento = src2.rows();
            }

            rect.x = 0;
            rect.y = 0;
            rect.width = src.cols();
            rect.height = src.rows();

            dst = Mat.zeros(new Size(largura, comprimento), src.type());
            dst2 = Mat.zeros(new Size(largura, comprimento), src2.type());

            dst.setTo(new Scalar(200, 200, 200));
            dst2.setTo(new Scalar(200, 200, 200));

            src.copyTo(new Mat(dst, rect));

            rect.x = 0;
            rect.y = 0;
            rect.width = src2.cols();
            rect.height = src2.rows();

            src2.copyTo(new Mat(dst2, rect));

            dst.copyTo(src);
            dst2.copyTo(src2);

            setSrc_gray();
            setSrc2_gay();

            cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
            cvtColor(src2, src2_gray, Imgproc.COLOR_BGR2GRAY);

            HighGui.namedWindow(source_window, HighGui.WINDOW_AUTOSIZE);
            HighGui.namedWindow(source_window2, HighGui.WINDOW_AUTOSIZE);

            HighGui.imshow(source_window, src);
            HighGui.imshow(source_window2, src2);

            goodFeaturesToTrack_Demo(0, 0);
//            goodFeaturesToTrack2_Demo(0, 0);

//            dist.distCalc(edges, edges2, where, where2);
//
//            edges[where].addDistanciaMax(edges, dist.getRazao(), where);
//            edges2[where2].addDistanciaMax(edges2, dist.getRazao(), where2);
//
//            edges[where].addAnguloCantos(edges, where);
//            edges2[where2].addAnguloCantos(edges2, where2);
//
//            estruturas.getMatch(edges, where, edges2, where2, strong_matches);
//            drawStrong(strong_matches, where, where2);

            System.out.println("\nMelhor match: " + melhorMatch);
            System.out.println("\nConta 1: " + contaacop1);
            System.out.println("\nConta 2: " + contaacop2);

            for (int i=0; i<where; i++) {
                edges[i].zeraQuantMelhores();
            }

            for (int j=0; j<where2; j++) {
                edges2[j].zeraQuantMelhores();
            }

            HighGui.waitKey(500);

            if (direto == 1) {
                contaa1 += salto;
                contaa2 += salto;
                direto = 0;
            } else {
                direto = 1;
            }

            where = 0;
            where2 = 0;
            check = 10;
            check2 = 10;
            dist.zeraCont();
            dist.zeraRazao();
            dist.zeraMaiorDistancia();
            maxTrackbar = 100;
            thresh = 180;
            max_thresh = 255;
            threshold_type = 1;
            apertureSize = 5;
            blockSize = 5;
            useHarrisDetector = true;
            kk = 0.04;
            tam = 1000;

        } else if ((src.empty() || src2.empty()) && (src.empty() != src2.empty()) ) {
            System.out.println("Um dos robos terminou de mapear");
            mapaa = String.valueOf(mapa);

            if(src.empty()) {
                contaa1 = contaa1 - salto;
                num1 = String.valueOf(contaa1);
            }
            if (src2.empty()) {
                contaa2 = contaa2 - salto;
                num2 = String.valueOf(contaa2);
            }

            robo1num = String.valueOf(robo1);
            robo2num = String.valueOf(robo2);

            if (direto == 0) {
                src = Imgcodecs.imread(img1, 1);
                src2 = Imgcodecs.imread(img2, 1);
            } else if (direto == 1) {
                src2 = Imgcodecs.imread(img1, 1);
                src = Imgcodecs.imread(img2, 1);
            }

            if (src.cols() >= src2.cols()) {
                largura = src.cols();
            } else if (src.cols() < src2.cols()) {
                largura = src2.cols();
            }
            if (src.rows() >= src2.rows()) {
                comprimento = src.rows();
            } else if (src.rows() < src2.rows()) {
                comprimento = src2.rows();
            }

            rect.x = 0;
            rect.y = 0;
            rect.width = src.cols();
            rect.height = src.rows();

            dst = Mat.zeros(new Size(largura, comprimento), src.type());
            dst2 = Mat.zeros(new Size(largura, comprimento), src2.type());

            dst.setTo(new Scalar(200, 200, 200));
            dst2.setTo(new Scalar(200, 200, 200));

            src.copyTo(new Mat(dst, rect));

            rect.x = 0;
            rect.y = 0;
            rect.width = src2.cols();
            rect.height = src2.rows();

            src2.copyTo(new Mat(dst2, rect));
            dst.copyTo(src);
            dst2.copyTo(src2);

            cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
            cvtColor(src2, src2_gray, Imgproc.COLOR_BGR2GRAY);

            goodFeaturesToTrack_Demo( 0, 0 );
//            goodFeaturesToTrack2_Demo( 0, 0 );

//            dist.distCalc(edges, edges2, where, where2);
//            edges[where].addDistanciaMax(edges, dist.getRazao(), where);
//            edges2[where2].addDistanciaMax(edges2, dist.getRazao(), where2);
//
//            edges[where].addAnguloCantos(edges, where);
//            edges2[where].addAnguloCantos(edges2, where2);
//
//            estruturas.getMatch(edges, where, edges2, where2, strong_matches);
//            drawStrong(strong_matches, where, where2);

            System.out.println("\nMelhor match: " + melhorMatch);
            System.out.println("\nMelhor conta 1: " + contaacop1);
            System.out.println("\nMelhor conta 2: " + contaacop2);

            for (int i=0; i<where; i++) {
                edges[i].zeraQuantMelhores();
            }
            for (int j=0; j<where2; j++) {
                edges2[j].zeraQuantMelhores();
            }

//            HighGui.waitKey(10);

            if (direto == 1) {
                if(!src.empty())
                    contaa1 += salto;
                if(!src2.empty())
                    contaa2 += salto;
                direto = 0;
            } else {
                direto = 1;
            }

            where = 0;
            where2 = 0;
            check = 10;
            check2 = 10;
            dist.zeraCont();
            dist.zeraRazao();
            dist.zeraMaiorDistancia();
            maxTrackbar = 100;
            thresh = 180;
            max_thresh = 255;
            threshold_type = 1;
            apertureSize = 5;
            blockSize = 5;
            useHarrisDetector = true;
            kk = 0.04;
            tam = 1000;
            System.out.println("\nfim\n");

        } else {
            System.out.println("\nAcabou o mapeamento\n");

            mapaa = String.valueOf(mapa);
            num1 = String.valueOf(contaa1-salto);
            num2 = String.valueOf(contaa2-salto);
            robo1num = String.valueOf(robo1);
            robo2num = String.valueOf(robo2);

            img1.concat(img1 + num1 + ".pgm");
            img2.concat(img2 + num2 + ".pgm");

            if (melhorDireto == 0) {
                src = Imgcodecs.imread(img1, 1);
                src2 = Imgcodecs.imread(img2, 1);
            } else if (melhorDireto == 1) {
                src2 = Imgcodecs.imread(img1, 1);
                src = Imgcodecs.imread(img2, 1);
            }

            if (src.cols() >= src2.cols()) {
                largura = src.cols();
            } else if (src.cols() < src2.cols()) {
                largura = src2.cols();
            }
            if (src.rows() >= src2.rows()) {
                comprimento = src.rows();
            } else if (src.rows() < src2.rows()) {
                comprimento = src2.rows();
            }

            rect.x = 0;
            rect.y = 0;
            rect.width = src.cols();
            rect.height = src.rows();

            dst = Mat.zeros(new Size(largura, comprimento), src.type());
            dst2 = Mat.zeros(new Size(largura, comprimento), src2.type());

            dst.setTo(new Scalar(200, 200, 200));
            dst2.setTo(new Scalar(200, 200, 200));

            src.copyTo(new Mat(dst, rect));

            rect.x = 0;
            rect.y = 0;
            rect.width = src2.cols();
            rect.height = src2.rows();

            src2.copyTo(new Mat(dst2, rect));

            if (src.empty() || src2.empty()) {
                System.out.println("\nDeu merda em algum mapa 1: " + (contaa1-salto));
                System.out.println("\nDeu merda em algum mapa 2: " + (contaa2-salto));
            }

            dst.copyTo(src);
            dst2.copyTo(src2);

            cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
            cvtColor(src2, src2_gray, Imgproc.COLOR_BGR2GRAY);

            goodFeaturesToTrack_Demo( 0, 0 );
//            goodFeaturesToTrack2_Demo( 0, 0 );

//            dist.distCalc(edges, edges2, where, where2);
//
//            edges[where].addDistanciaMax(edges, dist.getRazao(), where);
//            edges2[where2].addDistanciaMax(edges2, dist.getRazao(), where2);
//
//            edges[where].addAnguloCantos(edges, where);
//            edges2[where2].addAnguloCantos(edges2, where2);
//
//            estruturas.getMatch(edges, where, edges2, where2, strong_matches);
//            drawStrong(strong_matches, where, where2);


            for (int i=0; i<where; i++) {
                edges[i].zeraQuantMelhores();
            }
            for (int j=0; j<where2; j++) {
                edges2[j].zeraQuantMelhores();
            }

            HighGui.waitKey(500);

            where = 0;
            where2 = 0;
            check = 10;
            check2 = 10;

            dist.zeraCont();
            dist.zeraRazao();
            dist.zeraMaiorDistancia();
        }
        src.release();
        src2.release();
        dst.release();
        dst2.release();
        src_gray.release();
        src2_gray.release();
    }

    public void goodFeaturesToTrack_Demo(int zero1, int zero2) {

        if (maxCorners < 1) {
            maxCorners = 1;
        }

        MatOfPoint corners = new MatOfPoint();

        Mat copy = new Mat();

        src.copyTo(copy);
        threshold(src_gray, src_gray, 100, 255, THRESH_BINARY);
        Mat element = getStructuringElement(MORPH_RECT, new Size(3, 3), new Point(-1, -1));

        erode(src_gray, src_gray, element);
        erode(src_gray, src_gray, element);
        dilate(src_gray, src_gray, element);
        dilate(src_gray, src_gray, element);
        erode(src_gray, src_gray, element);
        medianBlur(src_gray, src_gray, 5);
        dilate(src_gray, src_gray, element);

        Mat mask = new Mat();

        goodFeaturesToTrack(src_gray, corners, maxCorners, qualityLevel, minDistance, mask, blockSize, useHarrisDetector, kk);

        System.out.println("Número de corners detectados: " + corners.toArray().length);

        int r = 5;
        for (int i = 0; i < corners.toArray().length; i++) {
            circle(copy, corners.toArray()[i], r, new Scalar(255, 255, 0), -1, 8, 0 );
            if (corners.toArray()[i].x != 0 && corners.toArray()[i].y != 0) {
                edges[where] = new Canto();
                if (edges[where].addCantos(edges, where, (int)corners.toArray()[i].x, (int)corners.toArray()[i].y, tam) == 0) {
                    return;
                } else {
                    where += 1;
                }
            } else {
                System.out.println("Introduziu canto nas bordas");
            }
        }

        copy.release();
        element.release();
        goodFeaturesToTrack2_Demo(0, 0, edges, where);
    }

    public void goodFeaturesToTrack2_Demo(int zero1, int zero2, Canto[] edges, int where) {
        if (maxCorners < 1) {
            maxCorners = 1;
        }

        MatOfPoint corners = new MatOfPoint();

        Mat copy = new Mat();

        src2.copyTo(copy);
        threshold(src2_gray, src2_gray, 100, 255, THRESH_BINARY);
        Mat element = getStructuringElement(MORPH_RECT, new Size(3, 3), new Point (-1, -1));

        erode(src2_gray, src2_gray, element);
        erode(src2_gray, src2_gray, element);
        dilate(src2_gray, src2_gray, element);
        dilate(src2_gray, src2_gray, element);
        erode(src2_gray, src2_gray, element);
        medianBlur(src2_gray, src2_gray, 5);
        dilate(src2_gray, src2_gray, element);

        Mat mask = new Mat();

        goodFeaturesToTrack(src2_gray, corners, maxCorners, qualityLevel, minDistance, mask , blockSize, useHarrisDetector, kk);

        System.out.println("Número de corners detectados: " + corners.toArray().length);


        int r = 5;
        for (int i=0; i<corners.toArray().length; i++) {
            circle(copy, corners.toArray()[i], r, new Scalar(255, 255, 0), -1, 8, 0);
            if (corners.toArray()[i].x != 0 && corners.toArray()[i].y != 0) {
                edges2[i] = new Canto();

                if (edges2[this.where2].addCantos(edges2, this.where2, (int)corners.toArray()[i].x, (int)corners.toArray()[i].y, tam) == 0) {
                    return;
                } else {
                    this.where2 += 1;
                }

            } else {
                System.out.println("Introduziu canto nas bordas");
            }
        }
        System.out.println("\nwhere 2 eh aqui: " + where2);

        copy.release();
        element.release();
        dist.distCalc(edges, edges2, where, this.where2);
//        edges[where-1].addDistanciaMax(edges, dist.getRazao(), where-1);
//        edges2[where2-1].addDistanciaMax(edges2, dist.getRazao(), where2-1);

//        edges[where-1].addAnguloCantos(edges, where-1);
//        edges2[where2-1].addAnguloCantos(edges2, where2-1);

        estruturas.getMatch(edges, where-1, edges2, where2-1, strong_matches, dist, src, src2, src2_gray, src_gray, melhorMatch, transMat, melhorDireto);
//        drawStrong(strong_matches, where, where2);

    }

    public void drawStrong(int[][] strong_matches, int where, int where2) {
        strong_matches = new int[1000][1000];
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
        for (int i=0; i<where; i++) {
            for (int j=0; j<where2; j++) {
                if (strong_matches[i][j] >= 2) {
                    if (min1 < strong_matches[i][j]) {
                        System.out.println("\n bora ver o resultado do min1: " + min1);
                        min1 = strong_matches[i][j];
                        System.out.println("\n bora ver o resultado do min1 parte 2: " + min1);
                        auxi = i;
                        auxj = j;
                    }
                }
            }
        }

        for (int i=0; i<where; i++) {
            for (int j=0; j<where2; j++) {
                if (strong_matches[i][j] >= 2) {
                    if (min2 < strong_matches[i][j] && min1 > strong_matches[i][j]) {
                        min2 = strong_matches[i][j];
                        auxi2 = i;
                        auxj2 = j;
                    }
                }
            }
        }

        for (int i=0; i<where; i++) {
            for (int j=0; j<where2; j++) {
                if (strong_matches[i][j] >= 2) {
                    if (min3 < strong_matches[i][j] && min2 > strong_matches[i][j]){
                        min3 = strong_matches[i][j];
                        auxi3 = i;
                        auxj3 = j;
                    }
                }
            }
        }

        System.out.println("\nmin1 eh: " + min1);

        if (min1 >= 2) {
            System.out.println("\nMaior matching: " + min1);
        }

        for(int i = 0 ; i < where ; i++){
            for(int j = 0 ; j < where2 ; j++){
                if(strong_matches[i][j] > 2){
                    if(min4 < strong_matches[i][j] && (edges[auxi].getX() != edges[i].getX() || edges[auxi].getY() != edges[i].getY())
                            && (edges2[auxj].getX() != edges2[j].getX() || edges2[auxj].getY() != edges2[j].getY()) && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) >= 18
                            && edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                        if((edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()))
                                && (edges[auxi].getMelhores(0).calcDist(edges[auxi].getX(),edges[auxi].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj].getMelhores(0).calcDist(edges2[auxj].getX(),edges2[auxj].getY(),edges2[j].getX(),edges2[j].getY()))){
                            min4 = strong_matches[i][j];
                            System.out.println("\n min4 fica: " + min4);
                            auxi4 = i;
                            auxj4 = j;
                        }
                    }
                }
                if(min2 >= 2)
                    if(strong_matches[i][j] > 2){
                        if(min5 < strong_matches[i][j] && (edges[auxi2].getX() != edges[i].getX() || edges[auxi2].getY() != edges[i].getY())
                                && (edges2[auxj2].getX() != edges2[j].getX() || edges2[auxj2].getY() != edges2[j].getY()) && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) >= 18
                                && edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                            if((edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                    && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                    && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()))
                                    && (edges[auxi2].getMelhores(0).calcDist(edges[auxi2].getX(),edges[auxi2].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj2].getMelhores(0).calcDist(edges2[auxj2].getX(),edges2[auxj2].getY(),edges2[j].getX(),edges2[j].getY()))){
                                min5 = strong_matches[i][j];
                                auxi5 = i;
                                auxj5 = j;
                            }
                        }
                    }
                if(min3 >= 2)
                    if(strong_matches[i][j] > 2){
                        if(min6 < strong_matches[i][j] && (edges[auxi3].getX() != edges[i].getX() || edges[auxi3].getY() != edges[i].getY())
                                && (edges2[auxj3].getX() != edges2[j].getX() || edges2[auxj3].getY() != edges2[j].getY()) && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) >= 18
                                && edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()) >= 18)){
                            if((edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) >= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY())-1.1)
                                    && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY()) <= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY())+1.1)
                                    && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY())-1.1 <= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()))
                                    && (edges[auxi3].getMelhores(0).calcDist(edges[auxi3].getX(),edges[auxi3].getY(),edges[i].getX(),edges[i].getY())+1.1 >= edges2[auxj3].getMelhores(0).calcDist(edges2[auxj3].getX(),edges2[auxj3].getY(),edges2[j].getX(),edges2[j].getY()))){
                                min6 = strong_matches[i][j];
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
                transformations(max1i[0], max1j[0], max1i[1], max1j[1], max2i[0], max2j[0], max2i[1], max2j[1]);
                if (min5 >= 2) {
                    transformations(max1i2[0], max1j2[0], max1i2[1], max1j2[1], max2i2[0], max2j2[0], max2i2[1], max2j2[1]);
                    if (min6 >= 2) {
                        transformations(max1i3[0], max1j3[0], max1i3[1], max1j3[1], max2i3[0], max2j3[0], max2i3[1], max2j3[1]);
                    }
                }
            } else {
                System.out.println("Não ocorreram matchings suficientes no segunto ponto: " + min4);
            }

            if (flagFim == 1 && melhorMatch > 0) {
                transformations(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3]);

                saveMatch(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3]);
            }
        } else {
            System.out.println("Não ocorreram matchings suficientes no primeiro ponto: " + min1);
        }
    }

    public int transformations(int canto11x, int canto11y, int canto12x, int canto12y, int canto21x, int canto21y, int canto22x, int canto22y) {
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

        Mat copy1 = new Mat();
        src_gray.copyTo(copy1);

        Mat copy2 = new Mat();
        src2_gray.copyTo(copy2);

        Mat intersect = new Mat();
        Mat uniao = new Mat();

        Mat element = getStructuringElement(MORPH_RECT, new Size(3, 3), new Point(-1, -1));

        int shift1X = 0, shift1Y = 0, shift2X = 0, shift2Y = 0;
        double angle = 0;

        shift1X = canto11x - canto12x;
        shift1Y = canto11y - canto12y;

        Core.bitwise_not(copy2, copy2);

//        trans_mat = (new MatOfDouble(2, 3), 0, (float)shift1X, 0, 1, (float)shift1Y);

        warpAffine(copy2, copy2, transMat, copy2.size());

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

        atualizaMelhorMatch(melhorCanto1[0], melhorCanto1[1], melhorCanto1[2], melhorCanto1[3], melhorCanto2[0], melhorCanto2[1], melhorCanto2[2], melhorCanto2[3]);
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

        return 1;
    }

    public int saveMatch(int canto11x, int canto11y, int canto12x, int canto12y, int canto21x, int canto21y, int canto22x, int canto22y) {
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
                src.copyTo(copy1);
                src2.copyTo(copy2);
            }else{
                src.copyTo(copy2);
                src2.copyTo(copy1);
            }
        }else{
            if(direto == 0){
                src.copyTo(copy2);
                src2.copyTo(copy1);
            }else{
                src.copyTo(copy1);
                src2.copyTo(copy2);
            }
        }
        Mat intersect = new Mat();
        Mat uniao = new Mat();
        cvtColor(copy1, copy1, COLOR_BGR2GRAY);
        cvtColor(copy2, copy2, COLOR_BGR2GRAY);

        Mat copyCorrige = new Mat();
        copy2.copyTo(copyCorrige);
        int shift1X = 0, shift1Y = 0, shift2X = 0, shift2Y = 0;
        double angle = 0;
        shift1X = canto11x - canto12x;
        shift1Y = canto11y - canto12y;
        Core.bitwise_not(copy2, copy2);
//        trans_mat = (new MatOfDouble(2,3).fromArray(1), 0, (float)shift1X, 0, 1, (float)shift1Y);
        warpAffine(copy2, copy2, transMat, src2_gray.size());
        for (int i = 0; i < copyCorrige.cols(); i++){
            for (int j = 0; j < copyCorrige.rows(); j++){
                copyCorrige.put(i, j, 255);

            }
        }
        warpAffine(copyCorrige,copyCorrige,transMat,copyCorrige.size());
        canto12x = canto12x + shift1X;
        canto12y = canto12y + shift1Y;
        canto22x = canto22x + shift1X;
        canto22y = canto22y + shift1Y;
        Mat rotateMatrix = getRotationMatrix2D(new Point(canto12x,canto12y), melhorAngle, 1);
        warpAffine(copy2, copy2, rotateMatrix, copy2.size());
        warpAffine(copyCorrige, copyCorrige, rotateMatrix, copyCorrige.size());
        Core.bitwise_not(copy2, copy2);
        for (int i = 0; i < copy2.cols(); i++){
            for (int j = 0; j < copy2.rows(); j++){
                if((copyCorrige.get(i,i)[0] < 200) || ((copy2.get(i, j)[0] < 240))&&(copy2.get(i, j)[0] > 100)){
                    copy2.get(i, j)[0] = 200;
                }
            }
        }
        copy2.copyTo(intersect);
        for (int i = 0; i < copy2.cols(); i++){
            for (int j = 0; j < copy2.rows(); j++){
                if(copy2.get(i, j)[0] < 50 || copy1.get(i, j)[0] < 50){
                    intersect.get(i, j)[0] = 0;
                }else if((copy2.get(i, j)[0] > 240) || (copy1.get(i, j)[0] > 240)){
                    intersect.get(i, j)[0] = 255;
                }else{
                    intersect.get(i, j)[0] = 200;
                }
            }
        }

        String robo1num, robo2num;
        String imagem, im;
        imagem = "/home/daniela/Documents/PIBITI/Higo/demo";
        im = imagem;
        im.concat(mapaa);
        im.concat("/matched");

        robo1num = String.valueOf(robo1);
        robo2num = String.valueOf(robo2);

        im.concat(robo1num);
        im.concat(robo2num);
        im.concat(".pgm");

        HighGui.namedWindow(corners_window3, HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow(corners_window3, intersect);
        Imgcodecs.imwrite(im, intersect);

        System.out.println("\nFim\n");
        copy1.release();
        copy2.release();
        intersect.release();
        uniao.release();
        transMat.release();
        copyCorrige.release();
        return 0;
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
        System.out.println("\nvalor pixel match: " + valor);
        return valor;
    }

    public int atualizaMelhorMatch(int canto11x, int canto11y, int canto12x, int canto12y, int canto21x, int canto21y, int canto22x, int canto22y) {
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

}
