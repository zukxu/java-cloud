package com.zukxu.test.others;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-07 9:43
 */
public class TestString {

    public static void main(String[] args) throws IOException {
        //String file = "E:\\Cloud\\temp.txt";
        //buildFunctionalRequirementsDocument(file);
        String s = "{\"accessToken\":\"\",\"busType\":\"CSVC\",\"content\":\"{\\\"Identifier\\\":\\\"20220920GZL85142358709\\\",\\\"IdentySubtype\\\":\\\"0205\\\",\\\"IdentyType\\\":\\\"02\\\",\\\"LaunchCompany\\\":\\\"851\\\",\\\"ParaList\\\":[{\\\"ParaID\\\":\\\"CreatorContactInfo\\\",\\\"ParaVal\\\":\\\"13885146567\\\"},{\\\"ParaID\\\":\\\"CreatTime\\\",\\\"ParaVal\\\":\\\"20220920175205\\\"},{\\\"ParaID\\\":\\\"HallStoreinfo\\\",\\\"ParaVal\\\":\\\"中移在线公司\\\"},{\\\"ParaID\\\":\\\"ProcessTime\\\",\\\"ParaVal\\\":\\\"20221020175205\\\"},{\\\"ParaID\\\":\\\"HandlerInfor\\\",\\\"ParaVal\\\":\\\"13639109135\\\"},{\\\"ParaID\\\":\\\"HandingDepartment\\\",\\\"ParaVal\\\":\\\"省公司客户服务部\\\"},{\\\"ParaID\\\":\\\"Creator\\\",\\\"ParaVal\\\":\\\"聂璇\\\"},{\\\"ParaID\\\":\\\"County\\\",\\\"ParaVal\\\":\\\"\\\"},{\\\"ParaID\\\":\\\"ListenProvice\\\",\\\"ParaVal\\\":\\\"851\\\"},{\\\"ParaID\\\":\\\"HandlingOpinion\\\",\\\"ParaVal\\\":\\\"工单创建信息同步\\\"},{\\\"ParaID\\\":\\\"FileNo\\\",\\\"ParaVal\\\":\\\"20220920FNO85109328333\\\"},{\\\"ParaID\\\":\\\"ResolutionStatus\\\",\\\"ParaVal\\\":\\\"02\\\"},{\\\"ParaID\\\":\\\"IdentyDetail\\\",\\\"ParaVal\\\":\\\"02020501\\\"},{\\\"ParaID\\\":\\\"ListenCity\\\",\\\"ParaVal\\\":\\\"0851\\\"},{\\\"ParaID\\\":\\\"CreatorCity\\\",\\\"ParaVal\\\":\\\"0851\\\"},{\\\"ParaID\\\":\\\"HandingTime\\\",\\\"ParaVal\\\":\\\"20220920175335\\\"},{\\\"ParaID\\\":\\\"AttachList\\\",\\\"ParaVal\\\":\\\"\\\"},{\\\"ParaID\\\":\\\"Introducer\\\",\\\"ParaVal\\\":\\\"聂璇\\\"},{\\\"ParaID\\\":\\\"ThinkAbout\\\",\\\"ParaVal\\\":\\\"改进思考\\\"},{\\\"ParaID\\\":\\\"InformationType\\\",\\\"ParaVal\\\":\\\"01\\\"},{\\\"ParaID\\\":\\\"HandlerRank\\\",\\\"ParaVal\\\":\\\"\\\"},{\\\"ParaID\\\":\\\"IntroducerTel\\\",\\\"ParaVal\\\":\\\"13885146567\\\"},{\\\"ParaID\\\":\\\"MainDiscover\\\",\\\"ParaVal\\\":\\\"主要发现\\\"},{\\\"ParaID\\\":\\\"ActivityModel\\\",\\\"ParaVal\\\":\\\"02\\\"},{\\\"ParaID\\\":\\\"ExtIdentylogList\\\",\\\"ParaVal\\\":\\\"[{\\\\\\\"ApprovalResults\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"HandingDepartment\\\\\\\":\\\\\\\"省公司客户服务部\\\\\\\",\\\\\\\"HandingOpinions\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"HandingTime\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"Handler\\\\\\\":\\\\\\\"刘廷勇\\\\\\\",\\\\\\\"HandlerContactInfor\\\\\\\":\\\\\\\"13639109135\\\\\\\",\\\\\\\"PhaseType\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"ProcessingName\\\\\\\":\\\\\\\"站店人员直线经理\\\\\\\"},{\\\\\\\"ApprovalResults\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"HandingDepartment\\\\\\\":\\\\\\\"省公司信息技术部系统开发测试室\\\\\\\",\\\\\\\"HandingOpinions\\\\\\\":\\\\\\\"流程启动\\\\\\\",\\\\\\\"HandingTime\\\\\\\":\\\\\\\"20220920175205\\\\\\\",\\\\\\\"Handler\\\\\\\":\\\\\\\"聂璇\\\\\\\",\\\\\\\"HandlerContactInfor\\\\\\\":\\\\\\\"13885146567\\\\\\\",\\\\\\\"PhaseType\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"ProcessingName\\\\\\\":\\\\\\\"站店人员\\\\\\\"}]\\\"},{\\\"ParaID\\\":\\\"Title\\\",\\\"ParaVal\\\":\\\"测试工单-贵州派发集团外呼中心站店听音建议任务单01\\\"},{\\\"ParaID\\\":\\\"Handler\\\",\\\"ParaVal\\\":\\\"刘廷勇\\\"},{\\\"ParaID\\\":\\\"ReformMeasures\\\",\\\"ParaVal\\\":\\\"优化举措\\\"},{\\\"ParaID\\\":\\\"HandlerDept\\\",\\\"ParaVal\\\":\\\"851\\\"},{\\\"ParaID\\\":\\\"Objective\\\",\\\"ParaVal\\\":\\\"目标\\\"},{\\\"ParaID\\\":\\\"CreatorDept\\\",\\\"ParaVal\\\":\\\"省公司信息技术部系统开发测试室\\\"},{\\\"ParaID\\\":\\\"Content\\\",\\\"ParaVal\\\":\\\"测试工单-贵州派发集团外呼中心站店听音建议任务单01\\\"},{\\\"ParaID\\\":\\\"ProblemType\\\",\\\"ParaVal\\\":\\\"1003\\\"},{\\\"ParaID\\\":\\\"ActivityTime\\\",\\\"ParaVal\\\":\\\"20220920\\\"},{\\\"ParaID\\\":\\\"OtherTips\\\",\\\"ParaVal\\\":\\\"其他建议\\\"},{\\\"ParaID\\\":\\\"ProblemNature\\\",\\\"ParaVal\\\":\\\"01\\\"}],\\\"ReceiverUnit\\\":\\\"0057\\\"}\",\"cutOffDay\":\"20220920\",\"domain\":\"CSVC\",\"envFlag\":\"0\",\"routeType\":\"00\",\"routeValue\":\"998\",\"sessionID\":\"7b7f1aff60954a9f84f94abb0e2978b7\",\"sign\":\"CF82F2EAF2A0591EFF577E5A55F21DF4\",\"signMethod\":\"md5\",\"timeStamp\":\"20220920175408\",\"transIDO\":\"7b7f1aff60954a9f84f94abb0e2978b7\",\"userPartyID\":\"COP8510\",\"version\":\"1.0.1\"}";
        JSONObject jsonObject = JSON.parseObject(s);
        //JSONArray paraList = jsonObject.getJSONArray("ParaList");
        //paraList.forEach(t->{
        //    System.out.println(t);
        //});
        System.out.println(jsonObject);
    }

