package com.lgh.quartz.server;

import java.util.List;

import org.quartz.SchedulerException;

import com.lgh.quartz.dto.JobDetail;
import com.lgh.quartz.dto.PageList;
import com.lgh.quartz.dto.ScheduleJob;
import com.lgh.quartz.model.TTimetask;

/**
 * 
 * @ClassName: IJobServer
 * @Description: job信息数据库操作
 * @Author lizhiting
 * @DateTime Jun 3, 2019 10:21:08 AM
 */
public interface IJobServer {

	/**
	 * 
	 * @Title: getAll
	 * @Description: 获得所有的可执行的方法
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 10:22:02 AM
	 * @return
	 */
	public List<ScheduleJob> getAll();
	
	
	/**
	 * 
	 * @Title: getAllByPage
	 * @Description: 获得所有任务并分页
	 * @Author lizhiting
	 * @param jobName
	 * @DateTime Jun 4, 2019 3:20:50 PM
	 * @return
	 */
	public PageList<TTimetask> getAllByPage(String jobName, int page, int pagerow);
	
	
	/**
	 * 
	 * @Title: getById
	 * @Description: 根据主键获得任务
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 4:04:50 PM
	 * @param id
	 * @return
	 * @throws SchedulerException 
	 */
	public TTimetask getById(String id) throws SchedulerException;
	
	/**
	 * 
	 * @Title: pauseJob
	 * @Description: 暂停任务
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 3:17:21 PM
	 * @param id:任务主键
	 * @return
	 * @throws SchedulerException 
	 */
	public boolean pauseJobById(String id) throws SchedulerException;
	
	
	/**
	 * 
	 * @Title: resumeJobById
	 * @Description: 恢复任务
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 4:03:56 PM
	 * @param id
	 * @return
	 * @throws SchedulerException 
	 */
	public boolean resumeJobById(String id) throws SchedulerException;
	
	/**
	 * 
	 * @Title: addJob
	 * @Description: 添加job
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 4:20:06 PM
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean addJob(JobDetail job) throws SchedulerException;
	
	
	/**
	 * 
	 * @Title: delJob
	 * @Description: 删除任务
	 * @Author lizhiting
	 * @DateTime Jun 6, 2019 1:48:20 PM
	 * @param jobId
	 * @return
	 * @throws SchedulerException 
	 */
	public boolean delJob(String jobId) throws SchedulerException;
	
	
}
