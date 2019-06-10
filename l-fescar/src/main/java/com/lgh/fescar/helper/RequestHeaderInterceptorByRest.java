package com.lgh.fescar.helper;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import io.seata.core.context.RootContext;

/**
 * 
 * @ClassName: RequestHeaderInterceptorByRest
 * @Description: 拦截resttemplate 请求，用于增加fescar ID来进行分布式事务
 * @Author lizhiting
 * @DateTime May 9, 2019 10:58:00 AM
 */
@Component
public class RequestHeaderInterceptorByRest implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// TODO Auto-generated method stub
		HttpHeaders headers = request.getHeaders();
		if(headers.containsKey("Fescar-Xid")) {
			return execution.execute(request, body);
		}
		String xid = RootContext.getXID();
        if(StringUtils.isNotBlank(xid)){
        	headers.add("Fescar-Xid",xid);
        }
		return execution.execute(request, body);
	}

}
