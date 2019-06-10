package com.lgh.quartz.helper;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
/**
 * 
 * @ClassName: QuartzJobFactoryDisallowConcurrentExecution
 * @Description: job执行
 * @Author lizhiting
 * @DateTime Jun 3, 2019 10:49:40 AM
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution extends AbstractJobFactory {
	
	
}
