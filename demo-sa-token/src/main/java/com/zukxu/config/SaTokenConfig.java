package com.zukxu.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.zukxu.common.result.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * 配置类
 *
 * @author xupu
 * @Date 2021-11-05 15:09
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册 [sa-token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定 [拦截路由] 与 [放行路由]
                .addInclude("/**").addExclude()
                // 认证函数: 每次请求执行
                .setAuth(r -> {
                    System.out.println("---------- sa全局认证");
                    SaRouter.match(Collections.singletonList("/**"))
                            .notMatch(Arrays.asList(
                            "/login",
                            "/druid/**",
                            "/default/**",
                            "/",
                            "/doc.html",
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "swagger/**",
                            "/webjars/**",
                            "/swagger-ui.html/*",
                            "/swagger-resources",
                            "/*.html",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/**/*.svg",
                            "/**/*.ico",
                            "/**/*.png",
                            "/**/*.jpg",
                            "/**/*.xlsx",
                            "/**/*.docx",
                            "/**/*.pdf",
                            "/webSocket/**",
                            "/*/api-docs",
                            "/v2/api-docs-ext")).check(StpUtil::checkLogin);
                    // 路由一定要有区分性
                    SaRouter.match("/user", () -> StpUtil.checkPermission("0001"));
                    SaRouter.match("/user/get/{id}", () -> StpUtil.checkPermission("001101"));

                })

                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    return R.fail(e.getMessage());
                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("sa-server")
                            // 是否可以在iframe显示视图：DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-Frame-Options", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                    ;
                });

    }

}
