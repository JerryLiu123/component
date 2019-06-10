package cn.com.lgh.dataformat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.com.lgh.dataformat.util.IpUtil;

/**
 * @ClassName: ExceptionDispose.java
 * @Description: 控制层的统一异常捕获
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:37:33 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
@ControllerAdvice
public class ExceptionDispose {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionDispose.class);
	
	/**
	 * 全局异常捕捉处理(捕获Controller中抛出的异常)
	 * @param ex
	 * @param request
	 * @param attr
	 * @return
	 */
//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseBody
//    public ModelAndView errorHandler(Exception ex, HttpServletRequest request, 
//			RedirectAttributes attr) {
//    	logger.error("-----捕获到Controller中异常-----", ex);
//    	attr.addFlashAttribute("ex", ex.getMessage());
//		ModelAndView modeAndView = new ModelAndView();
//		modeAndView.getModel().put("res_ip", IpUtil.getIpAdrress(request));
//		String url = request.getRequestURL().toString();
//		modeAndView.getModel().put("res_url", url);
//		modeAndView.getModel().put("res_state", "500");
//		modeAndView.getModel().put("res_msg", "请求失败");
//		if(ex instanceof BindException) {//如果是参数请求异常
//			BindException bex = (BindException)ex;
//			modeAndView.getModel().put("res_err", bex.getFieldError().getDefaultMessage());
//		}else {
//			modeAndView.getModel().put("res_err", ex.getMessage());
//		}
//		
//		//modeAndView.getModel().put("res_time", XTools.date.getDateTime());
//		return modeAndView;
//    }
    
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> errorHandler(Exception ex, HttpServletRequest request, 
			RedirectAttributes attr) {
    	logger.error("-----捕获到Controller中异常-----", ex);
    	attr.addFlashAttribute("ex", ex.getMessage());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("res_ip", IpUtil.getIpAdrress(request));
		String url = request.getRequestURL().toString();
		model.put("res_url", url);
		model.put("res_state", "500");
		model.put("res_msg", "请求失败");
		if(ex instanceof BindException) {//如果是参数请求异常
			BindException bex = (BindException)ex;
			model.put("res_err", bex.getFieldError().getDefaultMessage());
		}else if(ex instanceof MethodArgumentNotValidException) {//如果是参数请求异常
			MethodArgumentNotValidException bex = (MethodArgumentNotValidException)ex;
			model.put("res_err", bex.getBindingResult().getFieldError().getDefaultMessage());
		}else {
			model.put("res_err", ex.getMessage());
		}
		
		//modeAndView.getModel().put("res_time", XTools.date.getDateTime());
		return model;
    }
}
