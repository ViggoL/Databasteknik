package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.model.Album;
import src.model.AlbumGenre;
import src.model.Artist;
import src.model.Director;
import src.model.Genre;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.Movie;
import src.model.MediaAttributes;
import src.model.MediaPerson;
import src.model.MediaReview;
import src.model.MovieGenre;
import src.model.MovieReview;
import src.model.MediaAttributes;
import src.model.Person;
import src.model.PersonType;
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
	public List<Genre> getGenres() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addMedia(Media album) throws SQLException {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public List<Media> getMedia(MediaAttributes attribute, String value) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int logIn(String userName, String passWord) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addMediaReview(MediaReview review) throws SQLException {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addMediaPerson(MediaPerson artist) throws SQLException {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean mediaReviewExists(int userId, int movieId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Person> getPersons(PersonType typeEnum) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
