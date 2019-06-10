package com.lgh.sys.manage.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class SysConstant {
	/**在header中存储Token的名称**/
    @Value("${jwt.header}")
    private String tokenHeader;
    
	/**header中token开始字段**/
	@Value("${jwt.tokenstart:Bearer }")
	private String authTokenStart;
    
	/**是否唯一登陆**/
	@Value("${jwt.isonly:false}")
	private Boolean only;
	
	/**不需要拦截的方法**/
	@Value("#{'${jwt.matcher:/api/v1/login, /api/v1/logout, /api/v1/sign, /error/**, /test/login}'.split(',')}")
	private List<String> matcher;
	
	/**不需要拦截的web资源**/
	@Value("#{'${jwt.webmatcher:/**/*.css, /**/*.js, /**/*.png, /**/*.gif}'.split(',')}")
	private List<String> webMatcher;//
	
	/**是否开启https 访问**/
	@Value("${jwt.ssl:false}")
	private boolean isSSL;
	
	/**https 限制地址**/
	@Value("#{'${jwt.sslMatcher:/api/v1/login, /api/v1/sign}'.split(',')}")
	private List<String> sslMatcher;	
	
	/**token 加盐字段**/
    @Value("${jwt.secret:lghsecurityjwttoken001}")
    private String secret;

    /**默认token保存时间**/
    @Value("${jwt.expiration:8400}")
    private Long accessTokenExpiration;

    /**token刷新时间，暂时没有用到**/
    @Value("${jwt.expiration:8400}")
    @Deprecated
    private Long refreshTokenExpiration;
    
	/**保存密码时,token保存时间**/
	@Value("${jwt.rememverexpiration:604800}")
	private Long rememberPasswordExpiration;
	
	/**密码错误时重新输入密码的时间间隔(秒)**/
	@Value("${jwt.pwderrtimeinterval:3600}")
	private Long pwdErrTimeInterval;
	
	/**密码错误次数,为0的话不进行校验**/
	@Value("${jwt.pwderrnum:0}")
	private Long pwdErrNum;
	
	/**密码错误超过次数后是否锁定**/
	@Value("${jwt.ispwderrlock:false}")
	private boolean isPwdErrLock;
	
	/**默认登陆地址**/
	@Value("${jwt.loginurl:/api/v1/login}")
	private String loginUrl;
	
	/**登陆人数限制，为0的话则不限制**/
	@Value("${jwt.restrictusernum:0}")
	private Long restrictUserNum;
	
	public String getTokenHeader() {
		return tokenHeader;
	}

	public void setTokenHeader(String tokenHeader) {
		this.tokenHeader = tokenHeader;
	}

	public Boolean getOnly() {
		return only;
	}

	public void setOnly(Boolean only) {
		this.only = only;
	}

	public List<String> getMatcher() {
		return matcher;
	}

	public void setMatcher(List<String> matcher) {
		this.matcher = matcher;
	}

	public List<String> getWebMatcher() {
		return webMatcher;
	}

	public void setWebMatcher(List<String> webMatcher) {
		this.webMatcher = webMatcher;
	}

	public String getAuthTokenStart() {
		return authTokenStart;
	}

	public void setAuthTokenStart(String authTokenStart) {
		this.authTokenStart = authTokenStart;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getAccessTokenExpiration() {
		return accessTokenExpiration;
	}

	public void setAccessTokenExpiration(Long accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}

	public Long getRefreshTokenExpiration() {
		return refreshTokenExpiration;
	}

	public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public Long getRememberPasswordExpiration() {
		return rememberPasswordExpiration;
	}

	public void setRememberPasswordExpiration(Long rememberPasswordExpiration) {
		this.rememberPasswordExpiration = rememberPasswordExpiration;
	}

	public boolean isSSL() {
		return isSSL;
	}

	public void setSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}

	public List<String> getSslMatcher() {
		return sslMatcher;
	}

	public void setSslMatcher(List<String> sslMatcher) {
		this.sslMatcher = sslMatcher;
	}

	public Long getPwdErrTimeInterval() {
		return pwdErrTimeInterval;
	}

	public void setPwdErrTimeInterval(Long pwdErrTimeInterval) {
		this.pwdErrTimeInterval = pwdErrTimeInterval;
	}

	public Long getPwdErrNum() {
		return pwdErrNum;
	}

	public void setPwdErrNum(Long pwdErrNum) {
		this.pwdErrNum = pwdErrNum;
	}

	public boolean isPwdErrLock() {
		return isPwdErrLock;
	}

	public void setPwdErrLock(boolean isPwdErrLock) {
		this.isPwdErrLock = isPwdErrLock;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Long getRestrictUserNum() {
		return restrictUserNum;
	}

	public void setRestrictUserNum(Long restrictUserNum) {
		this.restrictUserNum = restrictUserNum;
	}
	
	
}
