package com.lgh.fescar.configuation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lgh.fescar.helper.FescarXidFilter;
import com.lgh.fescar.helper.RequestHeaderInterceptorByRest;

import io.seata.spring.annotation.GlobalTransactionScanner;

@Configuration
@Import(value= {RequestHeaderInterceptorByRest.class})
public class FescarConfiguration {

	@Value("${engine.fascar.id:myapplicationId}")
	private String applicationId;
	
	@Value("${engine.fascar.group:mygroup}")
	private String group;
	
	
	@ConditionalOnMissingBean
	@Bean
	public FescarXidFilter fescarXidFilter(){
	    return new FescarXidFilter();
	}

	/**
	 * 注册一个StatViewServlet
	 *
	 * @return global transaction scanner
	 */
	@ConditionalOnMissingBean
	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		//applicationId 在单机部署多个服务的话 需要唯一
		GlobalTransactionScanner globalTransactionScanner = new GlobalTransactionScanner(applicationId, group);
		return globalTransactionScanner;
	}
}
