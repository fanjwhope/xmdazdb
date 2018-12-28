package com.hr.tool.project.dao;

import com.hr.util.*;
import javax.servlet.http.*;
import com.hr.table.*;
import com.hr.dataAdmin.DataItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataAdmin extends BaseDataOP {
	Object curPool = null;
	MyHash fieldCnnameHash = null;
	MyXml xml = null;
	String tabName = null;
	String dbName = null;
	String[][] standFieldArr = null;
	com.hr.data.DataBean dbBean = null;
	MyList sepcailField = new MyList();
	MyList KeyIndex = new MyList();
	String curSubtype = "list";
	String appName = "";
	HttpServletRequest req = null;
	boolean isComeFromFile = true;
	int tabYear = -1; // 用于使用年度表
	String listField = " * ";
	String orderBy = " ";
	String countFied = " id ";
	String key = "";

	public void setTabName(String _tabName) {
		tabName = _tabName;
	}

	public String getTabName() {
		return tabName;
	}

	public String getDbName() {
		return dbName;
	}

	public DataAdmin(int configID, Object pool) throws Exception {
		super(pool);
		curPool = pool;
		isComeFromFile = false;
		String xmlString = buildXml(configID);
		setFieldArr(xmlString);
	}

	public DataAdmin(MyHash vals, String[][] fieldArr, Object pool)
			throws Exception {
		super(pool);
		curPool = pool;
		isComeFromFile = false;
		String xmlString = addToXml(vals, fieldArr);
		setFieldArr(xmlString);
	}

	public DataAdmin(Object pool, String fieldXml) throws Exception {
		super(pool);
		curPool = pool;
		isComeFromFile = false;
		setFieldArr(fieldXml);
	}

	private String buildXml(int configID) throws Exception {
		DataItem item = new DataItem(pool);
		String[][] fieldArr = null;
		MyHash vals = new MyHash();
		String sql = "select cname,tableName,db,haveFj,list_order,list_field,list_rows,file_listFile,file_add,addType from "
				+ tab.get(item.getParentTab(), item.getDbName())
				+ " where id="
				+ configID;
		String[] arr = this.queryLine(sql);
		if (arr == null || arr.length == 0)
			throw new Exception("著录配置不存在!! ");

		vals.set("appName", arr[0]);
		vals.set("tabName", arr[1]);
		vals.set("dbName", arr[2]);
		vals.set("haveFj", arr[3]);

		vals.set("order", arr[4]);
		vals.set("field", arr[5]);
		vals.set("rows", arr[6]);
		vals.set("listFile", arr[7]);
		vals.set("add", arr[8]);
		vals.set("addType", arr[9]);
		return addToXml(vals, fieldArr);
	}

	/****************************
	 * 必须是标准的字段格式
	 *****************************/
	public String addToXml(MyHash vals, String[][] fieldArr) throws Exception {
		StringBuffer s_buff = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"gb2312\"?><root>");
		s_buff.append("<app>" + vals.get("appName") + "</app>");
		s_buff.append("<field tab=\"" + vals.get("tabName") + "\" db=\""
				+ vals.get("dbName") + "\" haveFj=\"" + vals.get("haveFj")
				+ "\">");

		// 查数据库字段
		s_buff.append(convertArrToXmlLabel(fieldArr));
		s_buff.append("</field>");

		s_buff.append("<list>");
		s_buff.append("<countfield value=\"id\"/>");
		s_buff.append("<order value=\"" + vals.get("order") + "\"/>");
		s_buff.append("<field value=\"" + vals.get("field") + "\"/>");
		s_buff.append("<rows value=\"" + vals.get("rows") + "\"/>");
		s_buff.append("</list>");

		s_buff.append("<file>");
		s_buff.append("<listFile>" + vals.get("listFile") + "</listFile>");
		s_buff.append("<add>" + vals.get("add") + "</add>");
		s_buff.append("</file>");
		s_buff.append("</root>");
		return s_buff.toString();
	}

	private StringBuffer convertArrToXmlLabel(String[][] fields)
			throws Exception {
		if (fields == null)
			throw new Exception("字段数组为空!! ");
		if (fields[0].length < 6)
			throw new Exception("数组格式错误!! ");
		StringBuffer s_buff = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i][0];
			String fieldType = fields[i][1];
			if (fieldType.equals(Constants.fieldTypeNumber))
				fieldType = "number";
			String fieldLen = fields[i][2];
			String isCanNull = fields[i][3];
			String isIndex = fields[i][4];
			String cname = "";
			if (fields[i].length >= 6)
				cname = fields[i][5];
			int i_isCanNull = IncString.formatNull(isCanNull, 1);
			s_buff.append("<" + fieldName + " type=\"" + fieldType
					+ "\" len=\"" + fieldLen + "\" null=\"" + i_isCanNull
					+ "\" ");
			if (isIndex != null && isIndex.equals("1"))
				s_buff.append(" key=\"1\"");
			if (fields[i].length >= 7) {
				String from = fields[i][6];
				s_buff.append(" from=\"" + from + "\"");
			}
			s_buff.append(" cname=\"" + cname + "\" />");
		}
		return s_buff;
	}

	public DataAdmin(String xmlInfo, Object pool) throws Exception {
		super(pool);
		curPool = pool;
		setFieldArr(xmlInfo);
	}

	public DataAdmin(String xmlInfo, Object pool, boolean fromTabXml)
			throws Exception {
		super(pool);
		curPool = pool;
		setFieldArr(xmlInfo, fromTabXml);
	}

	public DataAdmin(String xmlName, Object pool, int Year) throws Exception {
		super(pool);
		curPool = pool;
		if (Year > 0)
			tabYear = Year; // 设置年度表
		setFieldArr(xmlName);
	}

	public MyXml getMyXml() {
		return xml;
	}

	public String getOption(String signField, String curValue) {
		StringBuffer buff = new StringBuffer("");
		String[][] arr = xml.ChildNodeAndValueArr("root/selectvalue/"
				+ signField);
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				String sel = "";
				String tempNode = arr[i][0];
				String val = tempNode;
				String tempValue = arr[i][1];
				if (tempValue != null && !tempValue.trim().equals("")) {
					val = tempValue;
				}
				if (val.equals(curValue))
					sel = " selected ";
				buff.append("<option value='" + val + "' " + sel + ">"
						+ tempNode + "</option>");
			}
		}
		return buff.toString();
	}

	public String getSelectValue(String signField, String curValue) {
		String returnValue = "";
		String[][] arr = xml.ChildNodeAndValueArr("root/selectvalue/"
				+ signField);
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				String sel = "";
				String tempNode = arr[i][0];
				String val = tempNode;
				String tempValue = arr[i][1];
				if (tempValue != null && !tempValue.trim().equals("")) {
					val = tempValue;
				}
				if (val.equals(curValue)) {
					returnValue = tempNode;
				}
			}
		}
		return returnValue;
	}

	public String[][] getstandFieldArr() {
		return standFieldArr;
	}

	public String[] getListConditions() {
		String[] listConditions = { listField, orderBy, countFied };
		return listConditions;
	}

	public String getKey() {
		return key;
	}

	public String getAppName() {
		return appName;
	}

	public MyHash getOneData(int id) {
		return getOneData(String.valueOf(id));
	}

	public MyHash getOneData(String id) {
		String field = xml.getNodeInfo("root/modify/field", "value");
		if (field == null || field.equals(""))
			field = "*";
		String sql = "select " + field + " from " + tab.get(tabName, dbName)
				+ " where id='" + id + "'";
		RowBean rb = new RowBean(sql, this);
		return rb.Vals;
	}

	private void setFieldArr(String xmlName) throws Exception {
		if (isComeFromFile) {
			if (xmlName.indexOf(d.fgh()) == -1) {
				String rootpath = new java.io.File("").getAbsolutePath();
				xmlName = rootpath + java.io.File.separator + "xml"
						+ java.io.File.separator + xmlName;
			}
			java.io.File fn = new java.io.File(xmlName);
			if (!fn.exists())
				throw new Exception(xmlName + "xml文件不存在！ ");
			xml = new MyXml(fn);
		} else {
			xml = new MyXml(xmlName, "gb2312");
		}
		appName = xml.getNodeInfo("root/app", null);
		String[][] fieldArr = xml.getNodeArr("root/field", "cname", "attri");
		if (fieldArr == null)
			throw new Exception("xml文件没有配置field！ ");
		standFieldArr = new String[fieldArr.length][5]; // {"Country","varchar","20","1","0"}

		tabName = xml.getNodeInfo("root/field", "tab");
		if (tabYear > 0)
			tabName = tabName + tabYear;
		dbName = xml.getNodeInfo("root/field", "db");
		if (dbName == null || dbName.equals(""))
			dbName = ConfigInfo.databaseName;
		boolean isnd = IncString.formatNull(
				xml.getNodeInfo("root/field", "isnd"), 0) == 1 ? true : false;

		for (int i = 0; i < fieldArr.length; i++) {
			String fieldSign = fieldArr[i][0];
			String FieldCname = xml.getNodeInfo("root/field/" + fieldSign,
					"cname");

			standFieldArr[i][0] = fieldSign;
			String fieldType = xml.getNodeInfo("root/field/" + fieldSign,
					"type");
			String fieldLen = xml.getNodeInfo("root/field/" + fieldSign, "len");
			int isKey = IncString.formatNull(
					xml.getNodeInfo("root/field/" + fieldSign, "key"), 0);
			String fieldFrom = xml.getNodeInfo("root/field/" + fieldSign,
					"from");

			if (isKey == 1) {
				KeyIndex.set(fieldSign, fieldFrom);
			} else {
				if (fieldFrom != null && !fieldFrom.equals("")) {
					sepcailField.set(fieldSign, fieldFrom);
				}
			}

			fieldType = checkStandardFieldType(fieldType,
					IncString.formatNull(fieldLen, 10), FieldCname);
			standFieldArr[i][1] = fieldType;
			standFieldArr[i][2] = fieldLen;
			int isNull = IncString.formatNull(
					xml.getNodeInfo("root/field/" + fieldSign, "null"), 1);
			standFieldArr[i][3] = isNull + "";
			standFieldArr[i][4] = isKey + "";
		}
		createTab(tabName, dbName);
	}

	/**
	 * 平台xml初始化
	 * 
	 * @param tableName
	 * @param fromTabXml
	 * @throws Exception
	 */
	private void setFieldArr(String tableName, boolean fromTabXml)
			throws Exception {
		if (fromTabXml) {
			
//			String path = this.getClass().getResource("").getPath();
//			String pakegePath = path.substring(1, path.indexOf("class"));
//			String xmlName = pakegePath.replaceAll("/", "\\\\")
//					+ "classes\\tabxml" + "\\" + tableName + ".xml";
			File baseFile=new File(DataAdmin.class.getClassLoader().getResource("").toURI());
			File fn = new File(baseFile,File.separator+"tabxml" + File.separator + tableName + ".xml");

			// java.io.File fn = new java.io.File(xmlName);
			if (!fn.exists())
				throw new Exception("tabxml目录下"+tableName+".xml文件不存在！ ");
			xml = new MyXml(fn);
		} else {
			xml = new MyXml(tableName, "gb2312");
		}
		appName = xml.getNodeInfo("root/app", null);
		String[][] fieldArr = xml.getNodeArr("root/field", "cname", "attri");
		if (fieldArr == null)
			throw new Exception("xml文件没有配置field！ ");
		standFieldArr = new String[fieldArr.length][5]; // {"Country","varchar","20","1","0"}

		tabName = xml.getNodeInfo("root/field", "tab");
		// 读xml时解析列表字段
		listField = xml.getNodeInfo("root/list/field", "value");
		countFied = xml.getNodeInfo("root/list/countfield", "value");
		orderBy = xml.getNodeInfo("root/list/order", "value");

		if (tabYear > 0)
			tabName = tabName + tabYear;
		dbName = xml.getNodeInfo("root/field", "db");
		if (dbName == null || dbName.equals(""))
			dbName = ConfigInfo.databaseName;
		boolean isnd = IncString.formatNull(
				xml.getNodeInfo("root/field", "isnd"), 0) == 1 ? true : false;

		for (int i = 0; i < fieldArr.length; i++) {
			String fieldSign = fieldArr[i][0];
			String FieldCname = xml.getNodeInfo("root/field/" + fieldSign,
					"cname");

			standFieldArr[i][0] = fieldSign;
			String fieldType = xml.getNodeInfo("root/field/" + fieldSign,
					"type");
			String fieldLen = xml.getNodeInfo("root/field/" + fieldSign, "len");
			int isKey = IncString.formatNull(
					xml.getNodeInfo("root/field/" + fieldSign, "key"), 0);
			String fieldFrom = xml.getNodeInfo("root/field/" + fieldSign,
					"from");

			if (isKey == 1) {
				KeyIndex.set(fieldSign, fieldFrom);
				key = fieldSign;
			} else {
				if (fieldFrom != null && !fieldFrom.equals("")) {
					sepcailField.set(fieldSign, fieldFrom);
				}
			}

			fieldType = checkStandardFieldType(fieldType,
					IncString.formatNull(fieldLen, 10), FieldCname);
			standFieldArr[i][1] = fieldType;
			standFieldArr[i][2] = fieldLen;
			int isNull = IncString.formatNull(
					xml.getNodeInfo("root/field/" + fieldSign, "null"), 1);
			standFieldArr[i][3] = isNull + "";
			standFieldArr[i][4] = isKey + "";
		}
		createTab(tabName, dbName);
	}

	// 通过xml得到标准的表字段数组
	private void createTab(String tabName, String dbName) {
		if (!ifExistsTab(tabName, dbName)) {
			String sql = Constants.BuildCreateTabSql(tab.get(tabName, dbName),
					standFieldArr);
			this.ExecSql(sql);
		}
	}

	public static String[][] convertFieldToStandField(String[][] fieldArr)
			throws Exception {
		if (fieldArr == null)
			return null;
		for (int i = 0; i < fieldArr.length; i++) {
			String ename = fieldArr[i][0];
			String type = fieldArr[i][1];
			Log.debug(fieldArr[i][2]);
			type = checkStandardFieldType(type, fieldArr[i][2], ename);
		}
		return fieldArr;
	}

	private static String checkStandardFieldType(String type, int len,
			String FieldCname) throws Exception {
		return checkStandardFieldType(type, len + "", FieldCname);
	}

	public static String checkStandardFieldType(String type, String len,
			String FieldCname) throws Exception {
		String returnType = "";
		if (type == null)
			throw new Exception(FieldCname + "field设置错误！");
		if (type.equalsIgnoreCase("varchar")) {
			int i_len = IncString.formatInt(len.trim());
			if (ConfigInfo.currentDBType == 2 && i_len > 4000)
				throw new Exception("在SQL SERVER中varchar字段太长会有问题！！ ");
			else if (i_len > 2000)
				throw new Exception("varchar字段太长会有问题，请修改短些！！ ");
			return "varchar";
		} else if (type.equalsIgnoreCase("int")) {
			returnType = Constants.fieldTypeInt;
		} else if (type.equalsIgnoreCase("number")) {
			if (len.indexOf(",") != -1) {
				String[] arr = Md5.splitOnly(len, ",");
				for (int i = 0; i < arr.length; i++) {
					int i_temp = IncString.formatNull(arr[i].trim(), -2);
					if (i_temp < 0 || i_temp > 18)
						throw new Exception("字段长度定义错误");
				}
			} else {
				int i_len = IncString.formatInt(len);
				if (i_len > 18 || i_len < 0)
					throw new Exception("字段长度定义错误(" + FieldCname + "  " + i_len
							+ ")");
			}

			returnType = Constants.fieldTypeNumber;
		} else if (type.equalsIgnoreCase("text"))
			returnType = Constants.fieldTypetext;
		else
			throw new Exception(FieldCname + "field设置错误！");
		return returnType;
	}

	private MyHash isHaveThisCtrl = null;

	public static MyList getParm(javax.servlet.http.HttpServletRequest request) {
		MyList li = new MyList();
		java.util.Enumeration en = request.getParameterNames();
		String parmStr = "";
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String val = request.getParameter(key);
			li.set(key, val);
		}
		return li;
	}

	private void setValues(HttpServletRequest request) {
		MyList li = getParm(request);
		setValues(li);
	}

	private String getFactValue(String field, MyList li) {
		if (isHaveThisCtrl == null) {
			String[][] arr = li.getArr();
			if (arr != null) {
				isHaveThisCtrl = new MyHash();
				for (int j = 0; j < arr.length; j++) {
					String tempField = arr[j][0];
					if (tempField != null && tempField.trim().length() > 0)
						isHaveThisCtrl.set(tempField, "1");
				}
			}
		}
		String val = li.get(field, "");
		if (val.equals("")) {// 看是否有控件
			if (!isHaveThisCtrl.get(field).equals("1"))
				val = null;
		}
		return val;
	}

	private void setValues(MyList li) {
		for (int j = 0; j < standFieldArr.length; j++) {
			String fieldName = standFieldArr[j][0];
			String F_field = "F_" + fieldName;
			String H_field = "H_" + fieldName;
			String input = getFactValue(F_field, li);
			if (input == null)
				input = getFactValue(H_field, li);
			if (input != null) {
				dbBean.setVal(fieldName, input);
			}
		}
		String[][] specailArr = sepcailField.getArr();
		if (specailArr != null) {
			for (int i = 0; i < specailArr.length; i++) {
				String field = specailArr[i][0];
				String val = specailArr[i][1];
				if (curSubtype.equals("insert") && val.startsWith("insert_")) {
					val = getSpecialValue(field, val);
					dbBean.setVal(field, val);
				}
				if (curSubtype.equals("update") && val.startsWith("update_")) {
					val = getSpecialValue(field, val);
					dbBean.setVal(field, val);
				}
			}
		}
		String[][] KeyArr = KeyIndex.getArr();
		if (KeyArr != null) {
			for (int i = 0; i < KeyArr.length; i++) {
				String field = KeyArr[i][0];
				String val = KeyArr[i][1];
				if (curSubtype.equals("insert") && val.startsWith("insert_")) {
					val = getSpecialValue(field, val);
					dbBean.setVal(field, val);
				}
				if (curSubtype.equals("update") && val.startsWith("update_")) {
					val = getSpecialValue(field, val);
					dbBean.setVal(field, val);
				}
			}
		}

	}

	private String getSpecialValue(String fieldSign, String fieldFrom) {
		String returnValue = "";
		if (fieldFrom != null) {
			if (fieldFrom.endsWith("_max")) {
				TableIndex index = new TableIndex(curPool);
				int max = index.getOkMaxID(tabName, dbName, fieldSign);
				returnValue = max + "";
				if (req != null) {
					req.setAttribute(fieldSign, String.valueOf(returnValue));
				}
			} else if (fieldFrom.endsWith("_date")) {
				returnValue = Md5.date("yyyy-MM-dd HH:mm:ss");
			} else if (fieldFrom.toLowerCase().endsWith(
					"_uniqueID".toLowerCase())) {
				returnValue = Md5.date("yyyyMMddHHmmss") + Md5.md5(8);
			} else if (fieldFrom.endsWith("_sessionid")) {
				if (req != null) {
					returnValue = req.getSession().getId();
				}
			}
		}
		return returnValue;
	}

	public void update(HttpServletRequest request) throws Exception {
		curSubtype = "update";
		req = request;
		dbBean = com.hr.data.DataBean.getUpdateDataBean(standFieldArr);
		setValues(request);
		DAO dao = new DAO(tab.get(tabName, dbName), curPool);
		dao.update(dbBean);
	}

	public void update(MyList li) throws Exception {
		curSubtype = "update";
		dbBean = com.hr.data.DataBean.getUpdateDataBean(standFieldArr);
		setValues(li);
		DAO dao = new DAO(tab.get(tabName, dbName), curPool);
		dao.update(dbBean);
	}

	public boolean isHaveFj() {
		boolean haveFj = xml.getNodeInfo("root/field", "haveFj").equals("true") ? true
				: false;
		return haveFj;
	}

	public void delete(int id) {
		delete(String.valueOf(id));
	}

	public void delete(String id) {
		String sql = "delete from " + tab.get(tabName, dbName) + " where id='"
				+ id + "'";
		ExecSql(sql);

		if (isHaveFj()) {
			String fjTab = getFjtab();

			sql = "select savename from " + tab.get(fjTab, dbName)
					+ " where parentid='" + id + "'";
			String[] arr = this.queryColumn(sql);
			String savepath = d.getGpkSavePath(this);
			savepath = d.addPath(savepath, fjTab);
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					String saveName = arr[i];
					saveName = d.addPath(savepath, saveName);
					try {
						java.io.File fn = new java.io.File(saveName);
						if (fn.exists())
							fn.delete();
					} catch (Exception ex) {

					}
				}
			}
			String delSQL = "delete from " + tab.get(fjTab, dbName)
					+ " where parentid='" + id + "'";
			ExecSql(delSQL);
		}
	}

	private String getFjtab() {
		String fjTab = xml.getNodeInfo("root/field", "fjTab");
		if (fjTab == null || fjTab.equals("")) {
			fjTab = tabName + "Fj";
		}
		return fjTab;
	}

	public static void writeData(String type, String tabName, String dbName,
			String[][] standFieldArr, MyList fieldValue, Object curPool)
			throws Exception {
		com.hr.data.DataBean dbBean = com.hr.data.DataBean
				.getEmptyDataBean(standFieldArr);

		DAO dao = new DAO(tab.get(tabName, dbName), curPool);
		dao.insert(dbBean);
	}

	private String _insertID = null;

	public int getInsertID() {
		return IncString.formatInt(_insertID);
	}

	public String getInsertIDStr() {
		return _insertID;
	}

	public void insert(MyList li) throws Exception {
		curSubtype = "insert";
		dbBean = com.hr.data.DataBean.getEmptyDataBean(standFieldArr);
		setValues(li);
		String getFjtab = getFjtab();
		DAO dao = new DAO(tab.get(tabName, dbName), curPool);
		dao.insert(dbBean);

		_insertID = dbBean.getVal("id");
		if (isHaveFj()) {
			String fjTab = xml.getNodeInfo("root/field", "fjTab");
			if (fjTab == null || fjTab.equals("")) {
				fjTab = tabName + "Fj";
			}
			if (req != null) {
				String sql = "update " + tab.get(fjTab, dbName)
						+ " set parentid=" + dbBean.getVal("id")
						+ " where sessionid='" + req.getSession().getId()
						+ "' and parentid<=0";
				ExecSql(sql);
			}
		}
	}

	public void insert(HttpServletRequest request) throws Exception {
		curSubtype = "insert";
		req = request;
		MyList li = getParm(request);
		insert(li);
	}

	public static void encodeFileToTxtFile(String docFile, String txtFile)
			throws Exception {
		FileOutputStream out = new FileOutputStream(txtFile);
		java.io.File fn = new java.io.File(docFile);
		FileInputStream in = new FileInputStream(fn);
		if (fn.exists()) {
			com.sun.mail.util.BASE64EncoderStream base64 = new com.sun.mail.util.BASE64EncoderStream(
					out);
			byte buf[] = new byte[1024];
			while (in.available() != -1) {
				int n = in.read(buf);
				if (n < 0)
					break;
				base64.write(buf, 0, n);
				base64.flush();
			}
		}
	}

	public static void decodeTxtFileToDoc(String txtFile, String docFile)
			throws Exception {
		java.io.File fn = new java.io.File(txtFile);
		FileOutputStream out = new FileOutputStream(docFile);
		FileInputStream in = new FileInputStream(fn);
		if (fn.exists()) {
			com.sun.mail.util.BASE64DecoderStream base64 = new com.sun.mail.util.BASE64DecoderStream(
					in);

			byte buf[] = new byte[1024];
			while (base64.available() != -1) {
				int n = base64.read(buf);
				if (n < 0)
					break;
				out.write(buf, 0, n);
				out.flush();
			}
		}
	}

	public static void sendFileByWebServiceTest() throws Exception {
		javax.activation.FileDataSource Data = new javax.activation.FileDataSource(
				"c:\\1204901512432700.xml");
		javax.activation.DataHandler data = new javax.activation.DataHandler(
				Data);
		com.hr.util.ExecJwsClient wc = new com.hr.util.ExecJwsClient();
		wc.Http = "http://192.168.1.107:8787/sjzx/WebserviceTest.jws?wsdl";
		// sendXMLFile(javax.activation.DataHandler dh)
		Object[] obj = new Object[1];
		obj[0] = (Object) data;
		wc.Exec("sendXMLFile", obj);
	}

	public static String getParmString(
			javax.servlet.http.HttpServletRequest request,
			String fullNameOfNotInclude, String fullNameOfNeed) {
		String[][] arr = getParmArr(request, fullNameOfNotInclude,
				fullNameOfNeed);
		String parmStr = "";
		int len = 0;
		if (arr != null && arr.length > 0)
			len = arr.length;
		for (int i = 0; i < len; i++) {
			String key = arr[i][0];
			String val = arr[i][1];
			if (parmStr.equals(""))
				parmStr += key + "=" + com.hr.util.IncString.formatNull(val);
			else
				parmStr += "&" + key + "="
						+ com.hr.util.IncString.formatNull(val);
		}
		return parmStr;
	}

	public static String[][] getParmArr(
			javax.servlet.http.HttpServletRequest request,
			String fullNameOfNotInclude, String fullNameOfNeed) {
		java.util.Enumeration en = request.getParameterNames();
		String parmStr = "";
		MyList li = new MyList();
		String[] needArr = null;
		String[] notNeedArr = null;
		if (fullNameOfNeed != null && fullNameOfNeed.length() > 0) {
			needArr = Md5.splitOnly(fullNameOfNeed, ",");
		}
		if (fullNameOfNotInclude != null && fullNameOfNotInclude.length() > 0) {
			notNeedArr = Md5.splitOnly(fullNameOfNotInclude, ",");
		}

		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String val = request.getParameter(key);
			String lowerKey = key.toLowerCase();
			boolean isAdd = true;
			if (notNeedArr != null && notNeedArr.length > 0) {
				for (int i = 0; i < notNeedArr.length; i++) {
					String tempS = notNeedArr[i];
					if (tempS != null && tempS.trim().length() > 0) {
						if (tempS.startsWith("*")) { // 前缀
							if (tempS.endsWith("*")) {// 前后均有
								tempS = tempS.substring(1, tempS.length() - 1);
							} else {
								tempS = tempS.substring(1);
							}
							if (key.indexOf(tempS) != -1) {
								isAdd = false;
								break;
							}
						} else if (tempS.endsWith("*")) {// 后缀
							tempS = tempS.substring(0, tempS.length() - 1);
							if (key.startsWith(tempS)) {
								isAdd = false;
								break;
							}
						} else {
							if (key.equals(tempS)) {
								isAdd = false;
								break;
							}
						}

					}
				}
			}
			if (isAdd) {
				boolean oneIsNeed = false;
				if (needArr != null && needArr.length > 0) {
					for (int i = 0; i < needArr.length; i++) {
						String tempS = needArr[i];
						if (tempS != null && tempS.trim().length() > 0) {
							if (tempS.startsWith("*")) { // 前缀
								if (tempS.endsWith("*")) {// 前后均有
									tempS = tempS.substring(1,
											tempS.length() - 1);
								} else {
									tempS = tempS.substring(1);
								}
								if (key.indexOf(tempS) != -1) {
									oneIsNeed = true;
									break;
								}
							} else if (tempS.endsWith("*")) {// 后缀
								tempS = tempS.substring(0, tempS.length() - 1);
								if (key.startsWith(tempS)) {
									oneIsNeed = true;
									break;
								}
							} else {
								if (key.equals(tempS)) {
									oneIsNeed = true;
									break;
								}
							}
						}
					}
				}
				if (!oneIsNeed)
					isAdd = false;
			}
			if (isAdd) {
				li.set(key, com.hr.util.IncString.formatNull(val));
			}
		}
		return li.getArr();
	}

	public static void main(String[] args) throws Exception {
		/***********************************************************************************
		 * DataAdmin dataAdmin1 = new
		 * DataAdmin("c:\\taskfj.xml",ConnectionPool.getPool()); MyList li=new
		 * MyList(); li.set("F_savename","---savename-");
		 * li.set("F_filename","==filename=="); li.set("F_parentid",12+"");
		 * dataAdmin1.insert(li);
		 ***********************************************************************************/
	}

	// public DataAdmin() {
	// try {
	// jbInit();
	// }catch(Exception e) {
	// e.printStackTrace();
	// }
	// }
	// private void jbInit() throws Exception {
	// }

	/**
	 * 通过数组构造需要的对象
	 * 
	 * @param fieldArr
	 * @param pool
	 * @param tabname
	 * @throws Exception
	 */

	// public DataAdmin(String[][] fieldArr, Object pool, String tabname)
	// throws Exception {
	// super(pool);
	// curPool = pool;
	// tabName = tabname;
	// // java.io.File fn = new java.io.File("");
	// xml=new
	// MyXml("<?xml version=\"1.0\" encoding=\"gb2312\"?><root></root>");
	// setFieldArr(fieldArr);
	// }
	//
	// private void setFieldArr(String[][] fieldArr) throws Exception {
	//
	// // appName = xml.getNodeInfo("root/app", null);
	// standFieldArr = new String[fieldArr.length][5]; //
	// {"Country","varchar","20","1","0"}
	//
	// // tabName = xml.getNodeInfo("root/field", "tab");
	// if (tabYear > 0)
	// tabName = tabName + tabYear;
	// //dbName = xml.getNodeInfo("root/field", "db");
	// if (dbName == null || dbName.equals(""))
	// dbName = ConfigInfo.databaseName;
	//
	// for (int i = 0; i < fieldArr.length; i++) {
	// String fieldSign = fieldArr[i][0];
	// String FieldCname = fieldArr[i][8];
	//
	// standFieldArr[i][0] = fieldSign;
	// String fieldType = fieldArr[i][1];
	// String fieldLen = fieldArr[i][2];
	// int isKey = IncString.formatNull(fieldArr[i][4], 0);
	// String fieldFrom = fieldArr[i][5];
	//
	// if (isKey == 1) {
	// KeyIndex.set(fieldSign, fieldFrom);
	// } else {
	// if (fieldFrom != null && !fieldFrom.equals("")) {
	// sepcailField.set(fieldSign, fieldFrom);
	// }
	// }
	//
	// fieldType = checkStandardFieldType(fieldType,
	// IncString.formatNull(fieldLen, 10), FieldCname);
	// standFieldArr[i][1] = fieldType;
	// standFieldArr[i][2] = fieldLen;
	// int isNull = IncString.formatNull(fieldArr[i][3], 0);
	// standFieldArr[i][3] = isNull + "";
	// standFieldArr[i][4] = isKey + "";
	// }
	// createTab(tabName, dbName);
	// }

}
