package cn.com.lgh.dataformat.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: IpUtil.java
 * @Description: IP操作工具类
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:40:45 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
public class IpUtil {
	
	/**
	 * 
	 * @Function: IpUtil.java
	 * @Description: 获得 request 中请求的IP地址
	 *
	 * @param request 
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月7日 上午11:40:54 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
	 */
    public static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
