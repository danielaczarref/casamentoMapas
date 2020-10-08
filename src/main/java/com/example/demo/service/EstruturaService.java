package com.example.demo.service;


import com.example.demo.domain.Canto;
import com.example.demo.domain.Estruturas;
import com.example.demo.repository.InfoCasamentoDAO;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.dilate;

@Service
public class EstruturaService {
    @Autowired
    Estruturas estruturas;

    @Autowired
    CantoService cantoService;

    @Autowired
    InfoCasamentoDAO infoCasamentoDAO;

    private int[][] strong_matches = new int[1000][1000];
    private static int thresh = 150;
    private static int max_thresh = 255;
    private static int threshold_type = 1;

    private String source_window = "Source image 1";
    private String corners_window = "Corners detected 1";

    private String source_window2 = "Source image 2";
    private String corners_window2 = "Corners detected 2";

//    Canto cantoObj2 = new Canto();
//    Canto cantoObj = new Canto();
    List<Canto> canto = new ArrayList<>();
//    List<Canto> canto2 = new ArrayList<>();
//    Canto[] canto2 = new Canto[1000];



    private Mat src_gray;
    private Mat src2_gray;

    public Mat src;
    private Mat src2;

    private Mat kernel;
    private Mat kernel2;
    private Mat resultMat;

    private Mat dst_norm_scaledGlobal;
    private Mat dst_norm_scaled2Global;


    private int tam = 1000;
    private int where = 0;
    int where2 = 0;
    private int check = 10;
    private int check2 = 10;

    public void setSrc(BufferedImage bufferedImage) {
        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        this.src = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        this.src.put(0, 0, pixels1);
    }

    public void setSrc_gray() {
        this.src_gray = new Mat(new Size(0, 0), CvType.CV_64FC1);
    }

    public void addCantoNaList() {
//        canto.add(cantoObj);
//        canto2.add(cantoObj2);
    }

    public void setSrc2_gay() {
        this.src2_gray = new Mat(new Size(0, 0), CvType.CV_64FC1);
    }

    public void setKernel() {
        this.kernel = new Mat(new Size(0, 0), CvType.CV_64FC1);
        this.kernel2 = new Mat(new Size(0, 0), CvType.CV_64FC1);
    }


    public void setSrc2(BufferedImage bufferedImage) {
        byte[] pixels1 = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        this.src2 = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        this.src2.put(0, 0, pixels1);
    }

    public void defineParametros(String urlName1, Long idInfoMapa1, String urlName2, Long idInfoMapa2) throws IOException {

        addCantoNaList();

        URL url1 = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa5.bmp");
        URL url2 = new URL("https://spring-matching-maps.s3-sa-east-1.amazonaws.com/mapa6.bmp");

        BufferedImage bufferedImage1 = ImageIO.read(url1);
        BufferedImage bufferedImage2 = ImageIO.read(url2);

        setSrc(bufferedImage1);
        setSrc2(bufferedImage2);

        setSrc_gray();
        setSrc2_gay();

        cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
        cvtColor(src2, src2_gray, Imgproc.COLOR_BGR2GRAY);

        HighGui.namedWindow(source_window, HighGui.WINDOW_AUTOSIZE);
        HighGui.namedWindow(source_window2, HighGui.WINDOW_AUTOSIZE);

        imshow(source_window, src);
        imshow(source_window2, src2);

        cornerHarris_demo(0, 0);
        cornerHarris_demo2(0, 0);


//        Imgproc.matchTemplate(estruturas.getSrc(), estruturas.getSrc2(), estruturas.getResultMat(), 1);
//        estruturas.getResultMat().rows();
//        estruturas.getResultMat().cols();
//
//        int[][] strong_matches = new int[estruturas.getResultMat().rows()][estruturas.getResultMat().cols()];
//        estruturas.getMatch(cantoObj, where, cantoObj2, where2, strong_matches);
        drawStrong(strong_matches, where, where2);

//        infoCasamentoDAO.save(cantoObj.getX(), cantoObj.getY(), cantoObj2.getX(), cantoObj2.getY(),
//                corners_window, dst_norm_scaledGlobal, corners_window2, dst_norm_scaled2Global, idInfoMapa1, idInfoMapa2);
//        HighGui.waitKey(0);
    }

