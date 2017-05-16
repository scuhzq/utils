package com.hzq.domain;

/**
 * Created by hzq on 2017/5/16.
 */

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class Bean2Map {
	private static Logger logger = LoggerFactory.getLogger(Bean2Map.class);

	public Bean2Map() {
	}

	public Map<String, String> getSortStringMap() {
		Map<String, String> resultMap = new TreeMap();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Field[] var4 = fields;
		int var5 = fields.length;

		for(int var6 = 0; var6 < var5; ++var6) {
			Field field = var4[var6];
			field.setAccessible(true);

			try {
				XStreamAlias annotation = field.getAnnotation(XStreamAlias.class);
				Object value = field.get(this);
				if(value != null) {
					if(annotation != null) {
						resultMap.put(annotation.value(), value.toString());
					} else {
						resultMap.put(field.getName(), value.toString());
					}
				}
			} catch (Throwable var10) {
				logger.info(var10.getMessage(), var10);
			}
		}

		return resultMap;
	}
}