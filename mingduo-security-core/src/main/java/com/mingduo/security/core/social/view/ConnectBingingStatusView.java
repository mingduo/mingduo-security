package com.mingduo.security.core.social.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 社交账号绑定状态视图
 *
 * @author : weizc
 * @description:
 * @since 2019/11/6
 */
@Component("connect/status")
public class ConnectBingingStatusView extends AbstractView {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * beannameViewResoler视图渲染器 渲染
     *
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connectionMap = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        Map<String, Boolean> proverIdMap = connectionMap.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey,
                                entry ->
                                        !CollectionUtils.isEmpty(entry.getValue())

                        ));
        // objectMapper.read
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(proverIdMap));
    }
}
