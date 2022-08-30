package com.zukxu.demoliteflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.demoliteflow.model.extend.Para;
import com.zukxu.demoliteflow.service.WorkFlowGZService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * <p>
 * GZCSVCServiceImpl
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 11:47
 */
@Slf4j
@Service
public class WorkFlowGZServiceImpl implements WorkFlowGZService {

    //@formatter:off
    /*与集团交互URL*/
    private static String queryCSSUrl;
    private static String dispatchCSSUrl;
    private static String urgeCSSUrl;
    private static String withdrawCSSUrl;
    private static String reprocessCSSUrl;
    private static String replyCSSUrl;
    private static String statementCSSUrl;
    private static String currencyCSSUrl;
    private static String syncDataCSSUrl;
    private static String testjobCSSUrl;
    /*业务使用常量*/
    @Value("${workflow.envFlag:0}")
    private String envFlag;
    @Resource
    private FlowInstanceService flowInstanceService;
    @Resource
    private WorkFlowFService workFlowFService;
    @Resource
    private WorkFlowSubService workFlowSubService;
    @Resource
    private WorkFlowReplyService workFlowReplyService;
    @Resource
    private WorkFlowDocumentService workFlowDocumentService;
    @Resource
    private CommonWorkFLowHandler commonHandler;
    //@formatter:on
    //正式环境 envFlag=1
    @PostConstruct
    private void init() {
        if(EnvEnum.DEV.getType().equals(envFlag)) {
            //http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/QueryCSS/ReplyCSS
            queryCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/QueryCSS";
            dispatchCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/DispatchCSS";
            urgeCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/WithdrawCSS";
            withdrawCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/WithdrawCSS";
            reprocessCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/ReprocessCSS";
            replyCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/ReplyCSS";
            statementCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/StatementCSS";
            currencyCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/CurrencyCSS";
            syncDataCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/SyncData";
            testjobCSSUrl = "http://135.10.145.100:18081/cboss/origapi/v1.0.0/CSVC/TestjobCSS";
        } else {
            //正式地址 http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/
            queryCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/QueryCSS";
            dispatchCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/DispatchCSS";
            urgeCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/UrgeCSS";
            withdrawCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/WithdrawCSS";
            reprocessCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/ReprocessCSS";
            replyCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/ReplyCSS";
            statementCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/StatementCSS";
            currencyCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/CurrencyCSS";
            syncDataCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/SyncData";
            testjobCSSUrl = "http://135.10.143.10:18105/cboss/origapi/v1.0.0/CSVC/TestjobCSS";
        }
    }

    @Override
    @SneakyThrows
    public R<?> dispatchByWorkFlowId(String identifier) {
        WorkFlowF flowF = initDispatchWorkFlow(identifier);
        dispatchBizHandler(flowF);
        R<?> result = dispatchWorkFlowF(flowF);
        if(result.isSuccess()) {
            workFlowFService.updateById(flowF.setUnit(CSVSConstant.UNIT_SN));
        }
        return result;
    }

    /**
     * 派发业务处理
     *
     * @param flowF WorkFlowF
     */
    private void dispatchBizHandler(WorkFlowF flowF) {
        //一线声音申请单
        if(StrUtil.equals(CSVSConstant.FLOW_LINE_VOICE, flowF.getIdentySubtype())) {
            //一线声音申请单——>一线声音申请单业务场景不同步档案
            if(!StrUtil.equals("03030201", flowF.getIdentyDetail())) {
                // 同步档案
                SyncDataDto syncDataDto = new SyncDataDto()
                        .setIdentifier(flowF.getIdentifier())
                        .setLaunchCompany(CSVSConstant.CODE_SN)
                        .setReceiverUnit(CSVSConstant.CODE_JT);
                buildDocSyncData(syncDataDto);
                syncData(syncDataDto);
            }
        }
    }

    /**
     * 一线声音同步档案
     *
     * @param syncDataDto SyncDataDto
     */
    private void buildDocSyncData(SyncDataDto syncDataDto) {
        WorkFlowDocument document = workFlowDocumentService.getById(syncDataDto.getIdentifier());
        Optional.ofNullable(document).ifPresent(t -> {
            Map<Object, Object> map = JSON.parseObject(document.getDocText(), Map.class);
            syncDataDto.setParaList(buildParaList(map));
            Object detail = map.get("IdentyDetail");
            syncDataDto.setIdentyType(String.valueOf(detail).substring(0, 2)).setIdentySubtype(String.valueOf(detail).substring(2, 6));
        });
    }

