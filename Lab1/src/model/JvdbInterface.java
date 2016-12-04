package src.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JvdbInterface {
	
	void close() throws SQLException;

	boolean isOpen() throws SQLException;

	List<User> getUsers() throws SQLException;

	boolean addUser(String userName, String password, String email) throws SQLException;

	List<Movie> getMoviesByTitle(String name) throws SQLException;
	
	List<Artist> getArtists() throws SQLException;

	List<Genre> getGenres() throws SQLException;

	void addAlbum(Album album) throws SQLException;

	List<Album> getAlbumsByDate(Date date) throws SQLException;

	List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException;

	void AddMovie(Movie movie) throws SQLException;

	List<Album> getAlbums(Operations operation, String value) throws SQLException;
	
}