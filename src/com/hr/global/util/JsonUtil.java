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
	 * 获取设置好data和timestamp序列化格式的ObjectMapper
	 * @return
	 */
	public static ObjectMapper getCustomedObjMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		//设置date格式
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(dateFormat);
		//设置timeStamp格式
		SimpleModule simpleModule = new SimpleModule("huangfei", new Version(1,
				0, 0, null));
		simpleModule.addSerializer(Timestamp.class, new JsonUtil().new TimeStampSerializer());
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}
	
	/**
	 * 将java对象转换成json字符串后发送给前台
	 * @param response
	 * @param toJson 待转换的java对象
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
	 * 将java对象转换成json字符串并发送给前台，此字符串为json的key:value形式，每个key均带有要转换的对象的简单类名，如："employee.name"
	 * 
	 * @param response
	 * @param toJson 待转换的java对象
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
	 * 将java对象转换成json字符串并发送给前台，此字符串为json的key:value形式，每个key均带有附加的类名，如："employee.name"
	 * @param response
	 * @param toJson 待转换的java对象
	 * @param className 附加的类名
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
	 * 发送给前台easyui的datagrid所需的数据
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
	 * 给前台发送json消息
	 * @param response
	 * @param success 成功与否
	 * @param msg 消息的具体内容
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
	 * 给前台发送json消息，可附带一个参数给前台
	 * @param response
	 * @param success 成功与否
	 * @param msg 消息的具体内容
	 * @param param 返回给前台使用的参数
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
	 * 根据java对象类上的JsonIgnoreProperties注释来选择部分属性及其值然后添加到map，同时给属性名前面加上了
	 * 指定的字符串和字符"."。
	 * @param toJson
	 * @param name 待添加到属相名前的字符串
	 * @return 属性及其值的map
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
	 * Timestamp的json序列化格式
	 * @author 黄飞
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