    /**
     * 根据工单id构建派发实体
     *
     * @param identifier identifier
     *
     * @return WorkFLowF
     */
    private WorkFlowF initDispatchWorkFlow(String identifier) {
        WorkFlowF flowF = workFlowFService.getById(identifier);
        WorkFlowSub workFlowSub = workFlowSubService.getById(identifier);
        Map<Object, Object> flowSubMap = JSON.parseObject(workFlowSub.getJson(), Map.class);
        CSVCUtils.appendNewMapValue(flowSubMap, JSON.parseObject(JSON.toJSONString(flowF), Map.class));
        Para[] paraList = new Para[0];
        if(!StrUtil.equals(workFlowSub.getJson(), StrUtil.EMPTY_JSON)) {
            paraList = buildParaList(flowSubMap);
        }
        flowF.setParaList(paraList);
        //本省处理完派单给集团，修改临时状态为处理中
        flowF.setWorkFlowStatus(CSVSConstant.FLOW_STATUS_PROCESSING)
             .setProcessId(null)
             .setWorkFlowStatus(null)
             .setUnit(null)
             .setIdentyDetailName(null);
        return flowF;
    }

    @Override
    public R<WorkFlowDocument> addDocument(WorkFlowDocument document) {
        String fileNo = Optional.ofNullable(document.getFileNo()).orElseGet(FileUtil::getFileNo);
        document.setFileNo(fileNo).setIdentifier(DateUtils.generateIdentifier());
        workFlowDocumentService.save(document);
        return R.ok(document.setDocText(null));
    }

    @SneakyThrows
    @Override
    public R<?> dispatchCSS(Map<String, Object> param) {
        String PARAM_JSON = JSON.toJSONString(param);
        log.info("省分===>集团-派单:::{}", PARAM_JSON);
        //通用处理 初始化工单
        WorkFlowF flowF = commonHandler.initDispatchCSS(param);
        //构建独有参数
        WorkFlowSub flowSub = workFlowSubService.convertToSub(flowF.getIdentySubtype(), PARAM_JSON).setIdentifier(flowF.getIdentifier());
        flowSub.setDispatcherJson(flowSub.getJson());
        flowF.setParaList(Para.buildParaList(flowSub.getDispatcherJson()));
        //数据入库
        workFlowFService.save(flowF);
        workFlowSubService.save(flowSub);
        boolean isSn = StrUtil.equals(CSVSConstant.CODE_SN, flowF.getReceiverUnit()) && StrUtil.equals(CSVSConstant.CODE_SN, flowF.getOriginUnit());
        R<?> result = R.ok();
        if(!isSn && (Boolean) param.get("isDispatchNow")) {
            result = dispatchWorkFlowF(flowF);
        }
        if(result.isSuccess()) {
            result.data(flowF.getIdentifier());
            //启动流程
            workFlowFService.updateById(flowF.setUnit(CSVSConstant.UNIT_SN).setWorkFlowStatus(CSVSConstant.FLOW_STATUS_PROCESSING));
            param.put("userId", flowF.getUserId());
            flowInstanceService.bindProcess(flowF, param);
        }
        return result;
    }

    /**
     * 派发工单
     */
    @SneakyThrows
    private R<?> dispatchWorkFlowF(WorkFlowF flowF) {
        RequestDto<String> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(JSON.toJSONString(flowF));
        requestDto.setSign(SignUtil.sign(JSON.toJSONString(requestDto)));
        String reqParam = JSON.toJSONString(requestDto);
        log.info("省分===>集团-工单派发:::请求报文:::{}", reqParam);
        String resp = HttpUtils.post(dispatchCSSUrl, reqParam);
        log.info("省分===>集团-工单派发:::响应结果:::{}", resp);
        return RJT.result(resp);
    }


