package db;
/**
 * JUnit tests for DbConnect
 * @author Charlie Cox
 * @version 21/03/2018
 */
import static org.junit.Assert.assertEquals;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
/**
 * At the time of writing, these tests all passed and since then the database has altered.
 * @author chc578
 *
 */
public class DbConnectTest {
	private DbConnect con;
	@Rule public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		try {
			this.con = new DbConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterUser() {
		boolean expected1 = true;
		boolean expected2 = false;

		boolean actual1 = con.registerUser("doggo12345", "doggo12345");
		boolean actual2 = con.registerUser("doggo1234", "hi");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testCheckUsernameExists() {
		boolean expected1 = true;
		boolean expected2 = false;

		boolean actual1 = con.checkUsernameExists("ch4rlie");
		boolean actual2 = con.checkUsernameExists("do");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testCheckUsernamePassword() {
		boolean expected1 = true;
		boolean expected2 = false;

		boolean actual1 = con.checkUsernamePassword("doggo12345");
		boolean actual2 = con.checkUsernamePassword("Iamnotinthedatabase");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testCheckLogin() {
		boolean expected1 = true;
		boolean expected2 = false;

		boolean actual1 = con.checkLogin("ch4rlie", "1234a");
		boolean actual2 = con.checkLogin("doggo12345", "doggo12");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testRetrieveHighScore() {
		int expected1 = 1;

		int actual1 = con.retrieveHighScore("ch4rlie");

		assertEquals(expected1, actual1);
	}

	@Test
	public void testRetrieveAllHighscores() {
		HashMap<String, Integer> expected1 = new HashMap();

		expected1.put("ass", 0);
		expected1.put("aaa", 0);
		expected1.put("teste", 0);
		expected1.put("Gui23", 0);
		expected1.put("fgh", 0);
		expected1.put("Hellloooo", 0);
		expected1.put("ben", 0);
		expected1.put("fffd", 0);
		expected1.put("George", 0);
		expected1.put("doggo1234", 0);
		expected1.put("gdfgdf", 0);
		expected1.put("Gui2", 0);
		expected1.put("ch4rlie", 1);
		expected1.put("dog", 0);
		expected1.put("tomo", 0);
		expected1.put("user", 0);
		expected1.put("weaverfever", 0);
		expected1.put("HelloGui", 0);
		expected1.put("ch4rli", 0);
		expected1.put("HelloSparta", 0);
		expected1.put("HelloSparta2", 0);
		expected1.put("rachel", 0);
		expected1.put("me", 0);
		expected1.put("fff", 0);
		expected1.put("cc", 0);
		expected1.put("12345", 0);
		expected1.put("asdfgh", 0);
		expected1.put("charlie", 0);
		expected1.put("asdsad", 0);
		expected1.put("madting", 0);
		expected1.put("sdfsdfsd", 0);
		expected1.put("fds", 0);
		expected1.put("h", 0);
		expected1.put("GHGH", 0);
		expected1.put("ffff", 0);
		expected1.put("fgfg", 0);
		expected1.put("f", 0);
		expected1.put("fd", 0);
		expected1.put("Rob", 0);
		expected1.put("rob2", 0);
		expected1.put("robert1234", 0);
		expected1.put("1+1=2", 0);
		expected1.put("doggo123", 0);
		expected1.put("aards", 0);
		expected1.put("zanal", 0);
		expected1.put("g", 0);
		expected1.put("doggo", 0);

		HashMap<String, Integer> actual1 = con.retrieveAllHighscores();

		assertEquals(expected1, actual1);
	}

	@Test
	public void testCheckNotNull() {
		boolean expected1 = false;
		boolean expected2 = false;
		boolean expected3 = false;
		boolean expected4 = false;

		boolean actual1 = con.checkLogin(null, null);
		boolean actual2 = con.checkLogin(null, "doggo12");
		boolean actual3 = con.checkLogin("hi", null);
		boolean actual4 = con.checkLogin("hi", "doggo12");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
	}

	@Test
	public void testCheckPassword() {
		boolean expected1 = false;
		boolean expected2 = false;
		boolean expected3 = false;
		boolean expected4 = false;
		boolean expected5 = true;

		boolean actual1 = con.checkPassword("");
		boolean actual2 = con.checkPassword("123456");
		boolean actual3 = con.checkPassword("asdfgh");
		boolean actual4 = con.checkPassword("1a2s3d");
		boolean actual5 = con.checkPassword("1A2s3d");

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);//fails
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
		assertEquals(expected5, actual5);
	}

	@Test
	public void testCheckHighScore() {
		boolean expected1 = false;
		boolean expected2 = false;
		boolean expected3 = true;
		
		boolean actual1 = con.checkHighScore("ch4rlie", 0);
		boolean actual2 = con.checkHighScore("ch4rlie", 1);
		boolean actual3 = con.checkHighScore("ch4rlie", 2);
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
	}

	@Test
	public void testCheckConfirmPassword() {
		boolean expected1 = false;
		boolean expected2 = true;
		
		boolean actual1 = con.checkConfirmPassword("1234", "1234a");
		boolean actual2 = con.checkConfirmPassword("1234a", "1234a");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testGetFirstPlace() {
		String expected = "ch4rlie";
		String actual = con.getFirstPlace();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSecondPlace() {
		String expected = "dog";
		String actual = con.getSecondPlace();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetThirdPlace() {
		String expected = "tomo";
		String actual = con.getThirdPlace();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetNumberGamesPlayed() {
		int expected = 1;
		
		int actual = con.getNumberGamesPlayed("ch4rlie");
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetNumberWins() {
		int expected = 0;
		
		int actual = con.getNumberWins("ch4rlie");
		
		assertEquals(expected, actual);
	}


	@Test
	public void testRetrieveAllFollowingUsernames() {
		ArrayList<String> expected1 = new ArrayList<String>(Arrays.asList("ben"));
		ArrayList<String> expected2 = new ArrayList<String>(Arrays.asList());
		
		ArrayList<String> actual1 = con.retrieveAllFollowingUsernames("ch4rlie");
		ArrayList<String> actual2 = con.retrieveAllFollowingUsernames("doggo");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void testRetrieveFollowingUser() {
		boolean expected1 = true;
		boolean expected2 = false;
		
		boolean actual1 = con.retrieveFollowingUser("ch4rlie", "ben");
		boolean actual2 = con.retrieveFollowingUser("ch4rlie", "doggo");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

}
