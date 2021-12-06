package com.zukxu.gencode.generator.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.gencode.common.constant.GenConstants;
import com.zukxu.gencode.generator.domain.GenTable;
import com.zukxu.gencode.generator.domain.GenTableColumn;
import com.zukxu.gencode.generator.mapper.GenTableColumnMapper;
import com.zukxu.gencode.generator.mapper.GenTableMapper;
import com.zukxu.gencode.generator.service.IGenTableService;
import com.zukxu.gencode.generator.util.GenUtils;
import com.zukxu.gencode.generator.util.VelocityInitializer;
import com.zukxu.gencode.generator.util.VelocityUtils;
import lombok.SneakyThrows;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 *
 * @author zukxu
 */
@Service
public class GenTableServiceImpl implements IGenTableService {

    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;


    /**
     * 查询业务信息
     *
     * @param id 业务ID
     *
     * @return 业务信息
     */
    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     *
     * @return 业务集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     *
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return genTableMapper.selectDbTableList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     *
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    @Override
    public List<GenTable> selectGenTableAll() {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     *
     * @return 结果
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable) {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if(row > 0) {
            for(GenTableColumn cenTableColumn : genTable.getColumns()) {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务对象
     *
     * @param tableIds 需要删除的数据ID
     *
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteGenTableByIds(Long[] tableIds) {
        genTableMapper.deleteGenTableByIds(tableIds);
        genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @SneakyThrows
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList) {
        String opName = "zukxu";
        try {
            for(GenTable table : tableList) {
                String tableName = table.getTableName();
                GenUtils.initTable(table, opName);
                int row = genTableMapper.insertGenTable(table);
                if(row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for(GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            }
        } catch(Exception e) {
            throw new Exception("导入失败：" + e.getMessage());
        }
    }

    /**
     * 预览代码
     *
     * @param tableId 表编号
     *
     * @return 预览数据列表
     */
    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for(String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * 同步数据库
     *
     * @param tableName 表名称
     */
    @SneakyThrows
    @Override
    @Transactional
    public void syncDb(String tableName) {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        List<String> tableColumnNames = tableColumns.stream()
                .map(GenTableColumn::getColumnName)
                .collect(Collectors.toList());

        List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (ObjectUtil.isEmpty(dbTableColumns)) {
            throw new Exception("同步数据失败，原表结构不存在" );
        }
        List<String> dbTableColumnNames = dbTableColumns.stream()
                .map(GenTableColumn::getColumnName)
                .collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            if (!tableColumnNames.contains(column.getColumnName())) {
                GenUtils.initColumnField(column, table);
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream()
                .filter(column -> !dbTableColumnNames.contains(column.getColumnName()))
                .collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(delColumns)) {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     */
    @SneakyThrows
    @Override
    public void genCodeByCustomPath(String tableName) {
        //文件路径
        String currentPath = System.getProperty("user.dir" );
        log.info("4 当前项目路径:{}" , currentPath);

        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        //系统目录,只生成.java文件
        if (GenConstants.GEN_TYPE_PROJECT.equals(table.getGenType())) {
            templates = templates.stream().filter(t -> StrUtil.contains(t, ".java" ) || StrUtil.contains(t, ".xml" )).collect(Collectors.toList());
        }
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);
            try {
                String path = getGenPath(table, template);
                FileUtil.appendString(sw.toString(), new File(path), CharsetUtil.UTF_8);
            } catch (IORuntimeException e) {
                log.error("渲染模板失败，表名：{},原因：{}" , table.getTableName(), e.getMessage());
            }
        }
    }

    /**
     * 批量生成代码（下载方式）
     *
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //每一张数据表的代码都存放到zip流中
        for(String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }


    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    @SneakyThrows
    @Override
    public void validateEdit(GenTable genTable) {
        if(GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if(StrUtil.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new Exception("树编码字段不能为空");
            } else if(StrUtil.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new Exception("树父编码字段不能为空");
            } else if(StrUtil.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new Exception("树名称字段不能为空");
            } else if(GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
                if(StrUtil.isEmpty(genTable.getSubTableName())) {
                    throw new Exception("关联子表的表名不能为空");
                } else if(StrUtil.isEmpty(genTable.getSubTableFkName())) {
                    throw new Exception("子表关联的外键名不能为空");
                }
            }
        }
    }

    /**
     * 设置主键列信息
     *
     * @param table 业务表信息
     */
    public void setPkColumn(GenTable table) {
        for(GenTableColumn column : table.getColumns()) {
            if(column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if(ObjectUtil.isNull(table.getPkColumn())) {
            table.setPkColumn(table.getColumns().get(0));
        }
        if(GenConstants.TPL_SUB.equals(table.getTplCategory())) {
            for(GenTableColumn column : table.getSubTable().getColumns()) {
                if(column.isPk()) {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if(ObjectUtil.isNull(table.getSubTable().getPkColumn())) {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     *
     * @param table 业务表信息
     */
    public void setSubTable(GenTable table) {
        String subTableName = table.getSubTableName();
        if(StrUtil.isNotEmpty(subTableName)) {
            table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
        }
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if(ObjectUtil.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /*=========================================private method========================================================*/

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置相关生成表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        //初始化模板
        VelocityInitializer.initVelocity();
        //获取初始化信息
        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);
            try {
                // 添加到zip流中
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                //在此处不关闭流
                IoUtil.writeUtf8(zip, false, sw.toString());
                IoUtil.flush(zip);
                //结束打包
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：{},原因：{}" , table.getTableName(), e.getMessage());
            }
        }
    }

    /**
     * 获取代码生成地址
     *
     * @param table    业务表信息
     * @param template 模板文件路径
     * @return 生成地址
     */
    private String getGenPath(GenTable table, String template) {
        String genPath = table.getGenPath();
        if (StrUtil.equals(genPath, "/" )) {
            return System.getProperty("user.dir" ) + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}