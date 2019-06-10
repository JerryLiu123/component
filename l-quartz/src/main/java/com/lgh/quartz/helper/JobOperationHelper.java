package com.lgh.quartz.helper;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgh.quartz.dto.ScheduleJob;

@Component
public class JobOperationHelper {
	
	private static Logger log = LoggerFactory.getLogger(JobOperationHelper.class);

	@Autowired
	private  StdSchedulerFactory schedulerFactoryBean;
	
	/**
	 * 
	 * @Title: addJob
	 * @Description: 添加任务
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 3:04:28 PM
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean addJob(ScheduleJob job) throws SchedulerException {
		if (job == null) {
			throw new JobExecutionException("任务为空，无法创建");
		}
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if(log.isDebugEnabled()) {
				log.debug(scheduler + "...........................................add");
			}
			
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			//不存在执行器，创建一个
			if (null == trigger) {
				Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class
				                                                                           : QuartzJobFactoryDisallowConcurrentExecution.class;
				 
				JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).usingJobData("data", job.getJobData()).build();
				 
				jobDetail.getJobDataMap().put("scheduleJob", job);
				 
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				if("0".equals(job.getIsConcurrent())) {
					scheduleBuilder.withMisfireHandlingInstructionDoNothing();
				}
				 
				trigger = TriggerBuilder.newTrigger().withDescription(job.getJobId().toString()).withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();
				 
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = null;
				if("0".equals(job.getIsConcurrent())) {
					scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression()).withMisfireHandlingInstructionDoNothing();
				}else {
					scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				}
				 
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData("data", job.getJobData())
							.withSchedule(scheduleBuilder).build();
				 
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
			if(ScheduleJob.STATUS_NOT_RUNNING.equals(job.getJobStatus())) {
				this.pauseJob(job.getJobName(), job.getJobGroup());
			}
			return true;
		} catch (SchedulerException e) {
			log.error("添加任务失败，jobName:{}, jobGroup:{}", job.getJobName(), job.getJobGroup());
			throw e;
		}
	}
	
	/**
	 * 
	 * @Title: getJobState
	 * @Description: 获得job状态
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 3:01:21 PM
	 * @param jobName
	 * @param jobGroup
	 * @return
	 * @throws SchedulerException
	 */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {             
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);    
        return schedulerFactoryBean.getScheduler().getTriggerState(triggerKey).name();
    }
    
    /**
     * 
     * @Title: pauseAllJob
     * @Description: 暂停所有任务
     * @Author lizhiting
     * @DateTime Jun 3, 2019 3:02:01 PM
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {            
    	schedulerFactoryBean.getScheduler().pauseAll();
	}
    
    /**
     * @Title: pauseJob
     * @Description:  暂停任务
     * @Author lizhiting
     * @DateTime Jun 3, 2019 3:02:41 PM
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {            
        JobKey jobKey = new JobKey(jobName, jobGroup);
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);  
        JobDetail jobDetail = schedulerFactoryBean.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null) {
        	return false;
        }else {
        	schedulerFactoryBean.getScheduler().pauseJob(jobKey);
        	//schedulerFactoryBean.getScheduler().pauseTrigger(triggerKey);
        	return true;
        }
    }
    
    /**
     * 
     * @Title: resumeAllJob
     * @Description: 恢复所有任务
     * @Author lizhiting
     * @DateTime Jun 3, 2019 3:04:18 PM
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {            
    	schedulerFactoryBean.getScheduler().resumeAll();
    }
    
    /**
     * 
     * @Title: resumeJob
     * @Description: 恢复某个任务
     * @Author lizhiting
     * @DateTime Jun 3, 2019 3:04:52 PM
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
        
        JobKey jobKey = new JobKey(jobName, jobGroup);
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);  
        JobDetail jobDetail = schedulerFactoryBean.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null) {
        	return false;
        }else {               
        	//schedulerFactoryBean.getScheduler().resumeTrigger(triggerKey);
        	schedulerFactoryBean.getScheduler().resumeJob(jobKey);
        	return true;
        }
    }
    
    /**
     * @Title: deleteJob
     * @Description: 删除任务
     * @Author lizhiting
     * @DateTime Jun 3, 2019 3:05:57 PM
     * @param appQuartz
     * @return
     * @throws SchedulerException
     */
    public String deleteJob(String jobName, String jobGroup) throws SchedulerException {            
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = schedulerFactoryBean.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null ) {
        	return "jobDetail is null";
        }else if(!schedulerFactoryBean.getScheduler().checkExists(jobKey)) {
            return "jobKey is not exists";
        }else {
        	schedulerFactoryBean.getScheduler().deleteJob(jobKey);
        	return "success";
        }  
    }
}
