package com.lgh.quartz.server;

/**
 * 
 * @ClassName: IRunJob
 * @Description: 所有的定时任务要实现此接口
 * @Author lizhiting
 * @DateTime Jun 3, 2019 11:32:20 AM
 */
public interface IRunJob {

	/**
	 * @Title: run
	 * @Description: 执行方法
	 * @Author lizhiting
	 * @DateTime Jun 3, 2019 11:32:51 AM
	 */
	public void run();
}
