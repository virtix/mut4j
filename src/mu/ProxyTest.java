package mu;

import org.junit.Test;


public class ProxyTest {
	@Test
	public void testProxy(){
		MyDAO cut = new MyDAO();
		DBMutProxy dbmut = new DBMutProxy(cut.getClass());
		//String s = dbmut.exec("hey hey");
		//System.out.println(s);
	}
	
}
