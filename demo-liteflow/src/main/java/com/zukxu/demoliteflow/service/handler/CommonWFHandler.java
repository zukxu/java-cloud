package com.zukxu.demoliteflow.service.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.demoliteflow.constant.CSVC;
import com.zukxu.demoliteflow.model.WorkFlowF;
import com.zukxu.demoliteflow.utils.WFUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 通用工单派单处理类
 * </p>
 *
 * @author xupu
 * @since 2022/6/9 16:27:17
 */
@Component("commonHandler")
public class CommonWFHandler {

    //@Resource
    //private WorkFlowFService workFlowFService;

    /**
     * 表单内容按照标准格式转换为派发实体
     *
     * @param param map
     * @return DispatchCSSDto
     */
    public WorkFlowF initDispatchCSS(Map<String, Object> param) {
        String ID_ = "Identifier";
        WorkFlowF flowF = JSON.parseObject(JSON.toJSONString(param), WorkFlowF.class);
        //校验设置工单主键
        String identifier = (String) Optional.ofNullable(param.get(ID_)).orElseGet(WFUtil::generateIdentifier);
        //设置接收方 为空就设置为集团接收
        flowF.setReceiverUnit(Optional.ofNullable(flowF.getReceiverUnit()).orElse(CSVC.CODE_JT));
        //创建人相关信息设置
        //SysUser user = SecurityUtils.getLoginUser().getUser();
        //生成时间格式化
        LocalDateTime now = LocalDateTimeUtil.now();
        flowF.setCreatTime(WFUtil.timeFormat(now))
             .setProcessTime(WFUtil.timeFormat(now.plusDays(15L)))
             .setIdentifier(identifier)
             .setCreator("Admin")
             .setCreatorContactInfo("15188888888");
        //.setCreator(user.getUserName())
        //.setCreatorContactInfo(user.getPhone());
        //flowF.setUserId(user.getId());
        return flowF;
    }