    @SneakyThrows
    @Override
    public R<?> replyCSS(ReplyCSSDto replyCSS) {
        String identifier = replyCSS.getIdentifier();
        WorkFlowF flowF = workFlowFService.getById(identifier);

        Map<Object, Object> replyMap = workFlowFService.getWorkFlowMap(identifier, WorkFlowSubEnum.ReplyCSS.getKey());
        R<?> result = replyBizHandler(flowF);
        if(!result.isSuccess()) return result;
        //处理人
        Map<Object, Object> handler = new HashMap<>();
        commonHandler.buildHandler(handler);
        BeanUtil.copyProperties(JSON.parseObject(JSON.toJSONString(handler), ReplyCSSDto.class), replyCSS);

        replyCSS.setLaunchCompany(CSVSConstant.CODE_SN).setForwardCompany(flowF.getOriginUnit());
        if(CollectionUtil.isNotEmpty(replyMap)) {
            replyCSS.setParaList(buildParaList(replyMap));
        }

        String reply = JSON.toJSONString(replyCSS);
        boolean isSn = StrUtil.equals(CSVSConstant.CODE_SN, flowF.getReceiverUnit()) && StrUtil.equals(CSVSConstant.CODE_SN, flowF.getOriginUnit());
        if(isSn) {
            workFlowReplyService.save(JSON.parseObject(reply, WorkFlowReply.class));
            return statementCSS(new StatementCSSDto(identifier));
        }

        log.info("省分===>集团-回复:::{}", reply);
        RequestDto<String> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(reply);
        requestDto.setSign(SignUtil.sign(JSON.toJSONString(requestDto)));
        String param = JSON.toJSONString(requestDto);
        log.info("省分===>集团-回复:::请求报文:::{}", param);
        String resp = HttpUtils.post(replyCSSUrl, param);
        log.info("省分===>集团-回复:::响应结果::::{}", resp);

        result = RJT.result(resp);
        if(result.isSuccess()) {
            workFlowReplyService.save(JSON.parseObject(reply, WorkFlowReply.class));
            updateWorkFlowStatus(identifier, CSVSConstant.FLOW_STATUS_REPLIED);
            result.setMsg("工单已回复");
        }
        return result;
    }

    private R<?> replyBizHandler(WorkFlowF flowF) {
        //查询工单状态
        //不在处理中或催办中
        String status = flowF.getWorkFlowStatus();
        boolean haveHandled = !StrUtil.equals(CSVSConstant.FLOW_STATUS_COMPLETE, status)
                              && !StrUtil.equals(CSVSConstant.FLOW_STATUS_PROCESSING, status)
                              && !StrUtil.equals(CSVSConstant.FLOW_STATUS_HAS_URGE, status);
        if(haveHandled) {
            return R.fail("工单已处理！");
        }
        return R.ok();
    }

    @Override
    @SneakyThrows
    public String uploadToJT(MultipartFile file) {
        //文件存储本地服务器备份
        String originalFilename = file.getOriginalFilename();
        String fName = FileUtil.getRandomFileName(Objects.requireNonNull(originalFilename));
        log.info("文件原名称:::{}", originalFilename);
        log.info("文件新名称:::{}", fName);
        File desc = FileUtil.getAbsoluteFile(FileUtil.SN_UPLOAD_DIR, fName);
        //文件存储本地服务器
        file.transferTo(desc);
        String src = desc.getPath();
        //sftp上传到集团服务器
        String dst = FileUtil.JT_UPLOAD_DIR + fName;
        log.info("本地服务器文件地址::::{}", src);
        log.info("集团服务器文件地址::::{}", dst);
        // 设置主机ip，端口，用户名，密码
        uploadToJt(SFTPConstants.sftpDetails(), src, dst);
        return fName;
    }

    private void uploadToJt(Map<String, String> sftpDetails, String src, String dst) throws Exception {
        //将附件传给集团
        log.info("上传附件到集团:{}=====>{}", src, dst);
        SFTPChannel channel = new SFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        chSftp.put(src, dst, ChannelSftp.OVERWRITE); // 代码段2
        chSftp.quit();
        channel.closeChannel();
        log.info("附件上传成功=====>");
    }

    @Override
    @SneakyThrows
    public void uploadToJT(String fileName) {
        //文件存储本地服务器备份
        File desc = FileUtil.getAbsoluteFile(FileUtil.SN_UPLOAD_DIR, fileName);
        String src = desc.getPath();
        //上传集团服务器地址 sftp服务器目标文件名
        String dst = FileUtil.JT_UPLOAD_DIR + fileName;
        log.info("本地服务器文件地址::::{}", src);
        log.info("集团服务器文件地址::::{}", dst);
        uploadToJt(SFTPConstants.sftpDetails(), src, dst);
    }

