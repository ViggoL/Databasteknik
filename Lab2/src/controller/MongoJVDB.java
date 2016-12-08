package src.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

import src.model.Genre;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.MediaAttributes;
import src.model.MediaPerson;
import src.model.MediaReview;
import src.model.MediaType;
import src.model.Person;
import src.model.PersonType;
import src.model.User;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;	

public class MongoJVDB implements JvdbInterface {
	
	
	private MongoClient client;
	private MongoDatabase db;
	private User currentUser;

	public MongoJVDB(String databaseName) throws UnknownHostException{
		//the new connection class MongoClient acknowledges all writes to MongoDB, 
		//in contrast to the existing connection class Db
		try{
			this.client = new MongoClient("localhost", 27017);
			db = client.getDatabase(databaseName);
		}catch (MongoException me){
			System.err.println(me.getMessage());
			throw new MongoException("Can't connect!");
			
		}finally{
			if(client != null) client.close();
		}
		
		
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		client.close();
	}
	

	public boolean isOpen() {
			return db != null;
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
	public boolean addMedia(Media media) throws SQLException {
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
		currentUser = new User(userName);
		return 0;
		
	}
	
	@Override
	public User getUser() {
		return currentUser;
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

	@Override
	public List<Media> getMedia(MediaAttributes all, MediaType movie, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MediaPerson> getMediaPersons(PersonType mediaPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
