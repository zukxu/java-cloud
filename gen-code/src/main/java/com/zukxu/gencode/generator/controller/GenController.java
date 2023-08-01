package com.zukxu.gencode.generator.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.github.pagehelper.PageInfo;
import com.zukxu.gencode.common.core.base.BaseController;
import com.zukxu.gencode.common.response.R;
import com.zukxu.gencode.generator.domain.GenTable;
import com.zukxu.gencode.generator.domain.GenTableColumn;
import com.zukxu.gencode.generator.service.IGenTableColumnService;
import com.zukxu.gencode.generator.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author zukxu
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    /**
     * 查询代码生成列表
     */
    @GetMapping("/list")
    public R<?> genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        PageInfo<GenTable> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 查询可以导入的数据库列表
     */
    @GetMapping("/db/list")
    public R<?> dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        PageInfo<GenTable> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 查询编辑的数据表相关信息
     */
    @GetMapping(value = "/{tableId}")
    public R<?> getInfo(@PathVariable Long tableId) {
        GenTable table = genTableService.selectGenTableById(tableId);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        Map<String, Object> map = new HashMap<>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return R.ok(map);
    }

    /**
     * 查询数据表字段列表
     */
    @GetMapping(value = "/column/{tableId}")
    public R<?> columnList(@PathVariable Long tableId) {
        startPage();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        PageInfo<GenTableColumn> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 导入表结构（保存）
     */
    @PostMapping("/importTable")
    public R<?> importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return R.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @PutMapping
    public R<?> editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return R.ok();
    }

    /**
     * 删除代码生成
     */
    @DeleteMapping("/{tableIds}")
    public R<?> remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return R.ok();
    }

    /**
     * 预览代码
     */
    @GetMapping("/preview/{tableId}")
    public R<?> preview(@PathVariable("tableId") Long tableId) {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return R.ok(dataMap);
    }

    /**
     * 同步数据库
     */
    @GetMapping("/syncDb/{tableName}")
    public R<?> syncDb(@PathVariable("tableName") String tableName) {
        genTableService.syncDb(tableName);
        return R.ok();
    }

    /**
     * 生成代码（自定义路径）
     */
    @GetMapping("/genCode/{tableName}")
    public R<?> genCode(@PathVariable("tableName") String tableName) {
        genTableService.genCodeByCustomPath(tableName);
        return R.ok();
    }

    /**
     * 批量生成代码
     */
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /*=========================================private method========================================================*/

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), true, data);
        //FileUtil.writeBytes(data, s);
    }

}