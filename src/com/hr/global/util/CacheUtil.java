package com.hr.global.util;

import java.io.Serializable;
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EHCache的管理类
 * 
 * @author 黄飞
 * 
 */
public final class CacheUtil {
	private static final CacheUtil instance = new CacheUtil();
	public static final String ONLINEUSER = "onlineUser";
	public static final String MESSAGE="message";
	private CacheManager cacheManager;

	private CacheUtil() {
		URL url = CacheUtil.class.getClassLoader().getResource(
				"ehcache.xml");
		this.cacheManager = new CacheManager(url);
	}

	public static final CacheUtil getInstance() {
		return instance;
	}

	/**
	 * 取出指定名称的缓存
	 * 
	 * @param cacheCode
	 *            缓存名称
	 * @return
	 */
	public Cache getCacheCodeName(String cacheCode) {
		return this.cacheManager.getCache(cacheCode);
	}

	/**
	 * 将数据存入到指定名称的缓存
	 * 
	 * @param cacheCode
	 *            缓存名称
	 * @param cacheKey
	 * @param cachedVal
	 */
	public void setDataCache(String cacheCode, String cacheKey,
			Serializable cachedVal) {
		Element el = new Element(cacheKey, cachedVal);
		getCacheCodeName(cacheCode).put(el);
	}

	/**
	 * 将数据存入到指定名称的缓存
	 * 
	 * @param cacheCode
	 *            缓存名称
	 * @param cacheKey
	 * @param cachedObj
	 */
	public void setDataCache(String cacheCode, String cacheKey, Object cachedObj) {
		Element el = new Element(cacheKey, cachedObj);
		getCacheCodeName(cacheCode).put(el);
	}

	/**
	 * 从指定名称的缓冲取出数据
	 * 
	 * @param cacheCode
	 *            缓存名称
	 * @param cacheKey
	 * @return
	 */
	public Object getObjectData(String cacheCode, String cacheKey) {
		Element element = getCacheCodeName(cacheCode).get(cacheKey);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 从指定名称的缓冲取出数据
	 * 
	 * @param cacheCode
	 *            缓存名称
	 * @param cacheKey
	 * @return
	 */
	public Object getSerializableData(String cacheCode, String cacheKey) {
		Element element = getCacheCodeName(cacheCode).get(cacheKey);
		return element == null ? null : element.getValue();
	}

	/**
	 * 将数据存入到指定的缓存
	 * 
	 * @param cache
	 *            缓存
	 * @param cacheKey
	 * @param cachedObj
	 */
	public void setDataCache(Cache cache, String cacheKey, Object cachedObj) {
		Element el = new Element(cacheKey, cachedObj);
		cache.put(el);
	}

	/**
	 * 将数据存入到指定的缓存
	 * 
	 * @param cache
	 *            缓存
	 * @param cacheKey
	 * @param cachedObj
	 */
	public void setDataCache(Cache cache, String cacheKey,
			Serializable cachedVal) {
		Element el = new Element(cacheKey, cachedVal);
		cache.put(el);
	}

	/**
	 * 从指定的缓冲取出数据
	 * 
	 * @param cache
	 *            缓存
	 * @param cacheKey
	 * @return
	 */
	public Object getObjectData(Cache cache, String cacheKey) {
		Element element = cache.get(cacheKey);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 从指定的缓冲取出数据
	 * 
	 * @param cache
	 *            缓存
	 * @param cacheKey
	 * @return
	 */
	public Object getSerializableData(Cache cache, String cacheKey) {
		Element element = cache.get(cacheKey);
		return element == null ? null : element.getValue();
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
