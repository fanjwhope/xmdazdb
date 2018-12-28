package com.hr.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.hr.bean.XmdaFile;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.d;

@SuppressWarnings("unchecked")
public class FilePathInfo {
	private static Integer pagenum;
	private static Map<String, String> fileSaveRules;
	private static String dbName;
	static {
		fileSaveRules = (Map<String, String>) readObjectFromFile("zdb");
		if (fileSaveRules == null) {
			fileSaveRules = new HashMap<String, String>();
		}
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public XmdaFile getRealPath(String lsh, String xmlx, String dwh,
			String xmbh, String dbName, String page,String zdbdbName) {		
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String gpk = op.queryRowAndCol("select path from zdb_wsda_gpk where flag='y'")[0][0];
		XmdaFile xmdaFile = new XmdaFile();
		this.dbName = dbName;
		String realPath;
		String pdfPath;
		String mDWH;
		if (dwh.length() < 6) {
			dwh = "Y100000000";
			realPath = dbName + d.fgh() + "xmda" + d.fgh() + dwh + d.fgh();
			pdfPath = dbName + d.fgh() + "xmda" + d.fgh();
			mDWH = "Y100000000";
		} else {
			realPath = dbName + d.fgh() + "xmda" + d.fgh() + dwh + d.fgh();
			pdfPath = dbName + d.fgh() + "xmda" + d.fgh();
			mDWH = dwh.substring(0, 6) + "0000";
		}
		String[] parentPaths = new String[] {
				gpk + File.separator + pdfPath,
				gpk + File.separator + realPath,
				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
				+ dwh + d.fgh()+ xmlx + "_" + xmbh + File.separator,
				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
						+ mDWH + d.fgh(),
				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
						+ mDWH + d.fgh() + xmlx + "_" + lsh + File.separator,

				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
						+ mDWH + d.fgh() + xmlx + "ID_" + lsh + File.separator,

				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
						+ mDWH + d.fgh() + "xmlrb_" + lsh + File.separator,

				gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
						+ mDWH + d.fgh() + "spgl_" + lsh + File.separator };
		if(zdbdbName.equals("zdb")){
			if(xmlx.equals("xmlrb")){
				xmlx="spgl";
			}
			parentPaths = new String[] {gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
					+ "Y200040043" + d.fgh()+ xmlx + "_" + xmbh + File.separator,
					gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
					+ "Y200040043" + d.fgh()+  "spgl_" + xmbh + File.separator,
					gpk + File.separator + dbName + d.fgh() + "xmda" + d.fgh()
					+ "Y200040043" + d.fgh()};
		}
		List<String> parentPath = new ArrayList<String>();		
		if ("Y100000000".equals(dwh)) {
			parentPath.add(gpk + File.separator + pdfPath);
			File dir = new File(gpk + File.separator + pdfPath);
			File[] tempList = dir.listFiles();
			if (tempList != null) {
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isDirectory()) {
						parentPath.add(tempList[i].getPath());
					}
				}
				for (int i = 0; i < parentPath.size(); i++) {
					File dir2 = new File(parentPath.get(i));
					File[] tempList2 = dir2.listFiles();
					if (tempList2 != null) {
						for (int m = 0; m < tempList2.length; m++) {
							if (tempList2[m].isDirectory()) {
								parentPath.add(tempList2[m].getPath());
							}
						}
					}
				}
			}
		} else {
			for (int i = 0; i < parentPaths.length; i++) {
				parentPath.add(parentPaths[i]);
			}
		}
		String[][] fileStr;
		if (xmlx.equals("2002") || xmlx.equals("xmlrb") || xmlx.equals("spgl")) {
			if (fileSaveRules.get(dbName + "2002") != null) {
				xmlx = fileSaveRules.get(dbName + "2002");
			}
			fileStr = new String[][] {
					{ xmbh + "", xmlx + "_" + lsh, xmlx + "ID_" + lsh,
							xmlx + "_" + xmbh },
					{ xmbh + "", "xmlrb_" + lsh, "xmlrbID_" + lsh,
							"xmlrb_" + xmbh },
					{ xmbh + "", "spgl_" + lsh, "spglID_" + lsh, "spgl_" + xmbh } };
		} else {
			fileStr = new String[][] { { xmbh + "", xmlx + "_" + lsh,
					xmlx + "ID_" + lsh, xmlx + "_" + xmbh } };
		}
		if (fileSaveRules.get(dbName) != null) {
			List<String> savedRules = new ArrayList<String>();
			for (String str : fileSaveRules.get(dbName).split(",")) {
				savedRules.add(fileStr[0][Integer.valueOf(str)]);
			}
			// 读取没有分页的文件
			String tempPath = getFileInSavedRules(parentPath, savedRules);
			if (tempPath != null && !"".equals(tempPath)) {
				xmdaFile.setFileName(tempPath);
				List<String> list = new ArrayList<String>();
				list.add(tempPath);
				xmdaFile.setAllFileNames(list);
				return xmdaFile;
			}
		}