    /**
     * 构建同步数据结构
     *
     * @param syncDataDto SyncDataDto
     */
  /*  @SneakyThrows
    public void buildSyncDataDto(SyncDataDto syncDataDto) {
        String identifier = syncDataDto.getIdentifier();
        //获取到同步的数据结构
        if(ObjectUtil.isEmpty(syncDataDto.getParaList())) {
            WorkFlowF byId = workFlowFService.getById(identifier);
            Map<Object, Object> workFlowMap = workFlowFService.getWorkFlowMap(identifier, WorkFlowSubEnum.SyncData.getKey());
            buildHandler(workFlowMap);
            //独有参数构建
            Class<CommonWorkFLowHandler> aClass = CommonWorkFLowHandler.class;
            CommonWorkFLowHandler instance = aClass.newInstance();
            String methodName = "buildSyncDataDto" + syncDataDto.getIdentySubtype();
            Method method = ReflectUtil.getMethodByNameIgnoreCase(aClass, methodName);
            method.invoke(instance, byId, workFlowMap);
            //更新同步数据
            SpringUtil.getBean(WorkFlowSubService.class).updateById(new WorkFlowSub(identifier).setSyncDataJson(JSON.toJSONString(workFlowMap)));
            syncDataDto.setParaList(buildParaList(workFlowMap));
        }
        if(ObjectUtil.isEmpty(syncDataDto.getLaunchCompany())) {
            //同步接收单位只有集团
            syncDataDto.setLaunchCompany(CSVCConstant.CODE_SN);
            syncDataDto.setReceiverUnit(CSVCConstant.CODE_JT);
        }
    }
*/
    /**
     * 处理人
     */
   /* public void buildHandler(Map<Object, Object> workFlowMap) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        SysDept sysDept = Optional.ofNullable(user.getDept()).orElseGet(() -> new SysDept().setDeptName("无"));
        Map<Object, Object> tempMap = new HashMap<>();
        tempMap.putIfAbsent("Handler", user.getUserName());
        tempMap.putIfAbsent("HandlerInfor", user.getPhone());
        tempMap.putIfAbsent("HandingDepartment", sysDept.getDeptName());
        tempMap.putIfAbsent("HandlerRank", StrUtil.EMPTY);
        tempMap.putIfAbsent("HandingTime", LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        workFlowMap.putAll(tempMap);
    }

    private void buildSyncDataDto0204(WorkFlowF byId, Map<Object, Object> workFlowMap) {

    }

    private void buildSyncDataDto0205(WorkFlowF byId, Map<Object, Object> workFlowMap) {
        //构建工单日志列表
        List<ExtIdentylogList> logLists = transToQueryExtLog(byId.getIdentifier());
        workFlowMap.put("ExtIdentylogList", JSON.toJSONString(logLists.toArray(new ExtIdentylogList[0])));
    }

    private void buildSyncDataDto0206(WorkFlowF byId, Map<Object, Object> workFlowMap) {
        workFlowMap.put("OriginalUnit", byId.getOriginUnit());
        workFlowMap.put("OriginReceiver", byId.getReceiverUnit());
    }

    *//**
     * 对于0302 一线声音回传申请单 同步接口构建
     * 工单细类为03030201时：
     * （1）对于省专公司内部处理闭环的一线声音回传工单，在工单归档后调用工单信息同步接口上报工单信息，包括所有内部流转记录。
     * （2）对于来自其他省专公司转派的工单，工单闭环后，省专公司需要使用原工单编号将处理轨迹同步至集团
     *
     * @param workFlowMap
     *//*
    private void buildSyncDataDto0302(WorkFlowF byId, Map<Object, Object> workFlowMap) {
        Sync_YXSYHCSQD yxsy = JSON.parseObject(JSON.toJSONString(workFlowMap), Sync_YXSYHCSQD.class);
        if(StrUtil.isBlank(yxsy.getHandlingOpinion())) yxsy.setHandlingOpinion("编号" + byId.getIdentifier() + "的工单已正常流转审批完成");
        if(StrUtil.isBlank(yxsy.getIsSolved())) yxsy.setIsSolved("0");//0:是 1:否
        //获取处理记录
        List<ExtIdentylogList> logLists = transToQueryExtLog(byId.getIdentifier());
        yxsy.setExtIdentylogList(logLists.toArray(new ExtIdentylogList[0]));
        Map map = JSON.parseObject(JSON.toJSONString(yxsy), Map.class);
        workFlowMap.putAll(map);
    }

    *//**
     * 转换成回复操作日志
     *//*
    private List<ExtIdentylogList> transToQueryExtLog(String identifier) {
        R<?> r = SpringUtil.getBean(OrderFlowService.class).queryTimeLineById(identifier);
        List<ExtIdentylogList> queryList = new ArrayList<>();
        if(r.getCode() == RStatus.OK.getCode()) {
            List<TaskVO> voList = (List<TaskVO>) r.getData();

            voList.forEach(t -> {
                if(!StrUtil.equals("申请人", t.getTaskName())) {
                    ExtIdentylogList extLog = new ExtIdentylogList();
                    extLog.setHandler(t.getAssigneeName());
                    extLog.setHandingOpinions("");
                    if(ObjectUtil.isNotNull(t.getComment())) {
                        extLog.setHandingOpinions(Optional.ofNullable(t.getComment().getComment()).orElse(""));
                    }
                    extLog.setHandingTime(DateUtils.dateToStr(t.getEndTime(), DateUtils.DATETIME_FORMAT_NOT_ALL));
                    extLog.setHandlerContactInfor(Optional.ofNullable(t.getSysUser().getPhone()).orElse(""));
                    extLog.setHandingDepartment(Optional.ofNullable(t.getDeptName()).orElse(""));
                    extLog.setProcessingName(t.getTaskName());
                    extLog.setPhaseType("0");//0：处理环节1：审批环节
                    extLog.setApprovalResults("1");//枚举值：1：通过:2：驳回；3：提交下一审批 注：如果是审批环节，则审批结果为必填
                    queryList.add(extLog);
                }
            });
        }
        return queryList;
    }
*/

}
