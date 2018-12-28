package com.hr.dao;

import java.util.List;

import com.hr.bean.Xmwj;
import com.hr.bean.XmwjBak;
import com.hr.bean.XmwjYJList;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.PagingBean;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.hr.util.BaseDataOP;

public class ArchiveDao extends BaseDao<Xmwj> {

	private String tableName;
	public static final String[][] tableField = {
			{ "lsh", Constants.fieldTypeNumber, "10", "0", "1" },
			{ "qymc", "varchar", "300", "0", "1" },
			{ "xmmc", "varchar", "400", "1", "0" },
			{ "xmbh", "varchar", "200", "1", "0" },
			{ "dkqx", "varchar", "100", "1", "0" },
			{ "xmfzr", "varchar", "200", "1", "0" },
			{ "xmnd", Constants.fieldTypeNumber, "4", "1", "0" },
			{ "dkje", "varchar", "200", "1", "0" },
			{ "dw", "varchar", "12", "1", "0" },
			{ "tbyy", Constants.fieldTypeNumber, "4", "1", "0" },
			{ "tbmm", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "tbdd", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "yjr", "varchar", "10", "1", "0" },
			{ "jsr", "varchar", "10", "1", "0" },
			{ "yjyy", Constants.fieldTypeNumber, "4", "1", "0" },
			{ "yjmm", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "yjdd", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "dasryy", Constants.fieldTypeNumber, "4", "1", "0" },
			{ "dasrmm", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "dasrdd", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "dasrjs", "varchar", "4", "1", "0" },
			{ "daycyy", Constants.fieldTypeNumber, "4", "1", "0" },
			{ "daycmm", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "daycdd", Constants.fieldTypeNumber, "2", "1", "0" },
			{ "ywhc", "varchar", "200", "1", "0" },
			{ "ycry", "varchar", "300", "1", "0" },// 由于老系统部分分行长度为300修改新表长度40位30
			{ "xyjs", "varchar", "4", "1", "0" },
			{ "bz", "varchar", "7999", "1", "0" },
			{ "num_Lsh", Constants.fieldTypeNumber, "10", "1", "0" },
			{ "dwh", "varchar", "10", "1", "0" },
			{ "ajsm", "varchar", "7999", "1", "0" },
			{ "ljr", "varchar", "50", "1", "0" },
			{ "jcr", "varchar", "50", "1", "0" },
			{ "yjflag", "varchar", "1", "1", "0" },
			{ "yjlist", "varchar", "800", "1", "0" },
			{ "jsdw", "varchar", "10", "1", "0" },
			{ "by1", "varchar", "30", "1", "0" },
			{ "by2", "varchar", "200", "1", "0" },
			{ "by3", "varchar", "200", "1", "0" },
			{ "projectType", "char", "5", "1", "0" },
			{ "square", "char", "5", "1", "0" } };
	public static final String[][] lshTableField = { { "lsh",
			Constants.fieldTypeNumber, "10", "0", "1" } };

	public ArchiveDao(String tableName, String dbName) {
		super(tableName, tableField, dbName);
		this.tableName = tableName;
		// 使用构造方法建表 xmwj表
		createTableAndLSH(tableName, tableField, lshTableField, dbName);
	}

