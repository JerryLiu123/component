package com.lgh.sys.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.bean.ResultJson;
import com.lgh.sys.manage.bean.auto.ResponseUserToken;
import com.lgh.sys.manage.bean.auto.User;
import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.server.AuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JoeTao
 * createAt: 2018/9/17
 */

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	private Logger log = LoggerFactory.getLogger(AuthController.class);
	
//    @Value("${jwt.header}")
//    private String tokenHeader;
	
	@Autowired
	private SysConstant sysConstant;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResultJson<ResponseUserToken> login(
             @RequestBody User user){
    	if(log.isDebugEnabled()) {
    		log.debug("-----开始生成token逻辑-----");
    	}
    	
    	try {
            ResponseUserToken response = authService.login(user);
            return ResultJson.ok(response);
		} catch (Exception e) {
			return ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage());
		}
    }

    @GetMapping(value = "/logout")
    public ResultJson logout(HttpServletRequest request){
        String token = request.getHeader(sysConstant.getTokenHeader());
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        authService.logout(token);
        return ResultJson.ok("退出成功");
    }

    @GetMapping(value = "/user")
    public ResultJson getUser(HttpServletRequest request){
        String token = request.getHeader(sysConstant.getTokenHeader());
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        UserDetail userDetail = authService.getUserByToken(token);
        return ResultJson.ok(userDetail);
    }
    
    @GetMapping(value = "/loginerror")
    public ResultJson loginError(HttpServletRequest request) {
    	 return ResultJson.failure(ResultCode.UNAUTHORIZED, request.getParameter("value"));
    }

//    @PostMapping(value = "/sign")
//    public ResultJson sign(@RequestBody User user) {
//        if (StringUtils.isAnyBlank(user.getName(), user.getPassword())) {
//            return ResultJson.failure(ResultCode.BAD_REQUEST);
//        }
//        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), new HashSet<SimpleGrantedAuthority>());
//        return ResultJson.ok(authService.register(userDetail));
//    }
//    @GetMapping(value = "refresh")
//    @ApiOperation(value = "刷新token")
//    public ResultJson refreshAndGetAuthenticationToken(
//            HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        ResponseUserToken response = authService.refresh(token);
//        if(response == null) {
//            return ResultJson.failure(ResultCode.BAD_REQUEST, "token无效");
//        } else {
//            return ResultJson.ok(response);
//        }
//    }
}
