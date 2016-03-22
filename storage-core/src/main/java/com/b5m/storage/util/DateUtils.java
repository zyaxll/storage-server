/**
 * //TODO
 */
package com.b5m.storage.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * //TODO
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Nov 6, 2015 10:35:46 PM
 */
public abstract class DateUtils{

	public static final String DATE_STR_FORMATTER = "yyyy-MM-dd";

	public static final String DATE_HH_STR_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	public static final int DAY_SECONDS = 24 * 60 * 60;

	/**
	 * 获取  当前年、半年、季度、月、日、小时 开始结束时间
	 */
	private static final DateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
	private static final DateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DateUtils(){}

	public static Date valueOf(Object value){
	    if(value == null){
	        return null;
	    }
	    if(value instanceof Date){
	        return (Date)value;
        }else if(value instanceof Long){
            return new Date((Long)value);
        }else{
            String str = value.toString();
            try {
                long val = Long.parseLong(str);
                return new Date(val);
            } catch (RuntimeException e) {
                try {
                    return longSdf.parse(str);
                } catch (ParseException e1) {
                    throw new RuntimeException(e);
                }
            }
        }
	}

	/**
	 * 将字符串类型的时间转换成long型的时间
	 *
	 * @param timeText,字符串格式的时间
	 * @param format,时间格式化表达式
	 * @return
	 */
	public static long longValue(String timeText, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date;
		try {
			date = df.parse(timeText);
		} catch (ParseException e) {
			throw new IllegalArgumentException("错误的参数格式");
		}
		return date.getTime();
	}
	
