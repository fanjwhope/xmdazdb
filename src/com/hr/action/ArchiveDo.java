package com.hr.action;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ArchiveDepart;
import com.hr.bean.ScanningPage;
import com.hr.bean.User;
import com.hr.bean.XmdaFile;
import com.hr.bean.Xmwj;
import com.hr.bean.XmwjBak;
import com.hr.dao.ArchiveDao;
import com.hr.dao.BaseDao;
import com.hr.dao.FilePathInfo;
import com.hr.dao.XmwjBakDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.global.util.PdfToImage;
import com.hr.global.util.Validation;
import com.hr.info.dbBean;
import com.hr.kgj.MultiPageReadTif;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.Log;
import com.hr.util.Md5;
import com.hr.util.d;

public class ArchiveDo {

	public void delByXmnd(HttpServletRequest request,
			HttpServletResponse response) {
		String xmnd = request.getParameter("xmnd");
		String dbName = request.getParameter("zdbdbName");
		int nd = Integer.parseInt(xmnd);
		String dep = request.getParameter("department");
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		tableName = tableName.replace("Y100000000", dep);
		ArchiveDao dao = new ArchiveDao(tableName,dbName);
		try {
			dao.deleteByXmnd(nd);
			JsonUtil.writeJsonMsg(response, true, "ɾ���ɹ���");
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "ɾ��ʧ�ܣ�");
		}
	}

	/**
	 * ������ˮ�Ų�ѯ�ĵ���Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void getOne(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String lsh = request.getParameter("lsh");
		String tbname = request.getParameter("tbname");
		Xmwj xmwj;
		if (Validation.isEmpty(tbname)) {
			xmwj = archiveDao.getOne(lsh,dbName);
		} else {
			xmwj = archiveDao.getOne(lsh, tbname,dbName);
		}
		if (xmwj != null) {
			if (!xmwj.getDasryy().equals("0")) {
				xmwj.setLjDate(xmwj.getDasryy() + "-" + xmwj.getDasrmm() + "-"
						+ xmwj.getDasrdd());
			}
			if (!xmwj.getTbyy().equals("0")) {
				xmwj.setTbDate(xmwj.getTbyy() + "-" + xmwj.getTbmm() + "-"
						+ xmwj.getTbdd());
			}
			String[] arr = xmwj.getYcry().split("`");
			if (arr.length > 3) {
				xmwj.setLvjz(arr[0].equals("null") ? "" : arr[0]);
				xmwj.setRate_direct(arr[1].equals("null") ? "" : arr[1]);
				xmwj.setRate_float(arr[2].equals("null") ? "" : arr[2]);
				xmwj.setLv(arr[3].equals("null") ? "" : arr[3]);
			}else{
				xmwj.setLv(xmwj.getYcry());
			}
			if(xmwj.getDw()!=null&&!"null".equals(xmwj.getDw())&&!"".equals(xmwj.getDw()))
			xmwj.setDkje(xmwj.getDw()+":"+xmwj.getDkje()+"��");
			JsonUtil.writeJsonObject(response, xmwj);
		} else {
			JsonUtil.writeJsonObject(response, xmwj);
		}

	}

	/**
	 * ���ݽ���ͬ�Ų�ѯ�ĵ���Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void findByXmbh(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String xmbh = request.getParameter("xmbh");
		try {
			Xmwj xmwj = archiveDao.findByXmbh(xmbh,dbName);
			if (xmwj != null) {
				if (!xmwj.getDasryy().equals("0")) {
					xmwj.setLjDate(xmwj.getDasryy() + "-" + xmwj.getDasrmm()
							+ "-" + xmwj.getDasrdd());
				}
				if (!xmwj.getTbyy().equals("0")) {
					xmwj.setTbDate(xmwj.getTbyy() + "-" + xmwj.getTbmm() + "-"
							+ xmwj.getTbdd());
				}
				String[] arr = xmwj.getYcry().split("`");
				if (arr.length > 3) {
					xmwj.setLvjz(arr[0].equals("null") ? "" : arr[0]);
					xmwj.setRate_direct(arr[1].equals("null") ? "" : arr[1]);
					xmwj.setRate_float(arr[2].equals("null") ? "" : arr[2]);
					xmwj.setLv(arr[3].equals("null") ? "" : arr[3]);
				}else{
					xmwj.setLv(xmwj.getYcry());
				}
				if(xmwj.getDw()!=null&&!"null".equals(xmwj.getDw())&&!"".equals(xmwj.getDw()))
				xmwj.setDkje(xmwj.getDw()+":"+xmwj.getDkje()+"��");
				JsonUtil.writeJsonObject(response, xmwj);
			} else {
				JsonUtil.writeJsonObject(response, xmwj);
			}
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	/**
	 * ִ�н��嶯��
	 * 
	 * @param request
	 * @param response
	 */
	public void square(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		Xmwj xmwj = getObject(request);
		xmwj.setSquare("1");
		try {
			archiveDao.isSquare(xmwj);
			JsonUtil.writeJsonMsg(response, true, "����ɹ�");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "����ʧ��");
		}
	}

	/**
	 * ��ȡ��ˮ������һ����¼
	 * 
	 * @param request
	 * @param response
	 */
	public void getLast(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		Xmwj xmwj = archiveDao.getLast(dbName);
		if (xmwj != null) {
			if (!xmwj.getDasryy().equals("0")) {
				xmwj.setLjDate(xmwj.getDasryy() + "-" + xmwj.getDasrmm() + "-"
						+ xmwj.getDasrdd());
			}
			if (!xmwj.getTbyy().equals("0")) {
				xmwj.setTbDate(xmwj.getTbyy() + "-" + xmwj.getTbmm() + "-"
						+ xmwj.getTbdd());
			}
			String[] arr = xmwj.getYcry().split("`");
			if (arr.length > 3) {
				xmwj.setLvjz(arr[0].equals("null") ? "" : arr[0]);
				xmwj.setRate_direct(arr[1].equals("null") ? "" : arr[1]);
				xmwj.setRate_float(arr[2].equals("null") ? "" : arr[2]);
				xmwj.setLv(arr[3].equals("null") ? "" : arr[3]);
			}else{
				xmwj.setLv(xmwj.getYcry());
			}
			if(xmwj.getDw()!=null&&!"null".equals(xmwj.getDw())&&!"".equals(xmwj.getDw()))
			xmwj.setDkje(xmwj.getDw()+":"+xmwj.getDkje()+"��");
			JsonUtil.writeJsonObject(response, xmwj);
		} else {
			JsonUtil.writeJsonObject(response, xmwj);
		}

	}

	/**
	 * ��ȡ��һ����¼
	 * 
	 * @param request
	 * @param response
	 */
	public void getNext(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String lsh = request.getParameter("lsh");
		Xmwj xmwj = archiveDao.getNextLSH(lsh,dbName);
		if (xmwj != null) {
			if (!xmwj.getDasryy().equals("0")) {
				xmwj.setLjDate(xmwj.getDasryy() + "-" + xmwj.getDasrmm() + "-"
						+ xmwj.getDasrdd());
			}
			if (!xmwj.getTbyy().equals("0")) {
				xmwj.setTbDate(xmwj.getTbyy() + "-" + xmwj.getTbmm() + "-"
						+ xmwj.getTbdd());
			}
			String[] arr = xmwj.getYcry().split("`");
			if (arr.length > 3) {
				xmwj.setLvjz(arr[0].equals("null") ? "" : arr[0]);
				xmwj.setRate_direct(arr[1].equals("null") ? "" : arr[1]);
				xmwj.setRate_float(arr[2].equals("null") ? "" : arr[2]);
				xmwj.setLv(arr[3].equals("null") ? "" : arr[3]);
			}else{
				xmwj.setLv(xmwj.getYcry());
			}
			if(xmwj.getDw()!=null&&!"null".equals(xmwj.getDw())&&!"".equals(xmwj.getDw()))
			xmwj.setDkje(xmwj.getDw()+":"+xmwj.getDkje()+"��");
			JsonUtil.writeJsonObject(response, xmwj);
		} else {
			JsonUtil.writeJsonObject(response, xmwj);
		}

	}

	/**
	 * ��ȡ��һ����¼
	 * 
	 * @param request
	 * @param response
	 */
	public void getBack(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String lsh = request.getParameter("lsh");
		Xmwj xmwj = archiveDao.getBackLSH(lsh,dbName);
		if (xmwj != null) {
			if (!xmwj.getDasryy().equals("0")) {
				xmwj.setLjDate(xmwj.getDasryy() + "-" + xmwj.getDasrmm() + "-"
						+ xmwj.getDasrdd());
			}
			if (!xmwj.getTbyy().equals("0")) {
				xmwj.setTbDate(xmwj.getTbyy() + "-" + xmwj.getTbmm() + "-"
						+ xmwj.getTbdd());
			}
			String[] arr = xmwj.getYcry().split("`");
			if (arr.length > 3) {
				xmwj.setLvjz(arr[0].equals("null") ? "" : arr[0]);
				xmwj.setRate_direct(arr[1].equals("null") ? "" : arr[1]);
				xmwj.setRate_float(arr[2].equals("null") ? "" : arr[2]);
				xmwj.setLv(arr[3].equals("null") ? "" : arr[3]);
			}else{
				xmwj.setLv(xmwj.getYcry());
			}
			if(xmwj.getDw()!=null&&!"null".equals(xmwj.getDw())&&!"".equals(xmwj.getDw()))
			xmwj.setDkje(xmwj.getDw()+":"+xmwj.getDkje()+"��");
			JsonUtil.writeJsonObject(response, xmwj);
		} else {
			JsonUtil.writeJsonObject(response, xmwj);
		}

	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String BakTab = ArchiveUtil.getFullTableName(request, XmwjBak.class);// ���ݱ�
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		//XmwjBakDao xmwjBakDao = new XmwjBakDao(BakTab,dbName);// ����dao
		User user = ArchiveUtil.getCurrentUser(request.getSession());		
		String isZhuLu = request.getParameter("isZhuLu");
		String ljDate = request.getParameter("ljDate");
		String tbDate = request.getParameter("tbDate");
		Xmwj xmwj = getObject(request);
		if (!Validation.isEmpty(ljDate)) {
			String[] str = ljDate.split("-");
			xmwj.setDasryy(str[0]);
			xmwj.setDasrmm(str[1]);
			xmwj.setDasrdd(str[2]);
		} else {
			xmwj.setDasryy("0");
			xmwj.setDasrmm("0");
			xmwj.setDasrdd("0");
		}

		if (!Validation.isEmpty(tbDate)) {
			String[] str = tbDate.split("-");
			xmwj.setTbyy(str[0]);
			xmwj.setTbmm(str[1]);
			xmwj.setTbdd(str[2]);
		} else {
			xmwj.setTbyy("0");
			xmwj.setTbmm("0");
			xmwj.setTbdd("0");
		}
		if (Validation.isEmpty(xmwj.getLvjz())) {
			xmwj.setLvjz("null");
		}
		if (Validation.isEmpty(xmwj.getRate_direct())) {
			xmwj.setRate_direct("null");
		}
		if (Validation.isEmpty(xmwj.getRate_float())) {
			xmwj.setRate_float("null");
		}
		if (Validation.isEmpty(xmwj.getLv())) {
			xmwj.setLv("null");
		}
		xmwj.setYcry(xmwj.getLvjz() + "`" + xmwj.getRate_direct() + "`"
				+ xmwj.getRate_float() + "`" + xmwj.getLv());
		String as = String.valueOf(xmwj.getLsh());
		if ((!as.equals("null")) && (!isZhuLu.equals("1"))) {// �ж���������Ŀ�������޸ġ�isZhuLu���Ƿ���¼״̬
			String[] fieldIncludes = { "qymc", "dkqx", "xmfzr", "xmmc", "dkje",
					"dw", "xmnd", "xmbh", "num_Lsh", "xyjs", "ywhc", "bz",
					"by1", "ycry", "by2", "projectType", "ljr", "jcr",
					"dasryy", "dasrmm", "dasrdd", "tbyy", "tbmm", "tbdd",
					"ajsm" };
			try {
				XmwjBak xmwjBak = getXmwjBak(request);
				xmwjBak.setDasryy(xmwj.getDasryy());
				xmwjBak.setDasrmm(xmwj.getDasrmm());
				xmwjBak.setDasrdd(xmwj.getDasrdd());
				xmwjBak.setTbyy(xmwj.getTbyy());
				xmwjBak.setTbmm(xmwj.getTbmm());
				xmwjBak.setTbdd(xmwj.getTbdd());
				xmwjBak.setYcry(xmwj.getYcry());
				//xmwjBak.setTabindex(xmwjBakDao.getMaxInex(as));
				xmwjBak.setIp(ArchiveUtil.getIpAddr(request));
				xmwjBak.setModifyManDwh(user.getDwh());
				xmwjBak.setModifyManCname(user.getDwmc());
				xmwjBak.setCurModifyTime(Md5.date("yyyy-MM-dd HH:mm:ss"));
				xmwjBak.setLogAction("update");
				//xmwjBakDao.add(xmwjBak);
				
				xmwjBak.setDwh(user.getDwh());  // update 2018-03-28 
				
				archiveDao.update(xmwj, fieldIncludes, null);
				JsonUtil.writeJsonMsg(response, true, "��¼�ɹ�",
						String.valueOf(xmwj.getLsh()));
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "��¼ʧ��",
						String.valueOf(xmwj.getLsh()));
			}

		} else {
			String lsh = archiveDao.getMaxLSH();
			xmwj.setLsh(Long.parseLong(lsh));
			xmwj.setSquare("0");
			try {
				XmwjBak xmwjBak = getXmwjBak(request);
				int index = 1;
				if (!as.equals("null")) {
					//index = xmwjBakDao.getMaxInex(as);
				}
				xmwjBak.setDasryy(xmwj.getDasryy());
				xmwjBak.setDasrmm(xmwj.getDasrmm());
				xmwjBak.setDasrdd(xmwj.getDasrdd());
				xmwjBak.setTbyy(xmwj.getTbyy());
				xmwjBak.setTbmm(xmwj.getTbmm());
				xmwjBak.setTbdd(xmwj.getTbdd());
				xmwjBak.setYcry(xmwj.getYcry());
				xmwjBak.setTabindex(index);
				xmwjBak.setIp(ArchiveUtil.getIpAddr(request));
				xmwjBak.setModifyManDwh(user.getDwh());
				xmwjBak.setModifyManCname(user.getDwmc());
				xmwjBak.setCurModifyTime(Md5.date("yyyy-MM-dd HH:mm:ss"));
				xmwjBak.setLogAction("add");
				xmwjBak.setLsh(Long.parseLong(lsh));
				//xmwjBakDao.add(xmwjBak);
				
				xmwjBak.setDwh(user.getDwh());  // update 2018-03-28 
				xmwj.setDwh(user.getDwh());   // update 2018-03-28 
				archiveDao.add(xmwj);
				JsonUtil.writeJsonMsg(response, true, "��¼�ɹ�", lsh);
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "��¼ʧ��", lsh);
			}
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String ljDate = request.getParameter("ljDate");
		String tbDate = request.getParameter("tbDate");
		Xmwj xmwj = getObject(request);
		if (!Validation.isEmpty(ljDate)) {
			String[] str = ljDate.split("-");
			xmwj.setDasryy(str[0]);
			xmwj.setDasrmm(str[1]);
			xmwj.setDasrdd(str[2]);
		} else {
			xmwj.setDasryy("0");
			xmwj.setDasrmm("0");
			xmwj.setDasrdd("0");
		}

		if (!Validation.isEmpty(tbDate)) {
			String[] str = tbDate.split("-");
			xmwj.setTbyy(str[0]);
			xmwj.setTbmm(str[1]);
			xmwj.setTbdd(str[2]);
		} else {
			xmwj.setTbyy("0");
			xmwj.setTbmm("0");
			xmwj.setTbdd("0");
		}
		String[] fieldIncludes = { "qymc", "dkqx", "xmfzr", "xmmc", "dkje",
				"dw", "xmnd", "xmbh", "num_Lsh", "xyjs", "ywhc", "bz", "by1",
				"ycry", "by2", "projectType", "ljr", "jcr", "dasryy", "dasrmm",
				"dasrdd", "tbyy", "tbmm", "tbdd", "ajsm" };
		try {
			archiveDao.update(xmwj, fieldIncludes, null);
			JsonUtil.writeJsonMsg(response, true, "�޸ĳɹ�");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "�޸ĳɹ�");
		}
	}

	public void del(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String BakTab = ArchiveUtil.getFullTableName(request, XmwjBak.class);// ���ݱ�
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		XmwjBakDao xmwjBakDao = new XmwjBakDao(BakTab,dbName);// ����dao
		User user = ArchiveUtil.getCurrentUser(request.getSession());
		String lsh = request.getParameter("lsh");
		try {
			XmwjBak xmwjBak = archiveDao.getXmwjBakBylsh(lsh);
			xmwjBak.setTabindex(xmwjBakDao.getMaxInex(lsh));
			xmwjBak.setIp(ArchiveUtil.getIpAddr(request));
			xmwjBak.setModifyManDwh(user.getDwh());
			xmwjBak.setModifyManCname(user.getDwmc());
			xmwjBak.setCurModifyTime(Md5.date("yyyy-MM-dd HH:mm:ss"));
			xmwjBak.setLogAction("delete");
			xmwjBakDao.add(xmwjBak);
			archiveDao.deleteById(lsh);
			JsonUtil.writeJsonMsg(response, true, "ɾ���ɹ�");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "ɾ��ʧ��");
		}
	}

	/**
	 * ��ѯ����б���ʾ
	 * 
	 * @param request
	 * @param response
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		Xmwj xmwj = getObject(request);
		if (xmwj != null) {
			String s = xmwj.getSquare();
			if (s != null && s.equals("2")) {
				xmwj.setSquare("");
			}
		}
		PagingBean pagingBean = new PagingBean(request);
		List<Xmwj> list = archiveDao.getList(xmwj, pagingBean);
		JsonUtil.writeJsonGrid(response, list, pagingBean);
	}

	/**
	 * ��Ŀ�ƽ��б��ѯʱʹ��
	 * 
	 * @param request
	 * @param response
	 */
	public void getListToTransfer(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		Xmwj xmwj = getObject(request);
		try {		
				archiveDao.updateFlag(xmwj.getQymc(), xmwj.getXmbh());// ��ѯʱ������ѯ������Ϣ
																		// yjflag=S
			List<Xmwj> list = archiveDao.getListToTransfer(xmwj);
			JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	//����
	public void clear(HttpServletRequest request,
			HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		try {		
			BaseDataOP op=BaseDao.getBaseDataOP(dbName);
			String sql="update "+tableName +" set yjflag='' where yjflag='s'";
			op.ExecSql(sql);
			JsonUtil.writeJsonMsg(response, true, "���óɹ�");
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	/**
	 * ��Ŀ����Ƿ��Ѿ�����
	 * 
	 * @param request
	 * @param response
	 */
	public void isExistXmbh(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		String xmbh = request.getParameter("xmbh");
		try {
			boolean flag = archiveDao.isExistXmbh(xmbh);
			if (flag) {
				JsonUtil.writeJsonMsg(response, true, "", "1");
			} else {
				JsonUtil.writeJsonMsg(response, false, "", "0");
			}
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	/**
	 * isNotStand �Ƿ�Ǳ�׼ ��
	 * 
	 * @param request
	 * @param response
	 */
	public void isNotStand(HttpServletRequest request,
			HttpServletResponse response) {
		String lsh = request.getParameter("lsh");
		String tab = ArchiveUtil.getFullTableName(request, ArchiveDepart.class);
		String isNotStandSql = "select isnotstd from " + tab + " where lsh ='"
				+ lsh + "'";
		String[] arr = new BaseDataOP(null).queryRow(isNotStandSql);
		String isNotStand = "0";// ��׼
		if (arr != null && arr.length >= 1) {
			if ("1".equals(arr[0])) {
				isNotStand = "1";// �Ǳ�׼
			}
		}
		JsonUtil.writeJsonMsg(response, false, "", isNotStand);
	}

	/**
	 * �ĵ���ʾʱ���ж��ļ��Ƿ��Ѿ��ϴ�
	 * 
	 * @param request
	 * @param response
	 */
	public void getArchiveTypeIsUploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao(tableName,dbName);
		try {
			//
		} catch (Exception e) {
			Log.debug(e);
		}

	}

	/**
	 * ������ˮ��lsh�;�����archiveType�Ȳ����ļ��Ƿ���� �ļ����ڣ����ж��ļ��Ƿ�ת���� �ļ�ת��Ϊjpg��ȡ��Ӧ��jpg���û��Ķ�
	 * 
	 * @throws IOException
	 */
	public void getJpgToShow(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FilePathInfo fpi = new FilePathInfo();
		String lsh = request.getParameter("lsh");
		String archiveType = request.getParameter("archiveType");
		String dwh = request.getParameter("dwh");
		String dbName = request.getParameter("dbName");
		String zdbdbName = request.getParameter("zdbdbName");
		String xmbh = request.getParameter("xmbh");
		String page = request.getParameter("page");
		XmdaFile xmdaFile = fpi.getRealPath(lsh, archiveType, dwh, xmbh,
				dbName, page,zdbdbName);
		String path = xmdaFile.getFileName();
		// �������xmlrb��ȡ·��Ϊ�գ������spgl��ȡ
		if (path == null || "".equals(path)) {
			xmdaFile = fpi.getRealPath(lsh, "spgl", dwh, xmbh, dbName, page,zdbdbName);
			path = xmdaFile.getFileName();
		}
		String pdfPath = path;
		File pdf = new File(pdfPath);
		if (!pdf.exists()) {
			JsonUtil.writeJsonMsg(response, false, "0");
			return;
		}
		
		String dirPath = path.split("\\.")[0] + "\\";
		String jpgPath = dirPath + page + ".jpg";
		if (xmdaFile.getTotalPage() != null && xmdaFile.getTotalPage() != 0) {
			page = String.valueOf(xmdaFile.getCurrentPage());
		}
		File img = new File(jpgPath);
		if (!img.getParentFile().exists()) {
			img.getParentFile().mkdirs();
		}
		int pages = 0; // pdf��ҳ��
		MultiPageReadTif PdfToTif = new MultiPageReadTif();
		if ("tif".equals(pdfPath.split("\\.")[1])
				|| "tiff".equals(pdfPath.split("\\.")[1]))
			pages = PdfToTif.getPages(pdfPath);
		try {
			if ("pdf".equals(pdfPath.split("\\.")[1]))
				pages = PdfToImage.tranfer(pdfPath, jpgPath,
						Integer.parseInt(page) - 1, 1f, null)[0];
			else {
				PdfToTif.doitJAI(pdfPath, dirPath,jpgPath, Integer.parseInt(page));
			}

			try {
				BufferedImage src = ImageIO.read(img); // �����ļ�
				int width = src.getWidth(); // �õ�Դͼ��
				int height = src.getHeight(); // �õ�Դͼ��
				Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
				int screenHeight = (int)screensize.getHeight();
				if ( height>(screenHeight)) {			
					width = width * (screenHeight) / height;
					height = screenHeight;
					Image image = src.getScaledInstance(width, height,
							Image.SCALE_SMOOTH);
					BufferedImage tag = new BufferedImage(width, height,
							BufferedImage.TYPE_USHORT_565_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(image, 0, 0, null); // ������С���ͼ
					g.dispose();
					ImageIO.write(tag, "JPEG", img);// ������ļ���
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (xmdaFile.getTotalPage() != null && xmdaFile.getTotalPage() != 0) {
				pages = xmdaFile.getTotalPage();
			}
			
			ScanningPage scanning = new ScanningPage();
			scanning.setCurrentPage(Integer.toString(pages));
			scanning.setDirPath(dirPath.replace("\\", "\\\\"));
			scanning.setFiles(xmdaFile.getAllFileNames());
			JsonUtil.writeJsonObject(response, scanning);
			
			/*JsonUtil.writeJsonMsg(response, true, Integer.toString(pages),
					dirPath.replace("\\", "\\\\"));// ����ַ��ҳ�棬������"\"����Ҫ�ں�̨java��ǰ̨js�и�ת��һ�Σ�������
*/		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "pdf��tifת��ʧ��");
			return;
		}
	}

	private Xmwj getObject(HttpServletRequest request) {
		Xmwj xmwj = null;
		try {
			xmwj = (Xmwj) BeanHelper.getValuesByRequest(request, Xmwj.class,
					false);
		} catch (Exception e) {
			Log.error(e);
		}
		return xmwj;
	}

	private XmwjBak getXmwjBak(HttpServletRequest request) {
		XmwjBak xmwjBak = null;
		try {
			xmwjBak = (XmwjBak) BeanHelper.getValuesByRequest(request,
					XmwjBak.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return xmwjBak;
	}

}
