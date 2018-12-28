package com.hr.global.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hr.util.Log;

/**
 * 从请求中自动封装Bean
 * 
 * @author 黄飞
 * 
 */
public class BeanHelper {

	private final static String preffix = "set";
	static StringBuffer sb = new StringBuffer();

	/**
	 * 
	 * 把request中的值赋给Object对象,也就是把request中的parameter和object属性对应的时候
	 * 把object中的属性赋值,使用标准的bean
	 * 
	 * @param requestObject
	 * @param target
	 *            目标bean
	 * @param showDebug
	 *            在控制台打印参数
	 * @return 封装之后的bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValuesByRequest(HttpServletRequest request,
			Class<T> classObj, boolean showDebug) throws Exception {
		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> valueMap = getValuesMap(request, keys);
		T object = getObject(valueMap, classObj, null, null);
		// debug show
		if (showDebug) {
			showDebug(valueMap);
			sb = new StringBuffer();
		}
		return object;
	}

	/**
	 * 
	 * @see 把request中的值赋给Object对象,也就是把request中的parameter和object属性对应的时候
	 *      把object中的属性赋值,使用标准的bean
	 * @param requestObject
	 * @param target
	 *            目标bean
	 * @param unSetProperties
	 *            不复制的对象
	 * @param showDebug
	 *            在控制台打印参数
	 * @return 封装之后的bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValuesByRequest(HttpServletRequest request,
			Class<T> classObj, boolean showDebug, String[] unSetProperties)
			throws Exception {
		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> valueMap = getValuesMap(request, keys);
		T object = getObject(valueMap, classObj, unSetProperties, null);

		// debug
		if (showDebug) {
			showDebug(valueMap);
			sb = new StringBuffer();
		}
		return object;
	}

	/**
	 * 
	 * @see 把request中的值赋给Object对象,也就是把request中的parameter和object属性对应的时候
	 *      把object中的属性赋值,使用标准的bean
	 * @param requestObject
	 * @param target
	 *            目标bean
	 * @param unSetProperties
	 *            不复制的对象
	 * @param showDebug
	 *            在控制台打印参数
	 * @return 封装之后的bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValuesByRequest(HttpServletRequest request,
			Class<T> classObj, boolean showDebug, String[] unSetProperties,
			Map<String, String> proertiesMap) throws Exception {
		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> valueMap = getValuesMap(request, keys);
		T object = getObject(valueMap, classObj, unSetProperties, proertiesMap);

		// debug
		if (showDebug) {
			showDebug(valueMap);
			sb = new StringBuffer();
		}
		return object;
	}

	protected static Map<String, String> getValuesMap(
			HttpServletRequest request, Enumeration<String> keys)
			throws Exception {
		if (keys == null)
			return null;
		Map<String, String> value = new HashMap<String, String>();
		while (keys.hasMoreElements()) {
			String element = keys.nextElement();
			value.put(element, request.getParameter(element));
		}
		return value;
	}

	protected static <T> T getObject(Map<String, String> valueMap,
			Class<T> classObj, String[] unSetProperties,
			Map<String, String> proertiesMap) {
		T object = null;
		try {
			object = classObj.newInstance();
		} catch (InstantiationException e) {
			Log.error(e);
		} catch (IllegalAccessException e) {
			Log.error(e);
		}
		List<String> unSet = null;
		if (unSetProperties != null)
			unSet = java.util.Arrays.asList(unSetProperties);
		if (valueMap == null)
			return null;
		for (Iterator<String> iter = valueMap.keySet().iterator(); iter
				.hasNext();) {
			String key = iter.next();
			if (unSet != null && unSet.contains(key)) {
				continue;
			}
			String methodValue = valueMap.get(key);
			if (proertiesMap != null && proertiesMap.containsKey(key)) {
				methodValue = proertiesMap.get(key);
			}
			if (methodValue != null)
				try {
					setValue(object, key, methodValue);
				} catch (Exception e) {
					Log.error(e);
				}

		}
		return object;
	}
	/**
	 * 
	 * @param obj
	 * @param name
	 * @param value
	 * @throws Exception
	 * 兰中伟修改，field.getType()获取基本类型字段时应多加一个判断
	 * 			field.getType() == Short.class ||field.getType()==short.class
	 */
	protected static void setValue(Object obj, String name, String value)
			throws Exception {
		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(name);
		} catch (Exception e) {
			return;
		}
		if (field == null) {
			return;
		}
		value=value.trim();//去除字符串前后的空格
		Method method = obj.getClass().getDeclaredMethod(
				getSetMethodName(name), field.getType());
		if ((field.getType() == Short.class ||field.getType()==short.class)&& Validation.isShort(value)) {
			method.invoke(obj, Short.parseShort(value));
		} else if ((field.getType() == Integer.class||field.getType()==int.class)
				&& Validation.isInteger(value)) {
			method.invoke(obj, Integer.parseInt(value));
		} else if ((field.getType() == Long.class ||field.getType()==long.class)&& Validation.isLong(value)) {
			method.invoke(obj, Long.parseLong(value));
		} else if ((field.getType() == Double.class||field.getType()==double.class)
				&& Validation.isDouble(value)) {
			method.invoke(obj, Double.parseDouble(value));
		} else if ((field.getType() == Float.class||field.getType()==float.class) && Validation.isDouble(value)) {
			method.invoke(obj, Float.parseFloat(value));
		} else if (field.getType() == Date.class && Validation.isDate(value)) {
			method.invoke(obj, DateFunc.strToDate(value, "yyyy-MM-dd"));
		} else if ((field.getType() == Boolean.class||field.getType()==boolean.class)
				&& Validation.isBoolean(value)) {
			method.invoke(obj, Boolean.parseBoolean(value));
		} else if (field.getType() == String.class) {
			method.invoke(obj, value);
		} else {
			// 实现其他情况
		}
		sb.append(name).append(" - ");
	}

	protected static String getSetMethodName(String methodName) {
		String first = methodName.substring(0, 1);
		String suffex = methodName.substring(1);
		return (preffix + first.toUpperCase() + suffex).toString();
	}

	public static void showDebug(Map<String, String> valueMap) {
		for (Iterator<String> iter = valueMap.keySet().iterator(); iter
				.hasNext();) {
			String key = iter.next();
			System.out.println("paramterName : " + key + "  value: "
					+ valueMap.get(key));
		}
		System.out.println("theSetValueProperties: " + sb.toString());
	}

	// 第二次扩展方法开始

	/**
	 * 将数组转换成Map
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @return
	 */
	public static Map<String, String> stringArray2Map(String[] keys,
			String[] values) {
		return stringArray2Map(keys, values, null);
	}
	
	/**
	 * 将数组转换成Map
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value  
	 * @return
	 */
	public static Map<String, String> stringArray2Map(String[] keys,
			String[] values, Map<String, String> keyChanges) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null || values == null || keys.length != values.length) {
			return map;
		}
		for (int i = 0; i < keys.length; i++) {
			int index = keys[i].indexOf(".");
			String target=keys[i];
			if (index > -1) {
				keys[i] = keys[i].substring(index + 1);
			}
			String key=keys[i];
			if(null!=keyChanges){
				String replacement=keyChanges.get(target);
				if(!Validation.isEmpty(replacement)){
					key=replacement;
				}
			}
			map.put(key, values[i]);
		}
		return map;
	}

	/**
	 * 将数组转换成List<Map>
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @return
	 */
	public static List<Map<String, String>> stringArray2List(String[] keys,
			String[][] values) {
		return stringArray2List(keys, values, null);
	}
	
	/**
	 * 将数组转换成List<Map>
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value         
	 * @return
	 */
	public static List<Map<String, String>> stringArray2List(String[] keys,
			String[][] values, Map<String, String> keyChanges) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (values == null) {
			return list;
		}
		for (int i = 0; i < values.length; i++) {
			Map<String, String> map = stringArray2Map(keys, values[i], keyChanges);
			list.add(map);
		}
		return list;
	}

	/**
	 * 将数组转换成Map
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> stringArray2Map(String keys,
			String[] values) {
		return stringArray2Map(keys, values, null);
	}
	
	/**
	 * 将数组转换成Map
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 *@param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> stringArray2Map(String keys,
			String[] values, Map<String, String> keyChanges) {
		if (keys == null) {
			return new HashMap<String, String>();
		}
		String[] keyArr = keys.split("\\s*,\\s*");
		return stringArray2Map(keyArr, values, keyChanges);
	}

	/**
	 * 将数组转换成List<Map>
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> stringArray2List(String keys,
			String[][] values) {
		return stringArray2List(keys, values, null);
	}
	
	/**
	 * 将数组转换成List<Map>
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 *@param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value 
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> stringArray2List(String keys,
			String[][] values, Map<String, String> keyChanges) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (values == null) {
			return list;
		}
		for (int i = 0; i < values.length; i++) {
			Map<String, String> map = stringArray2Map(keys, values[i], keyChanges);
			list.add(map);
		}
		return list;
	}

	/**
	 * 将数组封装成对象
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String keys, String[] values,
			Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * 将数组封装成对象
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String keys, String[] values,
			Class<T> classObj, boolean showDebug, Map<String, String> keyChanges) {
		Map<String, String> map = stringArray2Map(keys, values, keyChanges);
		T object = getObject(map, classObj, null, null);
		// debug show
		if (showDebug) {
			showDebug(map);
			sb = new StringBuffer();
		}
		return object;
	}

	/**
	 * 将数组封装成List<T>
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String keys,
			String[][] values, Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * 将数组封装成List<T>
	 * 
	 * @param keys
	 *            存放Key(各key之间用半角逗号分割。自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value        
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String keys,
			String[][] values, Class<T> classObj, boolean showDebug, Map<String, String> keyChanges) {
		List<T> list = new ArrayList<T>();
		if (values == null) {
			return list;
		}
		for (int i = 0; i < values.length; i++) {
			T object = stringArray2Object(keys, values[i], classObj, showDebug, keyChanges);
			list.add(object);
		}
		return list;
	}

	/**
	 * 将数组封装成对象
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String[] keys, String[] values,
			Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * 将数组封装成对象
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value                 
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String[] keys, String[] values,
			Class<T> classObj, boolean showDebug, Map<String, String> keyChanges) {
		Map<String, String> map = stringArray2Map(keys, values, keyChanges);
		T object = getObject(map, classObj, null, null);
		// debug show
		if (showDebug) {
			showDebug(map);
			sb = new StringBuffer();
		}
		return object;
	}

	/**
	 * 将数组封装成List<T>
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String[] keys,
			String[][] values, Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * 将数组封装成List<T>
	 * 
	 * @param keys
	 *            存放Key(自动去除key上携带的数据库表名，如employee.name将转换成name)
	 * @param values
	 *            各key对应的值
	 * @param classObj
	 *            欲封装的类
	 * @param showDebug
	 *            是否显示调试信息
	 * @param keyChanges
	 *            原keys中需要替换的部分和新key名称之间的映射。原key名称为keyChanges的key，新key名称为keyChanges的value   
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String[] keys,
			String[][] values, Class<T> classObj, boolean showDebug, Map<String, String> keyChanges) {
		List<T> list = new ArrayList<T>();
		if (values == null) {
			return list;
		}
		for (int i = 0; i < values.length; i++) {
			T object = stringArray2Object(keys, values[i], classObj, showDebug, keyChanges);
			list.add(object);
		}
		return list;
	}

	// 第二次扩展方法结束
}