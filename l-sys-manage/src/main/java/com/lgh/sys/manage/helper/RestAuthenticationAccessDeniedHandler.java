package com.lgh.sys.manage.helper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.bean.ResultJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @ClassName: RestAuthenticationAccessDeniedHandler
 * @Description: 权限不足时执行此方法
 * @Author lizhiting
 * @DateTime May 16, 2019 9:47:14 AM
 */
@Component
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = ResultJson.failure(ResultCode.FORBIDDEN, e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
