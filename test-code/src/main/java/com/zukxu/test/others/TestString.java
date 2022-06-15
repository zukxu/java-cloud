package com.zukxu.test.others;

import cn.hutool.core.io.FileUtil;
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
        //testSplit();
        System.out.println(FileUtil.readableFileSize(2147483647L));
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
        String str1 = "{\"CreatorContactInfo\":\"13639109135\",\"CreatTime\":\"20220614130023\",\"HallStoreinfo\":\"贵阳观山湖厅店\",\"ProcessTime\":\"20220630164304\",\"HandlerInfor\":\"13639109135\",\"HandingDepartment\":\"\",\"Creator\":\"liutingyong\",\"County\":\"贵阳\",\"ListenProvice\":\"北京\",\"HandlingOpinion\":\"创建站店听音建议任务单\",\"FileNo\":\"20220614FNO85154522074\",\"ResolutionStatus\":\"02\",\"ListenCity\":\"北京\",\"IdentyDetail\":\"02020503\",\"CreatorCity\":\"851\",\"HandingTime\":\"20220614130223\",\"ThinkAbout\":\"改进思考\",\"HandlerRank\":\"\",\"MainDiscover\":\"主要发现\",\"ActivityModel\":\"01\",\"Handler\":\"liutingyong\",\"Title\":\"一线回传申请单02:站店问题档案\",\"ReformMeasures\":\"无\",\"HandlerDept\":\"0003\",\"Objective\":\"站店目标\",\"CreatorDept\":\"省公司客户服务部\",\"Content\":\"一线回传申请单02:站店问题档案-贵州\",\"ProblemType\":\"1001\",\"ActivityTime\":\"20220630\",\"OtherTips\":\"无\"}";
        String str2 = "{\"CreatorContactInfo\":\"13639109135\",\"CreatTime\":\"20220614130023\",\"HallStoreinfo\":\"中移在线公司\",\"HandlerInfor\":\"13639109135\",\"ProcessTime\":\"20220630164304\",\"HandingDepartment\":\"省公司客户服务部\",\"Creator\":\"liutingyong\",\"County\":\"贵阳\",\"ListenProvice\":\"北京\",\"HandlingOpinion\":\"创建站店听音建议任务单\",\"FileNo\":\"20220614FNO85154522075\",\"ResolutionStatus\":\"02\",\"ListenCity\":\"北京\",\"IdentyDetail\":\"02020501\",\"CreatorCity\":\"851\",\"HandingTime\":\"20220614130223\",\"ThinkAbout\":\"改进思考\",\"HandlerRank\":\"\",\"MainDiscover\":\"主要发现\",\"ActivityModel\":\"02\",\"Handler\":\"liutingyong\",\"Title\":\"一线回传申请单03:听音问题档案\",\"ReformMeasures\":\"无\",\"HandlerDept\":\"0003\",\"Objective\":\"听音目标\",\"CreatorDept\":\"省公司客户服务部\",\"Content\":\"一线回传申请单03:听音问题档案-贵州\",\"ProblemType\":\"1001\",\"ActivityTime\":\"20220630\",\"OtherTips\":\"无\"}";
        String str3 = "{\"CreatorContactInfo\":\"13639109135\",\"CreatTime\":\"20220614130023\",\"HallStoreinfo\":\"贵阳观山湖厅店\",\"HandlerInfor\":\"13639109135\",\"ProcessTime\":\"20220630164304\",\"HandingDepartment\":\"省公司客户服务部\",\"Creator\":\"liutingyong\",\"County\":\"贵阳\",\"ListenProvice\":\"北京\",\"HandlingOpinion\":\"创建站店听音建议任务单\",\"FileNo\":\"20220614FNO85154522076\",\"ResolutionStatus\":\"02\",\"ListenCity\":\"北京\",\"IdentyDetail\":\"02020504\",\"CreatorCity\":\"851\",\"HandingTime\":\"20220614130223\",\"ThinkAbout\":\"改进思考\",\"HandlerRank\":\"\",\"MainDiscover\":\"主要发现\",\"ActivityModel\":\"03\",\"Handler\":\"liutingyong\",\"Title\":\"一线回传申请单04:追投诉问题档案\",\"ReformMeasures\":\"无\",\"HandlerDept\":\"0003\",\"Objective\":\"追投诉目标\",\"CreatorDept\":\"省公司客户服务部\",\"Content\":\"一线回传申请单04:追投诉问题档案-贵州\",\"ProblemType\":\"1001\",\"ActivityTime\":\"20220630\",\"OtherTips\":\"无\"}";
        //System.out.println(JSON.parseObject(str1));
        //System.out.println(JSON.parseObject(str2));
        //System.out.println(JSON.parseObject(str3));

        String str4 = "{\"Identifier\":\"20220614GZL85193450817\",\"IdentySubtype\":\"0302\",\"IdentyType\":\"03\",\"LaunchCompany\":\"851\",\"ParaList\":[{\"ParaID\":\"CreatorContactInfo\",\"ParaVal\":\"13639109135\"},{\"ParaID\":\"CreatTime\",\"ParaVal\":\"20220614130023\"},{\"ParaID\":\"HallStoreinfo\",\"ParaVal\":\"中移在线公司\"},{\"ParaID\":\"ProcessTime\",\"ParaVal\":\"20220630164304\"},{\"ParaID\":\"HandlerInfor\",\"ParaVal\":\"13639109135\"},{\"ParaID\":\"HandingDepartment\",\"ParaVal\":\"\"},{\"ParaID\":\"Creator\",\"ParaVal\":\"liutingyong\"},{\"ParaID\":\"County\",\"ParaVal\":\"贵阳\"},{\"ParaID\":\"ListenProvice\",\"ParaVal\":\"北京\"},{\"ParaID\":\"HandlingOpinion\",\"ParaVal\":\"创建站店听音建议任务单\"},{\"ParaID\":\"FileNo\",\"ParaVal\":\"20220614FNO85154522075\"},{\"ParaID\":\"ResolutionStatus\",\"ParaVal\":\"02\"},{\"ParaID\":\"ListenCity\",\"ParaVal\":\"北京\"},{\"ParaID\":\"IdentyDetail\",\"ParaVal\":\"02020501\"},{\"ParaID\":\"CreatorCity\",\"ParaVal\":\"851\"},{\"ParaID\":\"HandingTime\",\"ParaVal\":\"20220614130223\"},{\"ParaID\":\"ThinkAbout\",\"ParaVal\":\"改进思考\"},{\"ParaID\":\"HandlerRank\",\"ParaVal\":\"\"},{\"ParaID\":\"MainDiscover\",\"ParaVal\":\"主要发现\"},{\"ParaID\":\"ActivityModel\",\"ParaVal\":\"02\"},{\"ParaID\":\"Handler\",\"ParaVal\":\"liutingyong\"},{\"ParaID\":\"Title\",\"ParaVal\":\"一线回传申请单03:听音问题档案\"},{\"ParaID\":\"ReformMeasures\",\"ParaVal\":\"无\"},{\"ParaID\":\"HandlerDept\",\"ParaVal\":\"0003\"},{\"ParaID\":\"Objective\",\"ParaVal\":\"听音目标\"},{\"ParaID\":\"CreatorDept\",\"ParaVal\":\"省公司客户服务部\"},{\"ParaID\":\"Content\",\"ParaVal\":\"一线回传申请单03:听音问题档案-贵州\"},{\"ParaID\":\"ProblemType\",\"ParaVal\":\"1001\"},{\"ParaID\":\"ActivityTime\",\"ParaVal\":\"20220630\"},{\"ParaID\":\"OtherTips\",\"ParaVal\":\"无\"}],\"ReceiverUnit\":\"0057\"}";
        //System.out.println(JSON.parseObject(str4));

    }

}
