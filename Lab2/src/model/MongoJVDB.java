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
import com.mongodb.MongoConfigurationException;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import static com.mongodb.client.model.Filters.eq;

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
		String pwHash = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
		db.getCollection("users").insertOne(
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
		Document andQuery = new Document();
		List<Document> obj = new ArrayList<Document>();
		obj.add(new Document("username", userName));
		obj.add(new Document("hashed password", pwHash));
		andQuery.put("$and", obj);

		Document user = db.getCollection("users").find(andQuery).first();
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
		
		//Replace MediaPerson with existing MediaPerson in database
		if(media.getMediaPersons() != null);
		
		
		coll.insertOne(new Document("media type", media.getType().toString()).append("title", media.getTitle())
				.append("release date", ((Date) media.getReleaseDate()).toString())
				.append("genre", genres)
				.append("media person", persons)
				.append("rating", media.getRating())
				.append("adding user", this.currentUser.getId()));

		FindIterable<Document> fi = coll.find();
		for (Object o : fi) {
			System.out.println(o.toString());
		}

		return fi != null;
	}

	@Override
	public List<Media> getMedia(MediaAttributes attribute, String value) throws SQLException {
		FindIterable<Document> fi = null;

		switch(attribute){
		case ALL:			fi = db.getCollection("media").find(); break;
		case TITLE:			fi = db.getCollection("media").find((new Document("title",value))); break;
		case RATING:		fi = db.getCollection("media").find((eq("rating",value))); break;
		case MEDIA_PERSON:	fi = db.getCollection("media").find((eq("media person",value))); break;
		case GENRE:			fi = db.getCollection("media").find((eq("genre",value))); break;
		case RELEASE_DATE:	fi = db.getCollection("media").find((eq("release date",value))); break;
		default: 			throw new MongoConfigurationException("Syntax Error in method"); 
		}
		
		return parseMediaDocuments(fi);
	}

	private List<Media> parseMediaDocuments(FindIterable<Document> fi) throws MongoException {

		try {
			MongoCursor<Document> docs = fi.iterator();
			List<Document> dList = new ArrayList<>();
			List<Media> media = null;
			try {
				if(docs == null) return media;
				media = new ArrayList<>();
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
	public boolean mediaReviewExists(String userId, String movieId) throws SQLException {
		
		return false;
	}

	@Override
	public String getUserId() {
		return currentUser.getId();
	}

	@Override
	public List<Media> getMedia(MediaAttributes attribute, MediaType media, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MediaPerson> getMediaPersons(MediaPersonType type) {
		List<MediaPerson> list = null;
		FindIterable<Document> fi = null;
		
		switch(type){
		case ALL:			fi = db.getCollection("MediaPerson").find(); break;
		case ARTIST:			fi = db.getCollection("MediaPerson").find(eq("profession","Artist")); break;
		case DIRECTOR:		fi = db.getCollection("MediaPerson").find((eq("profession","Director"))); break;
		case COMPOSER:	fi = db.getCollection("MediaPerson").find((eq("profession","Composer"))); break;
		default: 			throw new MongoConfigurationException("Syntax Error in method"); 
		}
		
		if(fi != null){
			list = new ArrayList<>();
			MongoCursor<Document> i = fi.iterator();
			while(i.hasNext()){
				Document d = i.next();
				MediaPerson mp = new MediaPerson(d.getString("name"));
				
			}
			
			System.out.println(list);
		}
		return list;
	}

}
