package com.hzq.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by hzq on 2017/5/16.
 */
public class HttpUtils {

	private static final Gson gson = new Gson();

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final OkHttpClient client = new OkHttpClient();

	public HttpUtils() {

	}

	public static String get(String url, Map<String, String> paramsMap) {
		String tempUrl = "";

		try {
			if(paramsMap != null) {
				String key;
				for(Iterator var3 = paramsMap.keySet().iterator(); var3.hasNext(); tempUrl = tempUrl + key + "=" + (String)paramsMap.get(key) + "&") {
					key = (String)var3.next();
				}

				int index = tempUrl.lastIndexOf("&");
				if(index != -1) {
					tempUrl = tempUrl.substring(0, index);
				}

				if(tempUrl != null) {
					tempUrl = "?" + tempUrl;
					url = url + tempUrl;
				}
			}

			Request request = (new Request.Builder()).url(url).addHeader("Accept", "text/html").addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;").get().build();
			Response response = client.newCall(request).execute();
			if(!response.isSuccessful()) {
				logger.info("------get error:\t" + response.message());
				return null;
			} else {
				logger.info("---------url:\t" + url);
				logger.info("---------get params:\t" + tempUrl);
				logger.info("---------response\t" + gson.toJson(response.body().toString()));
				return response.body().toString();
			}
		} catch (Throwable var5) {
			logger.info(var5.getMessage(), var5);
			return null;
		}
	}

	public static String post(String url, Map<String, String> paramsMap) {
		try {
			FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
			Iterator var3 = paramsMap.keySet().iterator();

			while(var3.hasNext()) {
				String key = (String)var3.next();
				formEncodingBuilder.add(key, (String)paramsMap.get(key));
			}

			RequestBody requestBody = formEncodingBuilder.build();
			Request request = (new Request.Builder()).url(url).addHeader("Accept", "application/json;charset=utf-8;").addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;").post(requestBody).build();
			Response response = client.newCall(request).execute();
			if(!response.isSuccessful()) {
				logger.info("------post error:\t" + response.message());
				return null;
			} else {
				logger.info("---------url:\t" + url);
				logger.info("---------request Body:\t" + gson.toJson(paramsMap));
				logger.info("---------response\t" + gson.toJson(response.body().toString()));
				return response.body().toString();
			}
		} catch (Throwable var6) {
			logger.info(var6.getMessage(), var6);
			return null;
		}
	}
}
