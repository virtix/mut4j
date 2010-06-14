package edu.gmu.mut4j;

import static edu.gmu.mut4j.Mut4JAssertions.*;
import java.util.HashMap;
import org.junit.Test;


public class ExampleDBMutationTest {

	@Test
	public void testMyDAOMethod(){
		MyDAO dao = new MyDAO();
		dao = (MyDAO) DBMutProxy.createProxy(dao);
		//Execute some method with a JDBC call in the object under test and grab the results
		HashMap results = dao.exec("some sql param");
		assertMutantKilled( results );
		
	}
	
}
