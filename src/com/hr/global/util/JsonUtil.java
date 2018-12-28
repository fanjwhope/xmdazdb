package com.hr.global.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

import com.hr.util.Log;

public class JsonUtil {
	private static final ObjectMapper objectMapper=JsonUtil.getCustomedObjMapper();
	
	/**
	 * ��ȡ���ú�data��timestamp���л���ʽ��ObjectMapper
	 * @return
	 */
	public static ObjectMapper getCustomedObjMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		//����date��ʽ
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(dateFormat);
		//����timeStamp��ʽ
		SimpleModule simpleModule = new SimpleModule("huangfei", new Version(1,
				0, 0, null));
		simpleModule.addSerializer(Timestamp.class, new JsonUtil().new TimeStampSerializer());
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}
	
	/**
	 * ��java����ת����json�ַ������͸�ǰ̨
	 * @param response
	 * @param toJson ��ת����java����
	 */
	public static void writeJsonObject(HttpServletResponse response, Object toJson){
		response.setContentType("text/html;charset=utf-8");
		try {
			objectMapper.writeValue(response.getWriter(), toJson);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		} 
	}
	
	/**
	 * ��java����ת����json�ַ��������͸�ǰ̨�����ַ���Ϊjson��key:value��ʽ��ÿ��key������Ҫת���Ķ���ļ��������磺"employee.name"
	 * 
	 * @param response
	 * @param toJson ��ת����java����
	 */
	public static void writeRecWithClaName(HttpServletResponse response, Object toJson) throws Exception{
		String simplName = toJson.getClass().getSimpleName();
		simplName = simplName.substring(0, 2).toLowerCase() + simplName.substring(2, simplName.length());
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map=convert2Map(toJson, simplName);
		try {
			objectMapper.writeValue(response.getWriter(), map);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		} 
	}
	
	/**
	 * ��java����ת����json�ַ��������͸�ǰ̨�����ַ���Ϊjson��key:value��ʽ��ÿ��key�����и��ӵ��������磺"employee.name"
	 * @param response
	 * @param toJson ��ת����java����
	 * @param className ���ӵ�����
	 */
	public static void writeRecWithClaName(HttpServletResponse response, Object toJson, String className){
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map=convert2Map(toJson, className);
		try {
			objectMapper.writeValue(response.getWriter(), map);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		} 
	}
	
	/**
	 * ���͸�ǰ̨easyui��datagrid���������
	 * @param response
	 * @param toJson
	 * @param pagingBean
	 */
	public static void writeJsonGrid(HttpServletResponse response, Object toJson, PagingBean pagingBean){
		response.setContentType("text/html;charset=utf-8");
		StringBuffer json=new StringBuffer();
		json.append("{\"total\":").append(pagingBean.getTotalRecords()).append(",\"rows\":");
		try {
			json.append(objectMapper.writeValueAsString(toJson));
			json.append("}");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		} 
	}
	
	/**
	 * ��ǰ̨����json��Ϣ
	 * @param response
	 * @param success �ɹ����
	 * @param msg ��Ϣ�ľ�������
	 */
	public static void writeJsonMsg(HttpServletResponse response, boolean success, String msg){
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write("{\"success\":"+success+",\"msg\":\""+msg+"\"}");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		}
	}
	
	/**
	 * ��ǰ̨����json��Ϣ���ɸ���һ��������ǰ̨
	 * @param response
	 * @param success �ɹ����
	 * @param msg ��Ϣ�ľ�������
	 * @param param ���ظ�ǰ̨ʹ�õĲ���
	 */
	public static void writeJsonMsg(HttpServletResponse response, boolean success, String msg, String param){
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write("{\"success\":"+success+",\"msg\":\""+msg+"\",\"param\":\""+param+"\"}");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			Log.error(e);
		}
	}

	/**
	 * ����java�������ϵ�JsonIgnorePropertiesע����ѡ�񲿷����Լ���ֵȻ����ӵ�map��ͬʱ��������ǰ�������
	 * ָ�����ַ������ַ�"."��
	 * @param toJson
	 * @param name ����ӵ�������ǰ���ַ���
	 * @return ���Լ���ֵ��map
	 */
	private static Map<String,Object> convert2Map(Object toJson, String name) {
		List<String> ignoreProps=new ArrayList<String>();
		Map<String,Object> map=new HashMap<String, Object>();
		if(toJson.getClass().isAnnotationPresent(JsonIgnoreProperties.class)){
			JsonIgnoreProperties ann=toJson.getClass().getAnnotation(JsonIgnoreProperties.class);
			String[] ignStrs=ann.value();
			for(int i=0; ignStrs!=null&&i<ignStrs.length; i++){
				ignoreProps.add(ignStrs[i]);
			}
		}
		ignoreProps.add("class");
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(toJson.getClass());
		} catch (IntrospectionException e1) {
			Log.error(e1);
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd : propertyDescriptors){
			String propName=pd.getName();
			String[] ignStrs=ignoreProps.toArray(new String[0]);
			boolean iflag=false;
			for(int i=0; i<ignStrs.length; i++){
				if(propName.equals(ignStrs[i])){
					iflag=true;
				}
			}
			if(iflag){
				continue;
			}else{
				Object value=null;
				try {
					value = pd.getReadMethod().invoke(toJson);
				} catch (Exception e) {
					Log.error(e);
				} 
				map.put(name+"."+propName, value);
			}
		}
		return map;
	}
	
	/**
	 * Timestamp��json���л���ʽ
	 * @author �Ʒ�
	 *
	 */
	public class TimeStampSerializer extends JsonSerializer<Timestamp> {

		@Override
		public void serialize(Timestamp value, JsonGenerator jsonGenerator,
				SerializerProvider serializerProvider) throws IOException,
				JsonProcessingException {
			Date date = new Date(value.getTime());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String sdate = simpleDateFormat.format(date);
			jsonGenerator.writeString(sdate);
		}

	}
}
