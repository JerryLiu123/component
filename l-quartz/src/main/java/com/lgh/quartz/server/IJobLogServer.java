package com.lgh.quartz.server;

import com.lgh.quartz.model.TTimetaskLog;

public interface IJobLogServer {

	/**
	 * 
	 * @Title: insertRunLog
	 * @Description: 插入执行日志
	 * @Author lizhiting
	 * @DateTime Jun 5, 2019 5:40:27 PM
	 * @param logDto
	 */
	public void insertRunLog(TTimetaskLog logDto);
}
