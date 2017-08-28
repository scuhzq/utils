import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzq on 2017/7/5.
 */
public class DocRunnable implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(DocRunnable.class);

	private static final OkHttpClient client = new OkHttpClient();

	String prefix = "http://static3.duobeiyun.com/swf/";
	String midfix = "/slide-";
	String suffix = ".swf";

	private int start;

	private int end;

	private List<String> list;

	public DocRunnable(int start, int end, List<String> list) {
		this.start = start;
		this.end = end;
		this.list = list;
	}

	@Override
	public void run() {

		List<String> errList = new ArrayList<>();

		File errDocFile = new File("errDoc" + Thread.currentThread().getName() + ".csv");

		if(end >= list.size()){
			end = list.size();
		}

		for(int lt = start; lt <= end; lt++){
			for(String string : list){
				String[] array = string.split("\t");
				String docId = array[0];
				int slideCount = Integer.valueOf(array[1]);
				for(int i = 1; i <= slideCount; i++){
					String temp = prefix + docId + midfix + i +suffix;
					try {
						Request request = (new Request.Builder()).url(temp).get().build();
						Response response = client.newCall(request).execute();
						if(response == null || response.code()!= 200){
							errList.add(string);
						}

						Thread.sleep(100);
					} catch (Throwable t){
						logger.info(t.getMessage(), t);
						errList.add(docId + "\t" + i + "\n");
						continue;
					}
				}

				logger.info("ok, " + string);
			}
		}

		System.out.println(list.size());
		if(list != null && list.size() > 0){
			try {
				FileUtils.writeLines(errDocFile, list);
			} catch (IOException e) {
				logger.info(e.getMessage(), e);
			}
		}
	}
}
