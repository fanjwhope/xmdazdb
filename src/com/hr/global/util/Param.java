package com.hr.global.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询条件帮助类
 * 
 * @author Administrator
 * 
 */
public class Param {
	private Map<String, String> equalMap;
	private Map<String, String> likeMap;
	private Map<String, String> dateMap;
	private Map<String, String> assistMap;

	public void putEqualMap(String key, String value) {
		if (null == equalMap) {
			equalMap = new HashMap<String, String>();
		}
		equalMap.put(key, value);
	}

	public void putLikeMap(String key, String value) {
		if (null == likeMap) {
			likeMap = new HashMap<String, String>();
		}
		likeMap.put(key, value);
	}

	public void putDateMap(String key, String value) {
		if (null == dateMap) {
			dateMap = new HashMap<String, String>();
			assistMap = new HashMap<String, String>();
		}
		if (key.endsWith("End")) {
			String key1 = key.substring(0, key.lastIndexOf("End"));
			assistMap.put(key1, key1);

		} else if (key.endsWith("Start")) {
			String key1 = key.substring(0, key.lastIndexOf("Start"));
			assistMap.put(key1, key1);
		}

		dateMap.put(key, value);
	}

	public String getSql() {
		StringBuffer sb = new StringBuffer(" ");
		if (equalMap != null && equalMap.size() > 0) {
			for (String key : equalMap.keySet()) {
				sb.append(" and ").append(key).append("='")
						.append(equalMap.get(key)).append("' ");
			}

		}

		if (likeMap != null && likeMap.size() > 0) {
			for (String key : likeMap.keySet()) {
				sb.append(" and ").append(key).append(" like '%")
						.append(likeMap.get(key)).append("%' ");
			}

		}

		if (assistMap != null && assistMap.size() > 0) {
			for (String key : assistMap.keySet()) {
				if (dateMap.get(key + "Start") != null) {
					sb.append(" and ").append(key).append(" >= '")
							.append(dateMap.get(key + "Start")).append("' ");
				}
				if (dateMap.get(key + "End") != null) {
					sb.append(" and ").append(key).append(" <= '")
							.append(dateMap.get(key + "End")).append("' ");
				}
			}

		}
		return sb.toString();

	}

}
