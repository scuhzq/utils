import org.apache.commons.io.FileUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 2017/5/31.
 */
public class FileUtilsTest {

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	private static DateTimeFormatter dateTimeFormatter2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Test
	public void testProcessExcel() throws IOException {

		File zFile = new File("/Users/hzq/Desktop/总收入.csv");
		File fFile = new File("/Users/hzq/Desktop/分销.csv");

		List<String> zList = FileUtils.readLines(zFile, "UTF-8");
		List<String> fList = FileUtils.readLines(fFile, "UTF-8");
		List<String> resultList = new ArrayList<>();

		Map<String, String> wishMap = new HashMap<>();
		for(String string : zList){
			String[] array = string.split("\t");
			String key = array[13];
			String values = array[0];
			wishMap.put(key, values);
		}

		for(String string : fList){
			String[] array = string.split("\t");
			if(array[1].equals("成交时间")){
				string += "\t" + "购买场景";
				resultList.add(string);
				continue;
			}

			if(dateTimeFormatter2.parseDateTime(array[1]).isBefore(dateTimeFormatter.parseDateTime("2017-06-01 00:00:00"))){
				continue;
			}

			String key = array[4];
			if(wishMap.containsKey(key)){
				string += "\t" + wishMap.get(key);
			} else {
				string += "\t" + "其他场景";
			}
			resultList.add(string);
		}

		FileUtils.writeLines(new File("/Users/hzq/Desktop/分销订单.csv"), resultList);

	}

}