    public void cornerHarris_demo(int x, int y) {

        setKernel();

        Mat dst, dst_norm = new Mat(), dst_norm_scaled = new Mat();
        dst = Mat.zeros(src.size(), CvType.CV_32FC1);
        Mat mask = Mat.zeros(src2.size(), Imgproc.COLOR_BGR2GRAY);
        int pixel;

        int blockSize = 2;
        int apertureSize = 3;
        double k = 0.04;
        int color;

        dilate(src_gray, src_gray, kernel);
        Imgproc.erode(src_gray, src_gray, kernel);
        dilate(src_gray, src_gray, kernel);

        src_gray = (conv2(src_gray, 1));
        src_gray = (conv2(src_gray, 2));

        System.out.println("src_gray: " + src2_gray);

        Imgproc.cornerHarris(src_gray, dst, blockSize, apertureSize, k, Core.BORDER_DEFAULT);

        Core.normalize(dst, dst_norm, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());

        Core.convertScaleAbs(dst_norm, dst_norm_scaled);

        System.out.println("linhas da dst_norm: " + dst_norm.rows());
        System.out.println("colunas da dst_norm: " + dst_norm.cols());
//        for (int j=0; j<dst_norm.rows(); j++) {
//            for (int i=0; i<dst_norm.cols(); i++) {
//                if ((int)dst_norm.get(j, i)[0] > thresh) {
//                    Imgproc.circle(dst_norm_scaled, new Point(i, j), 1, new Scalar(0), 2, 8, 0);
//                    if (canto.get(0).addCantos( canto.get(0), where, i, j, tam) == 0){
//                        return;
//                    } else {
//                        where += 1;
//                    }
//                }
//            }
//        }
//        cantoObj.addAnguloCantos(cantoObj, where);
    }

    public Mat conv2(Mat src, int kernel_size) {
        Mat dst = new Mat();
        Mat kernel;
        Mat result = new Mat();
        kernel = Mat.ones(kernel_size, kernel_size, CvType.CV_32F);
        float res = (float)(kernel_size*kernel_size);
        Core.divide(kernel, Scalar.all(res), result);

        Imgproc.filter2D(src, dst, -1, kernel, new Point(-1, -1), 0,  Core.BORDER_DEFAULT);
        return dst;
    }

