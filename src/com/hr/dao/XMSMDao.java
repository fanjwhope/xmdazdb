package com.hr.dao;

import java.util.ArrayList;
import java.util.List;

import com.hr.bean.XMSM;
import com.hr.bean.XMSMForm;
import com.hr.global.util.Constants;

public class XMSMDao extends BaseDao<XMSM> {

	private String tableName;

	private static String[][] tableField = {
			{ "id", Constants.fieldTypeNumber, "18", "0", "1" },
			{ "lsh", Constants.fieldTypeNumber, "18", "0", "0" },
			{ "olddabh", "varchar", "30", "0", "0" },
			{ "controlname", "varchar", "10", "1", "0" },
			{ "controlvalue", "text", "10", "1", "0" },
			{ "by1", "varchar", "50", "1", "0" },
			{ "by2", Constants.fieldTypeNumber, "18", "1", "0" } };

	public XMSMDao(String tableName, String dbName) {
		super(tableName, tableField, dbName);
		this.tableName = tableName;
		createTable(dbName);
	}

	protected void createTable(String dbName) {
		if (!ifExistsTable(tableName, dbName)) {
			String createSQL = Constants.BuildCreateTabSql(tableName,
					tableField);
			String target = tableField[0][0] + " " + tableField[0][1] + "\\("
					+ tableField[0][2] + "\\)";
			String replacement = target + " identity(1,1)";
			createSQL = createSQL.replaceFirst(target, replacement);
			getBaseDataOP(dbName).ExecSql(createSQL);
		}
	}

	/**
	 * 根据原档案编号获取项目说明对象
	 * 
	 * @param olddabh
	 * @return
	 */
	public XMSMForm getXMSM(String lsh) {
		String sql = "select id,lsh,olddabh,controlname,controlvalue from "
				+ this.tableName + " where lsh = '" + lsh + "'";
		String[][] xmsm = queryRowAndCol(sql);
		List<XMSM> list = new ArrayList<XMSM>();
		for (String[] strs : xmsm) {
			XMSM x = new XMSM(Long.valueOf(strs[1]), strs[2], strs[3], strs[4]);
			list.add(x);
		}
		XMSMForm form = XMSMList2XMSMFORM(list);
		return form;

	}

	public XMSMForm getXMSMByOlddabh(String olddabh) {
		String sql = "select id,lsh,olddabh,controlname,controlvalue from "
				+ this.tableName + " where olddabh = '" + olddabh + "'";
		String[][] xmsm = queryRowAndCol(sql);
		List<XMSM> list = new ArrayList<XMSM>();
		for (String[] strs : xmsm) {
			XMSM x = new XMSM(Long.valueOf(strs[1]), strs[2], strs[3], strs[4]);
			list.add(x);
		}
		XMSMForm form = XMSMList2XMSMFORM(list);
		return form;

	}

	public boolean update(List<XMSM> list) {
		boolean flag = false;
		if (list.size() > 0) {
			String isExists = "select * from " + tableName + " where lsh='"
					+ list.get(0).getLsh() + "'";
			String strs[][] = queryRowAndCol(isExists);
			if (strs != null && strs.length > 0) {
				for (int i = 0; i < list.size(); i++) {
					XMSM xmsm = list.get(i);
					String sql = "update " + tableName + " set controlvalue='"
							+ xmsm.getControlvalue() + "' where lsh='"
							+ xmsm.getLsh() + "' and controlname='"
							+ xmsm.getControlname() + "'";
					flag = ExecSql(sql);
				}
			} else {
				int lsh = getLSH();
				for (int i = 0; i < list.size(); i++) {
					XMSM xmsm = list.get(i);
					String sql = "insert into " + tableName
							+ " (lsh,olddabh,controlname,controlvalue) values("
							+ lsh + ",'" + xmsm.getOlddabh() + "','"
							+ xmsm.getControlname() + "','"
							+ xmsm.getControlvalue() + "')";
					flag = ExecSql(sql);
				}
			}
		}
		return flag;
	}

