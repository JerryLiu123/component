package com.lgh.quartz.dto;

import java.io.Serializable;

public class JobDetail implements Serializable {

	private static final long serialVersionUID = -8882992485090877570L;

	/**job 名称**/
	private String jobName;
	/**任务组名称**/
	private String groupName;
	/**表达式**/
	private String cron;
	/**运行状态**/
	private String isConcurrent;
	/**实例Bean名称**/
	private String beanName;
	/**描述**/
	private String description;
	/**创建者ID**/
	private String createId;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getIsConcurrent() {
		return isConcurrent;
	}
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
