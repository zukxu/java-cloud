package com.zukxu.test.strings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xupu
 * @Date 2021-11-03 15:02
 */
public class TestSplit {

    public static void main(String[] args) {
        testStrings();
    }

    private static String testStrings() {
        String shape = "106.08178858430595,25.15883990097123;106.08163071547843,25.158982697170224;106.0816197282167,25.158999684686528;106.08161773700029,25.15901468395599;106.08161774297218,25.159025685366238;106.08161874862309,25.159036688102592;106.08162375083633,25.159043695648606;106.08194877584522,25.1593032072987;106.0819617766682,25.159314229738502;106.0819737736059,25.15931724968724;106.08198776814253,25.1593172726812;106.08224855534017,25.1591237080439;106.08225554575523,25.15911171966378;106.08226153336506,25.159093728673586;106.08227942466512,25.15890873900879;106.082276418892,25.158895731624018;106.08223241436275,25.158851643186996;106.08222041800117,25.158848620526395;106.08181956947749,25.158832946222482;106.08179957933385,25.158837916995573;106.08178858430595,25.15883990097123;";
        String[] point = shape.split(";");
        List<String[]> array = new ArrayList<>();
        for(String s : point) {
            String[] split = s.split(",");
            array.add(split);
        }
        String s = JSON.toJSONString(array);
        System.out.println(s);
        List<JSONArray> points = JSONObject.parseArray(s, JSONArray.class);
        double minX = points.get(0).getDoubleValue(0);
        double maxX = points.get(0).getDoubleValue(0);
        double minY = points.get(0).getDoubleValue(1);
        double maxY = points.get(0).getDoubleValue(1);
        return s;
    }

}
