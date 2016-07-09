

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;


public class DateFormatUtil {

    public static Calendar dateFormatFromString(String dateTimeStr, String pattern) {

        // "yyyy-MM-dd HH:mm:ss"
        Calendar c = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date dateTime = format.parse(dateTimeStr);
            c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            c.setTime(dateTime);
        } catch (ParseException e) {

            c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        }
        return c;
    }

    public static String dateFormatFromCalender(Calendar c, String pattern) {

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        String dateTime = format.format(c.getTime());

        return dateTime;
    }

    public static long dateFormatFromStringToLong(String dateTimeStr) {

        return dateFormatFromString(dateTimeStr, "yyyy-MM-dd HH:mm:ss").getTime().getTime();

    }

    public static long dateFormatFromStringToLong1(String dateTimeStr) {

        return dateFormatFromString(dateTimeStr, "yyyy-MM-dd HH:mm").getTime().getTime();

    }

    public static long dateFormatFromStringToLong2(String dateTimeStr) {

        return dateFormatFromString(dateTimeStr, "yyyy-MM-dd").getTime().getTime();

    }

    public static long dateFormatFromStringToLong3(String dateTimeStr) {

        return dateFormatFromString(dateTimeStr, "yyyyMMdd HH:mm").getTime().getTime();

    }

    public static String dateFormatFromMs(long mss) {

        Date dateTime = new Date(mss);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(dateTime);

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateTimeStr = format.format(c.getTime());

        return dateTimeStr;
    }

    public static String dateFormatFromMs1(long mss) {

        Date dateTime = new Date(mss);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(dateTime);

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日HH:mm");

        String dateTimeStr = format.format(c.getTime());

        return dateTimeStr;
    }

    public static String dateFormatFromMs2(long mss) {

        Date dateTime = new Date(mss);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(dateTime);

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("M月d日HH:mm");

        String dateTimeStr = format.format(c.getTime());

        return dateTimeStr;
    }

    public static String dateFormatFromMs3(long mss) {

        Date dateTime = new Date(mss);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(dateTime);

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        String dateTimeStr = format.format(c.getTime());

        return dateTimeStr;
    }

    public static String dateFormatFromMsToYesterday(long mss) {

        Date dateTime = new Date(mss);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(dateTime);

        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("昨天 HH:mm");

        String dateTimeStr = format.format(c.getTime());

        return dateTimeStr;
    }

    public static String dateFormat(long mss) {

        boolean isToday = isToday(dateFormatFromString(dateFormatFromMs(mss), "yyyy-MM-dd HH:mm:ss"));

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        long currentMs = c.getTime().getTime();
        Map<String, Long> datetimeMap = formatDuring(currentMs - mss);
        long months = datetimeMap.get("months");
        long weeks = datetimeMap.get("weeks");
        long days = datetimeMap.get("days");
        long hours = datetimeMap.get("hours");
        long minutes = datetimeMap.get("minutes");
        long seconds = datetimeMap.get("seconds");

        // Log.w("dateFormat", "months = " + months);
        // Log.w("dateFormat", "weeks = " + weeks);
        // Log.w("dateFormat", "days = " + days);
        // Log.w("dateFormat", "hours = " + hours);
        // Log.w("dateFormat", "minutes = " + minutes);
        // Log.w("dateFormat", "seconds = " + seconds);

        if (months >= 12) {

            return dateFormatFromMs1(mss);

        } else {

            if (hours >= 24) {
                if (hours <= 48 && days < 2) {

                    return dateFormatFromMsToYesterday(mss);

                }
                return dateFormatFromMs2(mss);

            }

            else if (hours > 0 && hours < 24) {

                if (isToday) {

                    return dateFormatFromMs3(mss);
                } else {

                    return dateFormatFromMsToYesterday(mss);
                }

            }
        }

        return dateFormatFromMs3(mss);
    }

    public static boolean dateFormat1(long mss) {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        long currentMs = c.getTime().getTime();
        Map<String, Long> datetimeMap = formatDuring(currentMs - mss);
        long months = datetimeMap.get("months");
        long weeks = datetimeMap.get("weeks");
        long days = datetimeMap.get("days");
        long hours = datetimeMap.get("hours");
        long minutes = datetimeMap.get("minutes");
        long seconds = datetimeMap.get("seconds");

        if (months >= 12) {

            return false;

        } else if (months > 0 && months < 12) {

            return false;
        }

        if (weeks >= 4) {

            return false;

        } else if (weeks > 0 && weeks < 4) {

            return false;
        }

        if (days >= 7) {

            return false;

        } else if (days > 0 && days < 7) {

            return true;
        }

        if (hours >= 24) {

            return true;

        } else if (hours > 0 && hours < 24) {

            return true;
        }

        if (minutes >= 60) {

            return true;

        } else if (minutes > 0 && minutes < 60) {

            return true;
        }

        if (seconds >= 60) {

            return true;

        } else if (seconds > 0 && seconds < 60) {

            return true;
        }

        return true;
    }

    /**
     * 按分、小时、天、周，月 具体时间五个纬度统一时间
     */
    public static Map<String, Long> formatDuring(long mss) {

        Map<String, Long> datetimeMap = new LinkedHashMap<String, Long>();

        long months = mss / (1000 * 60 * 60 * 24 * 30);
        long weeks = (mss % (1000 * 60 * 60 * 24 * 30)) / (1000 * 60 * 60 * 24 * 7);
        long days = (mss % (1000 * 60 * 60 * 24 * 7)) / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        datetimeMap.put("months", months);
        datetimeMap.put("weeks", weeks);
        datetimeMap.put("days", days);
        datetimeMap.put("hours", hours);
        datetimeMap.put("minutes", minutes);
        datetimeMap.put("seconds", seconds);

        return datetimeMap;
    }

    public static String formatDuring1(long mss) {

        long minutes = mss / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        if (minutes == 0) {

            return seconds + " \" ";
        }
        return minutes + " ' " + seconds + " \" ";
    }

    public static boolean isToday(Calendar c) {
        Calendar today_start = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_start.set(Calendar.HOUR_OF_DAY, 0);
        today_start.set(Calendar.MINUTE, 0);
        Calendar today_end = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_end.set(Calendar.HOUR_OF_DAY, 0);
        today_end.set(Calendar.MINUTE, 0);
        today_end.add(Calendar.DAY_OF_MONTH, 1);

        return c.before(today_end) && c.after(today_start);
    }
    
    public static boolean isYesterday(Calendar c) {
        Calendar today_start = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_start.set(Calendar.HOUR_OF_DAY, 0);
        today_start.set(Calendar.MINUTE, 0);
        today_start.add(Calendar.DAY_OF_MONTH, -1);
        Calendar today_end = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_end.set(Calendar.HOUR_OF_DAY, 0);
        today_end.set(Calendar.MINUTE, 0);

        return c.before(today_end) && c.after(today_start);
    }
    public static boolean isCurrentYear(Calendar c) {
        Calendar today_start = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_start.set(Calendar.HOUR_OF_DAY, 0);
        today_start.set(Calendar.MINUTE, 0);
        today_start.set(Calendar.MONTH,0);
        today_start.set(Calendar.DAY_OF_MONTH,1);
        today_start.set(Calendar.SECOND,0);
        System.out.println(dateFormatFromCalender(today_start,"yyyy-MM-dd HH:mm:ss"));
        Calendar today_end = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        today_end.set(Calendar.HOUR_OF_DAY, 23);
        today_end.set(Calendar.MINUTE, 59);
        today_end.set(Calendar.MONTH,11);
        today_end.set(Calendar.DAY_OF_MONTH,31);
        today_end.set(Calendar.SECOND,59);
        System.out.println(dateFormatFromCalender(today_end,"yyyy-MM-dd HH:mm:ss"));
        return c.before(today_end) && c.after(today_start);
    }
    
    public static void main(String[] args) {
        final Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        instance.set(2015,5, 23);
        final boolean today = isCurrentYear(instance);
        System.out.println(today);
    }

}