    //terminar dps
    public void drawStrong(int[][] strong_matches, int where, int where2) {
        Mat dst, dst_norm = new Mat(), dst_norm_scaled = new Mat();
        Mat dst2, dst_norm2 = new Mat(), dst_norm_scaled2 = new Mat();

        dst = Mat.zeros(src.size(), CvType.CV_32FC1);
        dst2 = Mat.zeros(src.size(), CvType.CV_32FC1);

        int blockSize = 2;
        int apertureSize = 3;
        double k = 0.04;
        int min1 = -1, min4 = -1;
        int auxi = 0, auxj = 0, auxi4 = 0, auxj4 = 0;
        int[] max1i = new int[2];
        int[] max1j = new int[2];
        int[] max2i = new int[2];
        int[] max2j = new int[2];

        Imgproc.cornerHarris(src_gray, dst, blockSize, apertureSize, k, Core.BORDER_DEFAULT);
        Imgproc.cornerHarris(src2_gray, dst2, blockSize, apertureSize, k, Core.BORDER_DEFAULT);

        Core.normalize(dst, dst_norm, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
        Core.convertScaleAbs(dst_norm, dst_norm_scaled);

        Core.normalize(dst2, dst_norm2, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
        Core.convertScaleAbs(dst_norm2, dst_norm_scaled2);

        for (int i=0; i<where; i++) {
            for (int j=0; j<where2; j++) {
                if (min1<strong_matches[i][j]) {
                    min1 = strong_matches[i][j];
                    auxi = i;
                    auxj = j;
                }
            }
        }
//
//        for (int i=0; i<where; i++) {
//            for (int j=0; j<where2; j++) {
//                if (min4 < strong_matches[i][j] && (cantoObj.getX() != cantoObj.getX() || cantoObj.getY() != cantoObj.getY())
//                && (cantoObj2.getX() != cantoObj2.getX() || cantoObj2.getY() != cantoObj2.getY())) {
//
//                    if((cantoObj.getMelhores(i).getDistancia() >= cantoObj2.getMelhores(j).getDistancia()-1.1)
//                            && (cantoObj.getMelhores(i).getDistancia() <= cantoObj2.getMelhores(j).getDistancia()+1.1)
//                            && (cantoObj.getMelhores(i).getDistancia()-1.1 <= cantoObj2.getMelhores(j).getDistancia())
//                            && (cantoObj.getMelhores(i).getDistancia()+1.1 >= cantoObj2.getMelhores(j).getDistancia())) {
//
//                        min4 = strong_matches[i][j];
//                        auxi4 = i;
//                        auxj4 = j;
//                    }
//                }
//            }
//        }
//        System.out.println("O ponto branco mapa 1: X = " + cantoObj.getX() + " Y = " + cantoObj.getY() + "\n\n");
//        System.out.println("O ponto branco mapa 2: X = " + cantoObj2.getX() + " Y = " + cantoObj2.getY() + "\n\n");
//
//        max1i[0] = cantoObj.getX();
//        max1j[0] = cantoObj.getY();
//        max1i[1] = cantoObj2.getX();
//        max1j[1] = cantoObj2.getY();
//        max2i[0] = cantoObj.getX();
//        max2j[0] = cantoObj.getY();
//        max2i[1] = cantoObj2.getX();
//        max2j[1] = cantoObj2.getY();

        Imgproc.circle(dst_norm_scaled, new Point(max1i[0], max1j[0]), 2, new Scalar(255), 2, 8, 0);
        Imgproc.circle(dst_norm_scaled2, new Point(max1i[1], max1j[1]), 2, new Scalar(255), 2, 8, 0);

        Imgproc.circle(dst_norm_scaled, new Point(max2i[0], max2j[0]), 2, new Scalar(0), 2, 8, 0);
        Imgproc.circle(dst_norm_scaled2, new Point(max2i[1], max2j[1]), 2, new Scalar(0), 2, 8, 0);

        HighGui.namedWindow(corners_window, HighGui.WINDOW_AUTOSIZE);
        imshow(corners_window, dst_norm_scaled);
        System.out.println("Corners window: " + corners_window);
        dst_norm_scaledGlobal = dst_norm_scaled;
        System.out.println("Dst norm scaled: " + dst_norm_scaledGlobal.toString());



        HighGui.namedWindow(corners_window2, HighGui.WINDOW_AUTOSIZE);
        imshow(corners_window2, dst_norm_scaled2);
        dst_norm_scaled2Global = dst_norm_scaled2;
        System.out.println("Corners window: " + corners_window2);
        System.out.println("Dst norm scaled: " + dst_norm_scaled2Global.toString());
     }

    public void cornerHarris_demo2(int x, int y) {
        Mat dst, dst_norm = new Mat(), dst_norm_scaled = new Mat();
        dst = Mat.zeros(src.size(), CvType.CV_32FC1);
        Mat mask;

        int blockSize = 2;
        int apertureSize = 3;
        double k = 0.04;

        dilate(src2_gray, src2_gray, kernel2);
        Imgproc.erode(src2_gray, src2_gray, kernel2);
        dilate(src2_gray, src2_gray, kernel2);

        src2_gray = (conv2(src2_gray, 1));
        src2_gray = (conv2(src2_gray, 2));

        Imgproc.cornerHarris(src2_gray, dst, blockSize, apertureSize, k, Core.BORDER_DEFAULT);

        Core.normalize(dst, dst_norm, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
        Core.convertScaleAbs(dst_norm, dst_norm_scaled);

//        for (int j=0; j<dst_norm.rows(); j++) {
//            for (int i=0; i<dst_norm.cols(); i++) {
//                if ((int)dst_norm.get(j, i)[0] > thresh) {
//                    Imgproc.circle(dst_norm_scaled, new Point(i, j), 1, new Scalar(0), 2, 8, 0);
////                    if (cantoObj2.addCantos(canto2.get(0), where2, i, j, tam) == 0) {
//                        return;
//                    } else {
//                        where2 += 1;
//                    }
//                }
//            }
//        }
//        cantoObj2.addAnguloCantos(cantoObj2, where2);
    }

}
