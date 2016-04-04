package Test;

import static org.junit.Assert.*;
import model.User;

import org.junit.Test;

public class ModelTest {

	@Test
	public void testUser() {
		User user = new User("name@smt.com","pass");
		User user2 = new User("name@gmail.com","pss");
		assertEquals(user.getId(),"name@smt.com");
		assertEquals(user.getPass(),"pass");
		assertNotEquals(user,user2);
	}

}
