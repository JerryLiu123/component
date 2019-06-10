package com.lgh.quartz.helper;

import org.springframework.context.ApplicationEvent;

import com.lgh.quartz.dto.ScheduleJob;

/**
 * 
 * @ClassName: JobRunSuccessEvent
 * @Description: 任务执行成功后事件
 * @Author lizhiting
 * @DateTime Jun 3, 2019 2:19:58 PM
 */
@SuppressWarnings("serial")
public class JobRunSuccessEvent extends ApplicationEvent{
	
	private ScheduleJob scheduleJob;

	public JobRunSuccessEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public ScheduleJob getScheduleJob() {
		return scheduleJob;
	}

	public void setScheduleJob(ScheduleJob scheduleJob) {
		this.scheduleJob = scheduleJob;
	}

	public JobRunSuccessEvent(Object source, ScheduleJob scheduleJob) {
		super(source);
		this.scheduleJob = scheduleJob;
	}

}
