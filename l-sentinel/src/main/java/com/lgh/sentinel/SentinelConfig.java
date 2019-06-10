package com.lgh.sentinel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;

@Configuration
public class SentinelConfig implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(SentinelConfig.class);
	
	@Autowired(required=false)
	private RequestOriginParser requestOriginParser;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(requestOriginParser == null) {  
			//注入默认的权限拦截，获得 调用者 信息的方法
			logger.info("--------------");
			WebCallbackManager.setRequestOriginParser(new IPAddrRequestOriginParser());
		}else {
			logger.info("==============");
			WebCallbackManager.setRequestOriginParser(requestOriginParser);
		}
	}

}
