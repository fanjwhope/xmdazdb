package com.hr.global.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.IllegalClassException;
import org.apache.log4j.Logger;

public class BuildSql {
	
	private Object entity;
	private String tableName=null;
	private static Logger Log=Logger.getLogger(BuildSql.class);
	
	public BuildSql(Object entity){
		this.entity=entity;
	}
	
	public BuildSql(Object entity, String tableName){
		this.entity=entity;
		this.tableName=tableName;
	}
	
	/**
	 * ������Ϊ�����������update sql��䣨�������ڸ�����������
	 * @return
	 */
	public SqlWithPlaceHolder getUpdateSql(){
		String primaryKeyName=null;
		primaryKeyName = getPrimaryKeyName(entity.getClass());
		return getUpdateSql(null, new String[]{primaryKeyName});
	}
	
	/**
	 * ������Ϊ�����������update sql��䣨�������ڸ�����������
	 * fieldIncludes��fieldExcludes���߾���Ϊnullʱ����fieldIncludesΪ�������߾�Ϊnullʱ����ʾ�����������ԡ�
	 * @param fieldIncludes ��Ҫ������������
	 * @param fieldExcludes ��Ҫ�ų���������
	 * @return
	 */
	public SqlWithPlaceHolder getUpdateSql(String[] fieldIncludes, String[] fieldExcludes){
		Map<String, Object> map=null;
		String primaryKeyName=null;
		primaryKeyName = getPrimaryKeyName(entity.getClass());
		Object primaryKeyValue=null;
		try {
			map=getFieldValuesToModify(fieldIncludes, fieldExcludes);
			Map<String, Object> tempMap=getFieldValuesToModify(new String[]{primaryKeyName}, null);
			for(String str:tempMap.keySet()){
				primaryKeyValue=tempMap.get(str);
			}
		} catch (Exception e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		Set<String> keys= map.keySet();
		Object[] values=new Object[keys.size()+1];
		int i=0;
		StringBuffer fields=new StringBuffer();
		if(keys.isEmpty()){
			throw new IllegalClassException("����һ���Ϸ���ʵ���࣬û���ҵ��κ����ԣ�"+entity.getClass().getName());
		}
		for(String key:keys){
			if(!key.equalsIgnoreCase(primaryKeyName)){//�������������
				fields.append(key+"=?,");
				values[i++]=map.get(key);
			}
		}
		fields.deleteCharAt(fields.length()-1);
		String sql="update "+getTableName()+" set "+fields.toString()+" where "+primaryKeyName+"=?";
		values[i]=primaryKeyValue;
		return new SqlWithPlaceHolder(sql, values);
	}
	
	/**
	 * �������insert sql��䡣
	 * @return
	 */
	public SqlWithPlaceHolder getInsertSql(){
		return getInsertSql(null,null);
	}
	
	/**
	 * �������insert sql��䡣
	 * fieldIncludes��fieldExcludes���߾���Ϊnullʱ����fieldIncludesΪ�������߾�Ϊnullʱ����ʾ�����������ԡ�
	 * @param fieldIncludes ��Ҫ������������
	 * @param fieldExcludes ��Ҫ�ų���������
	 * @return
	 */
	public SqlWithPlaceHolder getInsertSql(String[] fieldIncludes, String[] fieldExcludes){
		Map<String, Object> map=null;
		try {
			map=getFieldValuesToModify(fieldIncludes, fieldExcludes);
		} catch (Exception e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		Set<String> keys= map.keySet();
		Object[] values=new Object[keys.size()];
		int i=0;
		StringBuffer fields=new StringBuffer();
		StringBuffer placeHolders=new StringBuffer();
		if(keys.isEmpty()){
			throw new IllegalClassException("����һ���Ϸ���ʵ���࣬û���ҵ��κ����ԣ�"+entity.getClass().getName());
		}
		for(String key:keys){
			fields.append(key);
			fields.append(",");
			placeHolders.append("?,");
			values[i++]=map.get(key);
		}
		fields.deleteCharAt(fields.length()-1);
		placeHolders.deleteCharAt(placeHolders.length()-1);
		String sql="insert into "+getTableName()+"("+fields.toString()+") values("+placeHolders.toString()+")";
		return new SqlWithPlaceHolder(sql, values);
	}
	
	/**
	 * ��ȡʵ���������µ���������������ֵ��
	 * �÷������ų����д�@Transientע���get������Ӧ�����ԡ�
	 * fieldIncludes��fieldExcludes���߾���Ϊnullʱ����fieldIncludesΪ�������߾�Ϊnullʱ����ʾ�����������ԡ�
	 * @param fieldIncludes ��Ҫ������������
	 * @param fieldExcludes ��Ҫ�ų���������
	 * @return 
	 * @throws IllegalArgumentException �μ�java.lang.reflect.Method.invoke(Object obj, Object... args)�����׳����쳣
	 * @throws IllegalAccessException �μ�java.lang.reflect.Method.invoke(Object obj, Object... args)�����׳����쳣
	 * @throws InvocationTargetException �μ�java.lang.reflect.Method.invoke(Object obj, Object... args)�����׳����쳣
	 */
	public Map<String, Object> getFieldValuesToModify(String[] fieldIncludes, String[] fieldExcludes) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		BeanInfo beanInfo=null;
		Map<String, Object> map=new HashMap<String, Object>();
		String key=null;
		try {
			beanInfo=Introspector.getBeanInfo(entity.getClass());
		} catch (IntrospectionException e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			Method method=propertyDescriptor.getReadMethod();
			key=propertyDescriptor.getName();
			
			String columnName=key;
			Column column=method.getAnnotation(Column.class);
			if(null!=column&&!Validation.isEmpty(column.name())){//�����get�����������@Columnע�⣬�򷵻�@Columnע���name����
				columnName=column.name();
			}
			
			if(!method.isAnnotationPresent(Transient.class)&&!key.equals("class")){//1.��@Transientע���get������Ӧ���Բ�����־û���2.��������JAVA�඼���е�class����
				
				if(null!=fieldIncludes){
					if(StringHelper.ifStringInArray(fieldIncludes, key, true)){
						map.put(columnName, method.invoke(entity));
					}
				}else if(null!=fieldExcludes){
					if(!StringHelper.ifStringInArray(fieldExcludes, key, true)){
						map.put(columnName, method.invoke(entity));
					}
				}else{
					map.put(columnName, method.invoke(entity));
				}
			}
		}
		return map;
	}
	
	/**
	 * ����ʵ�����Ӧ�����ݿ���������ƣ��������ڸ������������÷������Ȳ��Ҵ���@Idע���get������Ӧ�����ԣ�����κ�get������û�д�@Idע�⣬����Ҹ����Ƿ�ӵ����Ϊid�����ԡ�
	 * @param entityClass
	 * @return
	 * @throws RuntimeException ���������κ�get������û��@Idע�⣬�Ҹ�������Ϊid�����ԣ����׳����쳣
	 */
	public static String getPrimaryKeyName(Class<?> entityClass) throws RuntimeException{
		BeanInfo beanInfo=null;
		boolean hasPropertyNamedId=false;//��¼��������������У��Ƿ������Ϊid������
		try {
			beanInfo=Introspector.getBeanInfo(entityClass);
		} catch (IntrospectionException e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			Method method=propertyDescriptor.getReadMethod();
			if(method.isAnnotationPresent(Id.class)){//���Ҵ���@Idע���get����
				javax.persistence.Column column=method.getAnnotation(javax.persistence.Column.class);
				if(null!=column&&!Validation.isEmpty(column.name())){//�����get�����������@Columnע�⣬�򷵻�@Columnע���name����
					return column.name();
				}else{//���򷵻ظ�get������Ӧ��������
					return propertyDescriptor.getName();
				}
			}
			if(propertyDescriptor.getName().equalsIgnoreCase("id")){//����������Ϊid�ķ���(�����Ǵ�Сд)
				hasPropertyNamedId=true;
			}
		}
		
		if(!hasPropertyNamedId){
			throw new RuntimeException("û���ҵ��������κε�������"+entityClass);
		}
		return "id";
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getTableName() {
		if(null!=this.tableName){
			return this.tableName;
		}
		return getTableName(this.entity.getClass());
	}
	
	/**
	 * ����ʵ�����Ӧ�����ݿ�����ơ�
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(Class<?> entityClass) {
		String tableName = entityClass.getSimpleName();
		Table table = entityClass.getAnnotation(Table.class);
		if (null!=table&&!Validation.isEmpty(table.name())) {
			tableName = table.name();
		}
		return tableName.toUpperCase();
	}
	
	/**
	 * ��Mapӳ���ʵ�����ͨ��Map��key��ʵ��������������ͬ�������Ǵ�Сд����ʵ��ӳ�䡣
	 * @param valueMap Map��key����ʵ��������������ͬ�������Ǵ�Сд����value����������������������һ��
	 * @param classObj ʵ�������
	 * @return
	 */
	public static <L> L getObject(Map<String, Object> valueMap, Class<L> classObj){
		L object=null;
		try {
			object=classObj.newInstance();
		} catch (Exception e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		
		Map<String, Object> newValueMap=new HashMap<String, Object>();
		Set<String> keys=valueMap.keySet();
		for(String key:keys){
			newValueMap.put(key.toUpperCase(), valueMap.get(key));
		}
		
		BeanInfo beanInfo=null;
		try {
			beanInfo=Introspector.getBeanInfo(classObj);
		} catch (IntrospectionException e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		String key=null;
		Object value=null;
		Method method=null;
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			key=propertyDescriptor.getName();
			value=newValueMap.get(key.toUpperCase());
			method=propertyDescriptor.getWriteMethod();
			try {
				if(!key.equals("class")){//��������JAVA�඼���е�class����
					method.invoke(object, value);
				}
			} catch (Exception e) {
				Log.error(ExceptionUtil.getExceptionMessage(e));
			}
		}
		return object;
	}
	
	/**
	 * ���б��е�ÿ��Mapӳ���һ��ʵ�����ͨ��Map��key��ʵ��������������ͬ�������Ǵ�Сд����ʵ��ӳ�䡣
	 * @param listOfMap Map�б�����ÿ�� Map��key����ʵ��������������ͬ�������Ǵ�Сд����value����������������������һ��
	 * @param classObj ʵ�������
	 * @return
	 */
	public static <L> List<L> getObjectList(List<Map<String, Object>> listOfMap, Class<L> classObj){
		List<L> list=new ArrayList<L>(listOfMap.size());
		for(Map<String, Object> map:listOfMap){
			list.add(getObject(map, classObj));
		}
		return list;
	}
	
	public class SqlWithPlaceHolder{
		private String sql;
		private Object[] fieldValues;
		
		public SqlWithPlaceHolder(){
			
		}
		public SqlWithPlaceHolder(String sql, Object[] fieldValues) {
			super();
			this.sql = sql;
			this.fieldValues = fieldValues;
		}
		
		public String getSql() {
			return sql;
		}
		
		public Object[] getFieldValues() {
			return fieldValues;
		}
		
		public String[] getStringFieldValues(){
			String[] stringFieldValues=new String[fieldValues.length];
			for(int i=0; i<fieldValues.length; i++){
				stringFieldValues[i]=(null==fieldValues[i]?null:fieldValues[i].toString());
			}
			return stringFieldValues;
		}
	}
	
}
