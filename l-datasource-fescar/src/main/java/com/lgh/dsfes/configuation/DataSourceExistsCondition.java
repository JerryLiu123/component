package com.lgh.dsfes.configuation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断配置是否存在
 * @author xiaoming
 *
 */
public class DataSourceExistsCondition implements Condition {
	private static final Logger log = LoggerFactory.getLogger(DataSourceExistsCondition.class);

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment env=context.getEnvironment();//获得上下文
		return true;
	}
}
