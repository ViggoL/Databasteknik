package src.model;

import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.security.auth.login.LoginException;
import javax.swing.SwingUtilities;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;

import src.view.ErrorDialogue;

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
	public MongoJVDB(String host, String databaseName) throws UnknownHostException, MongoException {
		// the new connection class MongoClient acknowledges all writes to
		// MongoDB,
		// in contrast to the existing connection class Db
		dbName = databaseName;
		try {
			ServerAddress sa = new ServerAddress(host, 27017);
			MongoCredential mc = MongoCredential.createCredential("client", "jvdb", "lab".toCharArray());
			
			this.client = new MongoClient(sa, Arrays.asList(mc));
			this.db = client.getDatabase(dbName);
			
		} catch (MongoException me) {
			System.err.println(me.getMessage());
			throw new MongoException("Can't connect!");
		} finally {
		}
	}

	@Override
	public void close() throws SQLException {
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
		boolean userAdded = true;
		MongoCollection<Document> coll = db.getCollection("users");
		String pwHash = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
		coll.insertOne(
				new Document("username", userName)
				.append("hashed password", pwHash)
				.append("email", email)
				);

		if (assertUser(userName, pwHash) == null) {
			SwingUtilities.invokeLater(new ErrorDialogue("User not added"));
			userAdded = false;
		}
		return userAdded;
	}

	private User assertUser(String userName, String pwHash) {
		MongoCollection<Document> doc = db.getCollection("users");
		Document andQuery = new Document();
		List<Document> obj = new ArrayList<Document>();
		obj.add(new Document("username", userName));
		obj.add(new Document("hashed password", pwHash));
		andQuery.put("$and", obj);

		Document user = doc.find(andQuery).first();
		User tmpUser = null;
		if (user != null) {
			tmpUser = new User(
					user.get("_id").toString(), 
					user.getString("username"),
					user.getString("hashed password"), 
					user.getString("email"));
		}
		return tmpUser;
	}

	@Override
	public List<Genre> getGenres(MediaType type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addMedia(Media media) throws SQLException {
		List<String> genres = new ArrayList<>();
		for (Genre g : media.getGenres()) {
			genres.add(g.getName());
		}
		List<String> persons = new ArrayList<>();
		for (MediaPerson mp : media.getMediaPersons()) {
			persons.add(mp.toString().replaceAll("[\\[\\]]", ""));
		}
		MongoCollection<Document> coll = db.getCollection("media");
		coll.insertOne(new Document("media type", media.getType().toString()).append("title", media.getTitle())
				.append("release date", ((Date) media.getReleaseDate()).toString()).append("genre", genres)
				.append("media person", persons).append("rating", media.getRating())
				.append("adding user", this.currentUser.getStringId()));

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

	private List<Media> parseMediaDocuments(FindIterable<Document> fi) throws MongoException {

		try {
			MongoCursor<Document> docs = fi.iterator();
			List<Document> dList = new ArrayList<>();
			List<Media> media = new ArrayList<>();
			try {
				while (docs.hasNext()) {
					Media m = new Media();
					Document doc = docs.next();
					m.setId(doc.getObjectId("_id").toString());
					m.setTitle(doc.getString("title"));
					m.setReleaseDate(Date.valueOf(doc.getString("release date")));
					m.setType(MediaType.valueOf(doc.getString("media type").toUpperCase()));

					List<String> genres = new ArrayList<>(doc.get("genre", ArrayList.class));
					List<Genre> mGenres = new ArrayList<>();
					for (String s : genres) {
						mGenres.add(new Genre(s));
					}
					m.setGenres(mGenres);
					for (String p : new ArrayList<String>(doc.get("media person", ArrayList.class)))
						m.getMediaPersons().add(new MediaPerson(p));
					m.setRating(doc.getInteger("rating", 1));
					m.setAddedBy(doc.getString("adding user"));
				}
			} finally {
				docs.close();
			}

			return media;
		} catch (MongoException e) {
			System.err.println("Failed to find media documents" + e.getLocalizedMessage());
			throw new MongoException("Failed to find media documents", e);
		}
	}

	@Override
	public int logIn(String userName, String passWord) throws SQLException, LoginException {
		String username = userName;
		currentUser = new User(userName);
		String pw = org.apache.commons.codec.digest.DigestUtils.sha1Hex(passWord);
		currentUser.setPwHash(pw);
		Document user = null;
		try {
			MongoCollection<Document> doc = db.getCollection("users");
			Document andQuery = new Document();
			List<Document> obj = new ArrayList<Document>();
			obj.add(new Document("username", userName));
			obj.add(new Document("hashed password", pw));
			andQuery.put("$and", obj);

			if((currentUser = assertUser(userName,pw)) == null) throw new LoginException();
				
		} catch (MongoException e) {
			e.printStackTrace(System.err);
		} finally {
			if (user != null)
				user.clear();
		}
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
	public boolean addMediaPerson(MediaPerson person) {
		MongoCollection<Document> coll = db.getCollection("MediaPerson");
		Document mp;
		boolean addedPersonConfirmed = false;
		coll.insertOne(mp = new Document("name", person.getName())
				.append("profession", person.getProfession().toString())
				.append("biography", person.getBio()));
		 
		FindIterable<Document> fi = coll.find();
		for (Object o : fi) {
			if(((Document)o).equals(mp)) addedPersonConfirmed = true;
			System.out.println(o.toString());
		}
		return addedPersonConfirmed;
	}

	@Override
	public boolean mediaReviewExists(int userId, String movieId) throws SQLException {
		
		return false;
	}

	@Override
	public int getUserId() {
		return currentUser.getId();
	}
	
	@Override
	public String getUserStringId(){
		return currentUser.getStringId();
	}

	@Override
	public List<Media> getMedia(MediaAttributes all, MediaType movie, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MediaPerson> getMediaPersons(MediaPersonType mediaPerson) {
		// TODO Auto-generated method stub
		return null;
	}

}
