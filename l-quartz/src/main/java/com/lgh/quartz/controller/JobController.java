package com.lgh.quartz.controller;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.quartz.dto.JobDetail;
import com.lgh.quartz.dto.PageList;
import com.lgh.quartz.dto.ResultCode;
import com.lgh.quartz.dto.ResultJson;
import com.lgh.quartz.model.TTimetask;
import com.lgh.quartz.server.IJobServer;
import com.lgh.quartz.util.ErrorUtil;

@RestController
@RequestMapping(value="/api/v1/job")
public class JobController {

	private Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private IJobServer iJobServer;
	
	/**
	 * 
	 * @Title: pauseJob
	 * @Description: 停止任务
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 3:34:22 PM
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/pause")
	public ResultJson pauseJob(@RequestParam(value="id")String id) {
		try {
			iJobServer.pauseJobById(id);
			return ResultJson.ok("停止任务成功");
		} catch (Exception e) {
			log.error("停止任务失败->", e);
			return ResultJson.failure(ResultCode.NOT_FOUND, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: resumeJob
	 * @Description: 恢复任务
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 5:35:29 PM
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/resume")
	public ResultJson resumeJob(@RequestParam(value="id")String id) {
		try {
			iJobServer.resumeJobById(id);
			return ResultJson.ok("恢复任务成功");
		} catch (Exception e) {
			log.error("开启任务失败->", e);
			return ResultJson.failure(ResultCode.NOT_FOUND, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAll
	 * @Description: 查看所有任务
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 4:09:16 PM
	 * @param jobName 任务名称
	 * @param page 开始页
	 * @param pagerow 返回条数
	 * @return
	 */
	@RequestMapping(value="/getall")
	public ResultJson<PageList<TTimetask>> getAll(@RequestParam(value="jobName", required=false)String jobName, 
									@RequestParam(value="page", required=true, defaultValue="1")int page,
									@RequestParam(value="pagerow", required=true, defaultValue="10")int pagerow){
		try {
			return ResultJson.ok(iJobServer.getAllByPage(jobName, page, pagerow));
		} catch (Exception e) {
			return ResultJson.failure(ResultCode.NOT_FOUND, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: add
	 * @Description: 添加任务
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 4:57:02 PM
	 * @param job
	 * @return
	 */
	@RequestMapping(value="/add", method={RequestMethod.POST})
	public ResultJson add(@RequestBody JobDetail job) {
		try {
			iJobServer.addJob(job);
			return ResultJson.ok("添加任务成功");
		} catch (SchedulerException e) {
			log.error("添加任务失败->", e);
			return ResultJson.failure(ResultCode.NOT_FOUND, e.getMessage());
		}
	}
	

	/**
	 * 
	 * @Title: del
	 * @Description: 删除任务
	 * @Author lizhiting
	 * @DateTime Jun 6, 2019 2:10:29 PM
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method={RequestMethod.POST, RequestMethod.DELETE})
	public ResultJson<String> del(@RequestParam(value="id")String id){
		try {
			iJobServer.delJob(id);
			return ResultJson.ok("删除任务成功");
		} catch (Exception e) {
			log.error("删除任务失败->", e);
			return ResultJson.failure(ResultCode.NOT_FOUND, e.getMessage());
		}
	}
}
