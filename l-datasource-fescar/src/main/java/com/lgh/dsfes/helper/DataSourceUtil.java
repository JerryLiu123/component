package com.lgh.dsfes.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataSourceUtil {
	private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);
	
	public static String getDriverClassName(String dbType) {
		dbType = dbType.replaceAll(" ", "");
		String className = "";
		switch (dbType) {
		case "mysql":
			className = "com.mysql.jdbc.Driver";
			break;
		case "oracle":
			className = "oracle.jdbc.OracleDriver";
			break;
		case "sqlserver":
			className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			break;
		default:
			throw new DataSourceException("-----请指定数据源类型:"+dbType+"-----");
		}
		return className;
	}
	
	public static String getUrl(String dbType, String ip, String port, String database) {
		String url = "";
		if(StringUtils.isBlank(ip) || StringUtils.isBlank(port) || StringUtils.isBlank(database)) {
			throw new DataSourceException("数据库连接 ip, port, database都不可为空!!!");
		}
		switch (dbType) {
		case "mysql":
			url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", ip, port, database);
			break;
		case "oracle":
			url = String.format("jdbc:oracle:thin:@%s:%s/%s", ip, port, database);
			break;
		case "sqlserver":
			url = String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s", ip, port, database);
			break;

		default:
			throw new DataSourceException("请指定数据源类型");
		}
		logger.info("数据连接地址: "+url);
		return url;
	}
}