    @Override
    public R<?> queryCSS(QueryCSSDto queryCSS) {
        String identifier = queryCSS.getIdentifier();
        WorkFlowF flowF = workFlowFService.getById(identifier);
        if(ObjectUtil.isEmpty(flowF)) {
            return R.fail("查询失败！没找到对应工单。");
        }
        boolean isRecSN = StrUtil.equals(CSVSConstant.CODE_SN, flowF.getReceiverUnit());//是否省内接单
        if(isRecSN) {
            return R.ok().message("工单为本地工单，无需查询集团");
        }
        log.info("省分===>集团-工单查询:::{}", JSON.toJSONString(queryCSS));
        RequestDto<String> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(JSON.toJSONString(queryCSS));
        String param = JSON.toJSONString(requestDto);
        log.info("省分===>集团-工单查询:::请求报文:::{}", param);
        String resp = HttpUtils.post(queryCSSUrl, param);
        log.info("省分===>集团-查询:::响应结果::::{}", resp);
        R<?> r = RJT.result(resp);
        if(r.isSuccess()) {
            //查询处理记录入库
            String queryJson = JSON.parseObject(resp, ResponseDto.class).getResult();
            workFlowSubService.updateById(new WorkFlowSub(identifier).setQueryJson(queryJson));
            return R.ok(JSON.parseObject(queryJson, QueryCSSDto.class));
        }
        return r;
    }

    public R<?> statementCSS(StatementCSSDto statementCSS) {
        WorkFlowF flowF = workFlowFService.getById(statementCSS.getIdentifier());
        R<?> result = R.ok();
        if(!StrUtil.equals(CSVSConstant.FLOW_STATUS_REPLIED, flowF.getWorkFlowStatus())) {
            return result.code(RStatus.FAIL.getCode()).message("当前工单未回复完毕，无法归档！");
        }
        if(StrUtil.equals(CSVSConstant.CODE_SN, flowF.getReceiverUnit()) && StrUtil.equals(CSVSConstant.CODE_SN, flowF.getOriginUnit())) {
            workFlowFService.updateById(flowF.setWorkFlowStatus(CSVSConstant.FLOW_STATUS_ARCHIVED));
            return result.message("省内工单已归档");
        }
        log.info("省分===>集团-工单归档:::{}", JSON.toJSONString(statementCSS));
        RequestDto<StatementCSSDto> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(statementCSS);
        String param = JSON.toJSONString(requestDto);
        log.info("省分===>集团-工单归档:::请求参数:::{}", param);
        String resp = HttpUtils.post(statementCSSUrl, param);
        log.info("省分===>集团-工单归档:::响应参数:::{}", resp);

        result = RJT.result(resp);
        if(result.isSuccess()) {
            workFlowFService.updateById(flowF.setWorkFlowStatus(CSVSConstant.FLOW_STATUS_ARCHIVED));
        }
        return result;
    }

