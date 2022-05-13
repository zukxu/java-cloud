package com.zukxu.test.others;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

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
        String statDate = "20220313";
        String type = "1001";
        String str1 = "SELECT " +
                "  a.*, b.managers_level3_id, " +
                "  b.principal_id " +
                "FROM " +
                "  ( " +
                "    SELECT DISTINCT " +
                "      * " +
                "    FROM " +
                "      wkod_overtime_reasonpro_day " +
                "    WHERE " +
                "      STAT_DATE = '" + statDate + "' AND type='" + type + "'" +
                "  ) a " +
                "INNER JOIN wkod_overtime_config b ON a.serv_request_name = b.bus_type " +
                "WHERE " +
                "  b.managers_level3_id IS NOT NULL " +
                "AND b.principal_id IS NOT NULL " +
                "ORDER BY " +
                "  b.managers_level3_id, " +
                "  a.wkod_newbuild_wkod_time DESC";
        System.out.println(JSON.toJSONString(str1));
        testJson();

    }

    public static void testJson() {
        String str1 = "[{\"process_id\":\"2dcb48c3-d079-11ec-a74e-005056bdac87\"},{\"process_id\":\"af8d5fa6-d0f7-11ec-a58f-005056bdac87\"},{\"process_id\":\"a77c3faf-d0f7-11ec-a58f-005056bdac87\"},{\"process_id\":\"7d374811-d101-11ec-a349-005056bdac87\"},{\"process_id\":\"be2c1544-d094-11ec-8a6d-005056bdac87\"},{\"process_id\":\"e6357385-d07b-11ec-ab99-005056bdac87\"},{\"process_id\":\"db898a35-d0a8-11ec-aea9-005056bdac87\"},{\"process_id\":\"a042a063-d098-11ec-8a6d-005056bdac87\"},{\"process_id\":\"a0425238-d098-11ec-8a6d-005056bdac87\"},{\"process_id\":\"9690df4a-d0fe-11ec-a349-005056bdac87\"},{\"process_id\":\"faae9f68-d0a2-11ec-8a6d-005056bdac87\"},{\"process_id\":\"8e37c8eb-d07e-11ec-ab99-005056bdac87\"},{\"process_id\":\"b40dc262-d07e-11ec-ab99-005056bdac87\"},{\"process_id\":\"39c815e8-d083-11ec-8a6d-005056bdac87\"},{\"process_id\":\"4d81110f-d083-11ec-8a6d-005056bdac87\"},{\"process_id\":\"ba0317c6-d092-11ec-8a6d-005056bdac87\"},{\"process_id\":\"be56445d-d092-11ec-8a6d-005056bdac87\"},{\"process_id\":\"0407211b-d080-11ec-ab99-005056bdac87\"},{\"process_id\":\"0406d2f0-d080-11ec-ab99-005056bdac87\"},{\"process_id\":\"5e419bc1-d0a2-11ec-8a6d-005056bdac87\"},{\"process_id\":\"69c74908-d0a2-11ec-8a6d-005056bdac87\"},{\"process_id\":\"8e00bfce-d099-11ec-8a6d-005056bdac87\"},{\"process_id\":\"2fd96b8a-d0a2-11ec-8a6d-005056bdac87\"},{\"process_id\":\"1079dd9a-d091-11ec-8a6d-005056bdac87\"},{\"process_id\":\"130c4861-d091-11ec-8a6d-005056bdac87\"}]";
        JSONArray objects = JSON.parseArray(str1);
        StringBuilder str2 = new StringBuilder();
        for (Object o : objects) {
            Map map = JSON.parseObject(String.valueOf(o), Map.class);
            //System.out.println(map.get("process_id"));
            str2.append(map.get("process_id")).append("\n");
        }
        System.out.print(str2.toString());
    }
}