	/**
	 * 获得本周的第一天，周一
	 *
	 * @return
	 */
	public static Date getCurrentWeekDayStartTime() throws Exception {
		Calendar c = Calendar.getInstance();

		int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
		c.add(Calendar.DATE, -weekday);
		c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));

		return c.getTime();
	}

	/**
	 * 获得本周的最后一天，周日
	 *
	 * @return
	 */
	public static Date getCurrentWeekDayEndTime() throws Exception {
		Calendar c = Calendar.getInstance();

		int weekday = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DATE, 8 - weekday);
		c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));

		return c.getTime();
	}

	/**
	 * 获得本天的开始时间，即2015-06-01 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentDayStartTime() throws Exception {
		Date now = new Date();

		now = shortSdf.parse(shortSdf.format(now));

		return now;
	}

	/**
	 * 获得本天的结束时间，即2012-01-01 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentDayEndTime() throws Exception {
		Date now = new Date();

		now = longSdf.parse(shortSdf.format(now) + " 23:59:59");

		return now;
	}

	/**
	 * 获得本小时的开始时间，即2012-01-01 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentHourStartTime() throws Exception {
		Date now = new Date();
		try {
			now = longHourSdf.parse(longHourSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本小时的结束时间，即2012-01-01 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentHourEndTime() throws Exception {
		Date now = new Date();

		now = longSdf.parse(longHourSdf.format(now) + ":59:59");

		return now;
	}

	/**
	 * 获得本月的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentMonthStartTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.DATE, 1);
		now = shortSdf.parse(shortSdf.format(c.getTime()));

		return now;
	}

	/**
	 * 当前月的结束时间，即2012-01-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentMonthEndTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");

		return now;
	}

	/**
	 * 获得下月的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Date getNextMonthStartTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);
		now = shortSdf.parse(shortSdf.format(c.getTime()));

		return now;
	}
	/**
	 * 下月的结束时间，即2012-01-31 23:59:59
	 *
	 * @return
	 */
	public static Date getNextMonthEndTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 2);
		c.add(Calendar.DATE, -1);
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");

		return now;
	}

	/**
	 * 当前年的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentYearStartTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		now = shortSdf.parse(shortSdf.format(c.getTime()));

		return now;
	}

	/**
	 * 当前年的结束时间，即2012-12-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentYearEndTime() throws Exception {
		Calendar c = Calendar.getInstance();
		Date now = null;

		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");

		return now;
	}

	/**
	 * 当前季度的开始时间，即2012-01-1 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() throws Exception {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;

		if (currentMonth >= 1 && currentMonth <= 3)
			c.set(Calendar.MONTH, 0);
		else if (currentMonth >= 4 && currentMonth <= 6)
			c.set(Calendar.MONTH, 3);
		else if (currentMonth >= 7 && currentMonth <= 9)
			c.set(Calendar.MONTH, 4);
		else if (currentMonth >= 10 && currentMonth <= 12)
			c.set(Calendar.MONTH, 9);
		c.set(Calendar.DATE, 1);
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");

		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() throws Exception {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;

		if (currentMonth >= 1 && currentMonth <= 3) {
			c.set(Calendar.MONTH, 2);
			c.set(Calendar.DATE, 31);
		} else if (currentMonth >= 4 && currentMonth <= 6) {
			c.set(Calendar.MONTH, 5);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= 7 && currentMonth <= 9) {
			c.set(Calendar.MONTH, 8);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= 10 && currentMonth <= 12) {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
		}
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");

		return now;
	}

	/**
	 * 获取前/后半年的开始时间
	 *
	 * @return
	 */
	public static Date getHalfYearStartTime() throws Exception {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;

		if (currentMonth >= 1 && currentMonth <= 6) {
			c.set(Calendar.MONTH, 0);
		} else if (currentMonth >= 7 && currentMonth <= 12) {
			c.set(Calendar.MONTH, 6);
		}
		c.set(Calendar.DATE, 1);
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");

		return now;

	}

	/**
	 * 获取前/后半年的结束时间
	 *
	 * @return
	 */
	public static Date getHalfYearEndTime() throws Exception {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;

		if (currentMonth >= 1 && currentMonth <= 6) {
			c.set(Calendar.MONTH, 5);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= 7 && currentMonth <= 12) {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
		}
		now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");

		return now;
	}


	public static String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// ComConstants.SDF_YYYYMMDDHHMMSS;
		return sdf.format(new Date());
	}

	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssMs");// ComConstants.SDF_YYYYMMDDHHMMSS;
		return sdf.format(new Date());
	}

	public static String getDateTime2() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssMs");// ComConstants.SDF_YYYYMMDDHHMMSS;
		return sdf.format(new Date());
	}

	public static synchronized String getInviteCodeByNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssMs");// ComConstants.SDF_YYYYMMDDHHMMSS;
		String dateStr = sdf.format(new Date());
		return getRandomInteger() + dateStr;
	}

	public static String getRandomInteger() {
		int temp = (int) (Math.random() * 9999);
		return String.valueOf(temp);
	}

	public static Date String2Date(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		return String2Date(dateStr, "yyyyMMdd");
	}

	public static String Date2String(Date date) {
		if (null == date) {
			return "";
		}
		return Date2String(date, "yyyy-MM-dd");
	}

	public static Date String2Date(String dateStr, String DateFormat) {


		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String Date2String(Date date, String DateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取离截止时间的剩余时间
	 *
	 * @param endDate 截止时间,格式yyyyMMddHHmmss
	 * @return
	 */
	public static String getRemainingTime(String endDate) {
		String rtn = "";
		if (StringUtils.isBlank(endDate))
			return rtn;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// ComConstants.SDF_YYYYMMDDHHMMSS;//存在线程安全问题，故不能使用静态
		try {
			Date deadline = sdf.parse(endDate);
			long remaining = deadline.getTime() - System.currentTimeMillis();
			// long remaining=lngDeadline-System.currentTimeMillis();
			if (remaining > 0) {
				// int ms = (int) (remaining % 1000);
				remaining /= 1000;
				// int sc = (int) (remaining % 60);
				remaining /= 60;
				int mn = (int) (remaining % 60);
				remaining /= 60;
				int hr = (int) (remaining % 24);
				long dy = (int) remaining / 24;
				rtn = dy + "天" + hr + "小时" + mn + "分";// + sc + "秒";
			} else {
				rtn = "过期";
			}
		} catch (ParseException e) {

		}
		return rtn;
	}

	/**
	 * 根据当天日期 格式 2013-05-31
	 *
	 * @return
	 */
	public static String getCurrentDay() {
		return getBeforeOrAfterDay(0);
	}

	/**
	 * 根据当前时间获取后一天日期 格式 2013-05-31
	 *
	 * @return
	 */
	public static String getAfterToday() {
		return getBeforeOrAfterDay(1);
	}

	/**
	 * 根据date 获取后一天日期 格式 2013-05-31
	 *
	 * @param date
	 * @return
	 */
	public static String getAfterToday(Date date) {
		return getBeforeOrAfterDay(date, 1);
	}

	/**
	 * 根据当前时间获取后一周前的日期 格式 2013-05-31
	 *
	 * @return
	 */
	public static String getOneWeekBefore() {
		return getBeforeOrAfterDay(-7);
	}

	/**
	 * 设置当前日期的 前 或者 后 daycount 天
	 *
	 * @param daycount
	 * @return
	 */
	public static String getBeforeOrAfterDay(int daycount) {
		Date dayBefore = new Date();
		if (daycount != 0) {
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(new Date());// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, daycount); // 设置为 当前 + dayCount
			dayBefore = calendar.getTime(); // 得到当前 + dayCount 的时间
		}
		return Date2String(dayBefore);
	}

	/**
	 * 设置当前日期的 前 或者 后 daycount 天
	 *
	 * @param daycount
	 * @return
	 */
	public static Date getBeforeOrAfterDate(int daycount) {
		Date dayBefore = new Date();
		if (daycount != 0) {
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(new Date());// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, daycount); // 设置为 当前 + dayCount
			dayBefore = calendar.getTime(); // 得到当前 + dayCount 的时间
		}
		return dayBefore;
	}

	public static String getBeforeOrAfterDay(Date date, int daycount) {
		if (daycount != 0) {
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(date);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, daycount); // 设置为 当前 + dayCount
			date = calendar.getTime(); // 得到当前 + dayCount 的时间
		}
		return Date2String(date);
	}

	public static Date getBeforeOrAfterDayToDate(Date date, int daycount) {
		if (daycount != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, daycount); // 设置为 当前 + dayCount
			date = calendar.getTime();
		}
		return date;
	}

	/**
	 * 计算当前一天还剩余多少秒
	 * @return int
	 */
	public static int remainSecondsInToday() {
	    long cur = System.currentTimeMillis();
	    Calendar c = Calendar.getInstance();
	    c.add(Calendar.DATE, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
		return (int)((c.getTimeInMillis() - cur) / 1000);
	}

	public static Date getBeforeOrAfterMinuteToDate(Date date, int minutecount) {
		if (null == date) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutecount);
		return c.getTime();
	}

	public static Date getBeforeOrAfterSecondToDate(Date date, int secondcount) {
		if (null == date) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, secondcount);
		return c.getTime();
	}


	/**
	 * 获取当前自然月 yyyyMM
	 *
	 * @return
	 */

	public static String getCurrentMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(new Date());
	}

	/**
	 * format: yyyy-MM-dd
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareDay(Date d1, Date d2) {
		long t1 = DateUtils.String2Date(DateUtils.Date2String(d1, DATE_STR_FORMATTER), DATE_STR_FORMATTER).getTime();

		long t2 = DateUtils.String2Date(DateUtils.Date2String(d2, DATE_STR_FORMATTER), DATE_STR_FORMATTER).getTime();

		return new Long((t1 - t2) / (24 * 60 * 60 * 1000)).intValue();
	}

	private static char[] CODE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F'};

    /**
     * 通过一定算法将当前当前纳秒级时间转换成由字母组成的字符串
     * 
     * @return
     */
    public synchronized static String getTimeString() {
        StringBuilder sb = new StringBuilder();
        long current = System.nanoTime();
        while (current > 0) {
            sb.append(CODE[(int) (current & 31L)]);
            current >>= 5;
        }
        return sb.toString();
    }
    
    private static char[] NEW_CODE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    /**
     * 通过一定算法将当前当前纳秒级时间转换成由0-f组成的字符串
     * 
     * @return
     */
    public synchronized static String getNanoString() {
        StringBuilder sb = new StringBuilder();
        long current = System.nanoTime();
        while (current > 0) {
            sb.append(NEW_CODE[(int) (current & 0xf)]);
            current >>= 4;
        }
        return sb.toString();
    }
    
    /**
     * 获取时间间隔描述
     * 
     * @param timeGap 时间间隔，单位毫秒
     * @return 时间间隔描述
     */
    public static String traslateTime(long timeGap) {
        if (timeGap < 0) {
            throw new IllegalArgumentException("The time gap is negative");
        }
        long day = timeGap / 86400000L;
        timeGap = timeGap % 86400000L;
        long hour = timeGap / 3600000L;
        timeGap = timeGap % 3600000L;
        long minute = timeGap / 60000L;
        timeGap = timeGap % 60000L;
        long second = timeGap / 1000L;
        timeGap = timeGap % 1000L;
        long[] num = new long[] {day, hour, minute, second, timeGap};
        String[] numName = new String[] {"天", "小时", "分", "秒", "毫秒"};
        StringBuilder sb = new StringBuilder();
        int i, len = num.length;
        for (i = 0; i < len; ++i) {
            if (num[i] > 0) {
                sb.append(num[i]).append(numName[i]);
            }
        }
        return sb.toString();
    }
	
}
