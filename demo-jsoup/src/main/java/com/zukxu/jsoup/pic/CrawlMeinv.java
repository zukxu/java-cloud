package com.zukxu.jsoup.pic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.zukxu.common.utils.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xupu
 * @Description 爬取美女图
 * @Date 2021-09-22 10:02
 */
public class CrawlMeinv {
    private static final Logger log = LoggerFactory.getLogger(CrawlMeinv.class);
    /**
     * 获取CPU个数
     */
    private static int cpu = Runtime.getRuntime().availableProcessors();
    /**
     * 创建线程池  调整队列数 拒绝服务
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu + 1, 10l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

    public static void main(String[] args) {
        String url = "https://www.tupianzj.com";
        try {
            crawlingMeinv(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crawlingMeinv(String baseUrl) throws IOException {
        log.info("系统CPU核数：{}", cpu);
        //正则 匹配页码
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Connection connect = Jsoup.connect(baseUrl + "/meinv/mm/meizitu/");
        Document document = connect.get();
        //获取本栏目所有妹子套图节点
        Elements elements = document.body().getElementsByClass("d1").select("li");
        for (Element img : elements) {
            Runnable task = () -> {
                try {
                    //获取套图地址
                    String href = img.child(0).attr("href");
                    Connection subConnect = Jsoup.connect(baseUrl + href)
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")
                            .timeout(8000);
                    Document subDocument = subConnect.get();
                    //获取套图标题用于创建目录
                    String title = subDocument.body().getElementsByTag("h1").eq(1).html();
                    System.out.println("开始下载：" + title);
                    //获取套图图片数量
                    String txt = subDocument.body().getElementsByClass("pages").select("li").eq(0).html();
                    Matcher m = p.matcher(txt);
                    if (StringUtils.isNotBlank(m.replaceAll("").trim())) {
                        int pageNo = Integer.parseInt(m.replaceAll("").trim());
                        for (int i = 0; i < pageNo; i++) {
                            String url = baseUrl + href;
                            if (i != 0) {
                                int page = i + 1;
                                url = url.replace(".html", "_" + page + ".html");
                            }
                            subConnect = Jsoup.connect(url);
                            subDocument = subConnect.get();
                            String src = subDocument.getElementById("bigpicimg").attr("src");
                            // 下载妹子
                            HttpUtil.downloadFile(src, FileUtil.mkdir("e:/妹子图/" + title + "/"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(task);
        }
    }
}
