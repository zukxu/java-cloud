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
        //testJsonStr();
        testSplit();
    }

    private static void testJsonStr() {
        String str = "process_research_audit:1:busType";
        String defKey = "process_research_audit";
        System.out.println(str.substring(str.lastIndexOf(":") + 1));
        System.out.println(JSON.toJSONString(str.split(":")));
    }

    private static void testSplit() {
        String s = "IdentyDetail,Title,Content,CreatTime,ProcessTime,Creator,CreatorContactInfo,ListenProvice,ListenCity,HallStoreinfo,Objective,MainDiscover,ThinkAbout,OtherTips,CreatorCity,County,CreatorDept,FileNo,ActivityModel,ActivityTime,ProblemType,ResolutionStatus,ReformMeasures,HandlerDept,HandlingOpinion,Handler,HandlerInfor,HandingTime,HandlerRank,HandingDepartment,AttachList";
        String[] split = s.split(",");
        System.out.println(split.length);
    }

    private static void testToMap() {

    }

}
