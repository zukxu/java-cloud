package com.zukxu.jsoup.pic;

import com.zukxu.jsoup.utils.PicUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author xupu
 * @Description 爬取王者荣耀英雄图
 * @Date 2021-09-22 9:50
 */
public class CrawlWzry {
    public static void main(String[] args) {
        String url = "https://pvp.qq.com/web201605/herolist.shtml";
        try {
            crawlingFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crawlingFromUrl(String url) throws IOException {

        //获取英雄介绍页面文档
        Document document = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
        //获取相对应的英雄节点
        Elements heroUl = document.select("[class=herolist clearfix]");
        Elements heroLis = heroUl.select("li");
        for (Element hero : heroLis) {
            int i = 1;
            //英雄详情页地址
            String details = hero.select("a").attr("href");
            //英雄名称
            String heroName = hero.select("a").text();
            System.out.println("爬取的链接:" + "https://pvp.qq.com/web201605/" + details);
            // 获取英雄介绍页面
            Document heroIntro = Jsoup.connect("https://pvp.qq.com/web201605/" + details).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
            //获取图片位置信息
            String location = heroIntro.select("[class = zk-con1 zk-con]").attr("style");
            //图片基础路径
            String baseUrl = "https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/";
            System.out.println(location);
            // 初始背景
            String initBackImg = location.substring(16, location.length() - 11);

            // 获取英雄代码
            String yxdm = initBackImg.substring(53, 56);

            PicUtils sd = new PicUtils();
            String path = "E:/images/" + heroName;
            String name = heroName + ".jpg";
            sd.downloadPicture("https:" + initBackImg, path, name);
            // 获取皮肤背景节点
            Elements skinBackImg = heroIntro.select("[class = con1-pic]");
            Elements showBox = skinBackImg.select("[class = pic-show-box]");
            Elements pfElement = showBox.select("[class= pic-pf]");
            Elements pfUl = pfElement.select("ul");
            for (Element pfUl2 : pfUl) {
                String pfName = pfUl2.select("[class = pic-pf-list pic-pf-list3]").attr("data-imgname");
                String[] pfArr = pfName.split("\\|");
                int a = pfArr.length;
                for (String pf : pfArr) {
                    pf = pf.substring(0, pf.indexOf("&"));
                    System.out.println(pf);
                    if (i > a) {
                        break;
                    }
                    name = pf + ".jpg";
                    String basePic = yxdm + "/" + yxdm + "-bigskin-" + i + ".jpg";
                    sd.downloadPicture(baseUrl + basePic, path, name);
                    i++;
                }
            }
        }
    }
}
