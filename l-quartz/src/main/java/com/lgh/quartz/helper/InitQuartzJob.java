package com.lgh.quartz.helper;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgh.quartz.dto.ScheduleJob;
import com.lgh.quartz.server.IJobServer;

@Component
public class InitQuartzJob {
	
	private static final Logger logger = LoggerFactory.getLogger(InitQuartzJob.class);
	
	@Autowired
	private  StdSchedulerFactory schedulerFactoryBean;
	
	@Autowired
	private IJobServer iJobServer;
	
	@Autowired
	private JobOperationHelper jobOperationHelper;
	
	@PostConstruct
	public void init() {
		try {
			initJob();
		} catch (SchedulerException e) {
			logger.error("初始化任务失败！！！", e);
		}
	}
	
	private void initJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		logger.info("获得定时任务执行器:{}", scheduler.getSchedulerName());
		//获得所有的定时任务
		List<ScheduleJob> jobs = iJobServer.getAll();
		Optional.ofNullable(jobs).ifPresent(obj -> {
			obj.stream().forEach(o ->{
				try {
					jobOperationHelper.addJob(o);
				} catch (SchedulerException e) {
					logger.error("创建任务失败->",e);
				}
			});
		});
		if(!scheduler.isStarted()) {
			scheduler.start();
		}
	}
	
}
