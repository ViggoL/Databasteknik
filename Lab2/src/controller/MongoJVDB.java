package src.controller;

import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MongoJVDB implements JvdbInterface {

	private MongoClient client;
	private MongoDatabase db;
	private User currentUser;
	private String dbName;

	public MongoJVDB(String databaseName) throws UnknownHostException, MongoException {
		// the new connection class MongoClient acknowledges all writes to
		// MongoDB,
		// in contrast to the existing connection class Db
		dbName = databaseName;
		try {
			this.client = new MongoClient("localhost", 27017);
			this.db = client.getDatabase(dbName);

		} catch (MongoException me) {
			System.err.println(me.getMessage());
			throw new MongoException("Can't connect!");

		} finally {
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
		db.createCollection("User", new CreateCollectionOptions().autoIndex(true));
		return false;
	}

	@Override
	public List<Genre> getGenres() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addMedia(Media media) throws SQLException {
		List<String> genres = new ArrayList<>();
		boolean gt1 = false;
		for (Genre g : media.getGenres()) {
			genres.add(g.getName());
		}
		List<String> persons = new ArrayList<>();
		for (MediaPerson mp : media.getMediaPersons()) {
			persons.add(mp.toString().replaceAll("[\\[\\]]",""));
		}
		MongoCollection<Document> coll = db.getCollection("media");
		coll.insertOne(new Document("media type", media.getType().toString()).append("title", media.getTitle())
				.append("release date", ((Date) media.getReleaseDate()).toString()).append("genre", genres)
				.append("media person", persons));

		FindIterable<Document> fi = coll.find();
		for (Object o : fi) {
			System.out.println(o.toString());
		}
		return fi != null;
	}

	@Override
	public List<Media> getMedia(MediaAttributes attribute, String value) throws SQLException {

		MongoCollection<Document> coll = db.getCollection("media");
		FindIterable<Document> fi = coll.find();
		return parseMediaDocuments(fi);
	}

	private List<Media> parseMediaDocuments(FindIterable<Document> fi) throws MongoException{
		
		try {
			MongoCursor<Document> docs = fi.iterator();
			List<Document> dList = new ArrayList<>();
			List<Media> media = new ArrayList<>();
			try{
				while(docs.hasNext()){
					Media m = new Media();
					Document doc = docs.next();
					m.setId(Integer.valueOf(doc.getObjectId("_id").toString()));
					m.setTitle(doc.getString("title"));
					m.setReleaseDate(Date.valueOf(doc.getString("release date")));
					m.setType(MediaType.valueOf(doc.getString("media type")));
					
					List<String> genres = doc.get("genres", ArrayList.class);
					List<Genre> mGenres = new ArrayList<>();
					for(String s: genres){
						mGenres.add(new Genre(s));
					}
					m.setGenres(mGenres);
					for(String p: doc.getString("media person"))m.getMediaPersons().add(new MediaPerson(p));
				}
			}finally{
				docs.close();
			}
			 
			return media;
		} catch (MongoException e) {
			System.err.println("Failed to find media documents" + e.getLocalizedMessage());
			throw new MongoException("Failed to find media documents", e);
		} 
	}
		
//	}
//	@Override
//	public List<UserEntity> findUserAccounts() throws MetaStoreException {
//	  try {
//	    List<UserEntity> users = Lists.newArrayList();
//
//	    MongoCursor<Document> cursor = userAccountCollection.find().iterator();
//	    try {
//	      while (cursor.hasNext()) {
//	        Document doc = cursor.next();
//	        UserEntity user = new UserEntity();
//	        user.setId(doc.getObjectId("_id").toString());
//	        user.setName(doc.getString("name"));
//	        user.setPassword(doc.get("password", Binary.class).getData());
//	        user.setCreateTime(doc.getDate("createTime"));
//	        user.setLastModifyTime(doc.getDate("lastModifyTime"));
//
//	        users.add(user);
//	      }
//	    } finally {
//	      cursor.close();
//	    }
//
//	    return users;
//	  } catch (MongoException e) {
//	    LOG.error("Failed to find user accounts", e);
//	    throw new MetaStoreException("Failed to find user accounts", e);
//	  }
//	}

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
		db.createCollection("MediaReview", new CreateCollectionOptions().autoIndex(true));
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addMediaPerson(MediaPerson artist) throws SQLException {
		db.createCollection("MediaPerson", new CreateCollectionOptions().autoIndex(true));
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
