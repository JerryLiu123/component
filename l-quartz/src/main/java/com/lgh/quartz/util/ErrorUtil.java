package com.lgh.quartz.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {

	public static String getErrorInfoFromException(Exception e) {
		try (StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);){
			e.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "ErrorInfoFromException";
		}
	}
}
