package com.zukxu.excel.model.satisfaction;

import com.zukxu.excel.annotations.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-29 19:38
 */
@Data
@Accessors(chain = true)
public class Sn5g {
    @Excel(name="编码")private String var1;
    @Excel(name="地州名称")private String var2;
    @Excel(name="样本量")private String var3;
    @Excel(name="手机客户满意度")private String var4;
    @Excel(name="手机网络质量")private String var5;
    @Excel(name="语音通话质量")private String var6;
    @Excel(name="手机上网质量")private String var7;
    @Excel(name="上网体验-视频业务")private String var8;
    @Excel(name="上网体验-游戏业务")private String var9;
    @Excel(name="手机资费套餐")private String var10;
    @Excel(name="资费规则清晰度")private String var11;
    @Excel(name="套餐设计合理性")private String var12;
    @Excel(name="套餐适配度")private String var13;
    @Excel(name="套餐办理便捷性")private String var14;
    @Excel(name="套餐办理规范性")private String var15;
    @Excel(name="账单服务")private String var16;
    @Excel(name="触点服务质量")private String var17;
    @Excel(name="整体感知")private String var18;
    @Excel(name="推荐意愿")private String var19;

}
