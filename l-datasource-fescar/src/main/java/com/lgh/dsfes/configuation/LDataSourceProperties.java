package com.lgh.dsfes.configuation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="engine")
public class LDataSourceProperties {

	private Map<String, Map<String, String>> datasource;
	//druid 页面地址
	@Value("${engine.servlet.druidurl:/druid/*}")
	private String druidurl;
	//druid 白名单
	@Value("${engine.servlet.druidallow:127.0.0.1}")
	private String druidallow;
	//druid 黑名单
	@Value("${engine.servlet.druiddeny:192.168.1.73}")
	private String druiddeny;
	//druid 用户名
	@Value("${engine.servlet.druiduser:root}")
	private String druiduser;
	//druid 密码
	@Value("${engine.servlet.druidpass:root}")
	private String druidpass;
	@Value("${engine.db.detailed:false}")
	private boolean detailed;
	
	
	public Map<String, Map<String, String>> getDatasource() {
		return datasource;
	}
	public void setDatasource(Map<String, Map<String, String>> datasource) {
		this.datasource = datasource;
	}
	public String getDruidurl() {
		return druidurl;
	}
	public void setDruidurl(String druidurl) {
		this.druidurl = druidurl;
	}
	public String getDruidallow() {
		return druidallow;
	}
	public void setDruidallow(String druidallow) {
		this.druidallow = druidallow;
	}
	public String getDruiddeny() {
		return druiddeny;
	}
	public void setDruiddeny(String druiddeny) {
		this.druiddeny = druiddeny;
	}
	public String getDruiduser() {
		return druiduser;
	}
	public void setDruiduser(String druiduser) {
		this.druiduser = druiduser;
	}
	public String getDruidpass() {
		return druidpass;
	}
	public void setDruidpass(String druidpass) {
		this.druidpass = druidpass;
	}
	public boolean isDetailed() {
		return detailed;
	}
	public void setDetailed(boolean detailed) {
		this.detailed = detailed;
	}
	
}
