package com.zukxu.cv.base;

import com.zukxu.common.utils.StringUtils;
import com.zukxu.cv.common.utils.Constants;
import com.zukxu.cv.common.utils.OpenCVUtil;
import com.zukxu.cv.common.web.BaseController;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


@Controller
@RequestMapping(value = "cardPlus")
public class CardPlusController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CardPlusController.class);

    /**
     * 红色色系0-20，160-180
     * 蓝色色系100-120
     * 绿色色系60-80
     * 黄色色系23-38
     * 识别出的颜色会标记为白色，其他的为黑色
     *
     * @param min
     * @param max
     */
    public static Mat findColorByHSV(Mat source, int min, int max) {
        Mat hsv_image = new Mat();
        Imgproc.GaussianBlur(source, source, new Size(3, 3), 0, 0);
        Imgproc.cvtColor(source, hsv_image, Imgproc.COLOR_BGR2HSV);
        Mat threshold = new Mat();
        Core.inRange(hsv_image, new Scalar(min, 90, 90), new Scalar(max, 255, 255), threshold);
        return threshold;
    }

    /**
     * 查找黑色
     *
     * @param source
     * @return
     */
    public static Mat findBlackColorByHSV(Mat source) {
        Mat hsv_image = new Mat();
        Imgproc.GaussianBlur(source, source, new Size(3, 3), 0, 0);
        Imgproc.cvtColor(source, hsv_image, Imgproc.COLOR_BGR2HSV);
        String image1 = "D:\\test\\testImge\\ttbefore.jpg";
        Imgcodecs.imwrite(image1, hsv_image);
        Mat threshold = new Mat();
        Core.inRange(hsv_image, new Scalar(0, 0, 0), new Scalar(180, 255, 46), threshold);
        String image2 = "D:\\test\\testImge\\ttblack.jpg";
        Imgcodecs.imwrite(image2, threshold);
        return threshold;
    }

    /**
     * 水平投影
     *
     * @param source 传入灰度图片Mat
     * @return
     */
    public static Mat horizontalProjection(Mat source) {
        Mat dst = new Mat(source.rows(), source.cols(), source.type());
        // 先进行反转二值化
        Imgproc.threshold(source, dst, 150, 255, Imgproc.THRESH_BINARY_INV);
        // 水平积分投影
        // 每一行的白色像素的个数
        int[] rowsWidth = new int[dst.rows()];
        for (int i = 0; i < dst.rows(); i++) {
            for (int j = 0; j < dst.cols(); j++) {
                if (dst.get(i, j)[0] == 255) {
                    rowsWidth[i]++;
                }
            }
        }
        // 定义一个白色跟原图一样大小的画布
        Mat matResult = new Mat(dst.rows(), dst.cols(), CvType.CV_8UC1, new Scalar(255, 255, 255));
        // 将每一行按照行像素值大小填充像素宽度
        for (int i = 0; i < matResult.rows(); i++) {
            for (int j = 0; j < rowsWidth[i]; j++) {
                matResult.put(i, j, 0);
            }
        }
        return matResult;
    }

    /**
     * 垂直投影
     *
     * @param source 传入灰度图片Mat
     * @return
     */
    public static Mat verticalProjection(Mat source) {
        // 先进行反转二值化
        Mat dst = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.threshold(source, dst, 150, 255, Imgproc.THRESH_BINARY_INV);
        // 垂直积分投影
        // 每一列的白色像素的个数
        int[] colsWidth = new int[dst.cols()];
        for (int j = 0; j < dst.cols(); j++) {
            for (int i = 0; i < dst.rows(); i++) {
                if (dst.get(i, j)[0] == 255) {
                    colsWidth[j]++;
                }
            }
        }
        Mat matResult = new Mat(dst.rows(), dst.cols(), CvType.CV_8UC1, new Scalar(255, 255, 255));
        // 将每一列按照列像素值大小填充像素宽度
        for (int j = 0; j < matResult.cols(); j++) {
            for (int i = 0; i < colsWidth[j]; i++) {
                matResult.put(matResult.rows() - 1 - i, j, 0);
            }
        }
        return matResult;
    }

    /**
     * 图片切块
     *
     * @param srcImg  传入水平或垂直投影的图片对象Mat
     * @param proType 传入投影Mat对象的 投影方式0：垂直投影图片,竖向切割；1：水平投影图片，横向切割
     * @param rowXY   由于传来的是可能是原始图片的部分切片，要计算切块的实际坐标位置需要给出切片时所在的坐标，所以需要传递横向切片的y坐标或者纵向切片的横坐标
     *                如当proType==0时，传入的是切片的垂直投影，那么切成块后能得出x坐标及块宽高度，但是实际y坐标需要加上原切片的y坐标值，所以rowXY为切片的y坐标点，
     *                同理当proType==1时，rowXY应该为x坐标
     * @return
     */
    public static List<Rect> getBlockRect(Mat srcImg, Integer proType, int rowXY) {
        Imgproc.threshold(srcImg, srcImg, 150, 255, Imgproc.THRESH_BINARY_INV);
        // 注意 countNonZero 方法是获取非0像素（白色像素）数量，所以一般要对图像进行二值化反转
        List<Rect> rectList = new ArrayList<>();
        int size = proType == 0 ? srcImg.cols() : srcImg.rows();
        int[] pixNum = new int[size];
        if (proType == 0) {
            for (int i = 0; i < srcImg.cols(); i++) {
                Mat col = srcImg.col(i);
                pixNum[i] = Core.countNonZero(col) > 1 ? Core.countNonZero(col) : 0;
            }
        } else {// 水平投影只关注行
            for (int i = 0; i < srcImg.rows(); i++) {
                Mat row = srcImg.row(i);
                pixNum[i] = Core.countNonZero(row) > 1 ? Core.countNonZero(row) : 0;
            }
        }
        int startIndex = 0;// 记录进入字符区的索引
        int endIndex;// 记录进入空白区域的索引
        boolean inBlock = false;// 是否遍历到了字符区内
        for (int i = 0; i < size; i++) {
            if (!inBlock && pixNum[i] != 0) {// 进入字符区，上升跳变沿
                inBlock = true;
                startIndex = i;
            } else if (pixNum[i] == 0 && inBlock) {// 进入空白区，下降跳变沿存储
                endIndex = i;
                inBlock = false;
                Rect rect;
                if (proType == 0) {
                    rect = new Rect(startIndex, rowXY, (endIndex - startIndex), srcImg.rows());
                } else {
                    rect = new Rect(rowXY, startIndex, srcImg.cols(), (endIndex - startIndex));
                }
                rectList.add(rect);
            }
        }
        return rectList;
    }

    /**
     * 答题卡识别优化
     */
    @RequestMapping(value = "answerSheet")
    public void answerSheet(HttpServletResponse response, String imageFile, Integer binary_thresh,
                            String blue_red_thresh) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        logger.info("\n 完整答题卡识别");

        String sourcePath = Constants.PATH + imageFile;
        logger.info("url==============" + sourcePath);
        Mat sourceMat = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_ANYCOLOR);
        long t1 = new Date().getTime();
        String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk0.png";
        Imgcodecs.imwrite(destPath, sourceMat);
        logger.info("原答题卡图片======" + destPath);
        // 初始图片灰度图
        Mat sourceMat1 = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_GRAYSCALE);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk1.png";
        Imgcodecs.imwrite(destPath, sourceMat1);
        logger.info("生成灰度图======" + destPath);
        // 先膨胀 后腐蚀算法，开运算消除细小杂点
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 + 1, 2 + 1));
        Imgproc.morphologyEx(sourceMat1, sourceMat1, Imgproc.MORPH_OPEN, element);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk2.png";
        Imgcodecs.imwrite(destPath, sourceMat1);
        logger.info("生成膨胀腐蚀后的图======" + destPath);

        // 切割右侧和底部标记位图片
        Mat rightMark = new Mat(sourceMat1, new Rect(sourceMat1.cols() - 100, 0, 100, sourceMat1.rows()));

        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk3.png";
        Imgcodecs.imwrite(destPath, rightMark);
        logger.info("截取右侧定位点图======" + destPath);
        // 平滑处理消除噪点毛刺等等
        Imgproc.GaussianBlur(rightMark, rightMark, new Size(3, 3), 0);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk4.png";
        Imgcodecs.imwrite(destPath, rightMark);
        logger.info("平滑处理后的右侧定位点图======" + destPath);

        // 根据右侧定位获取水平投影，并获取纵向坐标
        Mat matRight = horizontalProjection(rightMark);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk5.png";
        Imgcodecs.imwrite(destPath, matRight);
        logger.info("右侧水平投影图======" + destPath);
        // 获取y坐标点，返回的是横向条状图集合
        List<Rect> listY = getBlockRect(matRight, 1, 0);

        Mat footMark = new Mat(sourceMat1, new Rect(0, sourceMat1.rows() - 150, sourceMat1.cols(), 50));
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk6.png";
        Imgcodecs.imwrite(destPath, footMark);
        logger.info("截取底部定位点图======" + destPath);

        Imgproc.GaussianBlur(footMark, footMark, new Size(3, 3), 0);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk7.png";
        Imgcodecs.imwrite(destPath, footMark);
        logger.info("平滑处理后的底部定位点图======" + destPath);

        // 根据底部定位获取垂直投影，并获取横向坐标
        Mat matBottom = verticalProjection(footMark);

        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk8.png";
        Imgcodecs.imwrite(destPath, matBottom);
        logger.info("底部垂直投影图======" + destPath);
        // 获取x坐标点，返回的是竖向的柱状图集合
        List<Rect> listX = getBlockRect(matBottom, 0, 0);


        // 高阶处理：增加HSV颜色查找，查找红色像素点
        Mat matRed = findColorByHSV(sourceMat, 156, 180);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk9.png";
        Imgcodecs.imwrite(destPath, matRed);
        logger.info("HSV找出红色像素点======" + destPath);

        new Mat(sourceMat1.rows(), sourceMat1.cols(), sourceMat1.type());
        Mat dstNoRed;
        dstNoRed = OpenCVUtil.dilation(sourceMat1);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk10.png";
        Imgcodecs.imwrite(destPath, dstNoRed);
        logger.info("原灰度图的图片======" + destPath);

        Photo.inpaint(dstNoRed, matRed, dstNoRed, 1, Photo.INPAINT_NS);

        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk11.png";
        Imgcodecs.imwrite(destPath, dstNoRed);
        logger.info("去除红颜色后的图片======" + destPath);

        Mat grayHistogram1 = getGrayHistogram(dstNoRed);
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk12.png";
        Imgcodecs.imwrite(destPath, grayHistogram1);
        logger.info("灰度直方图图片1======" + destPath);

        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk13.png";
        Mat answerMat = dstNoRed.submat(new Rect(41, 895, 278, 133));
        Mat grayHistogram2 = getGrayHistogram(answerMat);
        Imgcodecs.imwrite(destPath, grayHistogram2);
        logger.info("灰度直方图图片2======" + destPath);

        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk14.png";
        Imgproc.threshold(dstNoRed, dstNoRed, binary_thresh, 255, Imgproc.THRESH_BINARY_INV);
        Imgcodecs.imwrite(destPath, dstNoRed);
        logger.info("去除红色基础上进行二值化======" + destPath);
        String redValue = StringUtils.split(blue_red_thresh, ",").get(0);
        String blueValue = StringUtils.split(blue_red_thresh, ",").get(1);
        System.out.println(blueValue + "			" + redValue);

        TreeMap<Integer, String> resultMap = new TreeMap<>();
        StringBuffer resultValue = new StringBuffer();
        for (int no = 0; no < listX.size(); no++) {
            Rect rectX = listX.get(no);
            for (int an = 0; an < listY.size(); an++) {
                Rect rectY = listY.get(an);
                Mat selectDst = new Mat(dstNoRed, new Range(rectY.y, rectY.y + rectY.height), new Range(rectX.x,
                        rectX.x + rectX.width));
                // 本来是在每个区域内进行二值化，后来挪至了14步，整体进行二值化，因此注释掉此处2行

                double p100 = Core.countNonZero(selectDst) * 100 / (selectDst.size().area());
                String que_answer = getQA(no, an);
                Integer que = Integer.valueOf(que_answer.split("_")[0]);
                String answer = que_answer.split("_")[1];
                System.out.println(que_answer + ":			" + p100);

                if (p100 >= Integer.parseInt(blueValue)) {// 蓝色
                    Imgproc.rectangle(sourceMat, new Point(rectX.x, rectY.y), new Point(rectX.x + rectX.width,
                                    rectY.y + rectY.height),
                            new Scalar(255, 0, 0), 2);
                    if (StringUtils.isNotEmpty(resultMap.get(que))) {
                        resultMap.put(que, resultMap.get(que) + "," + answer);
                    } else {
                        resultMap.put(que, answer);
                    }
                } else if (p100 > Integer.parseInt(redValue) && p100 < Integer.parseInt(blueValue)) {// 红色
                    Imgproc.rectangle(sourceMat, new Point(rectX.x, rectY.y), new Point(rectX.x + rectX.width,
                                    rectY.y + rectY.height),
                            new Scalar(0, 0, 255), 2);
                    if (StringUtils.isNotEmpty(resultMap.get(que))) {
                        resultMap.put(que, resultMap.get(que) + ",(" + answer + ")");
                    } else {
                        resultMap.put(que, "(" + answer + ")");
                    }
                } else {// 绿色
                    Imgproc.rectangle(sourceMat, new Point(rectX.x, rectY.y), new Point(rectX.x + rectX.width,
                                    rectY.y + rectY.height),
                            new Scalar(0, 255, 0), 1);
                }
            }
        }

        for (int i = 1; i <= 100; i++) {
            resultValue.append("  ")
                    .append(i)
                    .append("=")
                    .append(StringUtils.isEmpty(resultMap.get(i)) ? "未填写" : resultMap.get(i));
            if (i % 5 == 0) {
                resultValue.append("<br>");
            }
        }
        destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "dtk15.png";
        Imgcodecs.imwrite(destPath, sourceMat);
        logger.info("框选填图区域，绿色为选项，蓝色为填图，红色为临界======" + destPath);
        long t2 = new Date().getTime();
        System.out.println(t2 - t1);

        renderString(response, resultValue.toString());
    }

    /**
     * 绘制灰度直方图用于调整识别区域阈值判断
     *
     * @param img
     * @return
     */
    public Mat getGrayHistogram(Mat img) {
        List<Mat> images = new ArrayList<>();
        images.add(img);
        MatOfInt channels = new MatOfInt(0); // 图像通道数，0表示只有一个通道
        MatOfInt histSize = new MatOfInt(256); // CV_8U类型的图片范围是0~255，共有256个灰度级
        Mat histogramOfGray = new Mat(); // 输出直方图结果，共有256行，行数的相当于对应灰度值，每一行的值相当于该灰度值所占比例
        MatOfFloat histRange = new MatOfFloat(0, 255);
        Imgproc.calcHist(images, channels, new Mat(), histogramOfGray, histSize, histRange, false); // 计算直方图
        Core.MinMaxLocResult minmaxLoc = Core.minMaxLoc(histogramOfGray);
        // 按行归一化

        // 创建画布
        int histImgRows = 600;
        int histImgCols = 1300;
        System.out.println("---------" + histSize.get(0, 0)[0]);
        int colStep = (int) Math.floor(histImgCols / histSize.get(0, 0)[0]);// 舍去小数，不能四舍五入，有可能列宽不够
        Mat histImg = new Mat(histImgRows, histImgCols, CvType.CV_8UC3, new Scalar(255, 255, 255)); // 重新建一张图片，绘制直方图


        int max = (int) minmaxLoc.maxVal;
        System.out.println("--------" + max);
        double bin_u = (double) (histImgRows - 20) / max; // max: 最高条的像素个数，则 bin_u 为单个像素的高度，因为画直方图的时候上移了20像素，要减去
        int keDu = 0;
        for (int i = 1; keDu <= minmaxLoc.maxVal; i++) {
            keDu = i * max / 10;
            // 在图像中显示文本字符串
            Imgproc.putText(histImg, keDu + "", new Point(0, histImgRows - keDu * bin_u), 1, 1, new Scalar(0, 0, 0));
        }


        for (int i = 0; i < histSize.get(0, 0)[0]; i++) { // 画出每一个灰度级分量的比例，注意OpenCV将Mat最左上角的点作为坐标原点
            Imgproc.rectangle(histImg, new Point(colStep * i, histImgRows - 20), new Point(colStep * (i + 1),
                    histImgRows - bin_u * Math.round(histogramOfGray.get(i, 0)[0]) - 20), new Scalar(0, 0, 0), 1, 8, 0);
            keDu = i * 10;
            // 每隔10画一下刻度
            Imgproc.rectangle(histImg, new Point(colStep * keDu, histImgRows - 20), new Point(colStep * (keDu + 1),
                            histImgRows - 20),
                    new Scalar(255, 0, 0), 2, 8, 0);
            Imgproc.putText(histImg, keDu + "", new Point(colStep * keDu, histImgRows - 5), 1, 1, new Scalar(255, 0,
                    0)); //
            // 附上x轴刻度
        }

        return histImg;

    }

    // 获取题号及选项填涂情况
    public String getQA(int no, int an) {
        //返回1A、1B、1C...2A类似这样的返回值
        int first = no + 1 + an / 4 * 20;
        String second = "";
        if (an % 4 == 0) {
            second = "A";
        } else if (an % 4 == 1) {
            second = "B";
        } else if (an % 4 == 2) {
            second = "C";
        } else if (an % 4 == 3) {
            second = "D";
        }
        return first + "_" + second;
    }

}
