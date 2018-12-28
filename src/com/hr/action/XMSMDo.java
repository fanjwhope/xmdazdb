package com.hr.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.XMSM;
import com.hr.bean.XMSMForm;
import com.hr.dao.XMSMDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.global.util.Validation;

public class XMSMDo {

	public void add(HttpServletRequest request, HttpServletResponse response) {

		// 项目类型
		String xmlx = request.getParameter("xmlx");

		// 原档案编号
		String ydabh = request.getParameter("ydabh");

		// 贷款国别
		String dkgb = request.getParameter("dkgb");

		// 贷款协议号
		String dkxyh = request.getParameter("dkxyh");

		// 项目名称
		String xmmc = request.getParameter("xmmc");

		// 项目主管
		String xmzg = request.getParameter("xmzg");

		// 借款人名称
		String jkrmc = request.getParameter("jkrmc");

		// 转贷协议号
		String zdxyh = request.getParameter("zdxyh");

		// 档案编号
		String dabh = request.getParameter("dabh");

		// 填表时间
		String tbsj = request.getParameter("tbsj");

		// 贷款协议签订时间
		String dkxyqdsj = request.getParameter("dkxyqdsj");

		// 贷款协议情况
		String dkxy = request.getParameter("dkxy");

		// 项目提款情况
		String xmtk = request.getParameter("xmtk");

		// 还本付息情况
		String hbfx = request.getParameter("hbfx");

		// 其他资料情况
		String qtzl = request.getParameter("qtzl");

		// 填表人
		String tbr = request.getParameter("tbr");

		// 检查人
		String jcr = request.getParameter("jcr");

		// 获取表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		XMSMForm form = new XMSMForm();

		XMSMDao DAO = new XMSMDao(tableName,dbName);

		List<XMSM> list = new ArrayList<XMSM>();
		int lsh = DAO.getLSH();
		XMSM xmsm1 = new XMSM();
		xmsm1.setLsh(lsh);
		xmsm1.setOlddabh(ydabh);
		xmsm1.setControlname("smtype");
		xmsm1.setControlvalue(xmlx);
		list.add(xmsm1);

		XMSM xmsm2 = new XMSM();
		xmsm2.setLsh(lsh);
		xmsm2.setOlddabh(ydabh);
		xmsm2.setControlname("olddabh");
		xmsm2.setControlvalue(ydabh);
		list.add(xmsm2);

		XMSM xmsm3 = new XMSM();
		xmsm3.setLsh(lsh);
		xmsm3.setOlddabh(ydabh);
		xmsm3.setControlname("by1");
		xmsm3.setControlvalue(dkgb);
		list.add(xmsm3);

		XMSM xmsm4 = new XMSM();
		xmsm4.setLsh(lsh);
		xmsm4.setOlddabh(ydabh);
		xmsm4.setControlname("dkxyh");
		xmsm4.setControlvalue(dkxyh);
		list.add(xmsm4);

		XMSM xmsm5 = new XMSM();
		xmsm5.setLsh(lsh);
		xmsm5.setOlddabh(ydabh);
		xmsm5.setControlname("xmmc");
		xmsm5.setControlvalue(xmmc);
		list.add(xmsm5);

		XMSM xmsm6 = new XMSM();
		xmsm6.setLsh(lsh);
		xmsm6.setOlddabh(ydabh);
		xmsm6.setControlname("xmfzr");
		xmsm6.setControlvalue(xmzg);
		list.add(xmsm6);

		XMSM xmsm7 = new XMSM();
		xmsm7.setLsh(lsh);
		xmsm7.setOlddabh(ydabh);
		xmsm7.setControlname("qymc");
		xmsm7.setControlvalue(jkrmc);
		list.add(xmsm7);

		XMSM xmsm8 = new XMSM();
		xmsm8.setLsh(lsh);
		xmsm8.setOlddabh(ydabh);
		xmsm8.setControlname("ywhc");
		xmsm8.setControlvalue(zdxyh);
		list.add(xmsm8);

		XMSM xmsm9 = new XMSM();
		xmsm9.setLsh(lsh);
		xmsm9.setOlddabh(ydabh);
		xmsm9.setControlname("newxmbh");
		xmsm9.setControlvalue(dabh);
		list.add(xmsm9);

		XMSM xmsm10 = new XMSM();
		xmsm10.setLsh(lsh);
		xmsm10.setOlddabh(ydabh);
		xmsm10.setControlname("tbsj");
		xmsm10.setControlvalue(tbsj);
		list.add(xmsm10);

		XMSM xmsm11 = new XMSM();
		xmsm11.setLsh(lsh);
		xmsm11.setOlddabh(ydabh);
		xmsm11.setControlname("qysj");
		xmsm11.setControlvalue(dkxyqdsj);
		list.add(xmsm11);

		XMSM xmsm12 = new XMSM();
		xmsm12.setLsh(lsh);
		xmsm12.setOlddabh(ydabh);
		xmsm12.setControlname("xxqk");
		xmsm12.setControlvalue(dkxy);
		list.add(xmsm12);

		XMSM xmsm13 = new XMSM();
		xmsm13.setLsh(lsh);
		xmsm13.setOlddabh(ydabh);
		xmsm13.setControlname("getqk");
		xmsm13.setControlvalue(xmtk);
		list.add(xmsm13);

		XMSM xmsm14 = new XMSM();
		xmsm14.setLsh(lsh);
		xmsm14.setOlddabh(ydabh);
		xmsm14.setControlname("returnqk");
		xmsm14.setControlvalue(hbfx);
		list.add(xmsm14);

		XMSM xmsm15 = new XMSM();
		xmsm15.setLsh(lsh);
		xmsm15.setOlddabh(ydabh);
		xmsm15.setControlname("infoqk");
		xmsm15.setControlvalue(qtzl);
		list.add(xmsm15);

		XMSM xmsm16 = new XMSM();
		xmsm16.setLsh(lsh);
		xmsm16.setOlddabh(ydabh);
		xmsm16.setControlname("tbr");
		xmsm16.setControlvalue(tbr);
		list.add(xmsm16);

		XMSM xmsm17 = new XMSM();
		xmsm17.setLsh(lsh);
		xmsm17.setOlddabh(ydabh);
		xmsm17.setControlname("jcr");
		xmsm17.setControlvalue(jcr);
		list.add(xmsm17);

		boolean flag = DAO.insert(list);

		if (flag) {
			JsonUtil.writeJsonMsg(response, true, "添加成功！");
		} else {
			JsonUtil.writeJsonMsg(response, false, "添加失败！");
		}

	}

