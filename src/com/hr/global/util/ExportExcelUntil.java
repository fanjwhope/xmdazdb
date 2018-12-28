package com.hr.global.util;

import java.io.File;

import java.io.PrintStream;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;

import com.hr.util.DownFile;
import com.hr.util.IncString;
import com.hr.util.Md5;
import com.hr.util.d;

import jxl.Workbook;

import jxl.format.BorderLineStyle;

import jxl.format.UnderlineStyle;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcelUntil {

	public String exportExcelM(List<List<String>> list, String excelName,
			String[] fields) {
		BaseDataOP op = new BaseDataOP(ConnectionPool.getPool());
		String urlE = d.getGpkSavePath(op);
		String date = "\\" + Md5.date("yyyyMMdd") + "\\";
		String url = urlE + date + excelName + ".xls";
		File f = new File(url);
		String dir = urlE + date;
		File dirTest = new File(dir);
		if (!dirTest.exists()) {
			dirTest.mkdirs();
		}
		try {
			WritableWorkbook wb = Workbook.createWorkbook(f);// 打开文件
			WritableFont wfc = new WritableFont(WritableFont.createFont("宋体"),
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat normalFormat = new WritableCellFormat(wfc);
			normalFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
			int leng = fields[0].split(",").length;

			WritableSheet sheet = wb.createSheet(excelName, 0);// 生成名为excelName的工作表，参数0表示这是第一页
			for (int i = 0; i < list.size(); i++) {
				String[] cell = listToString(list.get(i), fields);
				for (int j = 0; j < cell.length; j++) {

					if (i == 0 && leng <= 2) {
						Label label = new Label(j, i, fields[j].split(",")[0],
								normalFormat);// 将定义好的单元格添加到工作表中
						sheet.addCell(label);
						Label label1 = new Label(j, i + 1, cell[j],
								normalFormat);// 将定义好的单元格添加到工作表中
						sheet.addCell(label1);
					} else if (i == 0) {
						String[][] str = String1To2(fields);
						for (int k = 0; k < str.length; k++) {
							sheet.addCell(new Label(
									Integer.parseInt(str[k][2]), Integer
											.parseInt(str[k][1]), str[k][0],
									normalFormat));
						}
						// 合并单元格
						sheet.mergeCells(0, 0, 0, 1);
						sheet.mergeCells(1, 0, 1, 1);
						sheet.mergeCells(2, 0, 2, 1);

						sheet.mergeCells(3, 0, 6, 0);
						sheet.mergeCells(7, 0, 10, 0);

						sheet.mergeCells(11, 0, 11, 1);
						sheet.mergeCells(12, 0, 12, 1);
						sheet.mergeCells(13, 0, 13, 1);
						sheet.mergeCells(14, 0, 14, 1);

						Label label1 = new Label(j, i + 2, cell[j],
								normalFormat);// 将定义好的单元格添加到工作表中
						sheet.addCell(label1);
					} else {
						if (leng > 2) {
							Label label = new Label(j, i + 2, cell[j],
									normalFormat);// 将定义好的单元格添加到工作表中
							sheet.addCell(label);
						} else {
							Label label = new Label(j, i + 1, cell[j],
									normalFormat);// 将定义好的单元格添加到工作表中
							sheet.addCell(label);
						}

						// 生成一个保存数字的单元格
						// 必须使用Number的完整包路径，否则有语法歧义
						// jxl.write.Number number = new
						// jxl.write.Number(j,i,Double.parseDouble(cell[j]));
						// sheet.addCell(number);
					}
				}
			}

			/*
			 * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义
			 */
			// 写入数据并关闭文件
			wb.write();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return url;
	}

	// 根据路径下载文件到客户左面
	public void downLoad(HttpServletRequest request,
			HttpServletResponse response, List<List<String>> list,
			String excelName, String[] fields) {
		try {
			String file = exportExcelM(list, excelName, fields);
			String name2 = excelName + ".xls";
			if (file != null) {
				DownFile.downHttpFile(file, response, name2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// list转化为数组
	public String[] listToString(List<String> list, String[] field) {

		String s4 = "1,2,3,17,7,14,18,19,8,15,20,16,4,21,22";
		int length = field[0].split(",").length;
		if (length == 4) {
			String[] s = new String[15];
			for (int i = 0; i < 15; i++) {
				String[] s1 = s4.split(",");
				s[i] = list.get(Integer.parseInt(s1[i]));
			}
			return s;
		} else if (length == 2) {
			String[] s = new String[field.length];
			for (int i = 0; i < s.length; i++) {
				String[] s1 = field[i].split(",");
				s[i] = list.get(Integer.parseInt(s1[length - 1]));
			}
			return s;
		} else {
			String[] s = new String[field.length];
			for (int i = 0; i < list.size(); i++) {
				s[i] = list.get(i);
			}
			return s;
		}
	}

	// 根据传入的String数组分割字符串组成2维数组
	public String[][] String1To2(String[] fields) {
		String[][] str = new String[fields.length][4];
		for (int i = 0; i < fields.length; i++) {
			String[] s = fields[i].split(",");
			for (int j = 0; j < s.length; j++) {
				str[i][j] = s[j];
			}
		}
		return str;
	}
	// 根据传入的2维数组排序，

}
