package com.lgh.quartz.server.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgh.quartz.model.TTimetaskLog;
import com.lgh.quartz.server.IJobLogServer;

@Service
public class JobLogServerImpl implements IJobLogServer {
	
    @Autowired
    private JdbcTemplate jdbc;

    @Transactional
	@Override
	public void insertRunLog(TTimetaskLog logDto) {
		String sql = "INSERT INTO t_timetask_log ( " + 
				"	`job_id`, " + 
				"	`start_time`, " + 
				"	`end_time`, " + 
				"	`status`, " + 
				"	`message`" + 
				") " + 
				"VALUES " + 
				"	(?,?,?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps  = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            ps.setObject(1, logDto.getJobId());
	            ps.setObject(2, logDto.getStartTime());
	            ps.setObject(3, logDto.getEndTime());
	            ps.setObject(4, logDto.getStatus());
	            ps.setObject(5, logDto.getMessage());
	            return ps;
	        }
	    }, keyHolder);

	}

}
