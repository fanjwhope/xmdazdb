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
 * ���������Զ���װBean
 * 
 * @author �Ʒ�
 * 
 */
public class BeanHelper {

	private final static String preffix = "set";
	static StringBuffer sb = new StringBuffer();

	/**
	 * 
	 * ��request�е�ֵ����Object����,Ҳ���ǰ�request�е�parameter��object���Զ�Ӧ��ʱ��
	 * ��object�е����Ը�ֵ,ʹ�ñ�׼��bean
	 * 
	 * @param requestObject
	 * @param target
	 *            Ŀ��bean
	 * @param showDebug
	 *            �ڿ���̨��ӡ����
	 * @return ��װ֮���bean
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
	 * @see ��request�е�ֵ����Object����,Ҳ���ǰ�request�е�parameter��object���Զ�Ӧ��ʱ��
	 *      ��object�е����Ը�ֵ,ʹ�ñ�׼��bean
	 * @param requestObject
	 * @param target
	 *            Ŀ��bean
	 * @param unSetProperties
	 *            �����ƵĶ���
	 * @param showDebug
	 *            �ڿ���̨��ӡ����
	 * @return ��װ֮���bean
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
	 * @see ��request�е�ֵ����Object����,Ҳ���ǰ�request�е�parameter��object���Զ�Ӧ��ʱ��
	 *      ��object�е����Ը�ֵ,ʹ�ñ�׼��bean
	 * @param requestObject
	 * @param target
	 *            Ŀ��bean
	 * @param unSetProperties
	 *            �����ƵĶ���
	 * @param showDebug
	 *            �ڿ���̨��ӡ����
	 * @return ��װ֮���bean
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
	 * ����ΰ�޸ģ�field.getType()��ȡ���������ֶ�ʱӦ���һ���ж�
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
		value=value.trim();//ȥ���ַ���ǰ��Ŀո�
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
			// ʵ���������
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

	// �ڶ�����չ������ʼ

	/**
	 * ������ת����Map
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @return
	 */
	public static Map<String, String> stringArray2Map(String[] keys,
			String[] values) {
		return stringArray2Map(keys, values, null);
	}
	
	/**
	 * ������ת����Map
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value  
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
	 * ������ת����List<Map>
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @return
	 */
	public static List<Map<String, String>> stringArray2List(String[] keys,
			String[][] values) {
		return stringArray2List(keys, values, null);
	}
	
	/**
	 * ������ת����List<Map>
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value         
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
	 * ������ת����Map
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> stringArray2Map(String keys,
			String[] values) {
		return stringArray2Map(keys, values, null);
	}
	
	/**
	 * ������ת����Map
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 *@param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value 
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
	 * ������ת����List<Map>
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> stringArray2List(String keys,
			String[][] values) {
		return stringArray2List(keys, values, null);
	}
	
	/**
	 * ������ת����List<Map>
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 *@param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value 
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
	 * �������װ�ɶ���
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String keys, String[] values,
			Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * �������װ�ɶ���
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value
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
	 * �������װ��List<T>
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String keys,
			String[][] values, Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * �������װ��List<T>
	 * 
	 * @param keys
	 *            ���Key(��key֮���ð�Ƕ��ŷָ�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value        
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
	 * �������װ�ɶ���
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @return
	 * @throws Exception
	 */
	public static <T> T stringArray2Object(String[] keys, String[] values,
			Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * �������װ�ɶ���
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value                 
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
	 * �������װ��List<T>
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> stringArray2Object(String[] keys,
			String[][] values, Class<T> classObj, boolean showDebug) {
		return stringArray2Object(keys, values, classObj, showDebug, null);
	}
	
	/**
	 * �������װ��List<T>
	 * 
	 * @param keys
	 *            ���Key(�Զ�ȥ��key��Я�������ݿ��������employee.name��ת����name)
	 * @param values
	 *            ��key��Ӧ��ֵ
	 * @param classObj
	 *            ����װ����
	 * @param showDebug
	 *            �Ƿ���ʾ������Ϣ
	 * @param keyChanges
	 *            ԭkeys����Ҫ�滻�Ĳ��ֺ���key����֮���ӳ�䡣ԭkey����ΪkeyChanges��key����key����ΪkeyChanges��value   
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

	// �ڶ�����չ��������
}