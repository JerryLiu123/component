package com.lgh.sys.manage.exception;

import com.lgh.sys.manage.bean.ResultJson;

/**
 * @author Joetao
 * Created at 2018/8/24.
 */
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }

	public ResultJson getResultJson() {
		return resultJson;
	}

	public void setResultJson(ResultJson resultJson) {
		this.resultJson = resultJson;
	}
	
	
	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
	
	public CustomException(String msg,Object ... objects) {
		super(String.format(msg,objects));
	}
	
    public CustomException(Throwable cause,String msg,Object ... objects) {
        super(String.format(msg,objects), cause);
    }
    
    
}
