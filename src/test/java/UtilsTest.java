import com.google.gson.*;
import com.hzq.domain.GetMsgBean;
import com.hzq.utils.HttpUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by hzq on 2017/5/16.
 */
public class UtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilsTest.class);

	/**
	 * 先与@Test注解的方法执行
	 */
	@Before
	public void setSystemProperty() {
		System.setProperty("env", "local");
	}

	/**
	 * 测试logback日志打印
	 */
	@Test
	public void testSlf4j() {
		Logger logger = LoggerFactory.getLogger(UtilsTest.class);
		logger.info("hello, slf4j info");
		logger.debug("hello, slf4j debug");
	}

	/**
	 * 测试通过反射将bean转成map，并自定义键。 可用于发送http请求参数封装。
	 */
	@Test
	public void testBean2Map() {
		GetMsgBean getMsgBean = new GetMsgBean();
		getMsgBean.setName("hzq");
		getMsgBean.setPwd("123");
		getMsgBean.setMoney(100000000L);
		Map<String, String> map = getMsgBean.getSortStringMap();
		Iterator var3 = map.keySet().iterator();

		while(var3.hasNext()) {
			String s = (String)var3.next();
			System.out.println(s + ":" + map.get(s));
		}

	}

	/**
	 * 测试的时候，可以根据不同的系统属性，来执行不同测试逻辑。local、dev、prod
	 */
	@Test
	public void testSystemProperty() {
		System.out.println(System.getProperty("env"));
	}

	/**
	 * 测试加载配置文件
	 */
	@Test
	public void testLoadroperties() {
		File file = new File("/Users/hzq/IdeaProjects/utils/src/main/resources/properties/test.properties");
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(file));
			System.out.println(properties.getProperty("name"));
			System.out.println(properties.getProperty("key"));
		} catch (Throwable var4) {
			logger.info(var4.getMessage(), var4);
		}

	}

	/**
	 * 测试Gson-- json<->Bean  jsonobject  jsonArray
	 */
	@Test
	public void testGson(){

		GetMsgBean getMsgBean = new GetMsgBean();
		getMsgBean.setMoney(1000000);
		getMsgBean.setAge(23L);
		getMsgBean.setName("test");

		Gson gson = new Gson();
		String s = gson.toJson(getMsgBean);
		System.out.println(s);

		GetMsgBean a = gson.fromJson(s, GetMsgBean.class);
		System.out.println(a);

		JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
		JsonObject resultObj = new JsonObject();
		for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
			System.out.println(entry.getKey());
			resultObj.add(entry.getKey() + "1", entry.getValue());
		}

		for(Map.Entry<String, JsonElement> entry : resultObj.entrySet()){
			System.out.println(entry.getKey());
		}


		String srr = "{\"systemId\":\"123\",\"uid\":\"uid\",\"role\":[1, 2, 4]}";
		JsonArray jsonArray = new JsonParser().parse(srr).getAsJsonObject().get("role").getAsJsonArray();
		for(JsonElement jsonElement : jsonArray){
			System.out.println(jsonElement.getAsInt());
		}

	}

	@Test
	public void testPostJson(){
		Map<String, String> map = new HashMap<>();
		map.put("timestamp", "1495787693000");
		map.put("sign", "7f25d903da96fc301d3f3cd64eb8206d");
		String json = "{\"systemIdList\": [\"jz1788c6e400874a4abc07f507bb97f531\"]}\n";
		System.out.println(HttpUtils.postJson("http://admin.jiangzuotong.com/api/room/getUidAndRole", map, json));
	}

	@Test
	public void test1(){
		System.out.println(new Date(1495788739000L));
	}

}
