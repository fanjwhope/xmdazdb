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

		// ��Ŀ����
		String xmlx = request.getParameter("xmlx");

		// ԭ�������
		String ydabh = request.getParameter("ydabh");

		// �������
		String dkgb = request.getParameter("dkgb");

		// ����Э���
		String dkxyh = request.getParameter("dkxyh");

		// ��Ŀ����
		String xmmc = request.getParameter("xmmc");

		// ��Ŀ����
		String xmzg = request.getParameter("xmzg");

		// ���������
		String jkrmc = request.getParameter("jkrmc");

		// ת��Э���
		String zdxyh = request.getParameter("zdxyh");

		// �������
		String dabh = request.getParameter("dabh");

		// ���ʱ��
		String tbsj = request.getParameter("tbsj");

		// ����Э��ǩ��ʱ��
		String dkxyqdsj = request.getParameter("dkxyqdsj");

		// ����Э�����
		String dkxy = request.getParameter("dkxy");

		// ��Ŀ������
		String xmtk = request.getParameter("xmtk");

		// ������Ϣ���
		String hbfx = request.getParameter("hbfx");

		// �����������
		String qtzl = request.getParameter("qtzl");

		// �����
		String tbr = request.getParameter("tbr");

		// �����
		String jcr = request.getParameter("jcr");

		// ��ȡ����
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
			JsonUtil.writeJsonMsg(response, true, "��ӳɹ���");
		} else {
			JsonUtil.writeJsonMsg(response, false, "���ʧ�ܣ�");
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
	 * ����ԭ������Ż�ȡ��Ŀ˵������
	 * 
	 * @param request
	 * @param response
	 */
	public void getDAData(HttpServletRequest request,
			HttpServletResponse response) {

		// ����
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// ԭ�������
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
		// ����
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// ԭ�������
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
		// ����
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// ԭ�������
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
		// ����
		String tableName = "zdb_xmsm_"
				+ ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		// ��ˮ��
		String lsh = request.getParameter("lsh");
		XMSMDao DAO = new XMSMDao(tableName,dbName);
		boolean flag = DAO.delete(lsh);
		if (flag) {
			JsonUtil.writeJsonMsg(response, true, "ɾ���ɹ���");
		} else {
			JsonUtil.writeJsonMsg(response, false, "ɾ��ʧ�ܣ�");
		}
	}

	public void saveXMSM(HttpServletRequest request,
			HttpServletResponse response) {
		Long lsh=0L;
		// ��ˮ��
		try {
			lsh = Long.valueOf(request.getParameter("lsh"));
		} catch (Exception e) {
			lsh=0L;
		}	
		// ��Ŀ����
		String xmlx = request.getParameter("xmlx");

		// ԭ�������
		String ydabh = request.getParameter("ydabh");

		// �������
		String dkgb = request.getParameter("dkgb");

		// ����Э���
		String dkxyh = request.getParameter("dkxyh");

		// ��Ŀ����
		String xmmc = request.getParameter("xmmc");

		// ��Ŀ����
		String xmzg = request.getParameter("xmzg");

		// ���������
		String jkrmc = request.getParameter("jkrmc");

		// ת��Э���
		String zdxyh = request.getParameter("zdxyh");

		// �������
		String dabh = request.getParameter("dabh");

		// ���ʱ��
		String tbsj = request.getParameter("tbsj");

		// ����Э��ǩ��ʱ��
		String dkxyqdsj = request.getParameter("dkxyqdsj");

		// ����Э�����
		String dkxy = request.getParameter("dkxy");

		// ��Ŀ������
		String xmtk = request.getParameter("xmtk");

		// ������Ϣ���
		String hbfx = request.getParameter("hbfx");

		// �����������
		String qtzl = request.getParameter("qtzl");

		// �����
		String tbr = request.getParameter("tbr");

		// �����
		String jcr = request.getParameter("jcr");

		// ��ȡ����
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
			JsonUtil.writeJsonMsg(response, true, "����ɹ���");
		} else {
			JsonUtil.writeJsonMsg(response, false, "����ʧ�ܣ�");
		}
	}
}
