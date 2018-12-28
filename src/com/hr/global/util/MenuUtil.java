package com.hr.global.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;

public class MenuUtil {
	public static final String serviceName="��ᱣ�Ϲ���ϵͳ";
	
	/**
	 * ��ѯ�û�ӵ�еĸ�Ȩ�޵���Ȩ�޵�·��
	 * @param serviceId ������id
	 * @param userId �û�id
	 * @return ��һ�У�������Ȩ��id���ڶ��У�������Ȩ�����ƣ������У�������Ȩ�޶�Ӧ��ҳ��·���������У�������Ȩ���������
	 * �ڶ���͵�һ����Դ�����
	 */
	private static String[][] queryPrivsPath(int serviceId, int userId){
		String sql="select s3.id id3, s3.NAME name3, s3.node node3, s3.tabIndex tabIndex3, s2.id id2, s2.NAME name2, s2.node node2, s2.tabIndex tabIndex2, s1.id id1, s1.NAME name1, s1.node node1, s1.tabIndex tabIndex1 from sysinfo s3"
		+" left join sysinfo s2 on s3.SUPERIOR_ID=s2.ID"
		+" left join sysinfo s1 on s2.SUPERIOR_ID=s1.ID"
		+" where s3.id in (select id from sysinfo where service_id="+serviceId+" and id in(select item_id from grantInfo where id="+userId+"))";
		String[][] arr = new BaseDataOP(ConnectionPool.getPool()).queryRowAndCol(sql);
		return arr;
	}
	
	/**
	 * ��ѯ��ǰ�����Id
	 * @return
	 */
	public static int queryServiceId(){
		String sql="select Service_ID from FUNCTIONINFO where name="+StringHelper.getFieldSql(serviceName);
		String strId=new BaseDataOP(ConnectionPool.getPool()).querySingleData(sql);
		return Validation.isInteger(strId)?Integer.parseInt(strId):-1;
	}
	
	/**
	 * ��ȡ��ǰ�û��ĸ��˵�
	 * @param userId
	 * @return
	 */
	public static List<MenuNode> getRootMenus(int userId){
		return getRootMenus(userId, true);
	}
	
	/**
	 * ��ȡ��ǰ�û��ĸ��˵�
	 * @param userId
	 * @param sorted �Ƿ�����
	 * @return
	 */
	private static List<MenuNode> getRootMenus(int userId, boolean sorted){
		int serviceId=queryServiceId();
		String[][] privsPath=queryPrivsPath(serviceId, userId);
		List<MenuNode> root=new ArrayList<MenuNode>();
		for(int i=0; i<privsPath.length; i++){
			MenuNode level1=null;
			MenuNode level2=null;
			MenuNode level3=null;
			int currentLevel=1;
			int currentIdIndex=privsPath[i].length-4;
			if(!Validation.isEmpty(privsPath[i][currentIdIndex])){
				level1=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
				level1=getFromList(root, level1.getId(), level1);
				currentLevel++;//���µ�ǰ�㼶
			}
			
			currentIdIndex=currentIdIndex-4;//���µ�ǰ�㼶��������������
			
			if(!Validation.isEmpty(privsPath[i][currentIdIndex])){
				if(currentLevel==1){
					level1=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level1=getFromList(root, level1.getId(), level1);
					
				}else{
					level2=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level2=getFromList(level1.getChildren(), level2.getId(), level2);
				}
				currentLevel++;//���µ�ǰ�㼶
			}
			
			currentIdIndex=currentIdIndex-4;//���µ�ǰ�㼶��������������
			
			if(!Validation.isEmpty(privsPath[i][currentIdIndex])){
				if(currentLevel==1){
					level1=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level1=getFromList(root, level1.getId(), level1);
					
				}else if(currentLevel==2){
					level2=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level2=getFromList(level1.getChildren(), level2.getId(), level2);
				}else{
					level3=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level3=getFromList(level2.getChildren(), level3.getId(), level3);
				}
			}
		}
		
		if(sorted){
			Collections.sort(root, new MenuComparator());
		}
		return root;
	}
	
	/**
	 * ��ȡ��ǰ�û��ĵڶ���˵�
	 * @param userId �û�id
	 * @param levelOneId ��һ��˵�id
	 * @return
	 */
	public static List<MenuNode> getLevelTwoMenus(int userId, String levelOneId){
		List<MenuNode> list=new ArrayList<MenuNode>();
		MenuNode levelOneNode=getFromList(getRootMenus(userId, false), levelOneId, null);
		if(null!=levelOneNode){
			list=levelOneNode.getSortedChildren();
		}
		return list;
	}
	
	/**
	 * ͨ��id��List�в���Ԫ�أ�����ҵ���id��Ӧ��Ԫ���򷵻ظ�Ԫ�أ�����toAddԪ�ؼ��뵽list���������¼����Ԫ��
	 * @param list
	 * @param id
	 * @param toAdd
	 * @return
	 */
	public static MenuNode getFromList(List<MenuNode> list, String id, MenuNode toAdd){
		MenuNode menuNode=null;
		Iterator<MenuNode> iterator=list.iterator();
		while(iterator.hasNext()){
			MenuNode compare=iterator.next();
			if(compare.getId().equals(id)){
				menuNode=compare;
				break;
			}
		}
		if(null==menuNode&&null!=toAdd){
			menuNode=toAdd;
			list.add(toAdd);
		}
		return menuNode;
	}
	
//	public static void main(String[] args){
//		List<MenuNode> list=getRootMenus(10354);
//		System.out.println(list);
//	}
}
