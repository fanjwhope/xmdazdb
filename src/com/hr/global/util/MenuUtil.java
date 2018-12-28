package com.hr.global.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;

public class MenuUtil {
	public static final String serviceName="社会保障管理系统";
	
	/**
	 * 查询用户拥有的各权限到根权限的路径
	 * @param serviceId 服务项id
	 * @param userId 用户id
	 * @return 第一列：第三层权限id，第二列：第三层权限名称，第三列：第三层权限对应的页面路径，第四列：第三层权限排序序号
	 * 第二层和第一层的以此类推
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
	 * 查询当前服务的Id
	 * @return
	 */
	public static int queryServiceId(){
		String sql="select Service_ID from FUNCTIONINFO where name="+StringHelper.getFieldSql(serviceName);
		String strId=new BaseDataOP(ConnectionPool.getPool()).querySingleData(sql);
		return Validation.isInteger(strId)?Integer.parseInt(strId):-1;
	}
	
	/**
	 * 获取当前用户的根菜单
	 * @param userId
	 * @return
	 */
	public static List<MenuNode> getRootMenus(int userId){
		return getRootMenus(userId, true);
	}
	
	/**
	 * 获取当前用户的根菜单
	 * @param userId
	 * @param sorted 是否排序
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
				currentLevel++;//更新当前层级
			}
			
			currentIdIndex=currentIdIndex-4;//更新当前层级的主键所在索引
			
			if(!Validation.isEmpty(privsPath[i][currentIdIndex])){
				if(currentLevel==1){
					level1=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level1=getFromList(root, level1.getId(), level1);
					
				}else{
					level2=new MenuNode(privsPath[i][currentIdIndex], privsPath[i][currentIdIndex+1], privsPath[i][currentIdIndex+2], privsPath[i][currentIdIndex+3]);
					level2=getFromList(level1.getChildren(), level2.getId(), level2);
				}
				currentLevel++;//更新当前层级
			}
			
			currentIdIndex=currentIdIndex-4;//更新当前层级的主键所在索引
			
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
	 * 获取当前用户的第二层菜单
	 * @param userId 用户id
	 * @param levelOneId 第一层菜单id
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
	 * 通过id在List中查找元素，如果找到了id对应的元素则返回该元素，否则将toAdd元素加入到list，并返回新加入的元素
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
