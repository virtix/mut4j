package mu;

import org.junit.Test;


public class ExampleDBMutationTest {

	@Test
	public void testMyDAOMethod(){
		MyDAO dao = new MyDAO();
		dao = (MyDAO) DBMutProxy.createProxy(dao);
		dao.exec("some sql param");
		
	}
	
}
