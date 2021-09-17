package com.zukxu.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author xupu
 * @Description 字符串生成图片
 * @Date 2021-09-17 11:14
 */
public class CharToImg {
    private static final String FILE_PATH = "E:/temp/";
    /**
     * 定义图像大小
     */
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    //初始化存储路径
    static {
        File file = new File(FILE_PATH);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void main(String[] args) throws IOException {
		/*String[] str = {"未分类", "学习教程", "书籍网站", "娱乐网站", "开发相关", "壁纸美图", "插件模板", "开发教程", "福利网站", "导航网站", "黑客网站", "项目收集", "资源收集", "工具软件", "源码学习"};
		for (String text : str) {
			StrToImg.generateImg(text);
		}*/
        CharToImg.generateImg("默认");
        System.out.println("完成");
    }

    /**
     * 初始化图片
     *
     * @param text 输出内容
     * @throws IOException IO
     */
    public static void generateImg(String text) throws IOException {
        //创建一个指定宽高的图片内存对象 画布
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //初始化画笔
        Graphics2D g2 = bi.createGraphics();
        // 设置“抗锯齿”的属性
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //设置背景颜色
        g2.setBackground(getRandomColor());
        //擦除区域，显示背景色
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.setPaint(Color.WHITE);
        //设置默认字体样式
        g2.setFont(new Font("新宋体", Font.BOLD, 32));
        //选择绘图
        chooseMode(g2, text);
        //圆角处理
        BufferedImage roundedImage = makeRoundedCorner(bi, 99);
        //释放资源
        g2.dispose();
        //将图片输出到文件
        //文件名生成
        long timeMillis = System.currentTimeMillis();
        String filename = FILE_PATH + text + timeMillis + ".jpg";
        File file = new File(filename);
        //输出
        ImageIO.write(roundedImage, "png", file);
    }

    private static void drawOne(Graphics2D g2, String text) {
        text = text.substring(0, 1);
        if (judgeCh(text)) {
            g2.setFont(new Font("华文行楷", Font.BOLD, 56));
            g2.drawString(text, 20, 64);
            return;
        }
        //英文
        g2.setFont(new Font("Snap ITC", Font.BOLD, 64));
        g2.drawString(text.toUpperCase(), 24, 72);
    }

    private static void drawTwo(Graphics2D g2, String text) {
        text = text.substring(0, 2);
        String firstLetter = text.substring(0, 1);
        String secondLetter = text.substring(1, 2);
        //两个中文 如 自由
        if (judgeCh(firstLetter)) {
            if (judgeCh(secondLetter)) {
                g2.drawString(text, 16, 60);
            } else {
                g2.drawString(firstLetter, 24, 56);
                g2.setFont(new Font("新宋体", Font.BOLD, 30));
                g2.drawString(secondLetter, 56, 64);
            }
        } else {
            if (!judgeCh(secondLetter)) {
                //两个英文 如 UK
                g2.setFont(new Font("Snap ITC", Font.BOLD, 36));
                g2.drawString(text.toUpperCase(), 20, 64);
            } else {
                //首英次中 如 Z由
                g2.setFont(new Font("新宋体", Font.BOLD, 40));
                g2.drawString(firstLetter, 24, 56);
                g2.setFont(new Font("新宋体", Font.BOLD, 32));
                g2.drawString(secondLetter, 56, 64);
            }
        }
    }

    private static void drawPer(Graphics2D g2, String text) {
        char[] charArray = text.toCharArray();
        //初始化第一个字符位置
        int marginLeft, marginTop;
        for (int i = 0; i < charArray.length; i++) {
            marginLeft = 20;
            marginTop = 40;
            g2.setFont(new Font("新宋体", Font.BOLD, 30));

            if (!judgeCh(String.valueOf(charArray[i]))) {
                g2.setFont(new Font("Snap ITC", Font.BOLD, 36));
            }
            //下标为0 2字符左边距都是20，区别在于上边距
            if (i % 2 == 0) {
                marginTop = (i / 2) * 40 + 40;
            } else {
                //下标为1 3字符左边距都是50，区别在于上边距
                marginLeft = 50;
                if (i % 3 == 0) {
                    marginTop = (i / 3) * 40 + 40;
                }
            }
            g2.drawString(String.valueOf(charArray[i]), marginLeft, marginTop);
            System.out.printf("%d,%d,%d", i, marginLeft, marginTop);
            System.out.println();
        }
    }

    /**
     * 根据字数选择绘图模式
     *
     * @param g2   g2
     * @param text 文本
     */
    private static void chooseMode(Graphics2D g2, String text) {
        int len = text.length();
        switch (len) {
            case 1:
                drawOne(g2, text);
                break;
            case 2:
                drawTwo(g2, text);
                break;
            default:
                drawPer(g2, text);
        }
    }

    /**
     * 获得随机颜色
     *
     * @return Color
     */
    private static Color getRandomColor() {
        String[] beautifulColors = new String[]{"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52", "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154", "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7", "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29", "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141", "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178", "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84", "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128", "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46", "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113", "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124", "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224", "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202", "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67", "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186", "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    /**
     * 图片做圆角处理
     *
     * @param image        图片
     * @param cornerRadius 角度
     * @return 图片
     */
    private static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    /**
     * 判断中文
     *
     * @param str 文本
     * @return bool
     */
    private static boolean judgeCh(String str) {
        String ch = "[\\u4e00-\\u9fa5]+";
        Pattern chp = Pattern.compile(ch);
        return chp.matcher(str).find();

    }
}