	public boolean insert(List<XMSM> list) {
		boolean flag = false;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XMSM xmsm = list.get(i);
				String sql = "insert into " + tableName
						+ " (lsh,olddabh,controlname,controlvalue) values("
						+ xmsm.getLsh() + ",'" + xmsm.getOlddabh() + "','"
						+ xmsm.getControlname() + "','"
						+ xmsm.getControlvalue() + "')";
				flag = ExecSql(sql);
			}
		}
		return flag;
	}

	/**
	 * 获取最大lsh号
	 * 
	 * @return
	 */
	public int getLSH() {
		int lsh = 1;
		String sql = "select max(lsh) from " + tableName;
		String[] data = queryLine(sql);
		if (data.length > 0) {
			lsh = data[0] == null ? 1 : (Integer.parseInt(data[0]) + 1);
		}
		return lsh;
	}

	private List<XMSM> XMSMFORM2XMSMList(XMSMForm xmsmform) {
		int lsh = getLSH();
		List<XMSM> list = new ArrayList<XMSM>();
		String[] names = new String[] { "by1", "dkxyh", "getqk", "infoqk",
				"jcr", "newxmbh", "olddabh", "qymc", "qysj", "returnqk",
				"smtype", "tbr", "tbsj", "xmfzr", "xmmc", "xxqk", "ywhc" };
		String[] values = new String[] { xmsmform.getBy1(),
				xmsmform.getDkxyh(), xmsmform.getGetqk(), xmsmform.getInfoqk(),
				xmsmform.getJcr(), xmsmform.getNewxmbh(),
				xmsmform.getOlddabh(), xmsmform.getQymc(), xmsmform.getQysj(),
				xmsmform.getReturnqk(), xmsmform.getSmtype(),
				xmsmform.getTbr(), xmsmform.getTbsj(), xmsmform.getXmfzr(),
				xmsmform.getXmmc(), xmsmform.getXxqk(), xmsmform.getYwhc() };
		for (int i = 0; i < names.length; i++) {
			XMSM xmsm = new XMSM(lsh, xmsmform.getOlddabh(), names[i],
					values[i]);
			list.add(xmsm);
		}
		return list;
	}

	private XMSMForm XMSMList2XMSMFORM(List<XMSM> xmsms) {
		XMSMForm form = new XMSMForm();
		if (xmsms.size() > 0) {
			form.setLsh(String.valueOf(xmsms.get(0).getLsh()));
		}
		for (XMSM xmsm : xmsms) {
			if ("smtype".equals(xmsm.getControlname())) {
				form.setSmtype(xmsm.getControlvalue());
				break;
			}
			if ("olddabh".equals(xmsm.getControlname())) {
				form.setOlddabh(xmsm.getControlvalue());
				break;
			}
			if ("by1".equals(xmsm.getControlname())) {
				form.setBy1(xmsm.getControlvalue());
				break;
			}
			if ("dkxyh".equals(xmsm.getControlname())) {
				form.setDkxyh(xmsm.getControlvalue());
				break;
			}
			if ("xmmc".equals(xmsm.getControlname())) {
				form.setXmmc(xmsm.getControlvalue());
				break;
			}
			if ("xmfzr".equals(xmsm.getControlname())) {
				form.setXmfzr(xmsm.getControlvalue());
				break;
			}
			if ("qymc".equals(xmsm.getControlname())) {
				form.setQymc(xmsm.getControlvalue());
				break;
			}
			if ("ywhc".equals(xmsm.getControlname())) {
				form.setYwhc(xmsm.getControlvalue());
				break;
			}
			if ("newxmbh".equals(xmsm.getControlname())) {
				form.setNewxmbh(xmsm.getControlvalue());
				break;
			}
			if ("tbsj".equals(xmsm.getControlname())) {
				form.setTbsj(xmsm.getControlvalue());
				break;
			}
			if ("qysj".equals(xmsm.getControlname())) {
				form.setQysj(xmsm.getControlvalue());
				break;
			}
			if ("xxqk".equals(xmsm.getControlname())) {
				form.setXxqk(xmsm.getControlvalue());
				break;
			}
			if ("getqk".equals(xmsm.getControlname())) {
				form.setGetqk(xmsm.getControlvalue());
				break;
			}
			if ("returnqk".equals(xmsm.getControlname())) {
				form.setReturnqk(xmsm.getControlvalue());
				break;
			}
			if ("infoqk".equals(xmsm.getControlname())) {
				form.setInfoqk(xmsm.getControlvalue());
				break;
			}
			if ("tbr".equals(xmsm.getControlname())) {
				form.setTbr(xmsm.getControlvalue());
				break;
			}
			if ("jcr".equals(xmsm.getControlname())) {
				form.setJcr(xmsm.getControlvalue());
				break;
			}
		}
		return form;
	}

	public XMSMForm getDAData(String ydabh, String userid) {
		XMSMForm xmsmForm = new XMSMForm();
		String sql = "select by1,xmmc,xmfzr,qymc from zdb_xmwj_" + userid
				+ " where xmbh='" + ydabh + "'";
		String[][] result = queryRowAndCol(sql);
		if (result != null && result.length > 0) {
			xmsmForm.setBy1(result[0][0]);
			xmsmForm.setXmmc(result[0][1]);
			xmsmForm.setXmfzr(result[0][2]);
			xmsmForm.setQymc(result[0][3]);
		}
		return xmsmForm;
	}

	public List<XMSMForm> queryAll(String smtype, String newxmbh, String dkxyh,
			String ywhc, String qymc, String xmmc) {
		List<XMSMForm> list = new ArrayList<XMSMForm>();
		String sql = "select distinct lsh from " + this.tableName;
		String[][] result = queryRowAndCol(sql);
		for (String[] strs : result) {
			for (String str : strs) {
				XMSMForm xmsmForm = getXMSM(str);
				if (smtype != null && smtype.trim() != "") {
					if (!xmsmForm.getSmtype().contains(smtype.trim())) {
						continue;
					}
				}
				if (qymc != null && qymc.trim() != "") {
					if (!xmsmForm.getQymc().contains(qymc.trim())) {
						continue;
					}
				}
				if (newxmbh != null && newxmbh.trim() != "") {
					if (!xmsmForm.getNewxmbh().contains(newxmbh.trim())) {
						continue;
					}
				}
				if (ywhc != null && ywhc.trim() != "") {
					if (!xmsmForm.getYwhc().contains(ywhc.trim())) {
						continue;
					}
				}
				if (dkxyh != null && dkxyh.trim() != "") {
					if (!xmsmForm.getDkxyh().contains(dkxyh.trim())) {
						continue;
					}
				}
				if (xmmc != null && xmmc.trim() != "") {
					if (!xmsmForm.getXmmc().contains(xmmc.trim())) {
						continue;
					}
				}
				list.add(xmsmForm);
			}
		}
		return list;
	}

	public Boolean delete(String lsh) {
		String sql = "delete from " + tableName + " where lsh='" + lsh + "'";
		return ExecSql(sql);
	}

	public static void main(String[] args) {
		System.out.println(new XMSMDao("xmsm_Y200020001", "zdb").delete("12"));
	}
}
