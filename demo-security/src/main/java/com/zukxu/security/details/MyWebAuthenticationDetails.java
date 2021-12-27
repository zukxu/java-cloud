package com.zukxu.security.details;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 15:56
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 603157756847194200L;
    private boolean isCaptcha;

    public MyWebAuthenticationDetails(HttpServletRequest req) {
        super(req);
        String code = req.getParameter("code");
        String verify_code = (String) req.getSession().getAttribute("verify_code");
        if(code != null && code.equals(verify_code)) {
            isCaptcha = true;
        }
    }

    public boolean isCaptcha() {
        return isCaptcha;
    }
}
