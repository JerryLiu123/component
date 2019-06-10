package com.lgh.quartz.helper;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.lgh.quartz.dto.PageList;
import com.lgh.quartz.util.SpringUtil;

public class PageHelper<T> {
	
    public PageList<T> queryByPageForMySQL(String sql, MapSqlParameterSource params, int page, int pagerow, Class<T> cla) {
    	NamedParameterJdbcTemplate jdbcTemplate = SpringUtil.getBean(NamedParameterJdbcTemplate.class);
        String rowsql="select count(*) from ("+sql+") gmtxtabs_";   //查询总行数sql
        int pages = 0;   //总页数
        int rows = jdbcTemplate.queryForObject(rowsql, params, Integer.class);  //查询总行数
        //判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
        if (rows % pagerow == 0) {
            pages = rows / pagerow;
        } else {
            pages = rows / pagerow + 1;
        }
        //查询第page页的数据sql语句
        if(page<=1){
            sql+=" limit 0,"+pagerow;
        }else{
            sql+=" limit "+((page-1)*pagerow)+","+pagerow;
        }
        //查询第page页数据
        List<T> list=jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<T>(cla));
        //返回分页格式数据
        PageList<T> pl = new PageList<T>();
        pl.setPage(page);  //设置显示的当前页数
        pl.setPages(pages);  //设置总页数
        pl.setList(list);   //设置当前页数据
        pl.setTotalRows(rows);    //设置总记录数
        return pl;
    }
}
