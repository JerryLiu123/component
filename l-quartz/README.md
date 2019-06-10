# 使用方法
1. 引入jar包
2. 根据所给的sql文件初始化数据库
3. 配置数据源
3. 自定义配置（详细配置项见下方）
4. 继承接口IRunJob，编写自己的定时方法，并注入到spring中
5. 调用接口/api/v1/job/add 添加定时任务
# 配置参数(括号中为默认值)
1. quartz.shread.instanceName：调度器实例名称(DefaultQuartzScheduler)
2. quartz.shread.instanceid：调度器ID(AUTO)
3. quartz.shread.rmi.export：rmi(false)
4. quartz.shread.rmi.rmiProxy：rmi(false)
5. quartz.shread.job.factory.class：jobFactory类(org.quartz.simpl.PropertySettingJobFactory)
6. quartz.shread.guardian：shread是否为守护线程(false)
7. quartz.shread.inherit：生产线程是否会继承初始化线程(true)
8. quartz.shread.dbreconnection：JobStore中的连接丢失,调度程序将在重试之间等待的时间量（以毫秒为单位）RamJobStore 无效(15000)
9. quartz.scheduler.updatecheck：是否跳过更新检查(true)
10. quartz.thread.class：默认的线程设置类(org.quartz.simpl.SimpleThreadPoo)
11. quartz.thread.num：线程池数量(10)
12. quartz.thread.priority：设置线程优先级，最大为10，最小为1(5)
13. quartz.thread.guardian：执行线程是否为守护线程(false)
14. quartz.therad.inherit：执行线程是否会继承初始化线程(false)
15. quartz.therad.nameprefix：线程前缀(lghTherad)
16. quartz.isram：是否为内存实例(true)
17. quartz.jobStore.misfireThreshold：调度程序将“tolerate”一个Triggers将其下一个启动时间通过的毫秒数，主要影响当任务超过执行时间后是否再执行(5000)
18. quartz.datasource.drive：当为DB实例时用的DB 驱动(com.mysql.jdbc.Driver)
19. quartz.datasource.url：当为DB实例时的数据库地址(无)
20. quartz.datasource.user：当为DB实例时的数据库用户名(无)
21. quartz.datasource.password：当为DB实例时的数据库密码(无)
22. quartz.datasource.maxConnections：当为DB实例时的最大连接数(无)
