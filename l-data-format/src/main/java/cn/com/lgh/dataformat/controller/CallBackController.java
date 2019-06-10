package cn.com.lgh.dataformat.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.lgh.dataformat.bean.ReturnData;
import cn.com.lgh.dataformat.bean.XPage;
import cn.com.lgh.dataformat.service.ICallBackService;


@Controller
@RequestMapping(value="/callback")
public class CallBackController {
	
	@Autowired
	private ICallBackService callBackService;

	@RequestMapping(value="/getvalue/{backId}")
	@ResponseBody
	public ReturnData getValue(HttpServletRequest request, 
								@PathVariable(value="backId")String backId){
		Map<String, Object> data = callBackService.getCallbackValue(backId);
		if(data.containsKey("page")) {
			return new ReturnData<Object>().req200(data.get("data"), (XPage)data.get("page"));
		}else {
			return new ReturnData<Object>().req200(data.get("data"));
		}
	}
}
