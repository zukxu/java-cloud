package com.zukxu.cv.test;

import com.zukxu.cv.common.utils.Constants;
import com.zukxu.cv.common.web.BaseController;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OpenCV人脸识别demo
 * 创建者 Songer
 * 创建时间	2018年3月9日
 */
@Controller
@RequestMapping(value = "demo")
public class DemoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@RequestMapping(value = "detectFace")
	public void detectFace(HttpServletResponse response, HttpServletRequest request, String url) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("===========java.library.path:" + System.getProperty("java.library.path"));
		logger.info("\nRunning DetectFaceDemo");
		String resourcePath = getClass().getResource("/lbpcascade_frontalface.xml").getPath().substring(1);
		logger.info("resourcePath============" + resourcePath);

		CascadeClassifier faceDetector = new CascadeClassifier(resourcePath);
		logger.info("url==============" + Constants.PATH + url);
		//TODO this is 2.5.13 version's method now the version is 4.5.3
		//so we need to use new method
		//Mat image = HighGui.imread(Constants.PATH + url);
		Mat image = null;
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		logger.info(String.format("Detected %s faces", faceDetections.toArray().length));
		// Draw a bounding box around each face.
		for(Rect rect : faceDetections.toArray()) {
			//TODO new method
			//Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 255, 0));
		}

		// Save the visualized detection.
		String filename = url.substring(url.lastIndexOf("/"), url.length());
		System.out.printf("Writing %s%n", Constants.PATH + Constants.DEST_IMAGE_PATH + filename);
		//TODO new method
		//Highgui.imwrite(Constants.PATH + Constants.DEST_IMAGE_PATH + filename, image);
		renderString(response, Constants.SUCCESS);
	}

	public static void main(String[] args) {
		System.out.println("Hello, OpenCV");
		// Load the native library.
		System.loadLibrary("opencv_java2413");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("===========java.library.path:" + System.getProperty("java.library.path"));

	}
}
