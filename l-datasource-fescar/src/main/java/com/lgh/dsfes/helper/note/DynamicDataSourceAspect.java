package com.lgh.dsfes.helper.note;

import javax.sql.DataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lgh.dsfes.helper.DBContextHolder;
import com.lgh.dsfes.helper.DynamicDataSource;


@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
@ConditionalOnBean({DataSource.class})
public class DynamicDataSourceAspect {
	private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
	@Pointcut("@annotation(source)")
	public void cut(TargetDataSource source){}
	
	@Before(value="cut(source)")
	public void changeDataSource(JoinPoint point, TargetDataSource source) throws Throwable{
		//TargetDataSource targetDataSource = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(TargetDataSource.class);  
		String dsId = source.value();
		if (!DynamicDataSource.getInstance().getTargetDataSource().containsKey(dsId)) {
    	  	logger.info("数据源["+source.value()+"]不存在，使用默认数据源");
	    } else {
	        //找到的话，那么设置到动态数据源上下文中。
	        DBContextHolder.setDataSource(source.value());
	        logger.info("切换为数据源 : "+source.value());
	    }
	}
	
	@After(value="cut(source)")
	public void after(TargetDataSource source) {
		DBContextHolder.clearDataSource();
		logger.debug("恢复为默认数据源");		
	}
}