	public void cateateTable(HttpServletRequest request,
			HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		XMSMDao xmsmDao = new XMSMDao(tableName,dbName);
	}

	/**
	 * 根据原档案编号获取项目说明对象
	 * 
	 * @param request
	 * @param response
	 */
	public void getDAData(HttpServletRequest request,
			HttpServletResponse response) {

		// 表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// 原档案编号
		String ydabh = request.getParameter("ydabh");
		XMSMDao DAO = new XMSMDao(tableName,dbName);

		if (!Validation.isEmpty(ydabh)) {
			XMSMForm from = DAO.getDAData(ydabh,
					ArchiveUtil.getDepartmentCode(request.getSession()));
			JsonUtil.writeJsonObject(response, from);
		}
	}

	public void getXMSMForm(HttpServletRequest request,
			HttpServletResponse response) {
		// 表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// 原档案编号
		String lsh = request.getParameter("lsh");
		String olddabh = request.getParameter("olddabh");
		XMSMDao DAO = new XMSMDao(tableName,dbName);
		if (!Validation.isEmpty(lsh)) {
			
			XMSMForm from = new XMSMForm();
			if (!"0".equals(lsh)) {
				from = DAO.getXMSM(lsh);
			}else{
				from = DAO.getXMSMByOlddabh(olddabh);
			}
			JsonUtil.writeJsonObject(response, from);
		}
	}

