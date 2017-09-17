import org.junit.Test;

/**
 * Created by hzq on 2017/9/12.
 */
public class ReflectTest {

	@Test
	public void testClass(){
		String str = "hzq";
		String[] arr = new String[]{"hzq", "123"};
		printClassName(str);
		printClassName(arr);
		System.out.println(Integer.toBinaryString(0x00002000));
	}

	void printClassName(Object obj) {
        System.out.println("The class of " + obj + " is " + obj.getClass().getName());
    }

    @Test
	public void testClass2() throws IllegalAccessException, InstantiationException {
	    Class<String> clazz = String.class;
	    ClassLoader loader = clazz.getClassLoader();
	    System.out.println("debug===");
	    String str = clazz.newInstance();
	    System.out.println(str);
    }
}
