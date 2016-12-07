package src.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

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
	 * Gets all artists
	 * @return
	 * @throws SQLException
	 */
	List<Person> getPersons(PersonType typeEnum) throws SQLException;

	/**
	 * Get all albums genres
	 * @return
	 * @throws SQLException
	 */
	List<Genre> getGenres() throws SQLException;

	/**
	 * Adds an album
	 * @param album
	 * @throws SQLException
	 */
	boolean addMedia(Media album) throws SQLException;

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
	 */
	int logIn(String userName, String passWord) throws SQLException;

	
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
}