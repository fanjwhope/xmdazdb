 package com.hr.global.util;
 
 import java.sql.Timestamp;
 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.ParsePosition;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.GregorianCalendar;
 import java.util.TimeZone;
 
 /**
  * 
  * @author 黄飞
  *
  */
 public class DateFunc extends GregorianCalendar
 {
   public static final String SHOWING_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String SHOWING_TIME_FORMAT2 = "HH24:mi:ss";
   public static final String SHOWING_DATE_FORMAT = "yyyy-MM-dd";
   public static final String SHOWING_DATE_YM_FORMAT = "yyyy-MM";
   public static final String SHOWING_DATE_H_M_FORMAT = "yyyy-MM-dd HH:mm";
   public static final String SHOWING_TIME_FORMAT = "HH:mm:ss";
   public static final String SHOWING_DATE_H_M_S_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String SHOWING_DATE_TIME_FORMAT2 = "yyyy-MM-dd HH24:mi:ss";
   public static final String SHOWING_DATE_TIME_FORMAT_ORA = "yyyy-MM-dd HH:MI:ss";
   public static final String SHOWING_DATE_TIME_FORMAT_DB = "%Y-%m-%d %H:%i:%s";
   public static final String SHOWING_DATE_FORMAT_DB = "%Y-%m-%d";
   public static final String SHOWING_DATE_H_M_FORMAT_DB = "%Y-%m-%d %H:%i";
   public static final String SHOWING_TIME_FORMAT_DB = "%H:%i:%s";
   public static final String DBGET_DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
   public static final String DBGET_DATE_FORMAT = "MM/dd/yyyy";
   public static final String EXCEL_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
   public static final String EXCEL_DATE_FORMAT = "dd/MM/yyyy";
   public static String dateFormate = "yyyy-MM-dd";
   private String url;
   private static final boolean gideaTime = true;
 
   public void setYear(int year)
   {
     set(1, year); } 
   public int getYear() { return get(1); } 
   public void setMonth(int month) { set(2, month); } 
   public int getMonth() { return get(2); } 
   public void setDay(int day) { set(5, day); } 
   public int getDay() { return get(5); } 
   public void setHour(int hour) { set(10, hour == 12 ? 0 : hour); } 
   public int getHour() { return get(10); } 
   public void setMinute(int minute) { set(12, minute); } 
   public int getMinute() { return get(12); } 
   public void setSecond(int second) { set(13, second); } 
   public int getSecond() { return get(13); } 
   public void setAMPM(int am_pm) {
     set(9, am_pm); } 
   public int getAMPM() { return get(9); } 
   public int getDayOfWeek() { return get(7); } 
   public int getActualMaximum(int field) {
     Calendar cale = Calendar.getInstance();
     return cale.getActualMaximum(field);
   }
 
   public DateFunc()
   {
     set(1931, 1, 1, 0, 0, 0);
   }
 
   public DateFunc(Timestamp t)
   {
     setTime(t);
   }
 
   public DateFunc(String t)
   {
     setTime(strToTimestamp(t));
   }
 
   public Timestamp getTimestamp()
   {
     return new Timestamp(getTimeInMillis());
   }
 
   public long getTimeInMillis()
   {
     return super.getTimeInMillis();
   }
 
   public static Timestamp getNow()
   {
     Calendar c = Calendar.getInstance();
     long now = c.getTime().getTime();
     Timestamp t = new Timestamp(now);
     return t;
   }
 
   public static String getNowString() {
     Timestamp t = getNow();
     String s = getDefDateFormat(t);
     return s;
   }
 
   public static String getNowStrDateHm() {
     Timestamp t = getNow();
     return dateToStr(t, "yyyy-MM-dd HH:mm");
   }
 
   public static java.util.Date getNowDate()
   {
     Calendar c = Calendar.getInstance();
     java.util.Date d = c.getTime();
     return d;
   }
 
   public static java.sql.Date getNowDateSQL() {
     return new java.sql.Date(Calendar.getInstance().getTime().getTime());
   }
 
   public static String getDefDateFormat(Timestamp adate) {
     try {
       if (adate == null) return "";
       SimpleDateFormat formatDate = new SimpleDateFormat(dateFormate);
       return formatDate.format(adate); } catch (Exception e) {
     }
     return null;
   }
 
   public static String getDefDateFormat(String strDate)
   {
     try {
       if (Validation.isEmpty(strDate)) return "";
       Timestamp tm = Timestamp.valueOf(strDate);
       return getDefDateFormat(tm);
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }return "";
   }
 
   public static String getDefDateFormat(java.util.Date adate)
   {
     try
     {
       SimpleDateFormat formatDate = new SimpleDateFormat(dateFormate);
       return formatDate.format(adate); } catch (Exception e) {
     }
     return "";
   }
   
   /**
    * 获取当期日期的字符串格式
    * @param dateFormat 指定日期格式
    * @return
    */
   public static String getNowDateStr(String dateFormat)
   {
     try
     {
       SimpleDateFormat formatDate = new SimpleDateFormat(dateFormat);
       return formatDate.format(getNowDate()); } catch (Exception e) {
     }
     return "";
   }
 
   public static String getDefDateFormat(Object adate)
   {
     if (adate == null) return "";
     String name = adate.getClass().getName().toLowerCase();
     if (name.indexOf("date") >= 0)
       return getDefDateFormat((java.util.Date)adate);
     if (name.indexOf("string") >= 0)
       return getDefDateFormat((String)adate);
     if (name.indexOf("timestamp") >= 0) {
       return getDefDateFormat((Timestamp)adate);
     }
     return "";
   }
 
   public static Timestamp strToTimestamp(String dateStr)
   {
     try
     {
       SimpleDateFormat formatDate = new SimpleDateFormat(dateFormate);
       ParsePosition pos = new ParsePosition(0);
       java.util.Date tempDate = formatDate.parse(dateStr, pos);
       return new Timestamp(tempDate.getTime()); } catch (Exception ex) {
     }
     return null;
   }
 
   public static String format(Timestamp ts, String formatter)
   {
     try
     {
       SimpleDateFormat formatDate = new SimpleDateFormat(formatter);
       return formatDate.format(ts); } catch (Exception e) {
     }
     return ts.toString();
   }
 
   public static long getCompareDay(Object date1, Object date2)
     throws Exception
   {
     long day = 0L;
     java.util.Date d1 = null;
     java.util.Date d2 = null;
     if ((date1.getClass().getName().toUpperCase().indexOf("DATE") >= 0) || 
       (date1.getClass().getName().toUpperCase().indexOf("TIMESTAMP") >= 0))
       d1 = (java.util.Date)date1;
     else if (date1.getClass().getName().toUpperCase().indexOf("STRING") >= 0) {
       d1 = strToTimestamp((String)date1);
     }
     if ((date2.getClass().getName().toUpperCase().indexOf("DATE") >= 0) || 
       (date2.getClass().getName().toUpperCase().indexOf("TIMESTAMP") >= 0))
       d2 = (java.util.Date)date2;
     else if (date2.getClass().getName().toUpperCase().indexOf("STRING") >= 0) {
       d2 = strToTimestamp((String)date2);
     }
     day = (d1.getTime() - d2.getTime()) / 1000L / 3600L / 24L;
     return day;
   }
   public static String getDateFormate() {
     return dateFormate;
   }
   public static void setDateFormate(String dateFormate) {
     dateFormate = dateFormate;
   }
 
   public static Timestamp getFirstDayOfMonth() {
     Timestamp ts = getNow();
     Timestamp tsday = new Timestamp(ts.getYear(), ts.getMonth(), 1, 0, 0, 0, 0);
     return tsday;
   }
 
   public static Timestamp getLastDayOfMonth() {
     DateFunc df = new DateFunc(getNow());
     Timestamp ts = getNow();
     int lastDay = df.getActualMaximum(5);
     Timestamp day = new Timestamp(ts.getYear(), ts.getMonth(), lastDay, 23, 59, 59, 0);
     return day;
   }
 
   public static java.util.Date strToDateAsDefFormat(String strDate)
   {
     return strToDate(strDate, dateFormate);
   }
 
   public static java.util.Date strToDate(String strDate, String strFormat)
   {
     java.util.Date resultDate = null;
     try {
       SimpleDateFormat dataFormat = new SimpleDateFormat(strFormat);
       ParsePosition pos = new ParsePosition(0);
       resultDate = dataFormat.parse(strDate, pos);
     }
     catch (Exception localException) {
     }
     return resultDate;
   }
   public static String strToStr(String dateStr, String formatter) {
     String dateString = "";
     try {
       SimpleDateFormat dataFormat1 = new SimpleDateFormat("yyyy-MM-dd");
       ParsePosition pos = new ParsePosition(0);
       java.util.Date date = dataFormat1.parse(dateStr);
       SimpleDateFormat dataFormat2 = new SimpleDateFormat(formatter);
       dateString = dataFormat2.format(date);
     } catch (ParseException e) {
       return dateString;
     }
 
     return dateString;
   }
 
   public static String dateToStr(java.util.Date date, String strFormat)
   {
     String resultDate = null;
     if (date == null)
       return "";
     try
     {
       SimpleDateFormat dataFormat = new SimpleDateFormat(strFormat);
       resultDate = dataFormat.format(date);
     }
     catch (Exception localException)
     {
     }
     return resultDate;
   }
 
   public static String getDateHmStr(java.util.Date date)
   {
     return dateToStr(date, "yyyy-MM-dd HH:mm");
   }
 
   public static String getDateHmStr(Timestamp date) {
     return getDateHmStr(timestampToDate(date));
   }
 
   public static Timestamp dateToTimestamp(java.util.Date date) {
     Timestamp ts = new Timestamp(date.getTime());
     return ts;
   }
 
   public static java.util.Date timestampToDate(Timestamp date) {
     java.util.Date ts = new java.util.Date(date.getTime());
     return ts;
   }
 
   public static int daysOfTwoDate(java.util.Date beginDate, java.util.Date endDate)
   {
     int days = 1;
     DateFormat df = new SimpleDateFormat("yyyyMMdd");
 
     Calendar beginCalendar = Calendar.getInstance();
     Calendar endCalendar = Calendar.getInstance();
 
     beginCalendar.setTime(beginDate);
     endCalendar.setTime(endDate);
 
     while (beginCalendar.before(endCalendar)) {
       days++;
       beginCalendar.add(5, 1);
     }
     return days;
   }
 
   public static String formatLongToTimeStr(Long l) {
     String str = "";
     int hour = 0;
     int minute = 0;
     int second = 0;
     second = l.intValue() / 1000;
     if (second > 60) {
       minute = second / 60;
       second %= 60;
     }
 
     if (minute > 60) {
       hour = minute / 60;
       minute %= 60;
     }
     String strtime = hour + "小时" + minute + "分钟" + second + "秒";
     return strtime;
   }
 
   public static String formatDuring(long mss) {
     long days = mss / 86400000L;
     long hours = mss % 86400000L / 3600000L;
     long minutes = mss % 3600000L / 60000L;
     long seconds = mss % 60000L / 1000L;
     return days + "日" + hours + "小时" + minutes + "分" + 
       seconds + "秒";
   }
 
   public static int getWeekOfYear()
   {
     TimeZone zone = TimeZone.getTimeZone("Asia/shenzhen");
     Calendar cal = Calendar.getInstance(zone);
     int c = cal.get(3);
     return c;
   }
 
   public static int getWeekOfYear(String strTime, String strFormat)
   {
     int cc = 0;
     try {
       SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat(strFormat);
       java.util.Date date = sdfyyyyMMdd.parse(strTime);
       Calendar c1 = Calendar.getInstance();
       c1.setTimeInMillis(date.getTime());
       System.out.println(date.getTime() == c1.getTimeInMillis());
       System.out.println(c1.get(3));
       cc = c1.get(3);
     } catch (Exception e) {
       e.printStackTrace();
     }
     return cc;
   }
 }
