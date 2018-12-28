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
	 * 以主键为条件构建表的update sql语句（不适用于复合主键）。
	 * @return
	 */
	public SqlWithPlaceHolder getUpdateSql(){
		String primaryKeyName=null;
		primaryKeyName = getPrimaryKeyName(entity.getClass());
		return getUpdateSql(null, new String[]{primaryKeyName});
	}
	
	/**
	 * 以主键为条件构建表的update sql语句（不适用于复合主键）。
	 * fieldIncludes与fieldExcludes二者均不为null时，以fieldIncludes为主；二者均为null时，表示操纵所有属性。
	 * @param fieldIncludes 需要包含的属性名
	 * @param fieldExcludes 需要排除的属性名
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
			throw new IllegalClassException("不是一个合法的实体类，没有找到任何属性："+entity.getClass().getName());
		}
		for(String key:keys){
			if(!key.equalsIgnoreCase(primaryKeyName)){//主键不参与更新
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
	 * 构建表的insert sql语句。
	 * @return
	 */
	public SqlWithPlaceHolder getInsertSql(){
		return getInsertSql(null,null);
	}
	
	/**
	 * 构建表的insert sql语句。
	 * fieldIncludes与fieldExcludes二者均不为null时，以fieldIncludes为主；二者均为null时，表示操纵所有属性。
	 * @param fieldIncludes 需要包含的属性名
	 * @param fieldExcludes 需要排除的属性名
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
			throw new IllegalClassException("不是一个合法的实体类，没有找到任何属性："+entity.getClass().getName());
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
	 * 获取实体对象待更新的属性名及其属性值。
	 * 该方法会排除所有带@Transient注解的get方法对应的属性。
	 * fieldIncludes与fieldExcludes二者均不为null时，以fieldIncludes为主；二者均为null时，表示操纵所有属性。
	 * @param fieldIncludes 需要包含的属性名
	 * @param fieldExcludes 需要排除的属性名
	 * @return 
	 * @throws IllegalArgumentException 参见java.lang.reflect.Method.invoke(Object obj, Object... args)方法抛出的异常
	 * @throws IllegalAccessException 参见java.lang.reflect.Method.invoke(Object obj, Object... args)方法抛出的异常
	 * @throws InvocationTargetException 参见java.lang.reflect.Method.invoke(Object obj, Object... args)方法抛出的异常
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
			if(null!=column&&!Validation.isEmpty(column.name())){//如果该get方法上面带有@Column注解，则返回@Column注解的name属性
				columnName=column.name();
			}
			
			if(!method.isAnnotationPresent(Transient.class)&&!key.equals("class")){//1.带@Transient注解的get方法对应属性不参与持久化。2.过滤所有JAVA类都含有的class属性
				
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
	 * 查找实体类对应的数据库表主键名称（不适用于复合主键）。该方法优先查找带有@Id注解的get方法对应的属性，如果任何get方法都没有带@Id注解，则查找该类是否拥有名为id的属性。
	 * @param entityClass
	 * @return
	 * @throws RuntimeException 如果该类的任何get方法都没带@Id注解，且该类无名为id的属性，则抛出该异常
	 */
	public static String getPrimaryKeyName(Class<?> entityClass) throws RuntimeException{
		BeanInfo beanInfo=null;
		boolean hasPropertyNamedId=false;//记录该类的所有属性中，是否存在名为id的属性
		try {
			beanInfo=Introspector.getBeanInfo(entityClass);
		} catch (IntrospectionException e) {
			Log.error(ExceptionUtil.getExceptionMessage(e));
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			Method method=propertyDescriptor.getReadMethod();
			if(method.isAnnotationPresent(Id.class)){//查找带有@Id注解的get方法
				javax.persistence.Column column=method.getAnnotation(javax.persistence.Column.class);
				if(null!=column&&!Validation.isEmpty(column.name())){//如果该get方法上面带有@Column注解，则返回@Column注解的name属性
					return column.name();
				}else{//否则返回该get方法对应的属性名
					return propertyDescriptor.getName();
				}
			}
			if(propertyDescriptor.getName().equalsIgnoreCase("id")){//查找属性名为id的方法(不考虑大小写)
				hasPropertyNamedId=true;
			}
		}
		
		if(!hasPropertyNamedId){
			throw new RuntimeException("没有找到该类有任何的主键："+entityClass);
		}
		return "id";
	}
	
	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName() {
		if(null!=this.tableName){
			return this.tableName;
		}
		return getTableName(this.entity.getClass());
	}
	
	/**
	 * 查找实体类对应的数据库表名称。
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
	 * 将Map映射成实体对象。通过Map的key与实体对象的属性名相同（不考虑大小写）来实现映射。
	 * @param valueMap Map的key须与实体对象的属性名相同（不考虑大小写），value的类型须与对象的属性类型一致
	 * @param classObj 实体对象类
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
				if(!key.equals("class")){//过滤所有JAVA类都含有的class属性
					method.invoke(object, value);
				}
			} catch (Exception e) {
				Log.error(ExceptionUtil.getExceptionMessage(e));
			}
		}
		return object;
	}
	
	/**
	 * 将列表中的每个Map映射成一个实体对象。通过Map的key与实体对象的属性名相同（不考虑大小写）来实现映射。
	 * @param listOfMap Map列表，其中每个 Map的key须与实体对象的属性名相同（不考虑大小写），value的类型须与对象的属性类型一致
	 * @param classObj 实体对象类
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