	public void createTableAndLSH(String tableName, String[][] tableField,
			String[][] lsh, String dbName) {
		BaseDataOP bd = BaseDao.getBaseDataOP(dbName);
		String[] tab = tableName.split("\\.");
		String[] _tab = tableName.split("_");
		String lshTab = null;// 存储流水号的表
		if (tab.length == 1) {
			String createSQL1 = Constants.BuildCreateTabSql(tableName,
					tableField);
			bd.ExecSql(createSQL1);
			if (_tab.length > 1) {
				lshTab = _tab[0] + "_" + "XMDA_LSH";
			}
			if (!bd.ifExistsTab(lshTab)) {
				String createSQL = Constants.BuildCreateTabSql(lshTab, lsh);
				bd.ExecSql(createSQL);
			}
		} else if (tab.length == 3) {
			if (!bd.ifExistsTab(tab[2], tab[0])) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				bd.ExecSql(createSQL);
			}
			// 创建流水表
			lshTab = tab[0] + "." + tab[1] + "." + "xmda_lsh";
			if (!bd.ifExistsTab("xmda_lsh", tab[0])) {
				String createSQL = Constants.BuildCreateTabSql(lshTab, lsh);
				bd.ExecSql(createSQL);
				String insertSql = "insert into " + lshTab + " values(1)";
				bd.ExecSql(insertSql);
			}
		}
	}

	/**
	 * 修改xmwj表信息移交状态位
	 * 
	 * @param tabs
	 *            表名
	 * @param lshs
	 *            流水号
	 * @param yjflag
	 *            移交状态表
	 */
	public void updateFlag(String tabs, String lshs, String yjflag) {
		String sql = "update " + tabs + " set yjflag="
				+ StringHelper.getFieldSql(yjflag) + " where lsh in (" + lshs
				+ ")";
		ExecSql(sql);
	}

	/**
	 * 为方便用户移交，查询出来的记录依然显示在移交列表 即查询yjflag=S的对象
	 * 
	 * @param qymc
	 * @param xmbh
	 */
	public void updateFlag(String qymc, String xmbh) {
		String sql = "update "
				+ tableName
				+ " set yjflag='S' where (qymc like "
				+ StringHelper.getFieldSql("%" + qymc + "%")
				+ " and xmbh like "
				+ StringHelper.getFieldSql("%" + xmbh + "%")
				+ ") and (( yjflag <>'0' and yjflag<>'1' and yjflag<>'2') or yjflag is null)";
		ExecSql(sql);
	}

	/**
	 * 是否结清
	 * 
	 * @param xmwj
	 */
	public void isSquare(Xmwj xmwj) {
		String sql = "update " + tableName + " set square="
				+ StringHelper.getFieldSql(xmwj.getSquare()) + " where lsh="
				+ StringHelper.getFieldSql(xmwj.getLsh());
		ExecSql(sql);
	}

	public Xmwj getOne(String lsh, String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + " from " + tableName
				+ " where lsh=" + StringHelper.getFieldSql(lsh);
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public Xmwj getOne(String lsh, String tbname, String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + " from " + tbname + " where lsh="
				+ StringHelper.getFieldSql(lsh);
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public Xmwj getLast(String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + "  from " + tableName
				+ " where (yjflag is null or ( yjflag<>'2')) order by lsh desc";
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public Xmwj getNextLSH(String lsh, String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + " from " + tableName
				+ " where lsh>" + lsh
				+ " and (yjflag is null or ( yjflag<>'2')) order by lsh  ";
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public String getYJGC(long lsh, String dbName) {
		String[] arr = tableName.split("_");
		String xmwjYJListTab = arr[0] + "_" + "xmda_yjlist";
		XmwjYJListDao xmwjYJListDao = new XmwjYJListDao(xmwjYJListTab, dbName);
		List<XmwjYJList> list = xmwjYJListDao
				.getList(null, String.valueOf(lsh));
		StringBuffer yjgc = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			yjgc.append(list.get(i).getYjsj() + "[移交人:" + list.get(i).getYjr()
					+ " 接收人:" + list.get(i).getJsr() + "];");
		}
		return yjgc.toString();
	}

	public Xmwj getBackLSH(String lsh, String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + "  from " + tableName
				+ " where lsh<" + lsh
				+ " and (yjflag is null or ( yjflag<>'2')) order by lsh desc";
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public Xmwj findByXmbh(String xmbh, String dbName) {
		String fieldNames = getFields(tableField);
		String sql = "select " + fieldNames + "  from " + tableName
				+ " where ywhc='" + xmbh
				+ "' and (yjflag is null or ( yjflag<>'2')) order by lsh desc";
		System.out.println(sql);
		String[] obj = queryLine(sql);
		if (obj.length > 0 && obj[0] != null) {
			Xmwj xmwj = BeanHelper.stringArray2Object(fieldNames, obj,
					Xmwj.class, false);
			xmwj.setYjgc(getYJGC(xmwj.getLsh(), dbName));
			return xmwj;
		} else {
			return null;
		}
	}

	public List<Xmwj> getList(Xmwj xmwj, PagingBean pagingBean) {
		String fieldNames = getFields(tableField);
		String tabs = tableName;
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		String orderBy = "";
		if (null != pagingBean && !Validation.isEmpty(pagingBean.getSort())) {
			if (!Validation.isEmpty(pagingBean.getSortOrder())) {
				if (pagingBean.getSort().equals("ljDate")) {
					orderBy = " dasryy" + " " + pagingBean.getSortOrder()
							+ ",dasrmm" + " " + pagingBean.getSortOrder()
							+ ",dasrdd " + pagingBean.getSortOrder();
				} else if (pagingBean.getSort().equals("tbDate")) {
					orderBy = " tbyy" + " " + pagingBean.getSortOrder()
							+ ",tbmm" + " " + pagingBean.getSortOrder()
							+ ",tbdd " + pagingBean.getSortOrder();
				} else {
					orderBy = pagingBean.getSort() + " "
							+ pagingBean.getSortOrder();
				}
			}
		}
		if (!Validation.isEmpty(orderBy)) {
			orderBy = "order by " + orderBy;
		}
		if (!Validation.isEmpty(xmwj.getQymc())) {// 借款人
			whereSql.append(" and qymc like '%" + xmwj.getQymc() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getXmfzr())) {// 信贷员
			whereSql.append(" and xmfzr like '%" + xmwj.getXmfzr() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getXmmc())) {// 项目名称
			whereSql.append(" and xmmc like '%" + xmwj.getXmmc() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getDkqx())) {// 贷款期限
			whereSql.append(" and dkqx like '%" + xmwj.getDkqx() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getYwhc())) {// 实施单位
			whereSql.append(" and ywhc like '%" + xmwj.getYwhc() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getBy1())) {// 保证人
			whereSql.append(" and by1 like '%" + xmwj.getBy1() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getXmbh())) {// 借款合同号 
			whereSql.append(" and xmbh like '%" + xmwj.getXmbh() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getBy2())) {// 档案编号
			whereSql.append(" and by2 like '%" + xmwj.getBy2() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getTbDate())) {// 审批起时间
			String[] arr = xmwj.getTbDate().split("-");
			if (arr.length == 3) {
				whereSql.append(" and CONVERT(varchar(100),cast((convert(VARCHAR(32),(CAST(tbyy AS VARCHAR(10)) + '-' + CAST(tbmm AS VARCHAR(10))+'-'+CAST(tbdd as VARCHAR(10))),23)) as datetime ),23)>='"
						+ xmwj.getTbDate() + "'");
			}
		}
		if (!Validation.isEmpty(xmwj.getTbDateE())) {// 审批止时间
			String[] arr = xmwj.getTbDateE().split("-");
			if (arr.length == 3) {
				whereSql.append(" and CONVERT(varchar(100),cast((convert(VARCHAR(32),(CAST(tbyy AS VARCHAR(10)) + '-' + CAST(tbmm AS VARCHAR(10))+'-'+CAST(tbdd as VARCHAR(10))),23)) as datetime ),23)<='"
						+ xmwj.getTbDateE() + "'");
			}
		}
		if (!Validation.isEmpty(xmwj.getDkje())) {// 金额
			whereSql.append(" and dkje like '%" + xmwj.getDkje() + "%'");
		}
		if (!Validation.isEmpty(xmwj.getProjectType())) {// 项目品种
			whereSql.append(" and projectType=" + xmwj.getProjectType());
		}
		if (!Validation.isEmpty(xmwj.getSquare())) {// 是否结清
			whereSql.append(" and square =" + xmwj.getSquare());
		}
		whereSql.append(" and (yjflag<>'2' or yjflag is null) ");
		String[][] obj = null;
		if (null != pagingBean) {
			obj = queryRowAndCol(pagingBean.getPageSize(),
					pagingBean.getCurrentPage(), fieldNames, "lsh", tabs,
					whereSql.toString(), orderBy);
			pagingBean.setTotalRecords(getRowSum());
		} else {
			obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		}
		List<Xmwj> list = BeanHelper.stringArray2Object(fieldNames, obj,
				Xmwj.class, false);
		return list;
	}

	public List<Xmwj> getListToTransfer(Xmwj xmwj) {
		String fieldNames = getFields(tableField);
		String tabs = tableName;
		StringBuffer whereSql = new StringBuffer(" where yjflag='S' ");
		String orderBy = " order by lsh ";
		String[][] obj = null;
		/*
		 * if(!Validation.isEmpty(xmwj.getXmbh())){//借款合同号
		 * whereSql.append(" or xmbh = '"+xmwj.getXmbh()+"'"); }
		 * if(!Validation.isEmpty(xmwj.getQymc())){//借款人
		 * whereSql.append(" or qymc ="
		 * +StringHelper.getFieldSql(xmwj.getQymc())); }
		 */
		obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
				+ whereSql + " " + orderBy);
		List<Xmwj> list = BeanHelper.stringArray2Object(fieldNames, obj,
				Xmwj.class, false);
		return list;
	}

	// 判断借款合同号是否存在
	public boolean isExistXmbh(String xmbh) {
		boolean flag = false;
		String sql = " select lsh  from " + tableName + " where xmbh="
				+ StringHelper.getFieldSql(xmbh);
		int a = queryRowNum(sql);
		if (a > 0) {
			flag = true;
		}
		return flag;
	}

	public String getMaxLSH() {
		String lshTab = getMaxLSHTable();
		String sql = "select lsh from " + lshTab;
		String updateSql = "update " + lshTab + " set lsh=lsh+1 ";
		String lsh = querySingleData(sql);
		if (Validation.isEmpty(lsh)) {
			lsh = "1";
			updateSql = "insert into " + lshTab + " values(2) ";
		}
		ExecSql(updateSql);
		return lsh;
	}

	// 根据年度删除指定库的记录
	public void deleteByXmnd(int xmnd) {
		String sql = "delete from " + tableName + " where xmnd = "
				+ StringHelper.getFieldSql(xmnd);
		ExecSql(sql);
	}

	public String getMaxLSHTable() {
		String[] tab = tableName.split("_");
		String lshTab = tab[0] + "_" + "XMDA_LSH";
		return lshTab;
	}

	/**
	 * 
	 * @param updateLSHS
	 * @param xmwjJSTab
	 */
	public void writeJSMessage(String updateLSHS, String xmwjJSTab) {
		String delSql = "delete from " + xmwjJSTab + " where lsh in ("
				+ updateLSHS + ")";
		// 由于老系统部分分行数据吧表多出几列字段，所以只处理现有字段
		String sql = "insert into " + xmwjJSTab + "(" + getFields(tableField)
				+ ") select " + getFields(tableField) + " from " + tableName
				+ " where lsh in(" + updateLSHS + ")";
		ExecSql(delSql);
		ExecSql(sql);
	}

	public void updateByJSmessage(String updateLSHS, String xmwjJSTab) {
		String sql = "update " + xmwjJSTab + " set yjflag=''  "
				+ "where lsh in(" + updateLSHS + ")";
		ExecSql(sql);
	}

	public XmwjBak getXmwjBakBylsh(String lsh) {
		String sql = "select " + getFields(tableField) + " from " + tableName
				+ " where lsh =" + lsh;
		XmwjBak xmwjBak = BeanHelper.stringArray2Object(getFields(tableField),
				queryLine(sql), XmwjBak.class, false);
		return xmwjBak;
	}

	public String getXMBHByLsh(String lsh) {
		String sql = "select xmbh from " + tableName + " where lsh=" + lsh;
		String xmbh = querySingleData(sql);
		return xmbh;
	}

	public static void main(String[] args) {
		ArchiveDao dao = new ArchiveDao("ZDB_XMWJ_Y200040001", "zdb");
		for (int i = 0; i < 1000; i++) {
			System.out
					.println(i
							+ ""
							+ dao.ExecSql("insert into ZDB_DHGL_Y200040001(lsh,sort_num,damc,dwh,dalx) values('7057','6','6','Y200040001','')"));
		}
	}
}
