package com.zukxu.img;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xupu
 * @Description 图片相关操作API
 * @Date 2021-09-17 11:16
 */
public class ImgAPI {

    /**
     * JAVA 图像等比缩放
     *
     * @param srcImageFile  缩放的图片
     * @param destImageFile 缩放后的图片
     * @param scale         缩放比例
     * @return bool
     */
    public static boolean scale(File srcImageFile, File destImageFile, float scale) {
        try {
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            //获取缩放后的宽高
            int width = (int) (read.getWidth() * scale);
            int height = (int) (read.getHeight() * scale);
            //调用缩放方法获取缩放后的图片
            Image img = read.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            //创建一个新的缓存图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D graphics = image.createGraphics();
            //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
            graphics.drawImage(img, 0, 0, null);
            //一定要释放资源
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(image, formatName, destImageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * JAVA裁剪图片
     *
     * @param srcImageFile  需要裁剪的图片
     * @param x             裁剪时x的坐标（左上角）
     * @param y             裁剪时y的坐标（左上角）
     * @param width         裁剪后的图片宽度
     * @param height        裁剪后的图片高度
     * @param destImageFile 裁剪后的图片
     * @return bool
     */
    public static boolean cut(File srcImageFile, int x, int y, int width, int height, File destImageFile) {
        try {
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            //调用裁剪方法
            BufferedImage image = read.getSubimage(x, y, width, height);
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(image, formatName, destImageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * JAVA添加文字水印
     *
     * @param srcImageFile  目标图片
     * @param destImageFile 结果图片
     * @param text          文字内容
     * @return bool
     */
    public static boolean watermarkText(File srcImageFile, File destImageFile, String text) {
        try {
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            int x = read.getWidth() / 2, y = read.getHeight() / 2;
            Graphics2D graphics = read.createGraphics();
            // 设置“抗锯齿”的属性
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 设置字体类型和大小
            graphics.setFont(new Font("新宋体", Font.PLAIN, 20));
            // 设置颜色
            graphics.setColor(new Color(193, 17, 17));
            // 添加文字
            graphics.drawString(text, x, y);
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(read, formatName, destImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * JAVA添加图片水印
     *
     * @param srcImageFile  目标图片
     * @param destImageFile 结果图片
     * @param waterImage    水印图片
     * @param x             水印x坐标
     * @param y             水印y坐标
     * @return boolean
     */
    public static boolean watermarkImage(File srcImageFile, File destImageFile, File waterImage, int x, int y) {
        try {
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            BufferedImage image = ImageIO.read(waterImage);
            //获取画布
            Graphics2D graphics = read.createGraphics();
            //设置透明度为0.5
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
            //添加水印
            graphics.drawImage(image, x, y, null);
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(read, formatName, destImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /** 给jpg添加文字 */
    public static boolean createStringMark(String origin, String text, String outPath) {
        ImageIcon imgIcon = new ImageIcon(origin);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
        System.out.println(width);
        System.out.println(height);
        System.out.println(theImg);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();

        Color mycolor = Color.red;
        g.setColor(mycolor);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);
        g.setFont(new Font("宋体", Font.PLAIN, 50)); //字体、字型、字号
        g.drawString(text, width / 2, height / 2); //画文字
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outPath); //先用一个特定的输出文件名
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(100, true);  //
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
