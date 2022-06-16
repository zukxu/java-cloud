package com.zukxu.test.others;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-07 9:43
 */
public class TestString {

    public static void main(String[] args) {
        //testJsonStr();
        //testSplit();
        //System.out.println(FileUtil.readableFileSize(2147483647L));
        testToMap();
    }

    private static void testJsonStr() {
        String str = "process_research_audit:1:busType";
        String defKey = "process_research_audit";
        System.out.println(str.substring(str.lastIndexOf(":") + 1));
        System.out.println(JSON.toJSONString(str.split(":")));
    }

    private static void testSplit() {
        String s = "IdentyDetail:12,Title:122";
    }

    private static void testToMap() {
        String str7 = "[\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"04\",\n" +
                      "            \"receiver_unit\": \"531\",\n" +
                      "            \"identifier\": \"20220615GZL85118279435\",\n" +
                      "            \"process_id\": \"ed1cb24d-ec8c-11ec-9ed2-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030201\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"04\",\n" +
                      "            \"receiver_unit\": \"431\",\n" +
                      "            \"identifier\": \"20220615GZL85145309128\",\n" +
                      "            \"process_id\": \"f0c658be-ec8c-11ec-9ed2-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030201\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"04\",\n" +
                      "            \"receiver_unit\": \"351\",\n" +
                      "            \"identifier\": \"20220615GZL85148631207\",\n" +
                      "            \"process_id\": \"ed7d7035-ec8c-11ec-9ed2-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030201\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"04\",\n" +
                      "            \"receiver_unit\": \"240\",\n" +
                      "            \"identifier\": \"20220615GZL85165032789\",\n" +
                      "            \"process_id\": \"f0e79c82-ec8c-11ec-9ed2-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030201\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"240\",\n" +
                      "            \"identifier\": \"20220616GZL85108254637\",\n" +
                      "            \"process_id\": \"d26244d4-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030202\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"431\",\n" +
                      "            \"identifier\": \"20220616GZL85113459608\",\n" +
                      "            \"process_id\": \"d19b99fc-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030202\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"531\",\n" +
                      "            \"identifier\": \"20220616GZL85114968537\",\n" +
                      "            \"process_id\": \"d0193fcc-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030202\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"351\",\n" +
                      "            \"identifier\": \"20220616GZL85154196302\",\n" +
                      "            \"process_id\": \"d0db7dd4-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030202\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"240\",\n" +
                      "            \"identifier\": \"20220616GZL85125703164\",\n" +
                      "            \"process_id\": \"d2a149cc-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030203\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"351\",\n" +
                      "            \"identifier\": \"20220616GZL85135642718\",\n" +
                      "            \"process_id\": \"d119e68c-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030203\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"531\",\n" +
                      "            \"identifier\": \"20220616GZL85150982647\",\n" +
                      "            \"process_id\": \"d057f6a4-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030203\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"431\",\n" +
                      "            \"identifier\": \"20220616GZL85198701346\",\n" +
                      "            \"process_id\": \"d1e881a4-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030203\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"431\",\n" +
                      "            \"identifier\": \"20220616GZL85137182450\",\n" +
                      "            \"process_id\": \"d225149c-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030204\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"531\",\n" +
                      "            \"identifier\": \"20220616GZL85147291065\",\n" +
                      "            \"process_id\": \"d09c03ac-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030204\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"351\",\n" +
                      "            \"identifier\": \"20220616GZL85163182907\",\n" +
                      "            \"process_id\": \"d15dcd84-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030204\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"work_flow_status\": \"01\",\n" +
                      "            \"receiver_unit\": \"240\",\n" +
                      "            \"identifier\": \"20220616GZL85170386524\",\n" +
                      "            \"process_id\": \"d2dc3014-ed1e-11ec-9fd3-005056bd390a\",\n" +
                      "            \"identy_detail\": \"03030204\"\n" +
                      "        }\n" +
                      "    ]";
        //JSONArray array7 = JSON.parseArray(str7);
        //printArr(array7);
    }

    public static void printArr(JSONArray array) {
        String pName = "";
        String pCode = ",0609,";
        for(int i = 0; i < array.size(); i++) {
            JSONObject object = JSON.parseObject(JSON.toJSONString(array.get(i)));
            pName = object.getString("identifier");
            System.out.println(object.getString("identy_detail") + "," + pName + (i + 1) + pCode + object.getString("identifier") + "," + object.getString(
                    "process_id"));
        }
    }

}