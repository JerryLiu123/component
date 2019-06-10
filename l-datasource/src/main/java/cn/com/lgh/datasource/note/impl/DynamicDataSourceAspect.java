package cn.com.lgh.datasource.note.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.com.lgh.datasource.DynamicDataSource;
import cn.com.lgh.datasource.note.TargetDataSource;
import cn.com.lgh.datasource.util.DBContextHolder;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class DynamicDataSourceAspect {
	private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
	@Pointcut("@annotation(source)")
	public void cut(TargetDataSource source){}
	
	@Before(value="cut(source)")
	public void changeDataSource(JoinPoint point, TargetDataSource source) throws Throwable{
		//TargetDataSource targetDataSource = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(TargetDataSource.class);  
		String dsId = source.value();
		if (!DynamicDataSource.getInstance().getTargetDataSource().containsKey(dsId)) {
    	  	logger.warn("数据源["+source.value()+"]不存在，使用默认数据源");
	    } else {
	        //找到的话，那么设置到动态数据源上下文中。
	        DBContextHolder.setDataSource(source.value());
	        if(logger.isDebugEnabled()) {
	        	logger.debug("切换为数据源 : "+source.value());
	        }
	    }
	}
	
	@After(value="cut(source)")
	public void after(TargetDataSource source) {
		DBContextHolder.clearDataSource();
		if(logger.isDebugEnabled()) {
			logger.debug("切换为默认数据源");	
		}
	}
}