    @Override
    public R<?> withdrawCSS(WithdrawCSSDto withdrawCSS) {
        WorkFlowF flowF = workFlowFService.getById(withdrawCSS.getIdentifier());
        if(!StrUtil.equals(CSVSConstant.FLOW_STATUS_PROCESSING, flowF.getWorkFlowStatus())) {
            return R.fail("当前工单已处理，无法撤单！");
        }
        log.info("省分===>集团-工单撤单:::{}", JSON.toJSONString(withdrawCSS));
        withdrawCSS.setWithdrawTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATETIME_PATTERN));

        RequestDto<WithdrawCSSDto> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(withdrawCSS);
        String param = JSON.toJSONString(requestDto);

        log.info("省分===>集团-工单撤单:::请求参数:::{}", param);
        String resp = HttpUtils.post(withdrawCSSUrl, param);
        log.info("省分===>集团-工单撤单:::响应参数:::{}", resp);
        R<?> result = RJT.result(resp);
        if(result.isSuccess()) {
            flowF = JSON.parseObject(JSON.toJSONString(withdrawCSS), WorkFlowF.class).setWorkFlowStatus(CSVSConstant.FLOW_STATUS_HAS_WITHDRAWN);
            workFlowFService.updateById(flowF);
        }
        return result;
    }

    @Override
    public R<?> reprocessCSS(ReprocessCSSDto reprocessCSS) {
        WorkFlowF flowF = workFlowFService.getById(reprocessCSS.getIdentifier());
        if(!StrUtil.equals(CSVSConstant.FLOW_STATUS_REPLIED, flowF.getWorkFlowStatus())) {
            return R.fail("当前工单未回复完毕，无法再处理！");
        }
        log.info("省分===>集团-工单再处理:::{}", JSON.toJSONString(reprocessCSS));

        reprocessCSS.setCreator(SecurityUtils.getLoginUser().getUser().getUserName());
        reprocessCSS.setCreatorTel(SecurityUtils.getLoginUser().getUser().getPhone());
        reprocessCSS.setReprocessingOpinion("工单需要重新处理");

        RequestDto<ReprocessCSSDto> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(reprocessCSS);
        String param = JSON.toJSONString(requestDto);

        log.info("省分===>集团-工单再处理:::请求参数:::{}", param);
        String resp = HttpUtils.post(reprocessCSSUrl, param);
        log.info("省分===>集团-工单再处理:::响应参数:::{}", resp);
        R<?> result = RJT.result(resp);
        if(result.isSuccess()) {
            updateWorkFlowStatus(reprocessCSS.getIdentifier(), CSVSConstant.FLOW_STATUS_PROCESSING);
        }
        return result;
    }

    @Override
    public R<?> urgeCSS(UrgeCSSDto urgeCSS) {
        WorkFlowF flowF = workFlowFService.getById(urgeCSS.getIdentifier());
        if(!StrUtil.equals(CSVSConstant.FLOW_STATUS_PROCESSING, flowF.getWorkFlowStatus())) {
            return R.fail("当前工单已回复完毕，无法催办！");
        }
        log.info("省分===>集团-工单催办:::{}", JSON.toJSONString(urgeCSS));

        urgeCSS.setUrgeTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATETIME_PATTERN));

        RequestDto<UrgeCSSDto> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(urgeCSS);
        String param = JSON.toJSONString(requestDto);

        log.info("省分===>集团-工单催办:::请求参数:::{}", param);
        String resp = HttpUtils.post(urgeCSSUrl, param);
        log.info("省分===>集团-工单催办:::响应参数:::{}", resp);
        R<?> result = RJT.result(resp);
        if(result.isSuccess()) {
            flowF = JSON.parseObject(JSON.toJSONString(urgeCSS), WorkFlowF.class).setWorkFlowStatus(CSVSConstant.FLOW_STATUS_HAS_URGE);
            workFlowFService.updateById(flowF);
        }
        return result;
    }

    @Override
    public R<?> currencyCSS(CurrentCSSDto currentCSS) {
        Map currentMap = workFlowFService.getWorkFlowMap(currentCSS.getIdentifier(),
                                                         WorkFlowSubEnum.CurrencyCSS.getKey(),
                                                         currentCSS.getInterfaceType());
        currentCSS.setParaList(buildParaList(currentMap));

        RequestDto<CurrentCSSDto> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(currentCSS);
        String param = JSON.toJSONString(requestDto);

        log.info("省分===>集团-工单通用接口:::请求参数:::{}", param);
        String resp = HttpUtils.post(currencyCSSUrl, param);
        log.info("省分===>集团-工单通用接口:::响应参数:::{}", resp);
        R<?> result = RJT.result(resp);
        if(result.isSuccess()) {
            //    TODO 业务逻辑
        }
        return result;
    }

    @SneakyThrows
    @Override
    public R<?> syncData(SyncDataDto syncDataDto) {
        commonHandler.buildSyncDataDto(syncDataDto);
        //将信息同步到集团
        RequestDto<String> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(JSON.toJSONString(syncDataDto));
        requestDto.setSign(SignUtil.sign(JSON.toJSONString(requestDto)));
        //数据格式如下
        String sendParam = JSON.toJSONString(requestDto);
        log.info("同步数据到集团===>：{}", sendParam);
        String resp = HttpUtils.post(syncDataCSSUrl, sendParam);
        log.info("同步数据接口响应结果：{}", resp);
        return RJT.result(resp);
    }

    private void syncDataBizHandler(WorkFlowF flowF) {}

    @Override
    @SneakyThrows
    public R<?> getUploadCheckFile() {
        String fileName = FlowConstants.UPLOAD_CHECK_FILE_PREFIX + "*";
        FileUtil.lsSftpFileList(fileName);
        return R.ok();
    }

    @Override
    @SneakyThrows
    public R<?> TestJobCSS(WorkFlowF workFlowF) {
        RequestDto<String> requestDto = RequestDto.buildRequestDTO(envFlag);
        requestDto.setContent(workFlowF.getIdentifier());
        requestDto.setSign(SignUtil.sign(JSON.toJSONString(requestDto)));
        //数据格式如下
        String sendParam = JSON.toJSONString(requestDto);
        log.info("同步删除测试工单===>：{}", sendParam);
        HttpUtils.post(testjobCSSUrl, sendParam);
        //删除本地数据
        workFlowFService.removeById(workFlowF.getIdentifier());
        SpringUtil.getBean(OAService.class).delete("", "", "", workFlowFService.getProcessId(workFlowF.getIdentifier()));
        return R.ok();
    }

    /**
     * 更新工单状态
     *
     * @param identifier identifier
     * @param status     status
     */
    private void updateWorkFlowStatus(String identifier, String status) {
        workFlowFService.updateById(new WorkFlowF(identifier).setWorkFlowStatus(status));
    }

}

