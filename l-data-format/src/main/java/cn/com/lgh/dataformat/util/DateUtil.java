package cn.com.lgh.dataformat.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import cn.com.lgh.dataformat.DataFormatException;

/**
 * 主要负责日期操作相关工具类
 * >>修改静态sdf为new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA)----2015-9-20 18:01:23  Gaojinquan
 * @date 2006-12-07
 * @author xwtt
 * @version 0.70
 * @ClassName: DateUtil.java
 * @Description: 拷贝自 zwfw TSDate 工具类，主要负责日期操作相关工具类
 *
 * Modification History:
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年8月9日     xiaoming          v1.0.0               修改原因<br>
 */
public class DateUtil {
	public static String defaultDateTimePatternString = "yyyyMMddHHmmss";
	
	public static String defaultDatePattern = "yyyy-MM-dd";
	public static String defaultDateTimePattern_mm = "yyyy-MM-dd HH:mm";
	public static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";
	public static String defaultDateTimePatternSSS = "yyyy-MM-dd HH:mm:ss SSS";



	// 简单日期格式属性
//	private  SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);

	/**
	 *注释： 获得当前年如2006
	 * 
	 * @return String
	 */
	public static String getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR) + "";
	}

	/**
	 * 注释：获得当前月 如 8
	 * 
	 * @return String
	 */
	public static String getMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1 + "";
	}
	/**
	 * 注释：获得当前日期的string格式 20150807151443
	 *
	 * @return String
	 */
	public static String getDateTimeString(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDateTimePatternString);
		return sdf.format(c.getTime());
	}

	/**
	 *注释： 获得当前天 如 7
	 * 
	 * @return String
	 */
	public static String getDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH) + "";
	}

	/*
	 * 注释：获得当前周 如第34周
	 * @return String
	 */
	public static String getWeek() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.WEEK_OF_YEAR) + "";
	}
	/*
	 *注释： 获得当天是本周第几天（星期天至星期六）
	 * @return String
	 */
	public static String getWeekDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK) + "";
	}

	/**
	 * 注释：获得当前小时 如12
	 * 
	 * @return String
	 */
	public static String getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY) + "";
	}

	/**
	 * 注释：获得当前分钟 如59
	 * 
	 * @return String
	 */
	public static String getMinute() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE) + "";
	}

	/**
	 * 注释：获得当前秒 如 53
	 * 
	 * @return String
	 */
	public static String getSeconde() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.SECOND) + "";
	}

	/**
	 * 注释：获得当前时间 模型<br>
	 * 1、 yyyy 为年 2、 MM 为月 3、 dd 为日 <br>
	 * 4、 HH 为小时 5、 mm 为分钟<br>
	 * 6、 ss 为秒 7、SSS 为毫秒 <br>
	 * 如模型 为 yyyy-MM-dd 则返回 2006-11-30 如模型 为 yyyy年MM月dd日<br>
	 * 则返回 2006年11月30日<br>
	 * 如模型 为 yy年MM月 则返回 08年11月<br>
	 * 
	 * @param pattern  日期格式
	 * @return String
	 */
	public static String getTime(String pattern) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(pattern);
		return sdf.format(c.getTime());
	}

	/**
	 * 注释：返回当前年月日 例如 2006-09-11
	 *
	 * @return String
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDatePattern);
		return sdf.format(c.getTime());
	}

	/**
	 *注释： 获得当前月的第一天
	 * @return
	 */
	public static String getBeginByMonth(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		return new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA).format(c.getTime());
	}
	/**
	 * 注释：获得当前月的最后一天
	 * @return
	 */
	public static String getEndByMonth(){
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sdf.format(ca.getTime());
	}

	// 获得本年第一天的日期

	/**
	 * 注释;获得本年第一天的日期
	 * @return
	 */
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preYearDay = df.format(yearDay);
		String[] arr_time = preYearDay.toString().split("-");
		if (arr_time[1].length() == 1) {
			arr_time[1] = "0" + arr_time[1];
		}
		if (arr_time[2].length() == 1) {
			arr_time[2] = "0" + arr_time[2];
		}
		preYearDay = arr_time[0] + "-" + arr_time[1] + "-" + arr_time[2];
		return preYearDay;
	}
	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}
	// 获得本年最后一天的日期 *

	/**
	 * 注释：获得本年最后一天的日期 *
	 * @return
	 */
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}
	/**
	 * 注释：返回距离January 1, 1970, 00:00:00 GMT.  date毫米的时间（计算接比实际多8小时）
	 * @param date 毫秒
	 * @return Date
	 * @todo  计算接比实际多8小时  	wangmin@2015年8月9日20:57:55
	 */
	public static Date getDate(long date) {
		return new Date(date);
	}
	public static String getDateTime(long date,String parent) {
		if(StringUtils.isBlank(parent)){
			parent=defaultDateTimePatternSSS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parent, Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 注释：返回当前时间，精确到秒 例如 2006-09-11 23:44:22
	 *
	 * @return String
	 */
	public static String getDateTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDateTimePattern);
		return sdf.format(c.getTime());
	}

	/**
	 * 注释：返回时间，精确到毫秒 例如 2006-09-11 23:44:22 333
	 *
	 * @return String
	 */
	public static String getDateTimeSSS() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDateTimePatternSSS);
		return sdf.format(c.getTime());
	}

	/**
	 * 注释：返回当前时间，排除日期，精确到秒 例如 23：44：22
	 *
	 * @return String
	 */
	public static String getTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern("HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 注释：获得日期格式的当前时间
	 * 
	 * @return java.util.Date
	 */
	public static Date getTimeToUtil() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 注释：获得当前时间针对SQL语句使用的，只包含日期而没有时间部分
	 *
	 * @return java.sql.Date
	 */
	public static java.sql.Date getTimeToSql() {
		Calendar c = Calendar.getInstance();
		return new java.sql.Date(c.getTime().getTime());
	}

	/**
	 * 注释：获得当前时间戳
	 * 
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getTimeToStamp() {
		Calendar c = Calendar.getInstance();
		return new Timestamp(c.getTime().getTime());
	}

	/** ******************************************得到当前时间类（end）****************************************************** */

	/** *******************************************时间转换类（begin）****************************************************** */

	/**
	 * 注释：通过java.util.date 及模型 转换为所需要的时间格式 <br>
	 * <p>如 getDate(new java.util.Date() ,"yyyy")<br>
	 * 获得当前年 得到2006<br>
	 * 
	 * @param date    日期
	 * @param pattern  日期格式
	 * @return String
	 */
	public static String getDateToString(Date date, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(pattern);

		return date == null ? "" : sdf.format(date);
	}
	/**
	 * 注释：通过java.sql.Timestamp 及模型转换为所需要的时间格式
	 * <p>例：String time=XTools.date.getDateToString(XTools.time.now(), "yyyy");<br>
	 * @param date
	 * @param pattern  日期格式
	 * @return  “2016”
	 */
	public static String getDateToString(Timestamp date, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(pattern);

		return date == null ? "" : sdf.format(date);
	}

	/**
	 * 注释：通过字符串时间类型得到相对应时间类型 <br>
	 * <p>如传入 "2006-11-30"得到相对应的时间类型<br>
	 * @param date  日期字符串
	 * @return java.util.Date
	 */
	public static Date getDateToUtil(String date) {
		String pattern = defaultDatePattern;
		Date curDate = null;
		date = date.replaceAll("年","-").replaceAll("月","-").replaceAll("日","");
		if (date.length()>19){
			pattern = defaultDateTimePatternSSS;
		}else if(date.length()>10&&date.length()<=16){
			pattern = defaultDateTimePattern_mm;
		}else if(date.length()>10){
			pattern = defaultDateTimePattern;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		sdf.applyPattern(pattern);
		try {
			curDate = sdf.parse(date);
		} catch (ParseException e) {
			throw new DataFormatException(e);
		}
		return curDate;
	}

	/**
	 * 注释：通过字符串时间类型得到相对应时间类型 如传入 "2006-11-30"得到相对应的时间类型<br>
	 * <p>例：XTools.date.getDateToUtil("2016-10-23 12:34:56.998","yyyy-MM-dd") <br>
	 * Sun Oct 23 00:00:00 CST 2016<br>
	 * @param date      日期字符串
	 * @param pattern   日期格式
	 * @return
	 */
	public static Date getDateToUtil(String date, String pattern) {

		Date curDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(pattern);
		try {
			curDate = new Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			throw new DataFormatException(e);
		}
		return curDate;
	}
	

	/**
	 *注释： 通过字符串时间类型得到相对应时间类型 如传入 "2006-11-30 12:34:56"得到相对应的时间类型<br>
	 * <p>例：XTools.date.getDateToUtil("2016-10-23 12:34:56.998","yyyy-MM-dd") <br>
	 * Sun Oct 23 00:00:00 CST 2016<br>
	 * @param date   日期字符串
	 * @return java.sql.Date
	 *
	 */
	public static Date getDateToString(String date) {

		Date curDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDateTimePattern);
		try {
			curDate = new Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			throw new DataFormatException(e);
		}
		return curDate;
	}

	/**
	 *注释： 通过传入的日期字符串，得到适用于sql的日期
	 * 
	 * @param date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getDateToSql(String date) {

		java.sql.Date curDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDatePattern);
		try {
			curDate = new java.sql.Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			throw new DataFormatException(e);
		}
		return curDate;
	}

	/**
	 * 注释：通过传入的日期字符串得到日期戳
	 * 
	 * @param date    日期字符串
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getDateToStamp(String date) {

		Timestamp curDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimePattern, Locale.CHINA);
		sdf.applyPattern(defaultDatePattern);
		try {
			curDate = new Timestamp(sdf.parse(date).getTime());
		} catch (ParseException e) {
			throw new DataFormatException(e);
		}
		return curDate;
	}

	/**
	 * 注释：自定义匹配时间类型得到相对应时间类型 如传入 "2006-11-30"得到相对应的时间类型
	 * 
	 * @param date    日期对象
	 * @return java.util.Date
	 */
	public static Date getObjectToDate(Object date) {

		Date curDate = null;

		if (date instanceof Date) {

			curDate = (Date) date;

			return curDate;

		} else if (date instanceof java.sql.Date) {

			curDate = new Date(((java.sql.Date) date).getTime());

			return curDate;

		} else if (date instanceof Timestamp) {

			curDate = new Date(((Timestamp) date).getTime());

			return curDate;

		} else if (date instanceof String) {

			if (date.toString().trim().indexOf(" ") != -1)
				curDate = getDateToUtil(date.toString(), defaultDateTimePattern);
			else
				curDate = getDateToUtil(date.toString());
			return curDate;

		} else {
			return null;
		}

	}

	/** *******************************************时间转换类（end）****************************************************** */
	/** *******************************************时间算法類（begin）****************************************************** */
	/** **加法** */

	/**
	 * 注释：天加法 算法为： 1、首先判断 date 类型 如为 String 类型 2006-11-30<br> 转换为 java.util.Date类型 精确到
	 * 毫秒 <br>2、day * 1天的毫秒数 <br>3、两数相加 转换 为 相应的类型<br> 如传入 “2006-11-30” 1 则得到 2006-12-01<br> 如传入
	 * "2006-11-30 11:00:00" 则得 2006-12-01 11:00:00<br>
	 * @param date   日期对象
	 * @param day    需要增加的天数
	 * @return
	 */
	public static Object addTimeToDay(Object date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.DAY_OF_YEAR, day);

		return c.getTime();
	}

	/**
	 * 注释：小时加法
	 * @param date  指定日期字符串
	 * @param hour  指定需要增加的小时个数
	 * @return
	 */
	public static Object addTimeToHour(Object date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.HOUR_OF_DAY, hour);

		return c.getTime();
	}

	/**
	 * 注释：分钟加法
	 * @param date  指定日期字符串
	 * @param minute  指定需要增加的分钟数
	 * @return
	 */
	public static Object addTimeToMinute(Object date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.MINUTE, minute);

		return c.getTime();

	}

	/**
	 * 注释：月加法
	 * @param date  指定日期字符串
	 * @param monty  指定需要增加月的个数
	 * @return
	 */
	public static Object addTimeToMonty(Object date, int monty) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.MONTH, monty);

		return c.getTime();

	}


	/**
	 * 注释：秒加法
	 * @param date   指定的日期字符串
	 * @param Seconde  指定需要增加的秒的个数
	 * @return
	 */
	public static Object addTimeToSeconde(Object date, int Seconde) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.SECOND, Seconde);

		return c.getTime();
	}

	/**
	 * 注释：毫秒加法
	 * @param date  指定需要增加的日期
	 * @param m      指定需要增加的毫秒值
	 * @return
	 */
	public static Object addTimeToMILLISECOND(Object date, int m) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));
		c.add(Calendar.MILLISECOND, m);
		return c.getTime();

	}

	/**
	 * 注释：周加法
	 * @param date  指定要增加周的日期
	 * @param week  指定要增加周的个数
	 * @return
	 */
	public static Object addTimeToWeek(Object date, int week) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.WEEK_OF_YEAR, week);

		return c.getTime();
	}
	/**
	 * 注释：年加法
	 * @param date  指定日期
	 * @param year  指定需要增加的年数
	 * @return
	 */
	public static Object addTimeToYear(Object date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.YEAR, year);

		return c.getTime();
	}

	/**
	 * 注释：日减法
	 * @param date  指定被减的日期
	 * @param day   指定需要减去的天数
	 * @return
	 */
	public static Object removeTimeToDay(Object date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.DAY_OF_YEAR, -day);

		return c.getTime();
	}

	/**
	 * 注释：小时减法
	 * @param date  指定日期
	 * @param hour  指定需要减去的小时个数
	 * @return       减去指定小时个数后的时间
	 */
	public static Object removeTimeToHour(Object date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.HOUR_OF_DAY, -hour);

		return c.getTime();
	}
	/**
	 * 注释：分钟减法
	 * @param date   指定日期
	 * @param minute  指定需要减去的分钟数
	 * @return         减去指定分钟数后的时间
	 */
	public static Object removeTimeToMinute(Object date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.MINUTE, -minute);

		return c.getTime();
	}

	/**
	 * 注释：月减法
	 * @param date  日期
	 * @param monty  指定要减去的月数
	 * @return       减去指定月数后的日期
	 */
	public static Object removeTimeToMonty(Object date, int monty) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.MONTH, -monty);

		return c.getTime();
	}

	/**
	 *注释：秒减法
	 * @param date    给定日期
	 * @param Seconde  指定需要减去的秒
	 * @return          减去指定秒后的日期
	 */
	public static Object removeTimeToSeconde(Object date, int Seconde) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.SECOND, -Seconde);

		return c.getTime();
	}

	/**
	 * 注释：周减法
	 * @param date  日期
	 * @param week  需要减去几周
	 * @return  	减去设定周后的日期
	 */
	public static Object removeTimeToWeek(Object date, int week) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.WEEK_OF_YEAR, -week);

		return c.getTime();
	}

	/**
	 *注释：年减法
	 * @param date  日期
	 * @param year  需要减去的年数
	 * @return
	 */
	public static Object removeTimeToYear(Object date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(getObjectToDate(date));

		c.add(Calendar.YEAR, -year);

		return c.getTime();
	}

	/**
	 *注释：开始时间-结束时间 剩余多少天 如begin 为2006-12-01 end 为 2006-12-10<br>
	 * 则结果为9天<br>
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return
	 */
	public static long partTimeToDay(Object begin, Object end) {

		return partTime(begin, end) / 1000 / 60 / 60 / 24;

	}

	/**
	 * 注释：开始时间-结束时间 剩余多少小时 如begin 为2006-12-01 end 为 2006-12-10<br>
	 * 则结果为9*24=216<br>
	 * @param begin  开始时间
	 * @param end    结束时间
	 * @return
	 */
	public static long partTimeToHour(Object begin, Object end) {

		return partTime(begin, end) / 1000 / 60 / 60;
	}
	/**
	 * 注释：开始时间-结束时间 剩余多少分钟 如begin 为2006-12-01 end 为 2006-12-10<br>
	 * 则结果为9*24*60=12960<br>
	 * @param begin  开始时间
	 * @param end    结束时间
	 * @return
	 */
	public static long partTimeToMinute(Object begin, Object end) {
		return partTime(begin, end) / 1000 / 60;
	}

	/**
	 *注释： 开始时间-结束时间 剩余多少秒 <br>
	 *      如begin 为2006-12-01 end 为 2006-12-10<br>
	 * 则结果为9*24*60*60=777600<br>
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return  则结果为9*24*60*60=777600<br>
	 */
	public static long partTimeToSeconde(Object begin, Object end) {
		return partTime(begin, end) / 1000;
	}

	/**
	 * 注释：开始时间-结束时间 剩余多少毫秒 如begin 为2006-12-01 end 为 2006-12-10<br>
	 * 则结果为9*24*60*60*1000=777600000<br>
	 * @param begin  开始时间
	 * @param end    结束时间
	 * @return
	 */
	public static long partTime(Object begin, Object end) {

		Date beginDate = getObjectToDate(begin);

		Date endDate = getObjectToDate(end);

		long milis = endDate.getTime() - beginDate.getTime();

		return milis;
	}


	/**
	 * 注释：得到指定年所有周末（周6、周日） 的集合
	 * @param year  指定年份
	 * @return     list
	 */
	public static List getWeekend(int year) {

		List<Date> retList = new ArrayList<Date>();

		GregorianCalendar c = new GregorianCalendar(year, 0, 1);

		while (c.get(Calendar.DAY_OF_WEEK) != 1
				&& c.get(Calendar.DAY_OF_WEEK) != 7) {

			c.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (c.get(Calendar.YEAR) == year) {
			if (c.get(Calendar.DAY_OF_WEEK) == 1
					|| c.get(Calendar.DAY_OF_WEEK) == 7) {
				retList.add(c.getTime());
			}

			c.add(Calendar.DATE, 1);

		}

		return retList;
	}

	/**
	 * 注释：通过自定义时间段所有周末（周6、周日）的集合
	 * @param begin   起始时间
	 * @param end     截止时间
	 * @return
	 */
	public static List getWeekend(Object begin, Object end) {

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(getObjectToDate(begin));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(getObjectToDate(end));
		if (cal1.after(cal2)) {
			return null;
		}
		List<Date> retList = new ArrayList<Date>();
		while (cal1.get(Calendar.DAY_OF_WEEK) != 1
				&& cal1.get(Calendar.DAY_OF_WEEK) != 7) {

			cal1.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (!(cal1.after(cal2))) {
			if (cal1.get(Calendar.DAY_OF_WEEK) == 1
					|| cal1.get(Calendar.DAY_OF_WEEK) == 7) {
				retList.add(cal1.getTime());
			}

			cal1.add(Calendar.DATE, 1);

		}
		return retList;

	}


	/**
	 * 注释：通过year年month月所有周末（周6、周日）的集合
	 * @param year  指定年份
	 * @param month  指定月份
	 * @return
	 */
	public static List getWeekendToMonth(int year, int month) {

		List<Date> retList = new ArrayList<Date>();

		GregorianCalendar c = new GregorianCalendar(year, month - 1, 1);

		while (c.get(Calendar.DAY_OF_WEEK) != 1
				&& c.get(Calendar.DAY_OF_WEEK) != 7) {

			c.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (c.get(Calendar.YEAR) == year
				&& c.get(Calendar.MONTH) + 1 == month) {
			if (c.get(Calendar.DAY_OF_WEEK) == 1
					|| c.get(Calendar.DAY_OF_WEEK) == 7) {
				retList.add(c.getTime());
			}

			c.add(Calendar.DATE, 1);

		}

		return retList;
	}

	/**
	 * 注释：得到指定年的工作日 排除周末（运行慢）
	 * 
	 * @param year    指定年份
	 * @param filter  为不用算的节日
	 * @return
	 * @todo  	wangmin@2015年8月9日20:53:56
	 */
	public static List getWorkDay(int year, List filter) {

		List<Date> retList = new ArrayList<Date>();

		GregorianCalendar c = new GregorianCalendar(year, 0, 1);

		while (c.get(Calendar.DAY_OF_WEEK) == 1
				|| c.get(Calendar.DAY_OF_WEEK) == 7) {

			c.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (c.get(Calendar.YEAR) == year) {
			boolean bool = true;
			if (c.get(Calendar.DAY_OF_WEEK) != 1
					&& c.get(Calendar.DAY_OF_WEEK) != 7) {
				for (int i = 0; i < filter.size(); i++) {
					Date filtdate = getObjectToDate(filter.get(i));
					if (filtdate.getTime() == c.getTime().getTime()) {
						bool = false;
					}
				}
				if (bool)
					retList.add(c.getTime());
			}

			c.add(Calendar.DATE, 1);

		}

		return retList;
	}

	/**
	 * 注释：得到指定时间段的工作日 排除周末
	 * @param begin   开始时间
	 * @param end     终止时间
	 * @param filter  需过滤时间
	 * @return        最终 工作日list
	 */
	public static List getWorkDay(Object begin, Object end, List filter) {
		Calendar c = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(getObjectToDate(begin));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(getObjectToDate(end));
		if (cal1.after(cal2)) {
			return null;
		}
		List<Date> retList = new ArrayList<Date>();
		while (cal1.get(Calendar.DAY_OF_WEEK) == 1
				|| cal1.get(Calendar.DAY_OF_WEEK) == 7) {

			cal1.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (!(cal1.after(cal2))) {
			boolean bool = true;

			if (cal1.get(Calendar.DAY_OF_WEEK) != 1
					&& cal1.get(Calendar.DAY_OF_WEEK) != 7) {

				for (int i = 0; i < filter.size(); i++) {
					Date filtdate = getObjectToDate(filter.get(i));
					if (filtdate.getTime() == cal1.getTime().getTime()) {
						bool = false;
					}
				}
				if (bool)
					retList.add(cal1.getTime());
			}

			cal1.add(Calendar.DATE, 1);

		}
		return retList;
	}

	/**
	 * 注释：得到year年month月的工作日 排除周末
	 * @param year  指定年份
	 * @param month 指定月份
	 * @param filter  需过滤的日期
	 * @return
	 */
	public static List getWorkDayToMonth(int year, int month, List filter) {

		List<Date> retList = new ArrayList<Date>();

		GregorianCalendar c = new GregorianCalendar(year, month - 1, 1);

		while (c.get(Calendar.DAY_OF_WEEK) == 1
				|| c.get(Calendar.DAY_OF_WEEK) == 7) {

			c.add(Calendar.DAY_OF_WEEK, 1);
		}

		while (c.get(Calendar.YEAR) == year
				&& c.get(Calendar.MONTH) + 1 == month) {
			boolean bool = true;

			if (c.get(Calendar.DAY_OF_WEEK) != 1
					&& c.get(Calendar.DAY_OF_WEEK) != 7) {

				for (int i = 0; i < filter.size(); i++) {
					Date filtdate = getObjectToDate(filter.get(i));
					if (filtdate.getTime() == c.getTime().getTime()) {
						bool = false;
					}
				}
				if (bool)
					retList.add(c.getTime());
			}

			c.add(Calendar.DATE, 1);

		}

		return retList;
	}


	/**
	 * 注释：要转换的毫秒数，该毫秒数转换为 * 天 * 小时 * 分钟哦 * 秒 后的格式
	 * @param mss 毫秒值
	 * @return
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
	}
	/**
	 * 注释：获取某年某月的最后一天
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 注释：是否闰年
	 *
	 * @param year  指定年
	 *
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}





	/** *******************************************时间转换成汉字表示****************************************************** */


	/**
	 * create date:2010-5-22下午03:40:44
	 * 描述：取出日期字符串中的年份字符串
	 *
	 * @param str 日期字符串
	 * @return
	 */
	public static String getChineseYearStr(String str) {
		String yearStr = "";
		str = format2ChineseStr(str);
		yearStr = str.substring(0, 4);
		return yearStr;
	}

	/**
	 * create date:2010-5-22下午03:40:47
	 * 描述：取出日期字符串中的月份字符串
	 *
	 * @param str 日期字符串
	 * @return
	 */
	public static String getChineseMonthStr(String str) {
		String monthStr;
		str = format2ChineseStr(str);
		int startIndex = str.indexOf("年");
		int endIndex = str.indexOf("月");
		monthStr = str.substring(startIndex + 1, endIndex);
		return monthStr;
	}

	/**
	 * create date:2010-5-22下午03:40:50
	 * 描述：取出日期字符串中的日字符串
	 *
	 * @param str 日期字符串
	 * @return
	 */
	public static String getChineseDayStr(String str) {
		String dayStr = "";
		str = format2ChineseStr(str);
		int startIndex = str.indexOf("月");
		int endIndex = str.indexOf("日");
		dayStr = str.substring(startIndex + 1, endIndex);
		return dayStr;
	}

	/**
	 * create date:2010-5-22下午03:32:31
	 * 描述：将源字符串中的阿拉伯数字格式化为汉字
	 *
	 * @param sign 源字符串中的字符
	 * @return
	 */
	private static char formatDigit(char sign) {
		if (sign == '0')
			sign = '〇';
		if (sign == '1')
			sign = '一';
		if (sign == '2')
			sign = '二';
		if (sign == '3')
			sign = '三';
		if (sign == '4')
			sign = '四';
		if (sign == '5')
			sign = '五';
		if (sign == '6')
			sign = '六';
		if (sign == '7')
			sign = '七';
		if (sign == '8')
			sign = '八';
		if (sign == '9')
			sign = '九';
		return sign;
	}

	/**
	 * create date:2010-5-22下午03:31:51
	 * 描述： 获得月份字符串的长度
	 *
	 * @param str  待转换的源字符串
	 * @param pos1 第一个'-'的位置
	 * @param pos2 第二个'-'的位置
	 * @return
	 */
	private static int getMidLen(String str, int pos1, int pos2) {
		return str.substring(pos1 + 1, pos2).length();
	}

	/**
	 * create date:2010-5-22下午03:32:17
	 * 描述：获得日期字符串的长度
	 *
	 * @param str  待转换的源字符串
	 * @param pos2 第二个'-'的位置
	 * @return
	 */
	private static int getLastLen(String str, int pos2) {
		return str.substring(pos2 + 1).length();
	}

	/**
	 * create date:2010-5-22下午03:32:46
	 * 描述：格式化日期
	 *
	 * @param str 源字符串中的字符
	 * @return
	 */
	public static String format2ChineseStr(String str) {
		StringBuffer sb = new StringBuffer();
		int pos1 = str.indexOf("-");
		int pos2 = str.lastIndexOf("-");
		for (int i = 0; i < 4; i++) {
			sb.append(formatDigit(str.charAt(i)));
		}
		sb.append('年');
		if (getMidLen(str, pos1, pos2) == 1) {
			sb.append(formatDigit(str.charAt(5)) + "月");
			if (str.charAt(7) != '0') {
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(7)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(7) != '1' && str.charAt(8) != '0') {
						sb.append(formatDigit(str.charAt(7)) + "十" + formatDigit(str.charAt(8)) + "日");
					} else if (str.charAt(7) != '1' && str.charAt(8) == '0') {
						sb.append(formatDigit(str.charAt(7)) + "十日");
					} else if (str.charAt(7) == '1' && str.charAt(8) != '0') {
						sb.append("十" + formatDigit(str.charAt(8)) + "日");
					} else {
						sb.append("十日");
					}
				}
			} else {
				sb.append(formatDigit(str.charAt(8)) + "日");
			}
		}
		if (getMidLen(str, pos1, pos2) == 2) {
			if (str.charAt(5) != '0' && str.charAt(6) != '0') {
				sb.append("十" + formatDigit(str.charAt(6)) + "月");
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十" + formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			} else if (str.charAt(5) != '0' && str.charAt(6) == '0') {
				sb.append("十月");
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十" + formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			} else {
				sb.append(formatDigit(str.charAt(6)) + "月");
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十" + formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			}
		}
		return sb.toString();
	}
}
