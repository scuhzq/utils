package com.hzq.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 2017/5/16.
 */
public class HttpUtils {

	private static final Gson gson = new Gson();

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final OkHttpClient client = new OkHttpClient();

	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

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
				return response.body().string();
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
			Request request = (new Request.Builder())
					.url(url)
					.addHeader("Accept", "application/json;charset=utf-8;")
					.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;")
					.post(requestBody).build();
			Response response = client.newCall(request).execute();
			if(!response.isSuccessful()) {
				logger.info("------post error:\t" + response.message());
				return null;
			} else {
				logger.info("---------url:\t" + url);
				logger.info("---------request Body:\t" + gson.toJson(paramsMap));
				return response.body().string();
			}
		} catch (Throwable t) {
			logger.info(t.getMessage(), t);
			return null;
		}
	}

	public static String postJson(String baseUrl, Map<String, String> paramsMap, String requestJson){

		try {
			String url = buildUrl(baseUrl, paramsMap);

			logger.info("okhttp url: " + url);
			logger.info("okhttp body: " + requestJson);

			Request request = new Request.Builder()
					.url(url)
					.post(RequestBody.create(MEDIA_TYPE_JSON, requestJson))
					.build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Throwable t){
			logger.info(t.getMessage(), t);
			return null;
		}
	}

	public static String buildUrl(String baseUrl, Map<String, String> paramsMap){
		String url;

		if(paramsMap == null || paramsMap.size() <= 0){
			return baseUrl;
		} else {
			url = baseUrl + "?";
		}

		List<String> keyList = new ArrayList<>(paramsMap.keySet());
		int i = 0;
		for(String key : keyList){
			String value = paramsMap.get(key);
			if(StringUtils.isBlank(value)){
				continue;
			}

			if(i > 0){
				url += "&";
			}

			url += key + "=" + paramsMap.get(key);

			i++;
		}

		return url;
	}
}
