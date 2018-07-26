import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for Database connectivity class.
 * 
 * @author Charlie Cox
 *
 */

public interface DbConnectInterface {

	/**
	 * Method to update the database user_info table to register a new user to the
	 * database.
	 */
	public void registerUser(String username, String password, String nickname);

	/**
	 * Method to update the nickname of a user.
	 * 
	 */
	public void updateNickname(String username, String nickname);

	/**
	 * Method to check that a username exists in the database
	 */
	public boolean checkUsernameExists(String username);

	/**
	 * A method to check the login details of a user, i.e checks that a password
	 * matches a username.
	 */
	public boolean checkLogin(String username, String password);

	/**
	 * Method to retrieve the high score for a given user
	 */
	public int retrieveHighScore(String user);

	/**
	 * Method to retrieve all high scores and their associated usernames. The
	 * user_info table must not be empty.
	 */
	public HashMap<String, Integer> retrieveAllHighscores();

	/**
	 * Method to update the high score of a user.
	 */
	public void updateHighScore(String user, int highScore);

	/**
	 * Method increments the number of games played by the user by 1 in the
	 * database.
	 */
	public void updateGames(String user);

	/**
	 * Method to update the database friends table whereby user_one sends a friend
	 * request to user_two. The relation will be set to "0" which corresponds to a
	 * pending friend request.
	 */
	public void addFriend(String user_one, String user_two);

	/**
	 * Method to update the database friends table whereby user_one accepts a friend
	 * request sent by user_two. The relation will be set to "1" which corresponds
	 * to an accepted friend request.
	 */
	public void acceptFriend(String user_one, String user_two);

	/**
	 * Method to update the database friends table whereby user_one declines a
	 * friend request sent by user_two. The relation will be set to "2" which
	 * corresponds to a declined friend request.
	 */
	public void declineFriend(String user_one, String user_two);

	/**
	 * Method to update the database friends table whereby user_one checks to see if
	 * they are friends with user_two.
	 */
	public void checkFriendship(String user_one, String user_two);

	/**
	 * 
	 * Method to retrieve the usernames of all friends a particular user has.
	 * 
	 */
	public ArrayList<String> friendsList(String user);

	/**
	 * Method to check whether the high score needs to be updated.
	 */
	public boolean checkHighScore(String username, int latestScore);

}