package Test;

import static org.junit.Assert.*;
import model.DataBase;
import model.User;

import org.junit.Test;

public class DataBaseTest {
	private DataBase database;

	@Test
	public void testAddingUser() throws Exception {
		database = new DataBase();
		User user = new User("test@abv.bg","pass");
		database.addNewUserAndData(user, "");
		assertFalse("user must not be valid",database.isValudUser(user));
		database.setValidUser(user.getId());
		assertTrue("user must be valid",database.isValudUser(user));
		database.deleteUser(user);
		assertFalse("user must be deleted",database.isValudUser(user));
		
	}
	
	@Test
	public void testUserData() throws Exception {
		database = new DataBase();
		User user = new User("test@abv.bg","pass");
		String smd = "Some data string";
		database.addNewUserAndData(user, smd);
		database.setValidUser(user.getId());
		String result = database.getDataForUser(user);
		assertEquals(result, smd);
		database.deleteUser(user);
	}
	
	@Test
	public void testFreeName() throws Exception {
		database = new DataBase();
		//this id is not allowed by the ios app
		User user = new User("test","pass");
		assertTrue(database.isValudUserNameFree(user.getId()));
		
	}

}
