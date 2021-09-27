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
import java.util.Date;


@Controller
@RequestMapping(value = "real")
public class RealTestController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RealTestController.class);

	/**
	 * 图像矫正透视变换
	 *
	 * @param response
	 * @param imageFile
	 * @param markType
	 */
	@RequestMapping(value = "test")
	public void rectification(HttpServletResponse response, String imageFile, Integer markType) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		logger.info("\n 图像矫正透视变换");

		String sourcePath = Constants.PATH + imageFile;
		logger.info("url==============" + sourcePath);
		// 加载为灰度图显示
		Mat source1 = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_ANYCOLOR);
		Mat source2 = Imgcodecs.imread(sourcePath, Imgcodecs.IMREAD_GRAYSCALE);
		long time1 = new Date().getTime();
		Point anchor01 = new Point();
		Point anchor02 = new Point();
		Point anchor03 = new Point();
		Point anchor04 = new Point();
		if(markType == 1) {// 模板匹配识别定位点
			String matchPath = Constants.PATH + Constants.SOURCE_IMAGE_PATH + "z1_temp.png";
			Mat matTmp = Imgcodecs.imread(matchPath, Imgcodecs.IMREAD_ANYCOLOR);
			fetchAnchorPoints1(source1, matTmp, anchor01, anchor02, anchor03, anchor04);
		}

		MatOfPoint mop = new MatOfPoint(anchor01, anchor02, anchor03, anchor04);
		MatOfPoint2f mat2f = new MatOfPoint2f();
		MatOfPoint2f refMat2f = new MatOfPoint2f();
		mop.convertTo(mat2f, CvType.CV_32FC1);

		Point point11 = new Point(99, 200);
		Point point12 = new Point(2317, 200);
		Point point13 = new Point(99, 3300);
		Point point14 = new Point(2317, 3300);

		Mat dst_vertices = new MatOfPoint(point11, point12, point13, point14);
		dst_vertices.convertTo(refMat2f, CvType.CV_32FC1);
		Mat warpMatrix = Imgproc.getPerspectiveTransform(mat2f, refMat2f);

		Mat dst = new Mat(source1.rows(), source1.cols(), source1.type());
		System.out.println(source1.rows() + " " + source1.cols());
		Imgproc.warpPerspective(source1, dst, warpMatrix, dst.size(), Imgproc.INTER_LINEAR, 0, new Scalar(255, 255,
																										  255));
		long time2 = new Date().getTime();
		logger.info("耗时(ms)：==============" + (time2 - time1));
		try {
			byte[] imgebyte = OpenCVUtil.covertMat2Byte1(dst);
			renderImage(response, imgebyte);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得锚点(定位点)
	 * 方法1，通过模板匹配圆心，应该换成正方形也可以，之前模板匹配不行是因为模板图形不是最小的
	 *
	 * @param grayImage
	 * @param matTmp
	 * @param anchor01
	 * @param anchor02
	 * @param anchor03
	 * @param anchor04
	 */
	public static void fetchAnchorPoints1(Mat grayImage, Mat matTmp, Point anchor01, Point anchor02, Point anchor03,
										  Point anchor04) {
		long t1 = new Date().getTime();
		Mat imageMatch = new Mat();
		Point maxLoc01, maxLoc02, maxLoc03, maxLoc04;
		int srcRows = grayImage.rows();
		int srcCols = grayImage.cols();
		Mat src01 = grayImage.submat(new Rect(0, 0, srcCols / 2, srcRows / 2));
		Mat src02 = grayImage.submat(new Rect(srcCols / 2, 0, srcCols / 2, srcRows / 2));
		Mat src03 = grayImage.submat(new Rect(0, srcRows / 2, srcCols / 2, srcRows / 2));
		Mat src04 = grayImage.submat(new Rect(srcCols / 2, srcRows / 2, srcCols / 2, srcRows / 2));

		Imgproc.matchTemplate(matTmp, src01, imageMatch, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult minmaxLoc1 = Core.minMaxLoc(imageMatch);

		maxLoc01 = minmaxLoc1.maxLoc;
		anchor01.x = maxLoc01.x;
		anchor01.y = maxLoc01.y;

		long t2 = new Date().getTime();
		System.out.println("第1坐标耗时：" + (t2 - t1));
		Imgproc.matchTemplate(matTmp, src02, imageMatch, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult minmaxLoc2 = Core.minMaxLoc(imageMatch);

		maxLoc02 = minmaxLoc2.maxLoc;
		anchor02.x = maxLoc02.x + srcCols / 2.0;
		anchor02.y = maxLoc02.y;

		long t3 = new Date().getTime();
		System.out.println("第2坐标耗时：" + (t3 - t2));
		Imgproc.matchTemplate(matTmp, src03, imageMatch, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult minmaxLoc3 = Core.minMaxLoc(imageMatch);

		maxLoc03 = minmaxLoc3.maxLoc;
		anchor03.x = maxLoc03.x;
		anchor03.y = maxLoc03.y + srcRows / 2.0;

		long t4 = new Date().getTime();
		System.out.println("第3坐标耗时：" + (t4 - t3));
		Imgproc.matchTemplate(matTmp, src04, imageMatch, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult minmaxLoc4 = Core.minMaxLoc(imageMatch);

		maxLoc04 = minmaxLoc4.maxLoc;
		anchor04.x = maxLoc04.x + srcCols / 2.0;
		anchor04.y = maxLoc04.y + srcRows / 2.0;
		long t5 = new Date().getTime();
		System.out.println("第4坐标耗时：" + (t5 - t4));
	}


}
