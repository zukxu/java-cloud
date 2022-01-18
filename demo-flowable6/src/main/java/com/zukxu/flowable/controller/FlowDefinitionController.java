package com.zukxu.flowable.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.zukxu.common.constant.Const;
import com.zukxu.common.result.R;
import com.zukxu.flowable.common.InitUtils;
import com.zukxu.flowable.common.constant.ProcessConstants;
import com.zukxu.flowable.common.entity.SysRole;
import com.zukxu.flowable.common.entity.SysUser;
import com.zukxu.flowable.domain.dto.FlowCategoryDTO;
import com.zukxu.flowable.domain.dto.ProcessDefinitionDTO;
import com.zukxu.flowable.domain.entity.SysFormDeploy;
import com.zukxu.flowable.domain.vo.FlowSaveXmlVo;
import com.zukxu.flowable.service.FlowDefinitionService;
import com.zukxu.flowable.service.SysFormDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作流程定义
 * </p>
 *
 * @author xupu
 * @since 2021/12/15 20:10
 */
@Slf4j
@Api(tags = "流程定义")
@RestController
@RequestMapping("/flowable/definition")
public class FlowDefinitionController {

    @Autowired
    private FlowDefinitionService flowDefinitionService;

    //@Autowired
    //private SysUserService sysUserService;
    //@Autowired
    //private SysDeptService sysDeptService;
    //@Resource
    //private SysRoleService sysRoleService;

    @Autowired
    private SysFormDeployService sysFormDeployService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "流程定义列表")
    public R<?> list(Integer pageNum, Integer pageSize) {
        return R.ok(flowDefinitionService.list(pageNum, pageSize));
    }


    @ApiOperation(value = "导入流程文件")
    @PostMapping("/import")
    public R<?> importFile(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String category,
                           MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        if (!ArrayUtil.containsIgnoreCase(ProcessConstants.BPMN_FILE_SUFFIX_ARR, suffix)) {
            return R.fail("文件格式不支持" + suffix);
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return R.ok(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return R.ok("导入成功");
    }


    @ApiOperation(value = "导出")
    @GetMapping("/export")
    public void export() {
        PageInfo<ProcessDefinitionDTO> pageInfo = flowDefinitionService.listProcessDefinition(new ProcessDefinitionDTO());
        List<ProcessDefinitionDTO> list = pageInfo.getList();
        //ExcelUtil<ProcessDefinitionDTO> util = new ExcelUtil<>(ProcessDefinitionDTO.class);
        //util.exportExcel(ServletUtils.getResponse(), list, "流程定义数据");
    }

    @ApiOperation(value = "读取xml文件")
    @GetMapping("/readXml/{deployId}")
    public R<?> readXml(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId) {
        try {
            return R.ok(flowDefinitionService.readXml(deployId));
        } catch (Exception e) {
            return R.fail("加载xml文件异常");
        }

    }

    @ApiOperation(value = "读取图片文件")
    @GetMapping("/readImage/{deployId}")
    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(flowDefinitionService.readImage(deployId));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @ApiOperation(value = "保存流程设计器内的xml文件")
    @PostMapping("/save")
    public R<?> save(@RequestBody FlowSaveXmlVo vo) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8));
            flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
        } catch (Exception e) {
            log.error("新增失败:", e);
            return R.fail(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return R.ok().message("保存成功");
    }

    /*==========================流程表单挂载方法==========================================*/
    @ApiOperation("挂载流程表单")
    @PostMapping("/addDeployForm")
    public R<?> addDeployForm(@RequestBody SysFormDeploy sysDeployForm) {
        return R.ok(sysFormDeployService.save(sysDeployForm));
    }

    @ApiOperation("修改挂载流程表单")
    @PutMapping("/updDeployForm")
    public R<?> updDeployForm(@RequestBody SysFormDeploy sysDeployForm) {
        return R.ok(sysFormDeployService.update(sysDeployForm, new UpdateWrapper<SysFormDeploy>().lambda().eq(SysFormDeploy::getDeployId, sysDeployForm.getDeployId())));
    }

    /*==========================流程实例方法==========================================*/
    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/start/{procDefId}")
    public R<?> start(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                      @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        String message = flowDefinitionService.startProcessInstanceById(procDefId, variables);
        return StrUtil.equals(Const.SUCCESS, message) ? R.ok().message("流程实例启动成功") : R.fail(message);
    }

    @ApiOperation(value = "激活或挂起流程定义")
    @PutMapping(value = "/updateState")
    public R<?> updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                            @ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return R.ok();
    }

    @ApiOperation(value = "删除流程")
    @DeleteMapping(value = "/delete")
    public R<?> delete(@ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        sysFormDeployService.deleteByDeployId(deployId);
        return R.ok();
    }

    @ApiOperation(value = "指定流程办理人员列表")
    @GetMapping("/userList")
    public R<?> userList() {
        //List<SysUser> list = InitUtils.getUserList();
        List<SysUser> userList = InitUtils.getUserList();
        return R.ok(userList);
    }

    @ApiOperation(value = "指定流程办理组列表")
    @GetMapping("/roleList")
    public R<?> roleList() {
        //List<SysRole> list = sysRoleService.selectRoleList(new SysRole());
        List<SysRole> roleList = InitUtils.getRoleList();
        return R.ok(roleList);
    }

    @ApiOperation(value = "流程分类列表")
    @GetMapping("/categoryList")
    public R<?> categoryList() {
        List<FlowCategoryDTO> list = flowDefinitionService.listCategory();
        return R.ok(list);
    }

}