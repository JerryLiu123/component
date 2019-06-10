package cn.com.lgh.dataformat;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lgh.dataformat.bean.ExcelBasisBean;
import cn.com.lgh.dataformat.bean.ReturnData;
import cn.com.lgh.dataformat.util.DateUtil;
import cn.com.lgh.dataformat.util.IpUtil;



/**
 * 
 * 
 * @ClassName: InterceptorConfig.java
 * @Description: spring 拦截器 用户拦截所有的springMVC请求，用于格式化返回数据
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:50:41 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
public class DataInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(DataInterceptor.class);
	  
	/**
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)  
	 * @Function: InterceptorConfig.java
	 * @Description: 进入controller层之前拦截请求 
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param handler 请求handler
	 * @return
	 * @throws Exception
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月7日 上午11:50:57 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("--------------处理请求之前操作--------------");
		logger.info("--------------URL:"+request.getRequestURL()+"--------------");
		if(request.getRequestURL().toString().endsWith(".task")) {
			try (PrintWriter out = response.getWriter()){
				String id = request.getSession().getId();//返回sessionId
				response.reset();
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8");
			    out.append("{\"id\":\""+id+"\"}");
			    logger.info(".task后缀请求返回的ID为:"+id);
			    request.setAttribute("backID", id);
			} catch (Exception e) {
				logger.error("发送 json 失败!!!", e);
				throw new DataFormatException("发送 json 失败!!!", e);
			}
		}
		return true;
	}

	/**
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)  
	 * @Function: InterceptorConfig.java
	 * @Description: 处理请求完成后视图渲染之前的处理操作
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param handler 请求handler
	 * @param modelAndView 控制层返回的 数据和view解析器
	 * @throws Exception
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月7日 上午11:51:27 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//格式化返回数据
		if(modelAndView == null) {
			return;
		}
		Map<String, Object> model = modelAndView.getModel();
		ReturnData returnData = null;
		if(model.containsKey("webAsyncTask")) {
			returnData = (ReturnData) model.get("webAsyncTask");
		}else if(model.containsKey("returnData")) {
			returnData = (ReturnData) model.get("returnData");
		}
		if(returnData !=null) {
			model.put("res_ip", IpUtil.getIpAdrress(request));
			String url = request.getRequestURL().toString();
			model.put("res_url", url);
			model.put("res_state", returnData.getCode());
			model.put("res_msg", returnData.getMessage());
			model.put("res_data", returnData.getResData());
			model.put("res_time", DateUtil.getDateTime());
			if(returnData.getPage() != null) {
				model.put("list_page", returnData.getPage());
			}
			if(returnData.getCode() == 200) {
				if(url.endsWith(".xls") || url.endsWith(".xlsx") || url.endsWith(".excel")) {//判断是否是导出xls文件
					if(returnData.getExcelBasis() == null) {
						model.put("excelBasis", new ExcelBasisBean());
					}else {
						model.put("excelBasis", returnData.getExcelBasis());
					}
				}
			}			
			
			model.remove("returnData");
		}

		logger.debug("--------------处理请求完成后视图渲染之前的处理操作--------------");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("--------------视图渲染之后的操作--------------");
	}
}
