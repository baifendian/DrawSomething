package com.bfd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/** 
 * <p>文件名称: TimeUtils.java</p>
 * 
 * <p>文件功能: 时间操作工具类</p>
 *
 * <p>编程者: 拜力文</p>
 * 
 * <p>初作时间: 2016年7月9日 上午10:14:35</p>
 * 
 * <p>版本: version 1.0 </p>
 *
 * <p>输入说明: </p>
 *
 * <p>输出说明: </p>
 *
 * <p>程序流程: </p>
 * 
 * <p>============================================</p>
 * <p>修改序号:</p>
 * <p>时间:	 </p>
 * <p>修改者:  </p>
 * <p>修改内容:  </p>
 * <p>============================================</p>
 */
public class TimeUtils {
	 private static final Logger logger = LoggerFactory.getLogger(ClientConstants.CLIENT_LOG);
    
    /**
     * 年月日   yyyy-MM-dd
     */
    public final static String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 时分  HH:mm
     */
    public final static String FORMAT_TIME = "HH:mm";

    /**
     * 年月日 时分  yyyy-MM-dd HH:mm
     */
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";

    /**
     * MM月dd日 HH:mm
     */
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 HH:mm";
    
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static String FORMAT_FULL_DATE_TIME_WITH_SYMBOL = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * yyyyMMddHHmmss
     */
    public final static String FORMAT_FULL_DATE_TIME_NO_SYMBOL = "yyyyMMddHHmmss";
    
    /**
     * yyyyMMddHHmmssSSS
     */
    public final static String FORMAT_FULL_DATE_TIME_WITH_MILLS_NO_SYMBOL = "yyyyMMddHHmmssSSS";
    
    /**
     * Description：比较俩日期相差秒数
     * 
     * @param date1
     * @param date2
     * @return
     * @return int
     * @author name：
     */
    public static int compareDateToSecond(Date date1, Date date2) {
        long days = date1.getTime() - date2.getTime();
        int time = (int) (days / 1000);
        return time;
    }

    /**
     * Description： 比较俩日期相差天数
     * 
     * @param date1
     * @param date2
     * @return
     * @return int
     * @author name：
     */
    public static int compareDateToday(Date date1, Date date2) {
        long days = date1.getTime() - date2.getTime();
        int time = (int) (days / (1000 * 60 * 60 * 24));
        return time;
    }

    /**
     * Description：字符串转为日期
     *  string to date
     * @param str
     * @return Date
     * @author name：
     * <pre>
     * 关于参数 format
     * @see #FORMAT_DATE
     * @see #FORMAT_DATE_TIME
     * @see #FORMAT_FULL_DATE_TIME_NO_SYMBOL
     * @see #FORMAT_FULL_DATE_TIME_WITH_MILLS_NO_SYMBOL
     * @see #FORMAT_FULL_DATE_TIME_WITH_SYMBOL
     * @see #FORMAT_MONTH_DAY_TIME
     * @see #FORMAT_TIME
     * </pre>
     */
    public static Date parseToDate(String str ,String formats) {

        SimpleDateFormat format = new SimpleDateFormat(formats);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
        	logger.error("发生错误", e);
        }
        return date;
    }
    
    /**
     * Description：将长时间格式时间转换为字符串
     *  date to String
     * @param format
     * @param date
     * @return String
     * @author name：lljqiu
     * <pre>
     * 关于参数 format
     * @see #FORMAT_DATE
     * @see #FORMAT_DATE_TIME
     * @see #FORMAT_FULL_DATE_TIME_NO_SYMBOL
     * @see #FORMAT_FULL_DATE_TIME_WITH_MILLS_NO_SYMBOL
     * @see #FORMAT_FULL_DATE_TIME_WITH_SYMBOL
     * @see #FORMAT_MONTH_DAY_TIME
     * @see #FORMAT_TIME
     * </pre>
     */
    public static String parseToString(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }
    
    /** 
    * Description：
    *   获取当前时间
    * @param format 时间格式
    *   例如：yyyyMMddHHmmss
    * @return
    		String
    * @author name：lljqiu
     **/
    public static String getTimes(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(new Date());
        return dateString;
    }
    
	/**
	 * Description：将长时间格式时间转换为字符串
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @return String
	 * @author name：
	 */
	public static String dateFormat(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}    
    
    public static void main(String[] args) {
        System.out.println(getTimes(TimeUtils.FORMAT_FULL_DATE_TIME_WITH_SYMBOL));
    }
    
}
