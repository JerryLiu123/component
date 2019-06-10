package com.lgh.quartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class LQuartzProperties {
	
	/**调度器属性**/
	
	/**用来区分特定的调度器实例，可以按功能用途来给调度器起名**/
	@Value("${quartz.shread.instanceName:DefaultQuartzScheduler}")
	private String shreadInstanceName;
	@Value("${quartz.shread.instanceid:AUTO}")
	private String shreadInstanceid;
	/**如果希望Quartz Scheduler通过RMI作为服务器导出本身，则将"rmi.export"标志设置为true**/
	@Value("${quartz.shread.rmi.export:false}")
	private String shreadRmiExport;
	@Value("${quartz.shread.rmi.rmiProxy:false}")
	private String shreadRmiProxy;
	/**要使用的JobFactory的类名**/
	@Value("${quartz.shread.job.factory.class:org.quartz.simpl.PropertySettingJobFactory}")
	private String shreadJobFactory;
	/**是否为守护线程**/
	@Value("${quartz.shread.guardian:false}")
	private String shreadGuardian;
	/**生产线程是否会集成初始化线程**/
	@Value("${quartz.shread.inherit:true}")
	private String shreadInherit;
	/**JobStore中的连接丢失,调度程序将在重试之间等待的时间量（以毫秒为单位）RamJobStore 无效**/
	@Value("${quartz.shread.dbreconnection:15000}")
	private String shreadDBFailureRetryInterval;
	/**是否跳过更新检查**/
	@Value("${quartz.scheduler.updatecheck:true}")
	private String shreadSkipUpdateCheck;
	
	/**线程池设置**/
	/**默认的线程设置类**/
	@Value("${quartz.thread.class:org.quartz.simpl.SimpleThreadPool}")
	private String threadClass;
	/**线程池数量**/
	@Value("${quartz.thread.num:10}")
	private String threadNum;
	/**设置线程优先级，最大为10，最小为1**/
	@Value("${quartz.thread.priority:5}")
	private String threadPriority;
	/**是否为守护线程**/
	@Value("${quartz.thread.guardian:false}")
	private String threadGuardian;
	/**生产线程是否会集成初始化线程**/
	@Value("${quartz.therad.inherit:false}")
	private String threadInherit;
	/**线程前缀**/
	@Value("${quartz.therad.nameprefix:lghTherad}")
	private String threadNamePrefix;
	
	/**是否为内存实例**/
	@Value("${quartz.isram:true}")
	private boolean isRAM;
	
	/**调度器配置**/
	
	/**调度程序将“tolerate”一个Triggers将其下一个启动时间通过的毫秒数**/
	@Value("${quartz.jobStore.misfireThreshold:5000}")
	private String jobStoreMisfireThreshold;
	
	/**dataSource 配置**/
	@Value("${quartz.datasource.drive:com.mysql.jdbc.Driver}")
	private String dataSourceDrive;
	@Value("${quartz.datasource.url:default}")
	private String dataSourceURL;
	@Value("${quartz.datasource.user:default}")
	private String dataSourceUser;
	@Value("${quartz.datasource.password:default}")
	private String dataSourcePassword;
	@Value("${quartz.datasource.maxConnections:10}")
	private String dataSourceMaxConnections;
	
	public String getShreadInstanceName() {
		return shreadInstanceName;
	}

	public void setShreadInstanceName(String shreadInstanceName) {
		this.shreadInstanceName = shreadInstanceName;
	}

	public String getShreadInstanceid() {
		return shreadInstanceid;
	}

	public void setShreadInstanceid(String shreadInstanceid) {
		this.shreadInstanceid = shreadInstanceid;
	}

	public String getShreadRmiExport() {
		return shreadRmiExport;
	}

	public void setShreadRmiExport(String shreadRmiExport) {
		this.shreadRmiExport = shreadRmiExport;
	}

	public String getShreadRmiProxy() {
		return shreadRmiProxy;
	}

	public void setShreadRmiProxy(String shreadRmiProxy) {
		this.shreadRmiProxy = shreadRmiProxy;
	}

	public String getShreadJobFactory() {
		return shreadJobFactory;
	}

	public void setShreadJobFactory(String shreadJobFactory) {
		this.shreadJobFactory = shreadJobFactory;
	}

	public String getShreadGuardian() {
		return shreadGuardian;
	}

	public void setShreadGuardian(String shreadGuardian) {
		this.shreadGuardian = shreadGuardian;
	}

	public String getShreadInherit() {
		return shreadInherit;
	}

	public void setShreadInherit(String shreadInherit) {
		this.shreadInherit = shreadInherit;
	}

	public String getShreadDBFailureRetryInterval() {
		return shreadDBFailureRetryInterval;
	}

	public void setShreadDBFailureRetryInterval(String shreadDBFailureRetryInterval) {
		this.shreadDBFailureRetryInterval = shreadDBFailureRetryInterval;
	}

	public String getShreadSkipUpdateCheck() {
		return shreadSkipUpdateCheck;
	}

	public void setShreadSkipUpdateCheck(String shreadSkipUpdateCheck) {
		this.shreadSkipUpdateCheck = shreadSkipUpdateCheck;
	}

	public String getThreadClass() {
		return threadClass;
	}

	public void setThreadClass(String threadClass) {
		this.threadClass = threadClass;
	}

	public String getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}

	public String getThreadPriority() {
		return threadPriority;
	}

	public void setThreadPriority(String threadPriority) {
		this.threadPriority = threadPriority;
	}

	public String getThreadGuardian() {
		return threadGuardian;
	}

	public void setThreadGuardian(String threadGuardian) {
		this.threadGuardian = threadGuardian;
	}

	public String getThreadInherit() {
		return threadInherit;
	}

	public void setThreadInherit(String threadInherit) {
		this.threadInherit = threadInherit;
	}

	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}

	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}

	public boolean isRAM() {
		return isRAM;
	}

	public void setRAM(boolean isRAM) {
		this.isRAM = isRAM;
	}

	public String getJobStoreMisfireThreshold() {
		return jobStoreMisfireThreshold;
	}

	public void setJobStoreMisfireThreshold(String jobStoreMisfireThreshold) {
		this.jobStoreMisfireThreshold = jobStoreMisfireThreshold;
	}

	public String getDataSourceDrive() {
		return dataSourceDrive;
	}

	public void setDataSourceDrive(String dataSourceDrive) {
		this.dataSourceDrive = dataSourceDrive;
	}

	public String getDataSourceURL() {
		return dataSourceURL;
	}

	public void setDataSourceURL(String dataSourceURL) {
		this.dataSourceURL = dataSourceURL;
	}

	public String getDataSourceUser() {
		return dataSourceUser;
	}

	public void setDataSourceUser(String dataSourceUser) {
		this.dataSourceUser = dataSourceUser;
	}

	public String getDataSourcePassword() {
		return dataSourcePassword;
	}

	public void setDataSourcePassword(String dataSourcePassword) {
		this.dataSourcePassword = dataSourcePassword;
	}

	public String getDataSourceMaxConnections() {
		return dataSourceMaxConnections;
	}

	public void setDataSourceMaxConnections(String dataSourceMaxConnections) {
		this.dataSourceMaxConnections = dataSourceMaxConnections;
	}
	
}
