package com.zukxu.flowable.service;
import com.github.pagehelper.PageInfo;
import com.zukxu.flowable.domain.dto.ActProcDefDTO;
import com.zukxu.flowable.domain.dto.FlowCategoryDTO;
import com.zukxu.flowable.domain.dto.ProcessDefinitionDTO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FlowDefinitionService {
    /**
     * 流程定义列表
     *
     * @param pageNum  num
     * @param pageSize size
     * @return PageInfo<ActProcDefDTO>
     */
    PageInfo<ActProcDefDTO> list(Integer pageNum, Integer pageSize);

    /**
     * 导入文件
     *
     * @param name     文件名
     * @param category 分类
     * @param in       InputStream
     */
    void importFile(String name, String category, InputStream in);

    /**
     * 导出数据
     * @param processDefinition
     * @return
     */
    PageInfo<ProcessDefinitionDTO> listProcessDefinition(ProcessDefinitionDTO processDefinition);

    /**
     * 根据部署id查询部署的xml内容
     * @param deployId
     * @return
     */
    String readXml(String deployId);

    /**
     * 根据部署id查询流程图片
     *
     * @param deployId
     * @return
     */
    InputStream readImage(String deployId);

    /**
     * 启动流程实例
     *
     * @param procDefId
     * @param variables
     * @return
     */
    String startProcessInstanceById(String procDefId, Map<String, Object> variables);

    /**
     * 激活/挂起流程实例
     *
     * @param state    1:激活,2:挂起
     * @param deployId 流程部署ID
     */
    void updateState(Integer state, String deployId);

    /**
     * 删除流程实例
     *
     * @param deployId 流程部署id
     */
    void delete(String deployId);

    /**
     * 获取分类列表 分类数据添加自数据库添加
     * @return
     */
    List<FlowCategoryDTO> listCategory();
}
