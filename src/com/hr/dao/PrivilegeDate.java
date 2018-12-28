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
	/******* ���㵱ǰ�����빫ԪԪ��Ԫ��֮������� ********/
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

	/******* �õ��������� **********/
	public int getLeapYearDays() {
		int yearsNum = 0;
		int nowYear = IncString.formatInt(Md5.date("yyyy"));
		yearsNum = nowYear / 4;
		return yearsNum;
	}

	/******
	 * ������������֮�������
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

	/******* ���㼸���»��������� *******/
	public String getFeaDate(int year, int month, int day, int months) {
		Calendar ca = Calendar.getInstance();// �õ�һ��Calendar��ʵ��
		ca.set(year, month, day);// �·��Ǵ�0��ʼ�ģ�����11��ʾ12��
		Date now = ca.getTime();
		ca.add(Calendar.MONTH, months); // �·ݼ�1
		Date lastMonth = ca.getTime(); // ���
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
