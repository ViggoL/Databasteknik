package src.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.LoginException;

public interface JvdbInterface {
	
	/**
	 * Closes the connection
	 * @throws SQLException
	 */
	void close() throws SQLException;

	/**
	 * 
	 * @return Wether the connection is open or not
	 * @throws SQLException
	 */
	boolean isOpen() throws SQLException;

	/**
	 * 
	 * @return All users
	 * @throws SQLException
	 */
	List<User> getUsers() throws SQLException;

	/**
	 * Adds a user with hashed password
	 * @param userName
	 * @param password
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	boolean addUser(String userName, String password, String email) throws SQLException;

	/**
	 * Get all genres
	 * @return
	 * @throws SQLException
	 */
	List<Genre> getGenres(MediaType type) throws SQLException;

	/**
	 * Adds an album
	 * @param movie 
	 * @param album
	 * @throws SQLException
	 */
	boolean addMedia(Media media) throws SQLException;

	/**
	 * Returns all movies with matching values for the specified attribute.
	 * @param attribute
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	List<Media> getMedia(MediaAttributes attribute, String value) throws SQLException;
	
	/**
	 * Attempts to log in to the database by selecting data from the "users" table. 
	 * @param userName
	 * @param passWord
	 * @return Returns the userId.
	 * @throws SQLException
	 * @throws LoginException 
	 */
	int logIn(String userName, String passWord) throws SQLException, LoginException;

	
	/**
	 * Adds an album review.
	 * @param review
	 * @throws SQLException
	 */
	boolean addMediaReview(MediaReview review) throws SQLException;
	
	/**
	 * Adds an artist.
	 * @param person
	 * @throws SQLException
	 */
	boolean addMediaPerson(MediaPerson mediaPerson) throws SQLException;

	/**
	 * 
	 * @param userId
	 * @param movieId
	 * @return True if the movie review already exists for the specified user and movie.
	 * @throws SQLException
	 */
	boolean mediaReviewExists(int userId, int mediaId) throws SQLException;

	/**
	 * 
	 * @return The current userId for this JVDB session.
	 */
	int getUserId();
	
	/**
	 * 
	 * @return The current userId for this JVDB session.
	 */
	String getUserStringId();
	
	/**
	 * 
	 * @return logged in User
	 */
	User getUser();
	
	/**
	 * Gets media by selected Enum MediaAttributes <b>selectedAttribute</b> 
	 * and by Enum MediaType <b>media</b>
	 * @param selectedAttribute 
	 * @param media
	 * @param title
	 * @return
	 */
	List<Media> getMedia(MediaAttributes selectedAttribute, MediaType media, String title);

	List<MediaPerson> getMediaPersons(MediaPersonType artist); 
	
}