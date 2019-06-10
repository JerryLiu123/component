package com.lgh.sys.manage.helper.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.bean.ResultJson;
import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.exception.CustomException;

public class HttpHelper {
	private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);
	
    public static String getBodyString(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        //InputStream inputStream = null;
        //BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream();
        	 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        	){
            //inputStream = request.getInputStream();
            //reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        	logger.error("读取request 信息错误！！！", e);
        } 
//        finally {
////            if (inputStream != null) {
////                try {
////                    inputStream.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////            if (reader != null) {
////                try {
////                    reader.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
//        }
        return sb.toString();
    }
    
    /**
     * 
     * @Title: getUserByRequest
     * @Description: 获得登陆后的用户信息
     * @Author lizhiting
     * @DateTime May 30, 2019 10:17:09 AM
     * @param request
     * @return
     */
    public static UserDetail getUserByRequest(HttpServletRequest request) {
    	return Optional.ofNullable(request.getAttribute("userDetail"))
    			.map(u -> (UserDetail)u)
    			.orElseThrow(() -> new CustomException("获得用户信息为空"));
    }
    
    
    /**
     * 
     * @Title: write
     * @Description: 向页面返回json数据
     * @Author lizhiting
     * @DateTime May 30, 2019 9:44:06 AM
     * @param response
     * @param body
     * @throws IOException
     */
    public static void write(HttpServletResponse response, String body, ResultCode code) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(ResultJson.failure(code, body).toString());
        printWriter.flush();
    }
}