		// 读取没有分页的文件
		String tempPath = getFile(parentPath, fileStr,zdbdbName);
		if (tempPath != null && !"".equals(tempPath)) {
			xmdaFile.setFileName(tempPath);
			List<String> list = new ArrayList<String>();
			list.add(tempPath);
			xmdaFile.setAllFileNames(list);
			return xmdaFile;
		}

		// 尝试读取带@@的分页类型扫描件	
		String fileName = xmlx + "_" + lsh;
		if (xmlx.equals("2002")) {
			fileName = "xmlrb_" + lsh;
		}
		if(zdbdbName.equals("zdb")){
			fileName=xmlx + "_" + xmbh;
		}
		List<File> pagingFile = getPagingFile(parentPath, fileName);
		SortByName s = new SortByName();
		Collections.sort(pagingFile, s);
		if (page == null || "".equals(page)) {
			page = "1";
		}
		Integer tempPage = Integer.valueOf(page);
		Integer currentPage = tempPage;
		// 处理分页的扫描件
		for (int i = 0; i < pagingFile.size(); i++) {
			tempPage = tempPage
					- Integer.valueOf(pagingFile.get(i).getName().split("@@")[1].split("-")[1]
							.split("\\.")[0]);
			if (tempPage <= 0) {
				xmdaFile.setFileName(pagingFile.get(i).getPath());
				xmdaFile.setTotalPage(pagenum);
				xmdaFile.setCurrentPage(currentPage);
				List<String> list = new ArrayList<String>();
				for (File f : pagingFile) {
					list.add(f.getPath());
				}
				xmdaFile.setAllFileNames(list);
				return xmdaFile;
			} else {
				currentPage = tempPage;
			}
		}

