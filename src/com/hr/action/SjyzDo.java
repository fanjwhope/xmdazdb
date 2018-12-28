package com.hr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ComboboxBean;
import com.hr.bean.Sjyz;
import com.hr.dao.BaseDao;
import com.hr.dao.ZdbStandardDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.util.BaseDataOP;

public class SjyzDo {
	public void yzsj(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		String id = ArchiveUtil.getDepartmentCode(request.getSession());
		String sql = "select xmbh from zdb_xmwj_" + id
				+ " where xmbh in (select xmbh from zdb_xmwj_" + id
				+ " group by xmbh having count(1) >= 2)";
		BaseDataOP op = BaseDao.getBaseDataOP(dbName);
		String[][] result = op.queryRowAndCol(sql);
		List<Map<String, String>> list = new ArrayList();
		for (String[] strs : result) {
			for (String str : strs) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("xmbh", str);
				list.add(map);
			}
		}
		JsonUtil.writeJsonGrid(response, list, new PagingBean());
	}

	public void xxxx(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		String xmbh = request.getParameter("xmbh").trim();
		String id = request.getParameter("xmfzrid");
		String tbsj = request.getParameter("tbsj");
		String yy = "9999";
		String mm = "9999";
		String dd = "9999";
		if (tbsj != null && !"".equals(tbsj.trim())) {
			String[] strs = tbsj.split("-");
			yy = strs[0];
			mm = strs[1];
			dd = strs[2];
		}
		if (id == null || "".equals(id)) {
			id = ArchiveUtil.getDepartmentCode(request.getSession());
		}
		String sql = "select xmbh,tx.lsh,ywhc,sum1 ,sum("
				+ "case when isnumeric(t3.days)!=0 then CAST(t3.days AS INT) else 0 end"
				+ ") sum2,tx.xmmc,tbyy,tbmm,tbdd from("
				+ " select xmbh,t1.lsh,ywhc,sum("
				+ "case when isnumeric(t2.days)!=0 then CAST(t2.days AS INT) else 0 end"
				+ ") sum1,t1.xmmc,tbyy,tbmm,tbdd from zdb_xmwj_"
				+ id
				+ " t1 left join "
				+ "zdb_dhgl_"
				+ id
				+ " t2 on t1.lsh=t2.lsh  group by xmbh,t1.lsh,ywhc,t1.xmmc,tbyy,tbmm,tbdd) tx left join zdb_xmlrb_"
				+ id
				+ " t3 "
				+ " on  tx.lsh=t3.lsh where xmbh like '%"
				+ xmbh
				+ "%' and ((tbyy<"
				+ Integer.valueOf(yy)
				+ " and tbmm<"
				+ Integer.valueOf(mm)
				+ " and tbdd<"
				+ Integer.valueOf(dd)
				+ ") or (tbyy<"
				+ Integer.valueOf(yy)
				+ " and tbmm<"
				+ Integer.valueOf(mm)
				+ ") or (tbyy<"
				+ Integer.valueOf(yy)
				+ ")) group by xmbh,tx.lsh,ywhc,sum1,tx.xmmc,tbyy,tbmm,tbdd order by xmbh";
		BaseDataOP op = BaseDao.getBaseDataOP(dbName);
		String[][] result = op.queryRowAndCol(sql);
		List<Sjyz> list = new ArrayList<Sjyz>();
		for (String[] strs : result) {
			Sjyz s = new Sjyz();
			s.setDabh(strs[0]);
			s.setLsh(strs[1]);
			s.setYwhc(strs[2]);
			s.setDhsl(strs[3]);
			s.setSpsl(strs[4]);
			s.setXmmc(strs[5]);
			s.setTime(strs[6] + "-" + strs[7] + "-" + strs[8]);
			list.add(s);
		}
		PagingBean pb= new PagingBean();
		pb.setCurrentPage(1);
		pb.setTotalRecords(list.size());
		JsonUtil.writeJsonGrid(response, list,pb);
	}

	public void getUsers(HttpServletRequest request,
			HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		String sql = "select dwmc,dwh from zdb_yhxx";
		BaseDataOP op = BaseDao.getBaseDataOP(dbName);
		String[][] result = op.queryRowAndCol(sql);
		List<ComboboxBean> list = new ArrayList();
		for (String[] strs : result) {
			ComboboxBean c = new ComboboxBean();
			c.setText(strs[0]);
			c.setValue(strs[1]);
			list.add(c);
		}
		JsonUtil.writeJsonObject(response, list);
	}
}
