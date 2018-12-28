package com.hr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.BaseDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.util.BaseDataOP;

public class ZsrkDo {
	public void yz(HttpServletRequest request, HttpServletResponse response) {
		String ywhc = request.getParameter("ywhc");
		String zdbdbName = request.getParameter("zdbdbName");
		String id = request.getParameter("xmfzrid");
		if (id == null || "".equals(id)) {
			id = ArchiveUtil.getDepartmentCode(request.getSession());
		}
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String sql = "select xmmc,xmbh,ywhc,qymc from zdb_xmwj_" + id
				+ " where ywhc='" + ywhc + "'";
		String[][] result = op.queryRowAndCol(sql);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (String[] strs : result) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("xmmc", strs[0]);
			m.put("xmbh", strs[1]);
			m.put("ywhc", strs[2]);
			m.put("qymc", strs[3]);
			list.add(m);
		}
		JsonUtil.writeJsonGrid(response, list, new PagingBean());
	}

	public void dqrk(HttpServletRequest request, HttpServletResponse response) {
		String ywhc = request.getParameter("ywhc");
		String zdbdbName = request.getParameter("zdbdbName");
		String id = request.getParameter("xmfzrid");
		if (id == null || "".equals(id)) {
			id = ArchiveUtil.getDepartmentCode(request.getSession());
		}
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String sql = "select ywhc from zdb_xmwj_" + id + " where ywhc='" + ywhc
				+ "'";
		String[][] result = op.queryRowAndCol(sql);
		if (result != null && result.length > 0) {
			String sql2 = "select ywhc from zdb_xmwj_y200040043 where ywhc='"
					+ result[0][0] + "'";
			String[][] result2 = op.queryRowAndCol(sql2);
			if (result2 != null && result2.length > 0) {
				JsonUtil.writeJsonMsg(response, false, "入库失败,已存在协议号为'"
						+ result2[0][0] + "'的档案");
			} else {
				String sql3 = "insert into zdb_xmwj_y200040043 select lsh,qymc,xmmc,xmbh,dkqx,xmfzr,xmnd,dkje,dw,tbyy,tbmm,tbdd,yjr,jsr,yjyy,yjmm,yjdd,dasryy,dasrmm,dasrdd,dasrjs,daycyy,daycmm,daycdd,ywhc,ycry,xyjs,bz,num_lsh,dwh,ajsm,ljr,jcr,yjflag,yjlist,jsdw,by1,by2,by3,square,projectType from zdb_xmwj_"
						+ id + " where ywhc='" + ywhc + "'";
				op.ExecSql(sql3);
				JsonUtil.writeJsonMsg(response, true, "入库成功");
			}
		} else {
			JsonUtil.writeJsonMsg(response, false, "协议号不存在");
		}
	}

	public void bqspnr(HttpServletRequest request, HttpServletResponse response) {
		String ywhc = request.getParameter("ywhc");
		String xmbh = request.getParameter("xmbh");
		String zdbdbName = request.getParameter("zdbdbName");
		String id = request.getParameter("xmfzrid");
		if (id == null || "".equals(id)) {
			id = ArchiveUtil.getDepartmentCode(request.getSession());
		}
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String sql = "select xmbh,lsh from zdb_xmwj_" + id + " where ywhc='"
				+ ywhc + "'";
		String[][] result = op.queryRowAndCol(sql);
		if (result != null && result.length > 0) {
			if (!xmbh.equals(result[0][0])) {
				JsonUtil.writeJsonMsg(response, false, "该协议号的档案编号"
						+ result[0][0] + "与目的档案编号" + xmbh + "不一致,操作失败!! ");
			} else {
				String del = "delete from zdb_xmlrb_y200040043 where lsh='"
						+ result[0][1] + "'";
				op.ExecSql(del);
				String insert = "insert into zdb_xmlrb_y200040043(dalx,damc,days,dabz,sort_num,lsh,dwh) select dalx,damc,days,dabz,sort_num,lsh,dwh from zdb_xmlrb_"
						+ id + " where lsh='" + result[0][1] + "'";
				op.ExecSql(insert);
				JsonUtil.writeJsonMsg(response, true, "补齐成功");
			}
		} else {
			JsonUtil.writeJsonMsg(response, false, "协议号不存在");
		}
	}

	public void bqdhnr(HttpServletRequest request, HttpServletResponse response) {
		String ywhc = request.getParameter("ywhc");
		String xmbh = request.getParameter("xmbh");
		String zdbdbName = request.getParameter("zdbdbName");
		String id = request.getParameter("xmfzrid");
		if (id == null || "".equals(id)) {
			id = ArchiveUtil.getDepartmentCode(request.getSession());
		}
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String sql = "select xmbh,lsh from zdb_xmwj_" + id + " where ywhc='"
				+ ywhc + "'";
		String[][] result = op.queryRowAndCol(sql);
		if (result != null && result.length > 0) {
			if (!xmbh.equals(result[0][0])) {
				JsonUtil.writeJsonMsg(response, false, "该协议号的档案编号"
						+ result[0][0] + "与目的档案编号" + xmbh + "不一致,操作失败!! ");
			} else {
				String del = "delete from zdb_dhgl_y200040043 where lsh='"
						+ result[0][1] + "'";
				op.ExecSql(del);
				String insert = "insert into zdb_dhgl_y200040043(dalx,damc,days,dabz,sort_num,lsh,dwh) select dalx,damc,days,dabz,sort_num,lsh,dwh from zdb_dhgl_"
						+ id + " where lsh='" + result[0][1] + "'";
				op.ExecSql(insert);
				JsonUtil.writeJsonMsg(response, true, "补齐成功");
			}
		} else {
			JsonUtil.writeJsonMsg(response, false, "协议号不存在");
		}
	}
}
