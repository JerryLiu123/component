package com.lgh.sys.manage.helper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.bean.ResultJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 
 * @ClassName: JwtAuthenticationEntryPoint
 * @Description: 认证失败后进入此方法
 * @Author lizhiting
 * @DateTime May 16, 2019 9:45:21 AM
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = ResultJson.failure(ResultCode.UNAUTHORIZED, authException.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
