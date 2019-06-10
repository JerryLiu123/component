package com.lgh.quartz.helper;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgh.quartz.dto.ScheduleJob;
import com.lgh.quartz.model.TTimetaskLog;
import com.lgh.quartz.server.IJobLogServer;
import com.lgh.quartz.server.IRunJob;
import com.lgh.quartz.util.ErrorUtil;
import com.lgh.quartz.util.SpringUtil;

public abstract class AbstractJobFactory implements Job  {
	
	private static Logger log = LoggerFactory.getLogger(AbstractJobFactory.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		IRunJob job= SpringUtil.getBean(scheduleJob.getBeanClass(), IRunJob.class);
		IJobLogServer logServer = SpringUtil.getBean(IJobLogServer.class);
		if(job != null) {
			Date startDate = new Date();
			String status = "1";
			String message = null;
			try {
				job.run();
				message = "ID:"+scheduleJob.getJobId()+"任务执行成功";
			} catch (Exception e) {
				log.error("定时任务执行失败！！", e);
				message = scheduleJob.getJobId() +"-->" + ErrorUtil.getErrorInfoFromException(e);
				status = "0";
			}
			TTimetaskLog taskLog = new TTimetaskLog();
			taskLog.setEndTime(new Date());
			taskLog.setStartTime(startDate);
			taskLog.setJobId(scheduleJob.getJobId());
			taskLog.setStatus(status);
			taskLog.setMessage(message);
			logServer.insertRunLog(taskLog);
		}else {
			throw new JobExecutionException("任务"+scheduleJob.getJobName()+"为空，无法执行");
		}
		//发布任务执行结束后通知
		SpringUtil.getApplicationContext().publishEvent(new JobRunSuccessEvent(this, scheduleJob));
	}
}
