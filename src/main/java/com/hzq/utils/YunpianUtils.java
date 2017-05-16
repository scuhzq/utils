package com.hzq.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzq on 2017/5/17.
 */
public class YunpianUtils {

	private static final String SMS_SIGGLE_SEND = "https://sms.yunpian.com/v2/sms/single_send.json";

	private static final String APIKEY = "1b821c2881c29b7267700edb9ef17e47";

	public YunpianUtils() {
	}

	public static String singleSend(String apikey, String text, String mobile) {
		Map<String, String> params = new HashMap();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return HttpUtils.post("https://sms.yunpian.com/v2/sms/single_send.json", params);
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap();
		map.put("key", "123");
		HttpUtils.get("http://www.baidu.com", map);
	}

}
