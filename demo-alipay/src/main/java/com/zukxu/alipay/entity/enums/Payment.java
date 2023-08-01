package com.zukxu.alipay.entity.enums;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/18 0018 14:44
 */
public enum Payment {
    Alipay(true, "支付宝支付"), WxPay(false, "微信支付");
    private boolean enable;
    private String name;

    Payment(boolean enable, String name) {
        this.enable = enable;
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
