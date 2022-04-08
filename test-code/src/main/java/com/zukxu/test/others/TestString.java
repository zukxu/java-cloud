package com.zukxu.test.others;

import com.alibaba.fastjson.JSON;

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
        String str = "identifier, identy_type, identy_subtype, identy_detail, title, content, origin_unit, receiver_unit, unit, work_flow_status, create_time, process_time, process_id, creator, filing_opinion, withdraw_time, withdraw_reason, urge_reason, urge_time, creator_contact_info, attach_list";
        System.out.println(JSON.toJSONString(str.split("、")));
    }
}