    /**
     * 构建功能需求文档
     */
    private static void buildFunctionalRequirementsDocument(String file) throws IOException {
        String tblTemplate = "<table border=\"1\">\n" + "    <tr>\n" + "        <td bgcolor=\"#d9d9d9\">用户场景</td>\n" + "        <td style=\"width:400px\">T1</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td bgcolor=\"#d9d9d9\">功能描述</td>\n" + "        <td style=\"width:400px\">T2</td>\n" + "    </tr>\n" + "</table>";
        String key = "^";
        String startCode = "1.";
        System.out.println("# 1. 功能特性");

        String prefix = "## ";
        int second = 0, third = 0, fourth = 0;
        StringBuilder title = new StringBuilder(startCode);

        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String line;
        while((line = bfr.readLine()) != null) {
            int count = getCount(line, key);
            line = line.replace(key, "");
            switch(count) {
                case 2:
                    second++;
                    third = 0;
                    title.append(second).append(".");
                    System.out.println(prefix + title + line);
                    break;
                case 3:
                    third++;
                    fourth = 0;
                    title.append(second).append(".").append(third).append(".");
                    prefix = "### ";
                    System.out.println(prefix + title + line);
                    break;
                case 4:
                    fourth++;
                    title.append(second).append(".").append(third).append(".").append(fourth).append(".");
                    prefix = "#### ";
                    System.out.println(prefix + title + line);
                    System.out.println(tblTemplate.replace("T1", line) + "\n");
                    break;
                default:
                    title = new StringBuilder(startCode);
                    break;
            }
            title = new StringBuilder(startCode);
        }
        fr.close();
        bfr.close();
    }

    /**
     * 获取某个字符的数量
     */
    public static int getCount(String str, String key) {
        if(StrUtil.isBlank(str) || StrUtil.isBlank(key)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

}