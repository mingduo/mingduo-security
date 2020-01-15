package com.cloud.security.is.user.api.interceptor;

import com.cloud.security.is.user.api.log.AuditLog;
import com.cloud.security.is.user.api.log.AuditLogRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Component
public class AuditLogInterceptor implements HandlerInterceptor {


    @Autowired
    AuditLogRespository auditLogRespository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        AuditLog auditLog = new AuditLog();
        String requestMethod = request.getMethod();
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            requestMethod= String.format("%s::%s", requestMethod, method.getMethod());
        }
        auditLog.setMethod(requestMethod).setPath(request.getRequestURI());

        auditLogRespository.save(auditLog);

        request.getSession().setAttribute("auditLogId",auditLog.getId());
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long id = (Long) request.getSession().getAttribute("auditLogId");

        Optional<AuditLog> auditLog = auditLogRespository.findById(id);
        auditLog.ifPresent(l->auditLogRespository.save(l.setStatus(response.getStatus())));



    }
}
