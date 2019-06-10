package com.lgh.quartz.server.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgh.quartz.dto.JobDetail;
import com.lgh.quartz.dto.PageList;
import com.lgh.quartz.dto.ScheduleJob;
import com.lgh.quartz.helper.JobOperationHelper;
import com.lgh.quartz.helper.PageHelper;
import com.lgh.quartz.model.TTimetask;
import com.lgh.quartz.server.IJobServer;

@Service
public class JobServerImpl implements IJobServer {

	
	@Autowired
	private JobOperationHelper jobOperationHelper;
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private JdbcTemplate jdbc;
	
	@Override
	public List<ScheduleJob> getAll() {
		String sql = "SELECT" + 
				"	id," + 
				"	`name`," + 
				"	group_name," + 
				"	start_time," + 
				"	end_time," + 
				"	cron," + 
				"	job_status," + 
				"	plan_status," + 
				"	is_concurrent," + 
				"	job_data," + 
				"	method_name," + 
				"	bean_name," + 
				"	description," + 
				"	create_id," + 
				"	create_date," + 
				"	modify_id," + 
				"	modify_date " + 
				"FROM" + 
				"	t_timetask " + 
				"WHERE" + 
				"	job_status = '1'";
		List<TTimetask> lists = jdbcTemplate.query(sql, new BeanPropertyRowMapper<TTimetask>(TTimetask.class));
		if(lists == null || lists.isEmpty()) {
			return new ArrayList<>();
		}
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		lists.stream().forEach(obj -> {
			ScheduleJob job = new ScheduleJob();
			job.copyByJobDetail(obj);
			jobList.add(job);
		});
		return jobList;
	}

	@Override
	@Transactional
	public boolean pauseJobById(String id) throws SchedulerException {
		TTimetask dto = getById(id);
		//调用quaetz 停止job
		if(jobOperationHelper.pauseJob(dto.getName(), dto.getGroupName())) {
			//将任务标记为停止
			if(!("-1".equals(dto.getPlanStatus()))) {
				String sql = "UPDATE t_timetask SET plan_status = '-1' WHERE id = :id";
				MapSqlParameterSource parameters = new MapSqlParameterSource();
		        parameters.addValue("id", dto.getId());
				jdbcTemplate.update(sql, parameters);
			}
			return true;
		}else {
			throw new SchedulerException("在执行器中未找到该任务");
		}
	}

	@Override
	@Transactional
	public boolean resumeJobById(String id) throws SchedulerException {
		TTimetask dto = getById(id);
		if(jobOperationHelper.resumeJob(dto.getName(), dto.getGroupName())) {
			if(!("1".equals(dto.getPlanStatus()))) {
				String sql = "UPDATE t_timetask SET plan_status = '1' WHERE id = :id";
				MapSqlParameterSource parameters = new MapSqlParameterSource();
		        parameters.addValue("id", dto.getId());
				jdbcTemplate.update(sql, parameters);
			}
			return true;
		}else {
			throw new SchedulerException("在执行器中未找到该任务");
		}
	}

	@Override
	public TTimetask getById(String id) throws SchedulerException {
		String sql = "SELECT" + 
				"	id," + 
				"	`name`," + 
				"	group_name," + 
				"	start_time," + 
				"	end_time," + 
				"	cron," + 
				"	job_status," + 
				"	plan_status," + 
				"	is_concurrent," + 
				"	job_data," + 
				"	method_name," + 
				"	bean_name," + 
				"	description," + 
				"	create_id," + 
				"	create_date," + 
				"	modify_id," + 
				"	modify_date " + 
				"FROM" + 
				"	t_timetask " + 
				"WHERE" + 
				"	id = :id";
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
	        parameters.addValue("id", id);
			TTimetask dto = jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper<TTimetask>(TTimetask.class));
			Optional.ofNullable(dto).orElseThrow(() -> new SchedulerException("查询不到该任务"));
			return dto;
		} catch (EmptyResultDataAccessException e) {
			throw new SchedulerException("查询不到该任务");
		}

	}

	@Override
	public PageList<TTimetask> getAllByPage(String jobName, int page, int pagerow) {
		String sql = "SELECT" + 
				"	id," + 
				"	`name`," + 
				"	group_name," + 
				"	cron," + 
				"	job_status," + 
				"	plan_status," + 
				"	is_concurrent," + 
				"	method_name," + 
				"	bean_name," + 
				"	description," + 
				"	create_id," + 
				"	create_date," + 
				"	modify_id," + 
				"	modify_date " + 
				"FROM" + 
				"	t_timetask ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if(StringUtils.isNotEmpty(jobName)) {
			sql = sql + "WHERE `name` = :name";
			parameters.addValue("name", jobName);
		}
		return new PageHelper<TTimetask>().queryByPageForMySQL(sql, parameters, page, pagerow, TTimetask.class);
	}

	@Transactional
	@Override
	public boolean addJob(JobDetail job) throws SchedulerException {
		//插入数据库
		String sql = "INSERT INTO t_timetask ( " + 
				"	`name`, " + 
				"	`group_name`, " + 
				"	`start_time`, " + 
				"	`end_time`, " + 
				"	`cron`, " + 
				"	`job_status`, " + 
				"	`plan_status`, " + 
				"	`is_concurrent`, " + 
				"	`bean_name`, " + 
				"	`description`, " + 
				"	`create_id`, " + 
				"	`create_date` " + 
				") " + 
				"VALUES " + 
				"	(?,?,?,?,?,?,?,?,?,?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps  = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            ps.setObject(1, job.getJobName());
	            ps.setObject(2, job.getGroupName());
	            ps.setObject(3, new Date());
	            ps.setObject(4, new Date());
	            ps.setObject(5, job.getCron());
	            ps.setObject(6, "1");
	            ps.setObject(7, "1");
	            ps.setObject(8, job.getIsConcurrent());
	            ps.setObject(9, job.getBeanName());
	            ps.setObject(10, job.getDescription());
	            ps.setObject(11, job.getCreateId());
	            ps.setObject(12, new Date());
	            return ps;
	        }
	    }, keyHolder);
		//添加任务到quartz
		TTimetask task = getById(keyHolder.getKey().longValue()+"");
		ScheduleJob schdeuleJob = new ScheduleJob();
		schdeuleJob.copyByJobDetail(task);
		jobOperationHelper.addJob(schdeuleJob);
		return true;
	}

	@Transactional
	@Override
	public boolean delJob(String jobId) throws SchedulerException {
		TTimetask job = getById(jobId);
		String data = jobOperationHelper.deleteJob(job.getName(), job.getGroupName());
		if("success".equals(data)) {
			String sql = "DELETE FROM t_timetask WHERE id=:id";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("id", job.getId());
			jdbcTemplate.update(sql, parameters);
			return true;
		}else {
			throw new SchedulerException("删除任务失败-->"+data);
		}
	}

}
