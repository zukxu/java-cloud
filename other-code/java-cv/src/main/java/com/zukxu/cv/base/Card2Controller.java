package com.zukxu.cv.base;

import com.zukxu.cv.common.utils.Constants;
import com.zukxu.cv.common.web.BaseController;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "card2")
public class Card2Controller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(Card2Controller.class);


	/**
	 * 答题卡识别
	 *
	 * @param response
	 * @param imageFile
	 * @param picNo
	 */
	@RequestMapping(value = "cardMarking")
	public void cardMarking(HttpServletResponse response, String imageFile, Integer picNo) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			long t1 = new Date().getTime();
			String sourceImage = Constants.PATH + imageFile;
			//表格检测，获取到表格内容，是查找识别区域的部分，返回的是校正之后的图像
			Mat mat = markingArea(sourceImage);
			String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "cardResult_3.png";
			Imgcodecs.imwrite(destPath, mat);

			//具体的答题卡识别过程，主要就是答案识别部分了
			String result = cardResult(mat);
			renderString(response, result);
			long t2 = new Date().getTime();
			logger.info("===耗时" + (t2 - t1));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("答题卡识别异常！", e);
		}

	}


	/**
	 * 此方法主要是通过边缘检测凸包，查找识别区域。即客观题的框
	 *
	 * @param path
	 *
	 * @return
	 */
	public static Mat markingArea(String path) {
		Mat source = Imgcodecs.imread(path, Imgcodecs.IMREAD_ANYCOLOR);
		Mat img = new Mat();
		;
		Mat result = source.clone();
		// 彩色转灰度
		Imgproc.cvtColor(source, img, Imgproc.COLOR_BGR2GRAY);
		//此处图像预处理，可以使用方式1也可以使用方式2都可以，自己也可以测试下2种方式的差异
		//			//方式1：通过高斯滤波然后边缘检测膨胀来链接边缘，将轮廓连通便于轮廓识别
		//			// 高斯滤波，降噪
		//			Imgproc.GaussianBlur(img, img, new Size(3,3), 2, 2);
		//			// Canny边缘检测
		//			Imgproc.Canny(img, img, 20, 60, 3, false);
		//			// 膨胀，连接边缘
		//			Imgproc.dilate(img, img, new Mat(), new Point(-1,-1), 3, 1, new Scalar(1));
		//
		//方式2:使用形态学梯度算法，此算法来保留物体的边缘轮廓很有效果
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
		Imgproc.morphologyEx(img, img, Imgproc.MORPH_GRADIENT, element);

		//图像二值化，使用的是OTSU二值化，阈值为170，也可以使用自适用二值化
		//			Imgproc.adaptiveThreshold(img,img, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,  Imgproc
		//			.THRESH_BINARY_INV, 51, 10);
		Imgproc.threshold(img, img, 170, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

		String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "cardResult_1.png";
		Imgcodecs.imwrite(destPath, img);

		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		//轮廓查找，主要就是找最外表格框
		Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

		// 找出轮廓对应凸包的四边形拟合
		List<MatOfPoint> squares = new ArrayList<>();
		List<MatOfPoint> hulls = new ArrayList<>();
		MatOfInt hull = new MatOfInt();
		MatOfPoint2f approx = new MatOfPoint2f();
		approx.convertTo(approx, CvType.CV_32F);

		for(MatOfPoint contour : contours) {
			// 边框的凸包
			Imgproc.convexHull(contour, hull);

			// 用凸包计算出新的轮廓点
			Point[] contourPoints = contour.toArray();
			int[] indices = hull.toArray();
			List<Point> newPoints = new ArrayList<>();
			for(int index : indices) {
				newPoints.add(contourPoints[index]);
			}
			MatOfPoint2f contourHull = new MatOfPoint2f();
			contourHull.fromList(newPoints);

			// 多边形拟合凸包边框(此时的拟合的精度较低)
			Imgproc.approxPolyDP(contourHull, approx, Imgproc.arcLength(contourHull, true) * 0.02, true);

			// 筛选出面积大于某一阈值的，且四边形的各个角度都接近直角的凸四边形
			MatOfPoint approxF1 = new MatOfPoint();
			approx.convertTo(approxF1, CvType.CV_32S);
			//此处是筛选表格框，面积大于40000
			if(approx.rows() == 4 && Math.abs(Imgproc.contourArea(approx)) > 40000 && Imgproc.isContourConvex(approxF1)) {
				double maxCosine = 0;
				for(int j = 2; j < 5; j++) {
					double cosine = Math.abs(getAngle(approxF1.toArray()[j % 4], approxF1.toArray()[j - 2],
													  approxF1.toArray()[j - 1]));
					maxCosine = Math.max(maxCosine, cosine);
				}
				// 考虑到图片倾斜等情况，角度大概72度
				if(maxCosine < 0.3) {
					MatOfPoint tmp = new MatOfPoint();
					contourHull.convertTo(tmp, CvType.CV_32S);
					squares.add(approxF1);
					hulls.add(tmp);
				}
			}
		}


		// 找出外接矩形最大的四边形
		int index = findLargestSquare(squares);
		MatOfPoint largest_square = squares.get(index);
		if(largest_square.rows() == 0 || largest_square.cols() == 0)
			return result;

		// 找到这个最大的四边形对应的凸边框，再次进行多边形拟合，此次精度较高，拟合的结果可能是大于4条边的多边形
		MatOfPoint contourHull = hulls.get(index);

		MatOfPoint2f tmp = new MatOfPoint2f();
		contourHull.convertTo(tmp, CvType.CV_32F);
		Imgproc.approxPolyDP(tmp, approx, 3, true);
		List<Point> newPointList = new ArrayList<>();
		double maxL = Imgproc.arcLength(approx, true) * 0.02;

		// 找到高精度拟合时得到的顶点中 距离小于低精度拟合得到的四个顶点maxL的顶点，排除部分顶点的干扰
		List<Point> points = largest_square.toList();
		for(Point p : approx.toArray()) {
			if(!(getSpacePointToPoint(p, points.get(0)) > maxL && getSpacePointToPoint(p, points.get(1)) > maxL && getSpacePointToPoint(p, points.get(2)) > maxL && getSpacePointToPoint(p, points.get(3)) > maxL)) {
				newPointList.add(p);
			}
		}

		// 找到剩余顶点连线中，边长大于 2 * maxL的四条边作为四边形物体的四条边
		List<double[]> lines = new ArrayList<>();
		for(int i = 0; i < newPointList.size(); i++) {
			Point p1 = newPointList.get(i);
			Point p2 = newPointList.get((i + 1) % newPointList.size());
			if(getSpacePointToPoint(p1, p2) > 2 * maxL) {
				lines.add(new double[]{ p1.x, p1.y, p2.x, p2.y });
				logger.info("p1x:" + p1.x + "  p1y:" + p1.y + "  p2x:" + p2.x + "  p2y:" + p2.y);
				//画出4条边线，真正识别过程中这些都是可以注释掉的，只是为了方便观察
				Imgproc.line(source, new Point(p1.x, p1.y), new Point(p2.x, p2.y), new Scalar(255, 0, 0), 4);
			}
		}

		destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "cardResult_2.png";
		Imgcodecs.imwrite(destPath, source);

		// 计算出这四条边中 相邻两条边的交点，即物体的四个顶点
		List<Point> corners = new ArrayList<>();
		for(int i = 0; i < lines.size(); i++) {
			Point corner = computeIntersect(lines.get(i), lines.get((i + 1) % lines.size()));
			corners.add(corner);
		}

		// 对顶点顺时针排序
		sortCorners(corners);

		// 计算目标图像的尺寸
		Point p0 = corners.get(0);
		Point p1 = corners.get(1);
		Point p2 = corners.get(2);
		Point p3 = corners.get(3);
		logger.info("   " + p0.x + "   " + p0.y);
		logger.info("   " + p1.x + "   " + p1.y);
		logger.info("   " + p2.x + "   " + p2.y);
		logger.info("   " + p3.x + "   " + p3.y);
		double space0 = getSpacePointToPoint(p0, p1);
		double space1 = getSpacePointToPoint(p1, p2);
		double space2 = getSpacePointToPoint(p2, p3);
		double space3 = getSpacePointToPoint(p3, p0);
		// 使用最宽和最长的边作为进行图像矫正目标图像的长宽
		double imgWidth = Math.max(space1, space3);
		double imgHeight = Math.max(space0, space2);
		logger.info("imgWidth:" + imgWidth + "    imgHeight:" + imgHeight);
		// 如果提取出的图片宽小于高，则旋转90度，因为示例中的矩形框是宽>高的，如果宽小于高应该是图片旋转了
		if(imgWidth > imgHeight) {
			logger.info("----in");
			double temp = imgWidth;
			imgWidth = imgHeight;
			imgHeight = temp;
			Point tempPoint = p0.clone();
			p0 = p1.clone();
			p1 = p2.clone();
			p2 = p3.clone();
			p3 = tempPoint.clone();
		}

		Mat quad = Mat.zeros((int) imgHeight, (int) imgWidth, CvType.CV_8UC3);

		MatOfPoint2f cornerMat = new MatOfPoint2f(p0, p1, p2, p3);

		//quadMat目标图像的点设置，以之前取出的最长的长宽作为新图像的长宽，创建一个图层
		MatOfPoint2f quadMat = new MatOfPoint2f(new Point(0, 0), new Point(imgWidth, 0), new Point(imgWidth,
																								   imgHeight),
												new Point(0, imgHeight));

		// 提取图像，使用warpPerspective做图像的透视变换
		Mat transMtx = Imgproc.getPerspectiveTransform(cornerMat, quadMat);
		Imgproc.warpPerspective(result, quad, transMtx, quad.size());
		return quad;

	}

	/**
	 * 根据三个点计算中间那个点的夹角   pt1 pt0 pt2
	 */
	private static double getAngle(Point pt1, Point pt2, Point pt0) {
		double dx1 = pt1.x - pt0.x;
		double dy1 = pt1.y - pt0.y;
		double dx2 = pt2.x - pt0.x;
		double dy2 = pt2.y - pt0.y;
		return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
	}

	/**
	 * 找到最大的正方形轮廓
	 */
	private static int findLargestSquare(List<MatOfPoint> squares) {
		if(squares.size() == 0)
			return -1;
		int max_width = 0;
		int max_height = 0;
		int max_square_idx = 0;
		int currentIndex = 0;
		for(MatOfPoint square : squares) {
			Rect rectangle = Imgproc.boundingRect(square);
			if(rectangle.width >= max_width && rectangle.height >= max_height) {
				max_width = rectangle.width;
				max_height = rectangle.height;
				max_square_idx = currentIndex;
			}
			currentIndex++;
		}
		return max_square_idx;
	}

	/**
	 * 点到点的距离
	 */
	private static double getSpacePointToPoint(Point p1, Point p2) {
		double a = p1.x - p2.x;
		double b = p1.y - p2.y;
		return Math.sqrt(a * a + b * b);
	}

	/**
	 * 两直线的交点
	 */
	private static Point computeIntersect(double[] a, double[] b) {
		if(a.length != 4 || b.length != 4)
			throw new ClassFormatError();
		double x1 = a[0], y1 = a[1], x2 = a[2], y2 = a[3], x3 = b[0], y3 = b[1], x4 = b[2], y4 = b[3];
		double d = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
		if(d != 0) {
			Point pt = new Point();
			pt.x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
			pt.y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
			return pt;
		} else
			return new Point(-1, -1);
	}

	/**
	 * 对多个点按顺时针排序
	 */
	private static void sortCorners(List<Point> corners) {
		if(corners.size() == 0)
			return;
		Point p1 = corners.get(0);
		int index = 0;
		for(int i = 1; i < corners.size(); i++) {
			Point point = corners.get(i);
			if(p1.x > point.x) {
				p1 = point;
				index = i;
			}
		}

		corners.set(index, corners.get(0));
		corners.set(0, p1);

		Point lp = corners.get(0);
		for(int i = 1; i < corners.size(); i++) {
			for(int j = i + 1; j < corners.size(); j++) {
				Point point1 = corners.get(i);
				Point point2 = corners.get(j);
				if((point1.y - lp.y * 1.0) / (point1.x - lp.x) > (point2.y - lp.y * 1.0) / (point2.x - lp.x)) {
					Point temp = point1.clone();
					corners.set(i, corners.get(j));
					corners.set(j, temp);
				}
			}
		}
	}


	/**
	 * 答题卡识别，增加注释
	 *
	 * @param mat
	 *
	 * @return
	 */
	public String cardResult(Mat mat) {
		//设置剪切的边距，目的是裁剪表格边框，防止边框影响轮廓查找，这里设置为20像素
		int cutSize = 20;
		Mat img_cut = mat.submat(cutSize, mat.rows() - cutSize, cutSize, mat.cols() - cutSize);
		new Mat();
		Mat img_gray = img_cut.clone();
		//图像灰度化
		Imgproc.cvtColor(img_cut, img_gray, Imgproc.COLOR_BGR2GRAY);
		//图像二值化，注意是反向二值化以及OTSU算法
		Imgproc.threshold(img_gray, img_gray, 170, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
		Mat temp = img_gray.clone();
		//此处使用的是方式2，形态学梯度算法保留填图选项的边框
		//		 	//方式1：通过高斯滤波然后边缘检测膨胀来链接边缘，将轮廓连通便于轮廓识别
		//			// 高斯滤波，降噪
		//			Imgproc.GaussianBlur(temp, temp, new Size(3,3), 2, 2);
		//			// Canny边缘检测
		//			Imgproc.Canny(temp, temp, 20, 60, 3, false);
		//			// 膨胀，连接边缘
		//			Imgproc.dilate(temp, temp, new Mat(), new Point(-1,-1), 3, 1, new Scalar(1));

		//方式2:使用形态学梯度算法，此算法来保留物体的边缘轮廓很有效果
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
		Imgproc.morphologyEx(temp, temp, Imgproc.MORPH_GRADIENT, element);
		Imgproc.threshold(temp, temp, 170, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "cardResult_4.png";
		Imgcodecs.imwrite(destPath, temp);

		//按比例截取，此处是根据原答题卡的各列的比例截取成4列，学号那列还要横向分隔一下，上半部分是学号下半部分是答题卡
		Mat cut1 = temp.submat(0, temp.rows(), 0, (int) (0.275 * temp.cols()));
		Mat cut_gray1 = img_gray.submat(0, img_gray.rows(), 0, (int) (0.275 * img_gray.cols()));

		Mat cut2 = temp.submat(0, temp.rows(), (int) (0.275 * temp.cols()), (int) (0.518 * temp.cols()));
		Mat cut_gray2 = img_gray.submat(0, img_gray.rows(), (int) (0.275 * img_gray.cols()),
										(int) (0.518 * img_gray.cols()));

		Mat cut3 = temp.submat(0, temp.rows(), (int) (0.518 * temp.cols()), (int) (0.743 * temp.cols()));
		Mat cut_gray3 = img_gray.submat(0, img_gray.rows(), (int) (0.518 * img_gray.cols()),
										(int) (0.743 * img_gray.cols()));

		Mat cut4 = temp.submat((int) (0.387 * temp.rows()), temp.rows(), (int) (0.743 * temp.cols()), temp.cols());
		Mat cut_gray4 = img_gray.submat((int) (0.387 * img_gray.rows()), img_gray.rows(),
										(int) (0.743 * img_gray.cols()), img_gray.cols());
		//学号
		Mat cut5 = temp.submat(0, (int) (0.387 * temp.rows()), (int) (0.743 * temp.cols()), temp.cols());
		Mat cut_gray5 = img_gray.submat(0, (int) (0.387 * img_gray.rows()), (int) (0.743 * img_gray.cols()),
										img_gray.cols());


		List<String> resultList = new ArrayList<String>();
		//按列处理
		List<String> list1 = processByCol(cut1, cut_gray1, img_cut, 5);
		List<String> list2 = processByCol(cut2, cut_gray2, img_cut, 5);
		List<String> list3 = processByCol(cut3, cut_gray3, img_cut, 4);
		List<String> list4 = processByCol(cut4, cut_gray4, img_cut, 4);
		//学号单独处理
		List<String> list5 = processByCol(cut5, cut_gray5, img_cut, 4);
		resultList.addAll(list1);
		resultList.addAll(list2);
		resultList.addAll(list3);
		resultList.addAll(list4);
		String studentNo = getStudentNo(list5);
		logger.info("学生学号为：" + studentNo);
		StringBuffer result = new StringBuffer("学生学号为：" + studentNo);
		for(String s : resultList) {
			result.append(s);
			logger.info(s);
		}
		return result.toString();

	}


	/**
	 * 根据列单独处理
	 *
	 * @param cut1
	 * @param cut_gray
	 * @param temp
	 * @param answerCols
	 *
	 * @return
	 */
	private static List<String> processByCol(Mat cut1, Mat cut_gray, Mat temp, int answerCols) {
		List<String> result = new ArrayList<>();
		List<MatOfPoint> contours = new ArrayList<>();
		List<MatOfPoint> answerList = new ArrayList<>();
		Mat hierarchy = new Mat();
		//进行轮廓查找，注意因为该答题卡特征是闭合填图区域，所以用这种方式，如果是非闭合填涂区域，则不适用
		Imgproc.findContours(cut1.clone(), contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		for(MatOfPoint mop : contours) {
			//每个轮廓给出外包矩形，便于操作
			Rect rect = Imgproc.boundingRect(mop);
			//一个填图区域大概占整个表格的w:80/2733 h:0/2804,，所以排除掉太小的轮廓和过大的轮廓
			//此处是为了排除杂点，较小或较大的轮廓都是非填图选项，可以排除，可以按实际情况灵活变动限制条件，最好输出出轮廓便于观察
			if(rect.width > (temp.width() * 80 / 2693 - 20) && rect.height > (temp.height() * 80 / 2764 - 20) && rect.width < temp.width() * 0.05 && rect.height < temp.height() * 0.05) {
				answerList.add(mop);

			}
		}

		//按照y坐标升序排列
		answerList.sort((o1, o2) -> {
			Rect rect1 = Imgproc.boundingRect(o1);
			Rect rect2 = Imgproc.boundingRect(o2);
			return Integer.compare(rect1.y, rect2.y);
		});
		//每4/5个一组组成新list，并按x坐标排序
		//该方式依赖于预处理后的图片没有干扰轮廓。如果图像处理不佳，干扰项没排除，那么此处的分组就会错乱。
		//提供一个优化思路：对于闭合填涂区域的可以使用水平垂直投影进行坐标定位点的判定。
		int queNo = 1;
		int totoSize = answerList.size();
		for(int i = 0; i < totoSize; i += answerCols) {
			int toIndex = i + answerCols;
			if(toIndex > totoSize) {
				toIndex = totoSize - 1;
			}
			List<MatOfPoint> newList = answerList.subList(i, toIndex);
			//按照x坐标升序排列
			newList.sort((o1, o2) -> {
				Rect rect1 = Imgproc.boundingRect(o1);
				Rect rect2 = Imgproc.boundingRect(o2);
				return Integer.compare(rect1.x, rect2.x);
			});
			StringBuilder resultChoose = new StringBuilder();
			for(int j = 0; j < newList.size(); j++) {
				Imgproc.drawContours(cut_gray, newList, j, new Scalar(170), 2);
				//掩模提取出轮廓
				Mat mask = Mat.zeros(cut_gray.size(), CvType.CV_8UC1);
				//绘制轮廓便于观察
				Imgproc.drawContours(mask, newList, j, new Scalar(255), -1);
				Mat dst = new Mat();
				Core.bitwise_and(cut_gray, mask, dst);
				//获取填涂百分比,填涂区域的二值化后取出非0点/掩模的轮廓面积
				double p100 = Core.countNonZero(dst) * 100.0 / Core.countNonZero(mask);
				String anNo = index2ColName(j);
				//认为非0像素超过80%的算是填涂
				if(p100 > 80) {
					resultChoose.append(anNo);
					logger.info(p100 + " 第" + queNo + "行:选项（" + anNo + "）填涂");
				} else {
					logger.info(p100 + " 第" + queNo + "行:选项（" + anNo + "）未填涂");
				}
				if(i == 0 && j == 0) {//输出一下第一个掩模
					String destPath = Constants.PATH + Constants.DEST_IMAGE_PATH + "cardResult_5.png";
					Imgcodecs.imwrite(destPath, dst);
				}
			}
			result.add(resultChoose.toString());
			queNo++;
		}

		return result;
	}

	/**
	 * 编号转答案0-A 1-B
	 */
	public static String index2ColName(int index) {
		if(index < 0) {
			return null;
		}
		int num = 65;// A的Unicode码
		StringBuilder colName = new StringBuilder();
		do {
			if(colName.length() > 0) {
				index--;
			}
			int remainder = index % 26;
			colName.insert(0, ((char) (remainder + num)));
			index = (int) ((index - remainder) / 26);
		} while(index > 0);
		return colName.toString();
	}

	/**
	 * 根据表元的列名转换为列号
	 *
	 * @param colName
	 *
	 * @return
	 */
	public static int colName2Index(String colName) {
		int index = -1;
		int num = 65;// A的Unicode码
		int length = colName.length();
		for(int i = 0; i < length; i++) {
			char c = colName.charAt(i);
			if(Character.isDigit(c))
				break;// 确定指定的char值是否为数字
			index = (index + 1) * 26 + (int) c - num;
		}
		return index;
	}


	/**
	 * * 根据返回的结果集转换为学号
	 * 因为学号部分的处理跟答案一样是一横行进行处理的，同时返回的是ABCDE等选项结果
	 * 转换公式为：学生学号=遍历list的index值*（A=1000,B=100,C=10,D=1）相加
	 *
	 * @param resultList
	 *
	 * @return
	 */
	public static String getStudentNo(List<String> resultList) {
		int studentNo = 0;
		for(int i = 0; i < resultList.size(); i++) {
			String result = resultList.get(i);
			if(result.contains("A")) {
				studentNo += 1000 * i;
			}
			if(result.contains("B")) {
				studentNo += 100 * i;
			}
			if(result.contains("C")) {
				studentNo += 10 * i;
			}
			if(result.contains("D")) {
				studentNo += i;
			}
		}
		NumberFormat formatter = new DecimalFormat("0000");
		return formatter.format(studentNo);
	}

}
