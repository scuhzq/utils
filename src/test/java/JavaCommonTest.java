import org.junit.Test;

/**
 * Created by hzq on 2017/9/15.
 */
public class JavaCommonTest {

	@Test
	public void testVar(){
		byte byteVar = 127;//方法中声明的的基本类型存储在方法栈中。
		short shortVar = 255;
		int intVar = 500;
		long  longVar = 1000;
		float floatVar = 9.999f;
		double doubleVar = 19.999d;
		char charVar = 'c';

		char[] charArr = new char[]{'a', 'b'};
		String strNew = new String("new string");//这里会创建两个对象
		String staString = "new string";//这里将不创建对象。

		System.out.println("debug---");
	}

	@Test
	public void testArr(){
		int[] array = new int[10];
		int length = array.length;//数组的length属性，是编译器编译时动态添加的。
		System.out.println(length);
		System.out.println("debug---");
	}

}