		return xmdaFile;
	}

	/*
	 * 通过存储的规则获取扫描件路径
	 */
	public static String getFileInSavedRules(List<String> parentPath,
			List<String> fileName) {
		for (int n = 0; n < parentPath.size(); n++) {
			File dir = new File(parentPath.get(n));
			File[] tempList = dir.listFiles();
			if (tempList != null) {
				for (int i = 0; i < tempList.length; i++) {
					for (int m = 0; m < fileName.size(); m++) {
						if (!tempList[i].isDirectory()
								&& !"".equals(fileName.get(m))) {
							String tempstr = tempList[i].getName().split("\\.")[0];
							String tempFileName = fileName.get(m);
							if (tempFileName.contains("（")
									|| tempFileName.contains("）")
									|| tempstr.contains("（")
									|| tempstr.contains("）")
									|| tempFileName.contains("﹙")
									|| tempFileName.contains("﹚")
									|| tempstr.contains("﹙")
									|| tempstr.contains("﹚")) {
								tempstr = tempstr.replaceAll("（", "(");
								tempstr = tempstr.replaceAll("）", ")");
								tempstr = tempstr.replaceAll("﹙", "(");
								tempstr = tempstr.replaceAll("﹚", ")");
								tempFileName = tempFileName.replaceAll("（",
										"(");
								tempFileName = tempFileName.replaceAll("）",
										")");
								tempFileName = tempFileName.replaceAll("﹙",
										"(");
								tempFileName = tempFileName.replaceAll("﹚",
										")");
							}

							if (tempFileName.toLowerCase().equals(tempstr.toLowerCase())) {
								return tempList[i].getPath();
							}
						}
					}

				}
			}
		}
		return null;
	}

	/*
	 * 获取扫描件路径
	 */
	public static String getFile(List<String> parentPath, String[][] fileName,
			String zdbdbName) {
		for (int n = 0; n < parentPath.size(); n++) {
			File dir = new File(parentPath.get(n));
			File[] tempList = dir.listFiles();
			if (tempList != null) {
				for (int i = 0; i < tempList.length; i++) {
					for (int m0 = 0; m0 < fileName.length; m0++) {
						for (int m = 0; m < fileName[m0].length; m++) {
							if (!tempList[i].isDirectory()
									&& !"".equals(fileName[m0][m])) {
								String tempstr = tempList[i].getName().split(
										"\\.")[0];
								String tempFileName = fileName[m0][m];
								if (tempFileName.contains("（")
										|| tempFileName.contains("）")
										|| tempstr.contains("（")
										|| tempstr.contains("）")
										|| tempFileName.contains("﹙")
										|| tempFileName.contains("﹚")
										|| tempstr.contains("﹙")
										|| tempstr.contains("﹚")) {
									tempstr = tempstr.replaceAll("（", "(");
									tempstr = tempstr.replaceAll("）", ")");
									tempstr = tempstr.replaceAll("﹙", "(");
									tempstr = tempstr.replaceAll("﹚", ")");
									tempFileName = tempFileName.replaceAll("（",
											"(");
									tempFileName = tempFileName.replaceAll("）",
											")");
									tempFileName = tempFileName.replaceAll("﹙",
											"(");
									tempFileName = tempFileName.replaceAll("﹚",
											")");
								}
								if (tempFileName.toLowerCase().equals(tempstr.toLowerCase())) {
									if (m0 == 1) {
										fileSaveRules.put(dbName + "2002",
												"xmlrb");
										writeObjectToFile(fileSaveRules,
												zdbdbName);
									}
									if (m0 == 2) {
										fileSaveRules.put(dbName + "2002",
												"spgl");
										writeObjectToFile(fileSaveRules,
												zdbdbName);
									}

									fileSaveRules
											.put(dbName,
													fileSaveRules.get(dbName) == null ? ""
															+ m
															: (fileSaveRules
																	.get(dbName)
																	+ "," + m));
									writeObjectToFile(fileSaveRules, zdbdbName);
									return tempList[i].getPath();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/*
	 * 判断是否存在分页的扫描件
	 */
	public static List<File> getPagingFile(List<String> parentPath,
			String fileName) {
		pagenum = 0;
		if (fileName.contains("（")
				|| fileName.contains("）")||fileName.contains("﹙")
				|| fileName.contains("﹚")) {
			fileName = fileName.replaceAll("（",
					"(");
			fileName = fileName.replaceAll("）",
					")");
			fileName = fileName.replaceAll("﹙",
					"(");
			fileName = fileName.replaceAll("﹚",
					")");
		}
		List<File> fileList = new ArrayList<File>();
		for (int n = 0; n < parentPath.size(); n++) {
			File dir = new File(parentPath.get(n));
			File[] tempList = dir.listFiles();
			if (tempList != null) {
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].getName().toLowerCase().contains(fileName.toLowerCase() + "@@")
							&& !tempList[i].isDirectory()) {
						pagenum += Integer.valueOf(tempList[i].getName().split(
								"@@")[1].split("-")[1].split("\\.")[0]);
						fileList.add(tempList[i]);
					}
				}
			}
			if (fileList.size() > 0) {
				return fileList;
			}
		}
		return fileList;
	}

	@Test
	public void test() throws IOException {

		String xmbh = "进出银青送审字（2007）第11号";
		String xmlx = "psgl";
		String lsh = "96";
		String page = "1";
		String dwh = "Y200020007";
		String dbName = "shandongBank";

	}

	class SortByName implements Comparator<File> {
		@Override
		public int compare(File f1, File f2) {
			String res1 = f1.getName().split("@@")[1].split("-")[0];
			String res2 = f2.getName().split("@@")[1].split("-")[0];
			return Integer.valueOf(res1) - Integer.valueOf(res2);
		}
	}

	public static void writeObjectToFile(Object obj, String zdbdbName) {
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String gpk = op
				.queryRowAndCol("select path from zdb_wsda_gpk where flag='y'")[0][0];
		File file = new File(gpk + File.separator + dbName + File.separator
				+ "fileSaveRules");
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(obj);
			objOut.flush();
			objOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object readObjectFromFile(String zdbdbName) {
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String gpk = op
				.queryRowAndCol("select path from zdb_wsda_gpk where flag='y'")[0][0];
		Object temp = null;
		File file = new File(gpk + File.separator + dbName + File.separator
				+ "fileSaveRules");
		if (file.exists() && file.length() > 0) {
			FileInputStream in;
			try {
				in = new FileInputStream(file);
				ObjectInputStream objIn = new ObjectInputStream(in);
				temp = objIn.readObject();
				objIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
}
