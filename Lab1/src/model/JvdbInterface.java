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
	List<Artist> getArtists() throws SQLException;

	/**
	 * Get all albums genres
	 * @return
	 * @throws SQLException
	 */
	List<AlbumGenre> getAlbumGenres() throws SQLException;

	/**
	 * Adds an album
	 * @param album
	 * @throws SQLException
	 */
	void addAlbum(Album album) throws SQLException;

	/**
	 * Returns all movies with matching values for the specified attribute.
	 * @param attribute
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException;

	
	/**
	 * Adds a new movie. The movieId is ignored in the movie parameter.
	 * @param movie
	 * @throws SQLException
	 */
	void AddMovie(Movie movie) throws SQLException;

	/**
	 * Returns all albums with the matching value for the specified attribute.
	 * @param operation
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	List<Album> getAlbums(Operations operation, String value) throws SQLException;

	
	/**
	 * Gets all movie genres.
	 * @return
	 * @throws SQLException
	 */
	List<MovieGenre> getMovieGenres() throws SQLException;
	
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
	void addAlbumReview(AlbumReview review) throws SQLException;

	/**
	 * Adds a movie review.
	 * @param review
	 * @throws SQLException
	 */
	void addMovieReview(MovieReview review) throws SQLException;

	
	/**
	 * Adds an artist.
	 * @param artist
	 * @throws SQLException
	 */
	void addArtist(Artist artist) throws SQLException;

	/**
	 * Adds a director.
	 * @param director
	 * @throws SQLException
	 */
	void addDirector(Director director) throws SQLException;

	/**
	 * 
	 * @param userId
	 * @param movieId
	 * @return True if the movie review already exists for the specified user and movie.
	 * @throws SQLException
	 */
	boolean movieReviewExists(int userId, int movieId) throws SQLException;

	/**
	 * 
	 * @param userId
	 * @param albumId
	 * @return True if the album review already exists for the specified user and album.
	 * @throws SQLException
	 */
	boolean albumReviewExists(int userId, int albumId) throws SQLException;

	/**
	 * 
	 * @return The current userId for this JVDB session.
	 */
	int getUserId();
}