package src.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JvdbInterface {
	
	void close() throws SQLException;

	boolean isOpen() throws SQLException;

	List<User> getUsers() throws SQLException;

	boolean addUser(String userName, String password, String email) throws SQLException;
	
	List<Artist> getArtists() throws SQLException;

	List<AlbumGenre> getAlbumGenres() throws SQLException;

	void addAlbum(Album album) throws SQLException;


	List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException;

	void AddMovie(Movie movie) throws SQLException;

	List<Album> getAlbums(Operations operation, String value) throws SQLException;

	List<MovieGenre> getMovieGenres() throws SQLException;
	
	int logIn(String userName, String passWord) throws SQLException;

	void addAlbumReview(AlbumReview review) throws SQLException;

	void addMovieReview(MovieReview review) throws SQLException;
}