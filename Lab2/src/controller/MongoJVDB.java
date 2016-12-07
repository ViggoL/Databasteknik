package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.model.Album;
import src.model.AlbumGenre;
import src.model.AlbumReview;
import src.model.Artist;
import src.model.Director;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.Movie;
import src.model.MovieAttributes;
import src.model.MovieGenre;
import src.model.MovieReview;
import src.model.Operations;
import src.model.Person;
import src.model.User;

public class MongoJVDB implements JvdbInterface {

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOpen() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(String userName, String password, String email) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Artist> getArtists() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlbumGenre> getAlbumGenres() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAlbum(Media album) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddMovie(Movie movie) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Album> getAlbums(Operations operation, String value) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieGenre> getMovieGenres() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int logIn(String userName, String passWord) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addAlbumReview(AlbumReview review) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMovieReview(MovieReview review) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addArtist(Person artist) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDirector(Director director) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean movieReviewExists(int userId, int movieId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean albumReviewExists(int userId, int albumId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