	public void queryAll(HttpServletRequest request,
			HttpServletResponse response) {
		// 表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// 原档案编号
		String smtype = request.getParameter("smtype");
		String newxmbh = request.getParameter("newxmbh");
		String dkxyh = request.getParameter("dkxyh");
		String ywhc = request.getParameter("ywhc");
		String qymc = request.getParameter("qymc");
		String xmmc = request.getParameter("xmmc");
		XMSMDao DAO = new XMSMDao(tableName,dbName);
		List<XMSMForm> from = DAO.queryAll(smtype, newxmbh, dkxyh, ywhc, qymc,
				xmmc);
		JsonUtil.writeJsonGrid(response, from, new PagingBean());
	}

	public void deleteXMSM(HttpServletRequest request,
			HttpServletResponse response) {
		// 表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// 流水号
		String lsh = request.getParameter("lsh");
		XMSMDao DAO = new XMSMDao(tableName,dbName);
		boolean flag = DAO.delete(lsh);
		if (flag) {
			JsonUtil.writeJsonMsg(response, true, "删除成功！");
		} else {
			JsonUtil.writeJsonMsg(response, false, "删除失败！");
		}
	}

	public void saveXMSM(HttpServletRequest request,
			HttpServletResponse response) {
		Long lsh=0L;
		// 流水号
		try {
			lsh = Long.valueOf(request.getParameter("lsh"));
		} catch (Exception e) {
			lsh=0L;
		}	
		// 项目类型
		String xmlx = request.getParameter("xmlx");

		// 原档案编号
		String ydabh = request.getParameter("ydabh");

		// 贷款国别
		String dkgb = request.getParameter("dkgb");

		// 贷款协议号
		String dkxyh = request.getParameter("dkxyh");

		// 项目名称
		String xmmc = request.getParameter("xmmc");

		// 项目主管
		String xmzg = request.getParameter("xmzg");

		// 借款人名称
		String jkrmc = request.getParameter("jkrmc");

		// 转贷协议号
		String zdxyh = request.getParameter("zdxyh");

		// 档案编号
		String dabh = request.getParameter("dabh");

		// 填表时间
		String tbsj = request.getParameter("tbsj");

		// 贷款协议签订时间
		String dkxyqdsj = request.getParameter("dkxyqdsj");

		// 贷款协议情况
		String dkxy = request.getParameter("dkxy");

		// 项目提款情况
		String xmtk = request.getParameter("xmtk");

		// 还本付息情况
		String hbfx = request.getParameter("hbfx");

		// 其他资料情况
		String qtzl = request.getParameter("qtzl");

		// 填表人
		String tbr = request.getParameter("tbr");

		// 检查人
		String jcr = request.getParameter("jcr");

		// 获取表名
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		XMSMForm form = new XMSMForm();

		XMSMDao DAO = new XMSMDao(tableName,dbName);

		List<XMSM> list = new ArrayList<XMSM>();
		XMSM xmsm1 = new XMSM();
		xmsm1.setLsh(lsh);
		xmsm1.setOlddabh(ydabh);
		xmsm1.setControlname("smtype");
		xmsm1.setControlvalue(xmlx);
		list.add(xmsm1);

		XMSM xmsm2 = new XMSM();
		xmsm2.setLsh(lsh);
		xmsm2.setOlddabh(ydabh);
		xmsm2.setControlname("olddabh");
		xmsm2.setControlvalue(ydabh);
		list.add(xmsm2);

		XMSM xmsm3 = new XMSM();
		xmsm3.setLsh(lsh);
		xmsm3.setOlddabh(ydabh);
		xmsm3.setControlname("by1");
		xmsm3.setControlvalue(dkgb);
		list.add(xmsm3);

		XMSM xmsm4 = new XMSM();
		xmsm4.setLsh(lsh);
		xmsm4.setOlddabh(ydabh);
		xmsm4.setControlname("dkxyh");
		xmsm4.setControlvalue(dkxyh);
		list.add(xmsm4);

		XMSM xmsm5 = new XMSM();
		xmsm5.setLsh(lsh);
		xmsm5.setOlddabh(ydabh);
		xmsm5.setControlname("xmmc");
		xmsm5.setControlvalue(xmmc);
		list.add(xmsm5);

		XMSM xmsm6 = new XMSM();
		xmsm6.setLsh(lsh);
		xmsm6.setOlddabh(ydabh);
		xmsm6.setControlname("xmfzr");
		xmsm6.setControlvalue(xmzg);
		list.add(xmsm6);

		XMSM xmsm7 = new XMSM();
		xmsm7.setLsh(lsh);
		xmsm7.setOlddabh(ydabh);
		xmsm7.setControlname("qymc");
		xmsm7.setControlvalue(jkrmc);
		list.add(xmsm7);

		XMSM xmsm8 = new XMSM();
		xmsm8.setLsh(lsh);
		xmsm8.setOlddabh(ydabh);
		xmsm8.setControlname("ywhc");
		xmsm8.setControlvalue(zdxyh);
		list.add(xmsm8);

		XMSM xmsm9 = new XMSM();
		xmsm9.setLsh(lsh);
		xmsm9.setOlddabh(ydabh);
		xmsm9.setControlname("newxmbh");
		xmsm9.setControlvalue(dabh);
		list.add(xmsm9);

		XMSM xmsm10 = new XMSM();
		xmsm10.setLsh(lsh);
		xmsm10.setOlddabh(ydabh);
		xmsm10.setControlname("tbsj");
		xmsm10.setControlvalue(tbsj);
		list.add(xmsm10);

		XMSM xmsm11 = new XMSM();
		xmsm11.setLsh(lsh);
		xmsm11.setOlddabh(ydabh);
		xmsm11.setControlname("qysj");
		xmsm11.setControlvalue(dkxyqdsj);
		list.add(xmsm11);

		XMSM xmsm12 = new XMSM();
		xmsm12.setLsh(lsh);
		xmsm12.setOlddabh(ydabh);
		xmsm12.setControlname("xxqk");
		xmsm12.setControlvalue(dkxy);
		list.add(xmsm12);

		XMSM xmsm13 = new XMSM();
		xmsm13.setLsh(lsh);
		xmsm13.setOlddabh(ydabh);
		xmsm13.setControlname("getqk");
		xmsm13.setControlvalue(xmtk);
		list.add(xmsm13);

		XMSM xmsm14 = new XMSM();
		xmsm14.setLsh(lsh);
		xmsm14.setOlddabh(ydabh);
		xmsm14.setControlname("returnqk");
		xmsm14.setControlvalue(hbfx);
		list.add(xmsm14);

		XMSM xmsm15 = new XMSM();
		xmsm15.setLsh(lsh);
		xmsm15.setOlddabh(ydabh);
		xmsm15.setControlname("infoqk");
		xmsm15.setControlvalue(qtzl);
		list.add(xmsm15);

		XMSM xmsm16 = new XMSM();
		xmsm16.setLsh(lsh);
		xmsm16.setOlddabh(ydabh);
		xmsm16.setControlname("tbr");
		xmsm16.setControlvalue(tbr);
		list.add(xmsm16);

		XMSM xmsm17 = new XMSM();
		xmsm17.setLsh(lsh);
		xmsm17.setOlddabh(ydabh);
		xmsm17.setControlname("jcr");
		xmsm17.setControlvalue(jcr);
		list.add(xmsm17);

		boolean flag = DAO.update(list);

		if (flag) {
			JsonUtil.writeJsonMsg(response, true, "保存成功！");
		} else {
			JsonUtil.writeJsonMsg(response, false, "保存失败！");
		}
	}
}
