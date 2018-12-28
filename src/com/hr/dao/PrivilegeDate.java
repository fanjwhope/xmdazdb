package com.hr.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hr.util.IncString;
import com.hr.util.Md5;

public class PrivilegeDate {

	/**
	 * @param args
	 * @throws ParseException
	 */
	/******* 计算当前日期与公元元年元旦之间的天数 ********/
	public long getDays(String nowDate) throws ParseException {
		String date1 = nowDate;
		String date2 = "0000-00-00";
		java.text.SimpleDateFormat df;
		if (nowDate.contains("-")) {
			 df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
		}else{
			 df = new java.text.SimpleDateFormat(
						"yyyyMMdd");
		}
		java.util.Date cDate = df.parse(date1);
		java.util.Date dDate = df.parse(date2);
		int leapDays = getLeapYearDays();
		long betweenDate = (cDate.getTime() - dDate.getTime())
				/ (24 * 60 * 60 * 1000) - leapDays;
		return betweenDate;
	}

	/******* 得到闰年天数 **********/
	public int getLeapYearDays() {
		int yearsNum = 0;
		int nowYear = IncString.formatInt(Md5.date("yyyy"));
		yearsNum = nowYear / 4;
		return yearsNum;
	}

	/******
	 * 计算两个日期之间的天数
	 * 
	 * @throws ParseException
	 ******/
	public long getDays(String beginDate, String endDate) throws ParseException {
		String date1 = endDate;
		String date2 = beginDate;
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date cDate = df.parse(date1);
		java.util.Date dDate = df.parse(date2);
		int leapDays = getLeapYearDays();
		long betweenDate = (cDate.getTime() - dDate.getTime())
				/ (24 * 60 * 60 * 1000);
		return betweenDate;
	}

	/******* 换算几个月或几年后的日期 *******/
	public String getFeaDate(int year, int month, int day, int months) {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.set(year, month, day);// 月份是从0开始的，所以11表示12月
		Date now = ca.getTime();
		ca.add(Calendar.MONTH, months); // 月份减1
		Date lastMonth = ca.getTime(); // 结果
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(lastMonth);
	}

	public static void main(String[] args) throws ParseException {
		// String date1 = "2014-11-24";
		// String date2 = "0000-00-00";
		// java.text.SimpleDateFormat df = new
		// java.text.SimpleDateFormat("yyyy-MM-dd");
		// java.util.Date cDate = df.parse(date1);
		// java.util.Date dDate = df.parse(date2);
		// long betweenDate = (cDate.getTime()-dDate.getTime())/(24*60*60*1000);
		// System.out.println(betweenDate);
		PrivilegeDate pd = new PrivilegeDate();
		System.out.println(pd.getFeaDate(2014, 11, 24, -1));
	}

}
