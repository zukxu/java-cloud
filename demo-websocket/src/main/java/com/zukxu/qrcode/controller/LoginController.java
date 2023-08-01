package com.zukxu.qrcode.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.zukxu.common.result.R;
import com.zukxu.qrcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录controller
 *
 * @author zukxu
 * CreateTime: 2021/7/13 0013 16:23
 */
@Controller
@RequestMapping
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user/getLoginQr")
    public void getQRC(ModelAndView mav) {
        String qrc = userService.createQRC();
        mav.addObject("uuid", qrc);

        QrConfig qrConfig = new QrConfig().setHeight(300).setWidth(300);
        String jpg = QrCodeUtil.generateAsBase64(qrc, qrConfig, "jpg");
        mav.addObject("img", jpg);
    }

    /**
     * 扫描接口
     *
     * @param token
     * @param userId
     * @param projId
     * @return
     */
    @GetMapping("/bindUserIdAndToken")
    @ResponseBody
    public R<?> bindUserIdAndToken(@RequestParam("token") String token, @RequestParam("userId") Integer userId, @RequestParam(required = false, value = "projId") Integer projId) {

        try {
            return R.ok(userService.bindUserIdAndToken(userId, token, projId));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(500, e.getMessage());
        }

    }
}
