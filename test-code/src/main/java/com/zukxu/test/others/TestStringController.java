package com.zukxu.test.others;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * test
 * </p>
 *
 * @author xupu
 * @since 2022/6/16 11:07:19
 */
@RequestMapping("/str")
@RestController
public class TestStringController {

    private Map<String, String> sysCodeMap = new HashMap<>();

    @PostConstruct
    private void init() {
        System.out.println("初始化……");
        String strArr = "[{\"name\":\"内蒙古\",\"code\":\"471\"},{\"name\":\"北京\",\"code\":\"100\"},{\"name\":\"天津\",\"code\":\"220\"},{\"name\":\"山东\",\"code\":\"531\"},{\"name\":\"河北\",\"code\":\"311\"},{\"name\":\"山西\",\"code\":\"351\"},{\"name\":\"安徽\",\"code\":\"551\"},{\"name\":\"上海\",\"code\":\"210\"},{\"name\":\"江苏\",\"code\":\"250\"},{\"name\":\"浙江\",\"code\":\"571\"},{\"name\":\"福建\",\"code\":\"591\"},{\"name\":\"海南\",\"code\":\"898\"},{\"name\":\"广东\",\"code\":\"200\"},{\"name\":\"广西\",\"code\":\"771\"},{\"name\":\"青海\",\"code\":\"971\"},{\"name\":\"湖北\",\"code\":\"270\"},{\"name\":\"湖南\",\"code\":\"731\"},{\"name\":\"江西\",\"code\":\"791\"},{\"name\":\"河南\",\"code\":\"371\"},{\"name\":\"西藏\",\"code\":\"891\"},{\"name\":\"四川\",\"code\":\"280\"},{\"name\":\"重庆\",\"code\":\"230\"},{\"name\":\"陕西\",\"code\":\"290\"},{\"name\":\"贵州\",\"code\":\"851\"},{\"name\":\"云南\",\"code\":\"871\"},{\"name\":\"甘肃\",\"code\":\"931\"},{\"name\":\"宁夏\",\"code\":\"951\"},{\"name\":\"新疆\",\"code\":\"991\"},{\"name\":\"吉林\",\"code\":\"431\"},{\"name\":\"辽宁\",\"code\":\"240\"},{\"name\":\"黑龙江\",\"code\":\"451\"},{\"name\":\"终端公司\",\"code\":\"6243\"},{\"name\":\"咪咕大音二级平台\",\"code\":\"6230\"},{\"name\":\"在线公司/在线营销中\",\"code\":\"0267\"},{\"name\":\"物联网专席客服\",\"code\":\"0157\"},{\"name\":\"杭研智慧家庭\",\"code\":\"0609\"},{\"name\":\"上海产业研究院\",\"code\":\"6215\"},{\"name\":\"苏研云能经分系统\",\"code\":\"6217\"},{\"name\":\"金科公司\",\"code\":\"0604\"},{\"name\":\"中移互联网有限公司\",\"code\":\"0178\"},{\"name\":\"大音大数据子目录系统\",\"code\":\"6231\"},{\"name\":\"成都产业研究院\",\"code\":\"6214\"},{\"name\":\"集成公司\",\"code\":\"6247\"},{\"name\":\"大音平台\",\"code\":\"0057\"},{\"name\":\"北京大音系统\",\"code\":\"6224\"},{\"name\":\"上海大音系统\",\"code\":\"6234\"},{\"name\":\"天津大音系统\",\"code\":\"6227\"},{\"name\":\"重庆大音系统\",\"code\":\"6233\"},{\"name\":\"辽宁大音平台\",\"code\":\"6218\"},{\"name\":\"江苏大音系统\",\"code\":\"6219\"},{\"name\":\"湖北大音系统\",\"code\":\"2701\"},{\"name\":\"陕西大音系统\",\"code\":\"6226\"},{\"name\":\"河北大音平台\",\"code\":\"6223\"},{\"name\":\"山西大音系统\",\"code\":\"6229\"},{\"name\":\"河南大音系统\",\"code\":\"6228\"},{\"name\":\"黑龙江大音系统\",\"code\":\"6237\"},{\"name\":\"内蒙大音系统\",\"code\":\"6220\"},{\"name\":\"安徽大音系统\",\"code\":\"6236\"},{\"name\":\"浙江大音平台\",\"code\":\"5711\"},{\"name\":\"福建大音平台\",\"code\":\"5911\"},{\"name\":\"湖南大音平台\",\"code\":\"7311\"},{\"name\":\"云南大音系统\",\"code\":\"6222\"},{\"name\":\"海南大音系统\",\"code\":\"6225\"},{\"name\":\"甘肃BOSS开放平台\",\"code\":\"9311\"},{\"name\":\"宁夏大音平台\",\"code\":\"6221\"}]";
        JSONArray array = JSONArray.parseArray(strArr);
        for(Object o : array) {
            JSONObject object = JSON.parseObject(JSON.toJSONString(o));
            sysCodeMap.put(object.getString("code"), object.getString("name"));
        }
    }

    @PostMapping("/json")
    public List<String> getData(@RequestBody Map<String, Object> map) {
        String IDENTIFIER_ = "identifier";
        String PROCESS_ID_ = "process_id";
        List<String> list = new ArrayList<>();

        String json = String.valueOf(map.get("json"));
        List<JSONObject> objects = JSON.parseArray(json, JSONObject.class);
        objects.forEach(o -> {
            String id = o.getString(IDENTIFIER_);
            String pid = o.getString(PROCESS_ID_);
            list.add(id + "," + pid);
            System.out.println(id + "," + pid);
        });
        System.out.println("\n\n");
        return list;
    }

    public static void main(String[] args) throws IOException {
        String t1 = "|用户场景|${HEAD} |";
        String t2 = "|--------------------------------------- | ------------------------------------------------------------------------------------------ |";
        String t3 = "| 功能描述 | 1 |";

        FileReader fr1 = new FileReader("C:\\Users\\17747\\Desktop\\WORKING\\aaa.txt");
        BufferedReader bfr = new BufferedReader(fr1);
        //bfr.readLine() 通过换行符回车符 或者回车换行符作为终止符号
        String line;
        StringBuilder ct = new StringBuilder("");
        int i = 1;
        while((line = bfr.readLine()) != null) {
            if(line.equals("#")) {
                ct = new StringBuilder("t");
                System.out.println(ct);
                i = 1;
                System.out.println();
                ct = new StringBuilder("");
                continue;
            }
            if(i == 1) {
                if(StrUtil.isNotBlank(line)) {
                    String[] s = line.split("\t");
                    System.out.println(s[0]);
                    ct.append(i).append(".").append(s[1]).append(" ");
                    i++;
                    continue;
                }
            }
            ct.append(i).append(".").append(line.replace("\t", "")).append(" ");
            i++;
        }
        fr1.close();
        bfr.close();
    }

}
