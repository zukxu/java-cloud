package com.zukxu.test.others.cosmic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * cosmic接口类
 * </p>
 *
 * @author xupu
 * @since 2022-09-29 09:59
 */
@Slf4j
@RestController
@RequestMapping("/cosmic")
public class CosmicController {
    /*


    @GetMapping("/excel")
    @SneakyThrows
    @Transactional
    public void exportCosmic() {
        List<CosmicDTO> list = buildCosmic();
        ExcelUtil<CosmicDTO> util = new ExcelUtil<>(CosmicDTO.class);
        String fileName = WFUtil.timeFormat(LocalDateTime.now());
        util.exportExcel(ServletUtils.getResponse(), list, fileName);
    }

    private List<CosmicDTO> buildCosmic() {
        List<CosmicDTO> cosmicDTOList = new ArrayList<>();
        RepositoryService repositoryService = SpringUtil.getBean(RepositoryService.class);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                                                        .latestVersion()
                                                        .orderByProcessDefinitionCategory()
                                                        .asc()
                                                        .active()
                                                        .list();
        List<String> ids = list.stream().map(ProcessDefinition::getId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
        String[] names = new String[]{ "本省-重大事故投诉处理流程", "本省-各域流程SLA审核工单流程", "本省-一线声音工单超时督办单流程", "本省-本省问题整改任务单", "本省-业务受理类月抽查任务单流程", "本省-—线声音问题本省处理流程", "集团-通用版任务单流程", "集团-通用版督办单流程", "集团-升级投诉预测预警单流程", "本省-服务标准指标预警流程", "本省-集团不知情定制预测处理流程-省分", "本省-集团不知情定制预测处理流程-地市", "本省-—线声音接收集团转单工单流程", "本省-业务受理类日核查任务单流程", "本省-业务受理类月抽查任务单流程", "本省-一级关怀工单投诉处理流程", "本省-普通投诉日增量预警流程", "本省-普通投诉连续增长预警流程", "客服域全流程管控预警" };
        for(int i = 0; i < names.length; i++) {
            String id = ids.get(i);
            BpmnModel bpmnModel = null;
            try {
                bpmnModel = repositoryService.getBpmnModel(id);
            } catch(Exception e) {
                 id = ids.get(names.length+1);
                bpmnModel = repositoryService.getBpmnModel(id);
            }
            if(ObjectUtil.isNotEmpty(bpmnModel)) {
                assert bpmnModel != null;
                List<Process> processes = bpmnModel.getProcesses();
                for(Process process : processes) {
                    //String name = process.getName();
                    String name = names[i];
                    System.out.println("===================" + name + "===========================");
                    buildModel1(cosmicDTOList, name);
                    buildModel2(cosmicDTOList, name, process.getFlowElements());
                }
            }
        }
        return cosmicDTOList;
    }

    private void buildModel2(List<CosmicDTO> cosmicDTOList, String name, Collection<FlowElement> flowElements) {
        String[] opName = new String[]{ "工单审批流程图", "工单审批", "工单审批", "工单驳回", "工单催办", "工单撤单" };
        String[] t1 = new String[]{ "输入nameunamep1展示请求", "读取nameunamep1", "展示nameunamep1", "E,R,X", "uname任务编号,name工单编号,name流程编号", "uname任务编号,name工单编号,name流程编号,name部署编号", "name工单子类型,name工单流程图信息,name工单节点信息" };
        String[] t2 = new String[]{ "接收nameunamep1请求", "获取name工单信息", "保存name工单审批意见", "E,R,W", "uname审批信息,uname审批附件,name工单编号,name流程编号,uname任务编号", "uname任务编号,name工单编号,name流程编号", "uname审批信息,uname审批附件,name工单编号,name流程编号,uname任务编号" };
        String[] t3 = new String[]{ "输入nameunamep1请求", "保存name工单驳回意见", "返回name工单驳回信息", "E,W,X", "uname驳回信息,uname驳回附件,uname任务编号,name工单编号,name流程编号", "uname任务编号,uname驳回信息,uname驳回附件,name工单编号,name流程编号", "uname驳回消息,name工单编号,name流程编号,uname任务编号" };
        String[] t4 = new String[]{ "触发nameunamep1", "读取工单name信息", "发送name催办信息", "E,R,X", "uname催办信息,uname催办附件,name工单编号,name流程编号", "name工单编号,name工单类型,name工单子类型,name工单细类,name工单标题,name工单内容,name附件", "uname催办信息,name工单编号,name流程编号,uname任务编号" };
        String[] t5 = new String[]{ "接收nameunamep1请求", "读取name工单信息", "修改name工单状态为撤单", "E,R,W", "uname撤单信息,name工单编号,name流程编号", "name工单状态,name工单编号,name工单类型,name工单子类型,name工单细类,name工单标题,name工单内容,name附件", "uname撤单信息,uname任务编号,name工单编号,name流程编号,name工单状态" };

        if(CollectionUtil.isNotEmpty(flowElements)) {
            List<String> unameList = flowElements.stream()
                                                 .filter(UserTask.class::isInstance)
                                                 .map(FlowElement::getName)
                                                 .filter(t -> !StrUtil.equals("申请人", t))
                                                 .collect(Collectors.toList());
            unameList.forEach(uname -> {
                for(int i = 1; i <= opName.length; i++) {
                    switch(i) {
                        case 1:
                            buildRow(cosmicDTOList, name, uname, opName[i - 1], t1);
                            break;
                        case 2:
                            buildRow(cosmicDTOList, name, uname, opName[i - 1], t2);
                            break;
                        case 3:
                            buildRow(cosmicDTOList, name, uname, opName[i - 1], t3);
                            break;
                        case 4:
                            buildRow(cosmicDTOList, name, uname, opName[i - 1], t4);
                            break;
                        case 5:
                            buildRow(cosmicDTOList, name, uname, opName[i - 1], t5);
                            break;
                        default:
                            break;

                    }

                }
            });

            System.out.println("==============================================");
        }
    }

    private void buildRow(List<CosmicDTO> cosmicDTOList, String name, String uname, String op, String[] tt) {
        String[] split = tt[3].split(",");
        CosmicDTO c1 = CosmicDTO.builder()
                                .E(name + uname + op)
                                .F(tt[0].replace("uname", uname).replace("name", name).replace("p1", op))
                                .G(split[0])
                                .H(tt[4].replace("uname", uname).replace("name", name))
                                .build();
        CosmicDTO c2 = CosmicDTO.builder()
                                //.E(name + uname + op)
                                .F(tt[1].replace("uname", uname).replace("name", name).replace("p1", op))
                                .G(split[1])
                                .H(tt[5].replace("uname", uname).replace("name", name))
                                .build();
        CosmicDTO c3 = CosmicDTO.builder()
                                //.E(name + uname + op)
                                .F(tt[2].replace("uname", uname).replace("name", name).replace("p1", op))
                                .G(split[2])
                                .H(tt[6].replace("uname", uname).replace("name", name))
                                .build();
        cosmicDTOList.add(c1);
        cosmicDTOList.add(c2);
        cosmicDTOList.add(c3);


    }

    private void buildModel1(List<CosmicDTO> cosmicDTOList, String name) {
        String[] opName = new String[]{ "创建", "修改", "删除" };
        String[] t1 = new String[]{ "输入p1name工单审批流程信息", "name工单编号,name工单类型,name工单子类型,name工单细类,name工单标题,name工单内容,name附件", "name工单编号,name流程编号,name审批信息,name审批附件", "name工单编号,name流程编号,name删除审批信息" };
        String[] t2 = new String[]{ "p1name工单审批流程", "name工单编号,name工单类型,name工单子类型,name工单细类,name工单标题,name工单内容,name附件", "name工单编号,name流程编号,name审批信息,name审批附件", "name工单编号,name流程编号,name删除审批信息" };
        String cNname = name;
        cNname = cNname.endsWith("流程") ? cNname + "管控" : cNname + "流程管控";
        for(int i = 0; i < opName.length; i++) {
            String op = opName[i];
            CosmicDTO c1 = CosmicDTO.builder()
                                    .C(cNname)
                                    .E(name + "审批流程" + op)
                                    .F(t1[0].replace("p1", op).replace("name", name))
                                    .G("E")
                                    .H(t1[i + 1].replace("name", name))
                                    .build();
            cosmicDTOList.add(c1);
            CosmicDTO c2 = CosmicDTO.builder()
                                    //.E(name + "审批流程" + op)
                                    .F(t2[0].replace("p1", op).replace("name", name))
                                    .G("W")
                                    .H(t2[i + 1].replace("name", name))
                                    .build();
            cosmicDTOList.add(c2);
        }

    }

        */
    /**
     * 需求文档
     */    /*

    @GetMapping("/doc")
    @SneakyThrows
    @Transactional
    public R<?> exportDoc() {
        String fileName = WFUtil.timeFormat(LocalDateTime.now());
        FileWriter fw = new FileWriter("D:\\Downloads\\" + fileName + "需求说明书.txt");
        BufferedWriter bfw = new BufferedWriter(fw);

        List<CosmicDTO> list = buildCosmic();
        String[] names = new String[]{ "本省-重大事故投诉处理流程", "本省-各域流程SLA审核工单流程", "本省-一线声音工单超时督办单流程", "本省-本省问题整改任务单", "本省-业务受理类月抽查任务单流程", "本省-—线声音问题本省处理流程", "集团-通用版任务单流程", "集团-通用版督办单流程", "集团-升级投诉预测预警单流程", "本省-服务标准指标预警流程", "本省-集团不知情定制预测处理流程-省分", "本省-集团不知情定制预测处理流程-地市", "本省-—线声音接收集团转单工单流程", "本省-业务受理类日核查任务单流程", "本省-业务受理类月抽查任务单流程", "本省-一级关怀工单投诉处理流程", "本省-普通投诉日增量预警流程", "本省-普通投诉连续增长预警流程", "客服域全流程管控预警" };
        String noC = "1.1.";
        String noE = "1.1.";
        for(int i = 0; i < names.length; i++) {
            int finalI = i;
            List<CosmicDTO> collect = list.stream().filter(t -> t.getF().contains(names[finalI])).collect(Collectors.toList());
            String cName = "### " + noC + (i + 1) + ". " + collect.stream().map(CosmicDTO::getC).distinct().findFirst().get();
            bfw.write(cName);
            bfw.newLine();
            bfw.newLine();
            List<String> eName = collect.stream().map(CosmicDTO::getE).filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList());
            for(int j = 0; j < eName.size(); j++) {
                String ee = "#### " + noE + (i + 1) + "." + (j + 1) + ". " + eName.get(j);
                bfw.write(ee);
                bfw.newLine();
                bfw.newLine();
                bfw.flush();
            }
        }
        bfw.flush();
        fw.close();
        bfw.close();
        return R.ok();
    }
    */

}