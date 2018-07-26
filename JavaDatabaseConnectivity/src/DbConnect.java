import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows for connection to the database and contains SQL to update
 * the database and retrieve information.
 * 
 * @author Charlie Cox
 * @version 2-3-2018
 */
public class DbConnect implements DbConnectInterface {
	private Properties properties;
	private Connection connection;

	/**
	 * Constructor - creates a connection to the database
	 * 
	 * @param path
	 *            The path to the location of the properties file which cannot be
	 *            null.
	 */
	public DbConnect(String path, String url) {
		this.properties = new Properties();

		try {
			FileInputStream in = new FileInputStream(path);
			this.properties.load(in);

			connection = DriverManager.getConnection(url, properties);
			
		} catch (FileNotFoundException e) {
			System.err.println("Properties file could not be located");
		} catch (IOException e) {
			System.err.println("Error loading properties file");
		} catch (SQLException e) {
			System.err.println("Error connecting to database");
		}
	}


	/**
	 * Method to update the database user_info table to register a new user to the
	 * databbase.
	 * 
	 * @param username
	 *            username that the user requires - unique to all other username
	 *            already present in the database.
	 * @param password
	 *            password that the user has chosen -
	 * @param nickname
	 */
	public void registerUser(String username, String password, String nickname) {
		try {
			PreparedStatement query = connection
					.prepareStatement("INSERT INTO user_info(username,password,nickname) VALUES (?, ?, ?);");
			query.setString(1, username);
			query.setString(2, password);
			query.setString(3, nickname);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to update the nickname of a user.
	 * 
	 * @param username
	 *            username of the user.
	 * @param nickname
	 *            new nickname that the user wants to change their nickname too.
	 */
	public void updateNickname(String username, String nickname) {
		try {
			PreparedStatement query = connection
					.prepareStatement("INSERT INTO user_info(nickname) VALUES (?) WHERE username = ?;");
			query.setString(1, nickname);
			query.setString(2, username);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to check that a username exists in the database
	 * 
	 * @param username
	 *            The username to be checked
	 * @return true if the username exists in the database, false otherwise.
	 */
	public boolean checkUsernameExists(String username) {
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM user_info WHERE username = ?;");
			query.setString(1, username);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				if (!rs.wasNull())
					return true;
			}
			return false;
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
			return false;
		}

	}

	/**
	 * Method to check that a password matches a username.
	 * 
	 * @param username
	 *            The username that the user has input.
	 * @param password
	 *            The password that the user has input.
	 * @return true if the password matches the provided username.
	 */
	public boolean checkLogin(String username, String password) {
		if (checkUsernameExists(username)) {
			try {
				PreparedStatement query = connection
						.prepareStatement("SELECT password FROM user_info WHERE username = ?;");
				query.setString(1, username);
				ResultSet rs = query.executeQuery();
				
				while (rs.next()) {
					System.out.println(rs.getString(1));
					System.out.println(password);
					if (rs.getString(1).equals(password))
						return true;
					else
						return false;
				}
				
			} catch (SQLException e) {
				System.err.println("Error processing SQL statement");
				return false;
			}
		} else {
			System.out.println("username does not exist");
			return false;
		}
		return false;
	}

	/**
	 * Method to retrieve the high score for a given user
	 * 
	 * @param user
	 *            username of the user whose highscore is to be retrieved.
	 * @return high score of user.
	 */
	public int retrieveHighScore(String user) {
		try {
			PreparedStatement query = connection
					.prepareStatement("SELECT high_score FROM user_info WHERE username = ?;");
			query.setString(1, user);
			ResultSet rs = query.executeQuery();
			int highScore = 0;
			while (rs.next()) {
				highScore = rs.getInt(1);
			}
			return highScore;
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
			return 0;
		}
	}

	/**
	 * Method to retrieve all high scores and their associated usernames. The
	 * user_info table must not be empty.
	 * 
	 * @return HashMap<String, Integer> corresponding to username and high score.
	 */
	public HashMap<String, Integer> retrieveAllHighscores() {
		try {
			PreparedStatement query = connection
					.prepareStatement("SELECT username, high_score FROM user_info ORDER BY high_score DESC;");
			ResultSet rs = query.executeQuery();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			
			while (rs.next()) {
				map.put(rs.getString(1), rs.getInt(2));
			}
			return map;
			
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
			return new HashMap<String, Integer>();
		}
	}

	/**
	 * Method to update the high score of a user.
	 * 
	 * @param user
	 *            username of the user whose high score is to be updated.
	 * @param highScore
	 *            the new high score of the user.
	 */
	public void updateHighScore(String user, int highScore) {
		try {
			PreparedStatement query = connection
					.prepareStatement("UPDATE user_info SET high_score = ? WHERE username = ?;");
			query.setInt(1, highScore);
			query.setString(2, user);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method increments the number of games played by the user by 1 in the
	 * database.
	 * 
	 * @param user
	 *            The username of the user which requires the number of games played
	 *            to be incremented.
	 */
	public void updateGames(String user) {
		try {
			PreparedStatement query = connection.prepareStatement(
					"UPDATE user_info SET number_games_played = number_games_played +1 WHERE username = ?;");
			query.setString(1, user);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to update the database friends table whereby user_one sends a friend
	 * request to user_two. The relation will be set to "0" which corresponds to a
	 * pending friend request.
	 * 
	 * @param user_one
	 *            String corresponding to the username of the user who sent a friend
	 *            request
	 * @param user_two
	 *            String corresponding to the username of the user who received the
	 *            friend request.
	 */
	public void addFriend(String user_one, String user_two) {
		try {
			PreparedStatement query = connection.prepareStatement(
					"INSERT INTO friends (user_one, user_two, relation, action_user) VALUES (?, ?, 0, ?);");
			query.setString(1, user_one);
			query.setString(2, user_two);
			query.setString(3, user_one);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to update the database friends table whereby user_one accepts a friend
	 * request sent by user_two. The relation will be set to "1" which corresponds
	 * to an accepted friend request.
	 * 
	 * @param user_one
	 *            String corresponding to the username of the user who is accepting
	 *            a friend request
	 * @param user_two
	 *            String corresponding to the username of the user who originally
	 *            sent the friend request.
	 */
	public void acceptFriend(String user_one, String user_two) {
		try {
			PreparedStatement query = connection.prepareStatement(
					"UPDATE friends SET relation = 1, action_user = ?, WHERE user_one = ? AND user_two = ?;");
			query.setString(1, user_one);
			query.setString(2, user_two);
			query.setString(3, user_one);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to update the database friends table whereby user_one declines a
	 * friend request sent by user_two. The relation will be set to "2" which
	 * corresponds to a declined friend request.
	 * 
	 * @param user_one
	 *            String corresponding to the username of the user who is declining
	 *            a friend request
	 * @param user_two
	 *            String corresponding to the username of the user who originally
	 *            sent the friend request.
	 */
	public void declineFriend(String user_one, String user_two) {
		try {
			PreparedStatement query = connection.prepareStatement(
					"UPDATE friends SET relation = 2, action_user = ?, WHERE user_one = ? AND user_two = ?;");
			query.setString(1, user_one);
			query.setString(2, user_two);
			query.setString(3, user_one);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to update the database friends table whereby user_one checks to see if
	 * they are friends with user_two
	 * 
	 * @param user_one
	 *            String corresponding to the username of the user who is checking
	 *            to see whether they are friends.
	 * @param user_two
	 *            String corresponding to the username of the user where user_one
	 *            checks to see if they are friends.
	 */
	public void checkFriendship(String user_one, String user_two) {
		try {
			PreparedStatement query = connection
					.prepareStatement("SELECT * FROM friends WHERE user_one = ? AND user_two = ? AND relation = 2;");
			query.setString(1, user_one);
			query.setString(2, user_two);
			query.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
		}
	}

	/**
	 * Method to retrieve the usernames of all friends a particular user has.
	 * 
	 * @param user
	 *            String corresponding to the ID of the user who is checking their
	 *            friends list.
	 * @return ArrayList<String> corresponding to the appropriate SQL query, empty ArrayList if the user has no friends.
	 */
	public ArrayList<String> friendsList(String user) {
		try {
			PreparedStatement query = connection
					.prepareStatement("SELECT * FROM friends WHERE (user_one = ? OR user_two = ?) AND relation = 1;");
			query.setString(1, user);
			query.setString(2, user);
			ResultSet rs = query.executeQuery();
			ArrayList<String> friends = new ArrayList<String>();
			while (rs.next()) {
				friends.add(rs.getString(1));
			}
			return friends;
		} catch (SQLException e) {
			System.err.println("Error processing SQL statement");
			return new ArrayList<String>();
		}
	}


	/**
	 * Method that will check whether the high score needs to be updated after
	 * latest game played.
	 * 
	 * @param username
	 *            username of the user whose high score is to be updated.
	 * @param latestScore
	 *            the new high score of the user which is
	 * @return True if the high score has now been changed to the latest score.
	 */
	public boolean checkHighScore(String username, int latestScore) {
		int currentHighScore = retrieveHighScore(username);

		if (latestScore >= currentHighScore) {
			updateHighScore(username, latestScore);
			return true;
		} else
			return false;
	}
}