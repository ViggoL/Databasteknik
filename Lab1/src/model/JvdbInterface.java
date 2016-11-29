package src.model;

import java.sql.SQLException;
import java.util.List;

public interface JvdbInterface {

	void close() throws SQLException;

	boolean isOpen() throws SQLException;

	List<User> getUsers() throws SQLException;

	boolean addUser(String userName, String password, String email) throws SQLException;

	List<Movie> getMovies() throws SQLException;

	List<Artist> getArtists() throws SQLException;

	List<Genre> getGenres() throws SQLException;

}