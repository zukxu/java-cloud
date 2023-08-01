package com.zukxu.flowable.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zukxu.common.result.R;
import com.zukxu.common.utils.ServletUtils;
import com.zukxu.flowable.model.entity.SysForm;
import com.zukxu.flowable.service.SysFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 流程表单Controller
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 10:07
 */
@Api(tags = "工单-表单接口")
@RestController
@RequestMapping("/flowable/form")
public class FlowFormController {

    @Autowired
    private SysFormService sysFormService;

    @ApiOperation("查询流程表单列表")
    @GetMapping("/list")
    public R<?> list(String formName) {
        PageHelper.startPage(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"));
        List<SysForm> list = sysFormService.selectSysFormList(formName);
        return R.ok(new PageInfo<>(list));
    }

    /**
     * 导出流程表单列表
     */
    //@PreAuthorize("@ss.hasPerm('flowable:form:export')")
    @ApiOperation("导出流程列表")
    @GetMapping("/export")
    public void export(String formName) {
        List<SysForm> list = sysFormService.selectSysFormList(formName);
        //ExcelUtil<SysForm> util = new ExcelUtil<>(SysForm.class);
        //util.exportExcel(ServletUtils.getResponse(),list, "form");
    }

    //@PreAuthorize("@ss.hasPerm('flowable:form:query')")
    @ApiOperation("获取详情")
    @GetMapping(value = "/{formId}")
    public R<?> getInfo(@PathVariable("formId") String formId) {
        return R.ok(sysFormService.selectSysFormById(formId));
    }

    //@PreAuthorize("@ss.hasPerm('flowable:form:add')")
    @ApiOperation("新增流程表单")
    @PostMapping
    public R<?> add(@RequestBody SysForm sysForm) {
        //校验保证表单名称唯一
        SysForm one = sysFormService.getOne(new QueryWrapper<SysForm>().lambda().eq(SysForm::getFormName, sysForm.getFormName()));
        if (ObjectUtil.isNotEmpty(one)) {
            return R.fail("表单名称已存在");
        }
        sysForm.setCreateBy("admin");
        sysForm.setCreateTime(LocalDateTime.now());
        return R.ok(sysFormService.save(sysForm));
    }

    //@PreAuthorize("@ss.hasPerm('flowable:form:edit')")
    @ApiOperation("更新流程表单")
    @PutMapping
    public R<?> edit(@RequestBody SysForm sysForm) {
        sysForm.setUpdateBy("admin");
        return R.ok(sysFormService.updateById(sysForm));
    }

    //@PreAuthorize("@ss.hasPerm('flowable:form:delete')")
    @ApiOperation("删除流程表单")
    @DeleteMapping("/{formIds}")
    public R<?> remove(@PathVariable String[] formIds) {
        if (sysFormService.isBindTask(formIds)) {
            return R.fail("存在已绑定表单,删除失败");
        }
        return R.ok(sysFormService.removeByIds(Arrays.asList(formIds)));
    }

}
