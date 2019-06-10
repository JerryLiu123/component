package com.lgh.quartz.model;

import java.sql.Date;

public class TTimetask {
    /**
     * ID
     */
    private Integer id;

    /**
     * 任务名 
     */
    private String name;

    /**
     * 任务组名
     */
    private String groupName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 表达式
     */
    private String cron;

    /**
     * 任务状态
     */
    private String jobStatus;

    /**
     * 计划状态
     */
    private String planStatus;

    /**
     * 是否补偿执行（当启动或停止超过执行时间后，是否执行前面没有执行的任务）
	 *	1:是
	 *	0:否
     */
    private String isConcurrent;

    /**
     * 参数
     */
    private String jobData;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 事例Bean名称
     */
    private String beanName;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建者ID
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改者ID
     */
    private String modifyId;

    /**
     * 修改者时间
     */
    private Date modifyDate;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务名 
     *
     * @return name - 任务名 
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务名 
     *
     * @param name 任务名 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取任务组名
     *
     * @return group_name - 任务组名
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置任务组名
     *
     * @param groupName 任务组名
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取表达式
     *
     * @return cron - 表达式
     */
    public String getCron() {
        return cron;
    }

    /**
     * 设置表达式
     *
     * @param cron 表达式
     */
    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    /**
     * 获取任务状态
     *
     * @return job_status - 任务状态
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * 设置任务状态
     *
     * @param jobStatus 任务状态
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus == null ? null : jobStatus.trim();
    }

    /**
     * 获取计划状态
     *
     * @return plan_status - 计划状态
     */
    public String getPlanStatus() {
        return planStatus;
    }

    /**
     * 设置计划状态
     *
     * @param planStatus 计划状态
     */
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus == null ? null : planStatus.trim();
    }

    /**
     * 获取运行状态
     *
     * @return is_concurrent - 运行状态
     */
    public String getIsConcurrent() {
        return isConcurrent;
    }

    /**
     * 设置运行状态
     *
     * @param isConcurrent 运行状态
     */
    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent == null ? null : isConcurrent.trim();
    }

    /**
     * 获取参数
     *
     * @return job_data - 参数
     */
    public String getJobData() {
        return jobData;
    }

    /**
     * 设置参数
     *
     * @param jobData 参数
     */
    public void setJobData(String jobData) {
        this.jobData = jobData == null ? null : jobData.trim();
    }

    /**
     * 获取方法名
     *
     * @return method_name - 方法名
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置方法名
     *
     * @param methodName 方法名
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * 获取事例Bean名称
     *
     * @return bean_name - 事例Bean名称
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * 设置事例Bean名称
     *
     * @param beanName 事例Bean名称
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName == null ? null : beanName.trim();
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取创建者ID
     *
     * @return create_id - 创建者ID
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建者ID
     *
     * @param createId 创建者ID
     */
    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改者ID
     *
     * @return modify_id - 修改者ID
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改者ID
     *
     * @param modifyId 修改者ID
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId == null ? null : modifyId.trim();
    }

    /**
     * 获取修改者时间
     *
     * @return modify_date - 修改者时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改者时间
     *
     * @param modifyDate 修改者时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}