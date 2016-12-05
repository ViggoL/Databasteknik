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


	List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException;

	void AddMovie(Movie movie) throws SQLException;

	List<Album> getAlbums(Operations operation, String value) throws SQLException;

	List<MovieGenre> getMovieGenres() throws SQLException;
	
	int logIn(String userName, String passWord) throws SQLException;

	void addAlbumReview(AlbumReview review) throws SQLException;

	void addMovieReview(MovieReview review) throws SQLException;

	void addArtist(Artist artist) throws SQLException;

	void addDirector(Director director) throws SQLException;

	boolean movieReviewExists(int userId, int movieId) throws SQLException;

	boolean albumReviewExists(int userId, int albumId) throws SQLException;

	int getUserId();
}