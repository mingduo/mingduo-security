package com.mingduo.security.core.social.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 *
 * 绑定结果视图
 * 
 * @author : weizc
 * @description:
 * @since 2019/11/6
 */
public class WeixinConnectView extends AbstractView {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 社交账号绑定成功视图
     *
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        /**
         * 有connection是绑定
         * 没有是解绑
         */
        if(Objects.nonNull(model.get("connection"))){
            response.getWriter().write("<h3>绑定成功</h3>");
        }else {
            response.getWriter().write("<h3>解绑成功</h3>");
        }
    }
}
