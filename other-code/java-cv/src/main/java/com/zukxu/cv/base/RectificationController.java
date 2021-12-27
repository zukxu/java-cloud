package com.zukxu.cv.base;

import com.zukxu.cv.common.utils.Constants;
import com.zukxu.cv.common.utils.OpenCVUtil;
import com.zukxu.cv.common.web.BaseController;
import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "rect")
public class RectificationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RectificationController.class);

    /**
     * 获得锚点(定位点)
     * 方法1，通过模板匹配圆心，应该换成正方形也可以，之前模板匹配不行是因为模板图形不是最小的
     *
     * @param sourcePath
     * @param matchPath
     * @param anchor01
     * @param anchor02
     * @param anchor03
     * @param anchor04
     */
    public static void fetchAnchorPoints1(String sourcePath, String matchPath, Point anchor01, Point anchor02,
                                          Point anchor03, Point anchor04) {
        Mat imageMatch = new Mat();
        Mat colorImage = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_ANYCOLOR);
        Point maxLoc01, maxLoc02, maxLoc03, maxLoc04;
        int srcRows = colorImage.rows();
        int srcCols = colorImage.cols();
        Mat src01 = colorImage.submat(new Rect(0, 0, srcCols / 2, srcRows / 2));
        Mat src02 = colorImage.submat(new Rect(srcCols / 2, 0, srcCols / 2, srcRows / 2));
        Mat src03 = colorImage.submat(new Rect(0, srcRows / 2, srcCols / 2, srcRows / 2));
        Mat src04 = colorImage.submat(new Rect(srcCols / 2, srcRows / 2, srcCols / 2, srcRows / 2));

        Mat matTmp = Imgcodecs.imread(matchPath, Imgcodecs.IMREAD_ANYCOLOR);
        Imgproc.matchTemplate(matTmp, src01, imageMatch, Imgproc.TM_CCOEFF_NORMED);

        MinMaxLocResult minmaxLoc1 = Core.minMaxLoc(imageMatch);
        System.out.println("minmaxLoc1.maxVal:" + minmaxLoc1.maxVal);
        maxLoc01 = minmaxLoc1.maxLoc;
        anchor01.x = maxLoc01.x;
        anchor01.y = maxLoc01.y;
        Imgproc.circle(colorImage, maxLoc01, 3, new Scalar(0, 0, 255), 3);
        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_c1.png";
        Imgcodecs.imwrite(destPath, colorImage);

        Imgproc.matchTemplate(matTmp, src02, imageMatch, Imgproc.TM_CCOEFF_NORMED);
        MinMaxLocResult minmaxLoc2 = Core.minMaxLoc(imageMatch);
        System.out.println("minmaxLoc2.maxVal:" + minmaxLoc2.maxVal);
        maxLoc02 = minmaxLoc2.maxLoc;
        anchor02.x = maxLoc02.x + srcCols / 2.0;
        anchor02.y = maxLoc02.y;
        Imgproc.circle(colorImage, anchor02, 3, new Scalar(0, 0, 255), 3);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_c2.png";
        Imgcodecs.imwrite(destPath, colorImage);

        Imgproc.matchTemplate(matTmp, src03, imageMatch, Imgproc.TM_CCOEFF_NORMED);
        MinMaxLocResult minmaxLoc3 = Core.minMaxLoc(imageMatch);
        System.out.println("minmaxLoc3.maxVal:" + minmaxLoc3.maxVal);
        maxLoc03 = minmaxLoc3.maxLoc;
        anchor03.x = maxLoc03.x;
        anchor03.y = maxLoc03.y + srcRows / 2.0;
        Imgproc.circle(colorImage, anchor03, 3, new Scalar(0, 0, 255), 3);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_c3.png";
        Imgcodecs.imwrite(destPath, colorImage);

        Imgproc.matchTemplate(matTmp, src04, imageMatch, Imgproc.TM_CCOEFF_NORMED);
        MinMaxLocResult minmaxLoc4 = Core.minMaxLoc(imageMatch);
        System.out.println("minmaxLoc4.maxVal:" + minmaxLoc4.maxVal);
        maxLoc04 = minmaxLoc4.maxLoc;
        anchor04.x = maxLoc04.x + srcCols / 2.0;
        anchor04.y = maxLoc04.y + srcRows / 2.0;
        Imgproc.circle(colorImage, anchor04, 3, new Scalar(0, 0, 255), 3);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_c4.png";
        Imgcodecs.imwrite(destPath, colorImage);

    }

    /**
     * 获得锚点(定位点)
     * 方法2，霍夫曼圆变换查找定位点
     *
     * @param sourcePath
     * @param anchor01
     * @param anchor02
     * @param anchor03
     * @param anchor04
     */
    public static void fetchAnchorPoints2(String sourcePath, Point anchor01, Point anchor02, Point anchor03,
                                          Point anchor04) {
        Mat src = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_GRAYSCALE);
        Mat colorImage = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_ANYCOLOR);
        int srcRows = src.rows();
        int srcCols = src.cols();
        Mat source1 = src.submat(new Rect(0, 0, srcRows / 2, srcRows / 2));
        Mat source2 = src.submat(new Rect(srcCols / 2, 0, srcCols / 2, srcRows / 2));
        Mat source3 = src.submat(new Rect(0, srcRows / 2, srcCols / 2, srcRows / 2));
        Mat source4 = src.submat(new Rect(srcCols / 2, srcRows / 2, srcCols / 2, srcRows / 2));

        Mat src01 = colorImage.submat(new Rect(0, 0, srcRows / 2, srcRows / 2));
        Mat src02 = colorImage.submat(new Rect(srcCols / 2, 0, srcCols / 2, srcRows / 2));
        Mat src03 = colorImage.submat(new Rect(0, srcRows / 2, srcCols / 2, srcRows / 2));
        Mat src04 = colorImage.submat(new Rect(srcCols / 2, srcRows / 2, srcCols / 2, srcRows / 2));

        Mat circles = new Mat();// 声明一个向量，保存检测出的圆的圆心坐标和半径
        Imgproc.HoughCircles(source1, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, 300 / 8.0, 200, 90, 10, 50);// 霍夫变换检测圆
        System.out.println("图片高 宽：" + src.rows() + "		" + src.cols());
        System.out.println(circles.cols());
        int cols = circles.cols();
        if (cols > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] vCircle = circles.get(0, i);
                Point center = new Point(vCircle[0], vCircle[1]);
                int radius = (int) Math.round(vCircle[2]);
                Imgproc.circle(src01, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
                Imgproc.circle(src01, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
                anchor01.x = vCircle[0];
                anchor01.y = vCircle[1];

            }
        }
        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_cc1.png";
        Imgcodecs.imwrite(destPath, src01);

        Imgproc.HoughCircles(source2, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, 300 / 8.0, 200, 90, 10, 50);// 霍夫变换检测圆
        System.out.println(circles.cols());
        if (circles.cols() > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] vCircle = circles.get(0, i);
                Point center = new Point(vCircle[0], vCircle[1]);
                int radius = (int) Math.round(vCircle[2]);
                Imgproc.circle(src02, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
                Imgproc.circle(src02, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
                anchor02.x = vCircle[0] + srcCols / 2.0;
                anchor02.y = vCircle[1];
            }
        }
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_cc2.png";
        Imgcodecs.imwrite(destPath, src02);

        Imgproc.HoughCircles(source3, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, 300 / 8.0, 200, 90, 10, 50);// 霍夫变换检测圆
        System.out.println(circles.cols());
        if (circles.cols() > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] vCircle = circles.get(0, i);
                Point center = new Point(vCircle[0], vCircle[1]);
                int radius = (int) Math.round(vCircle[2]);
                Imgproc.circle(src03, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
                Imgproc.circle(src03, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
                anchor03.x = vCircle[0];
                anchor03.y = vCircle[1] + srcRows / 2.0;
            }
        }
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_cc3.png";
        Imgcodecs.imwrite(destPath, src03);

        Imgproc.HoughCircles(source4, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, 300 / 8.0, 200, 90, 10, 50);// 霍夫变换检测圆
        System.out.println(circles.cols());
        if (circles.cols() > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] vCircle = circles.get(0, i);
                Point center = new Point(vCircle[0], vCircle[1]);
                int radius = (int) Math.round(vCircle[2]);
                Imgproc.circle(src04, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
                Imgproc.circle(src04, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
                anchor04.x = vCircle[0] + srcCols / 2.0;
                anchor04.y = vCircle[1] + srcRows / 2.0;
            }
        }
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect_cc4.png";
        Imgcodecs.imwrite(destPath, src04);

    }

    /**
     * 图像矫正透视变换
     * 创建者 Songer
     * 创建时间	2018年4月10日
     */
    @RequestMapping(value = "rectification")
    public void rectification(HttpServletResponse response, String imageFile, Integer markType) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        logger.info("\n 图像矫正透视变换");

        String sourcePath = Constants.PATH + imageFile;
        logger.info("url==============" + sourcePath);
        // 加载为灰度图显示
        Mat source1 = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_ANYCOLOR);
        Mat source2 = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_GRAYSCALE);
        Point anchor01 = new Point();
        Point anchor02 = new Point();
        Point anchor03 = new Point();
        Point anchor04 = new Point();
        if (markType == 1) {// 模板匹配识别定位点
            String matchPath = Constants.PATH + Constants.SOURCE_IMAGE_PATH + "z1_temp.png";
            fetchAnchorPoints1(sourcePath, matchPath, anchor01, anchor02, anchor03, anchor04);
        } else if (markType == 2) {// 霍夫圆检测识别定位点
            fetchAnchorPoints2(sourcePath, anchor01, anchor02, anchor03, anchor04);
        }
        MatOfPoint mop = new MatOfPoint(anchor01, anchor02, anchor03, anchor04);
        MatOfPoint2f mat2f = new MatOfPoint2f();
        MatOfPoint2f refmat2f = new MatOfPoint2f();
        mop.convertTo(mat2f, CvType.CV_32FC1);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        contours.add(mop);
        Imgproc.polylines(source2, contours, true, new Scalar(0, 0, 255), 1);
        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect1.png";
        Imgcodecs.imwrite(destPath, source2);

        Point point11 = new Point(99, 200);
        Point point12 = new Point(2317, 200);
        Point point13 = new Point(99, 3300);
        Point point14 = new Point(2317, 3300);

        Mat dst_vertices = new MatOfPoint(point11, point12, point13, point14);
        dst_vertices.convertTo(refmat2f, CvType.CV_32FC1);
        Mat warpMatrix = Imgproc.getPerspectiveTransform(mat2f, refmat2f);

        Mat dst = new Mat(source1.rows(), source1.cols(), source1.type());
        System.out.println(source1.rows() + " " + source1.cols());
        Imgproc.warpPerspective(source1, dst, warpMatrix, dst.size(), Imgproc.INTER_LINEAR, 0, new Scalar(255, 255,
                255));
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "rect2.png";
        Imgcodecs.imwrite(destPath, dst);
        try {
            byte[] imgebyte = OpenCVUtil.covertMat2Byte1(dst);
            renderImage(response, imgebyte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
