package com.zukxu.cv.base;


import com.zukxu.cv.common.utils.Constants;
import com.zukxu.cv.common.utils.OpenCVUtil;
import com.zukxu.cv.common.web.BaseController;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Vector;

@Controller
@RequestMapping(value = "page")
public class PageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat markmat1 = Imgcodecs.imread("D:\\test\\abc\\1.png", Imgcodecs.IMREAD_GRAYSCALE);
        Mat markmat2 = Imgcodecs.imread("D:\\test\\abc\\2.png", Imgcodecs.IMREAD_GRAYSCALE);
        Vector<MatOfPoint> contours1 = OpenCVUtil.findContours(markmat1);
        Vector<MatOfPoint> contours2 = OpenCVUtil.findContours(markmat2);
        Mat mat1 = getSimMark("D:\\test\\abc\\1.png");
        Mat mat2 = getSimMark("D:\\test\\abc\\2.png");
        double result1 = Imgproc.matchShapes(contours1.get(0), contours2.get(0), Imgproc.CV_CONTOURS_MATCH_I1, 0);
        double result2 = Imgproc.matchShapes(mat1, mat2, Imgproc.CV_CONTOURS_MATCH_I1, 0);
        System.out.println(result1);
        System.out.println(result2);
    }

    private static MatOfPoint getSimMark(String path) {
        Mat markMat = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
        Vector<MatOfPoint> contours = new Vector<MatOfPoint>();
        Mat rsMat = new Mat();
        Imgproc.findContours(markMat, contours, rsMat, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point());
        MatOfPoint markMop = new MatOfPoint();
        Mat result = new Mat(markMat.size(), CvType.CV_8U, new Scalar(255));
        Imgproc.drawContours(result, contours, 0, new Scalar(0), 1);
        String image1 = "d:\\test\\abc\\t.jpg";
        Imgcodecs.imwrite(image1, result);
        for (int i = 0; i < contours.size(); i++) {
            markMop = contours.get(0);
        }
        return markMop;
    }

    /**
     * ?????????????????????
     */
    @RequestMapping(value = "pageOCR")
    public void pageOCR(HttpServletResponse response, String imageFile, Integer ocrType) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        logger.info("\n ????????????");

        String sourcePath = Constants.PATH + imageFile;
        logger.info("url==============" + sourcePath);
        String result = "";
        if (ocrType == 1) {// tesseracts ocr ????????????
            result = getPageNoByTesseract(sourcePath);
        } else if (ocrType == 2) {// ????????????
            result = getPageNoByContours(sourcePath);
        } else {
            result = getPageNoByTemplate(sourcePath);
        }
        renderString(response, result);
    }

    /**
     * ??????tesseracts???????????????????????????testate??????tomcat???bin?????????
     */
    public String getPageNoByTesseract(String filePath) {
        String result = "";
        try {
            File file = new File(filePath);
            Tesseract instance = new Tesseract(); // JNA Interface Mapping
            instance.setLanguage("chi_sim");//
            result = instance.doOCR(file);
            logger.info("result====={}", result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ????????????????????????
     */
    public String getPageNoByContours(String filePath) {
        Mat source = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_GRAYSCALE);
        System.out.println("row" + source.rows() + " col " + source.cols());
        // ???????????????
        Mat bininv = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.threshold(source, bininv, 170, 255, Imgproc.THRESH_BINARY_INV);
        // ?????????????????????????????????????????????
        Mat destination = OpenCVUtil.eroding(bininv);
        destination = OpenCVUtil.dilation(destination);
        // ????????????
        Vector<MatOfPoint> contours = OpenCVUtil.findContours(destination);
        int pageSize = 0;
        // ????????????????????????????????????????????????????????????????????????
        Mat image = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_ANYCOLOR);
        for (int i = 0; i < contours.size(); i++) {
            Mat result = new Mat(destination.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
            Imgproc.drawContours(result, contours, i, new Scalar(0, 0, 255), 1);
            MatOfPoint mop = contours.get(i);
            // ??????????????????
            double contArea = Math.abs(Imgproc.contourArea(mop, false));
            Rect r = Imgproc.boundingRect(mop);
            System.out.println("???????????????" + contArea);
            if (contArea > 1200) {// ???????????????????????????
                // ???????????????????????????
                Imgproc.rectangle(image, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(0,
                        0,
                        255), 2);
                pageSize++;
            }
        }

        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "page0.png";
        Imgcodecs.imwrite(destPath, image);
        System.out.println("????????????" + pageSize);
        return pageSize + "";
    }

    /**
     * ??????????????????????????????
     *
     * @param filePath
     * @return String
     * @Author ??????
     * @Date 2018???4???8???
     * ????????????
     * 2018???4???8??? ??????  ????????????
     */
    public String getPageNoByTemplate(String filePath) {
        String pageSize = "";
        Mat source = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_GRAYSCALE);
        // ???????????????
        Mat bininv = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.threshold(source, bininv, 170, 255, Imgproc.THRESH_BINARY_INV);
        Vector<MatOfPoint> contours1 = OpenCVUtil.findContours(bininv);
        MatOfPoint mop = contours1.get(0);
        Rect rect = Imgproc.boundingRect(mop);
        Mat matchTemp = source.submat(rect.y, rect.y + rect.height, rect.x, rect.x + rect.width);
        String page_temp = Constants.PATH + Constants.DEST_IMAGE_PATH + "page_temp.png";
        Imgcodecs.imwrite(page_temp, matchTemp);
        String pagePath = Constants.PATH + Constants.SOURCE_IMAGE_PATH + "shuzi.png";
        Mat pageImage = Imgcodecs.imread(pagePath, Imgcodecs.IMREAD_GRAYSCALE);
        int result_cols = pageImage.cols() - matchTemp.cols() + 1;
        int result_rows = pageImage.rows() - matchTemp.rows() + 1;
        Mat destination = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        Imgproc.matchTemplate(pageImage, matchTemp, destination, Imgproc.TM_CCOEFF);
        // ?????????????????????
        Core.normalize(destination, destination, 0, 255, Core.NORM_MINMAX, -1, new Mat());
        Core.MinMaxLocResult minmaxLoc = Core.minMaxLoc(destination);
        Point matchLoc = minmaxLoc.maxLoc;
        Imgproc.rectangle(pageImage, matchLoc, new Point(matchLoc.x + matchTemp.cols(), matchLoc.y + matchTemp.rows())
                , new Scalar(0), 2);
        System.out.println(matchLoc.x + "   " + matchLoc.y);
        pageSize = getPage(matchLoc.x) + "";
        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "page1.png";
        Imgcodecs.imwrite(destPath, pageImage);
        return pageSize;
    }

    /**
     * ???????????????????????????
     *
     * @param x
     * @return int
     * @Author ??????
     * @Date 2018???4???8???
     * ????????????
     * 2018???4???8??? ??????  ????????????
     */
    public int getPage(double x) {
        // ??????2??????????????????shuzi.png?????????????????????2??????????????????????????????????????????2,2;72,2;142,2
        // Math.floor ??????????????????????????????
        return (int) Math.floor((x - 2) / 70) + 1;
    }
}
