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

		//Replace MediaPerson with existing MediaPerson in database
		for (MediaPerson mp : media.getMediaPersons()){
			Document andQuery = new Document();
			List<Document> obj = new ArrayList<Document>();
			
			obj.add(new Document("name",mp.getName() ));
			obj.add(new Document("profession",mp.getProfession().toString()));
			andQuery.put("$and", obj);
			
			MediaPerson tmpMediaPerson = null;
			Document mediaPerson = null;
			try {
				//Find and replace
				if((mediaPerson = db.getCollection("MediaPerson").find(andQuery).first()) != null){
					tmpMediaPerson = new MediaPerson(mediaPerson.get("_id").toString(), 
							mediaPerson.getString("name"), 
							MediaPersonType.valueOf(mediaPerson.getString("profession").toUpperCase()), 
							mediaPerson.getString("biography"));
					//tmpMediaPerson.setProfession(MediaPersonType.valueOf(mediaPerson.getString("profession").toUpperCase()));
					mp = tmpMediaPerson;
				}
			} catch (MongoException e) {
				System.err.println("Find and replace method failure: \n\t" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		List<String> genres = new ArrayList<>();
		for (Genre g : media.getGenres()) {
			genres.add(g.getName());
		}
		List<String> persons = new ArrayList<>();
		for (MediaPerson mp : media.getMediaPersons()) {
			persons.add(mp.toMongoString().replaceAll("[\\[\\]]", ""));
		}
		
		Document newMedia;
		db.getCollection("media").insertOne(newMedia = new Document("media type", media.getType().toString()).append("title", media.getTitle())
				.append("release date", ((Date) media.getReleaseDate()).toString())
				.append("genre", genres)
				.append("media person", persons)
				.append("rating", media.getRating())
				.append("adding user", this.currentUser.getName()));
		
		Object fi = null;
		//Let's see what we have now, after the insert
		for (Object o : db.getCollection("media").find()) {
			if(o.equals(newMedia)) fi = o;
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
		case RATING:		fi = db.getCollection("media").find((eq("rating",Integer.parseInt(value)))); break;
		case MEDIA_PERSON:	fi = db.getCollection("media").find((eq("media person",value))); break;
		case GENRE:			fi = db.getCollection("media").find((eq("genre",value))); break;
		case RELEASE_DATE:	fi = db.getCollection("media").find((eq("release date",value))); break;
		default: 			throw new MongoConfigurationException("Syntax Error in method"); 
		}
		
		return parseMediaDocuments(fi);
	}

	@SuppressWarnings("unchecked")
	private List<Media> parseMediaDocuments(FindIterable<Document> fi) throws MongoException {

		try {
			MongoCursor<Document> docs = fi.iterator();
			List<Media> media = null;
			try {
				if(!docs.hasNext()) return media;
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
					{
						MediaPerson mp = new MediaPerson();
						mp.setName(p.split(",")[1]);
						mp.setProfession(MediaPersonType.valueOf(p.split(",")[2].toUpperCase()));
						m.getMediaPersons().add(mp);
					}
					m.setRating(doc.getInteger("rating", 1));
					m.setAddedBy(doc.getString("adding user"));
					media.add(m);
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
		String pw = org.apache.commons.codec.digest.DigestUtils.sha1Hex(passWord);
		currentUser = new User();
		currentUser.setPwHash(pw);
		currentUser.setUserName(userName);
		Document user = null;
		try {
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
		
		MongoCollection<Document> coll = db.getCollection("reviews");
		
		// Insert new review
		Document rev = new Document();
		rev.put("media type", review.getType().toString());
		rev.put("media title", review.getTitle());
		rev.put("rating", review.getRating());
		rev.put("reviewed by", review.getUser().name);
		rev.put("text", review.getText());
		
		try {
			coll.insertOne(rev);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		// Get new rating
		List<Document> obj = new ArrayList<Document>();
		obj.add(new Document("media title", review.getTitle()));
		FindIterable<Document> media = coll.find();
		MongoCursor<Document> cs = media.iterator();
		int rating = 0;
		int count = 0;
		while (cs.hasNext())
		{
			count++;
			Document tmpMedia = (Document) cs.next();
			rating += tmpMedia.getInteger("rating").intValue();
			rating /= count;
		}
		
		Document newDocument = new Document();
		newDocument.append("$set", new Document().append("rating", rating));

		Document searchQuery = new Document().append("title", review.getTitle());

		db.getCollection("media").updateOne(searchQuery, newDocument);
		
		return true;

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

	public boolean mediaReviewExists(String userName, String mediaTitle) throws SQLException {
		Document andQuery = new Document();
		List<Document> obj = new ArrayList<Document>();
		
		obj.add(new Document("media title", mediaTitle));
		obj.add(new Document("reviewed by", userName));
		andQuery.put("$and", obj);
		MongoCollection<Document> d = db.getCollection("reviews");
		return d.find(andQuery).iterator().hasNext();
	}

	@Override
	public String getUserId() {
		return currentUser.getId();
	}

	@Override
	public List<MediaPerson> getMediaPersons(MediaPersonType type) {
		List<MediaPerson> list = null;
		FindIterable<Document> fi = null;
		
		switch(type){
		case ALL:			fi = db.getCollection("MediaPerson").find(); break;
		case ARTIST:		fi = db.getCollection("MediaPerson").find(eq("profession","Artist")); break;
		case DIRECTOR:		fi = db.getCollection("MediaPerson").find((eq("profession","Director"))); break;
		case COMPOSER:		fi = db.getCollection("MediaPerson").find((eq("profession","Composer"))); break;
		default: 			throw new MongoConfigurationException("Syntax Error in method"); 
		}
		
		if(fi != null){
			list = new ArrayList<>();
			MongoCursor<Document> i = null; 
			try {
				i = fi.iterator();
				while(i.hasNext()){
					Document d = i.next();
					MediaPerson mp = new MediaPerson(d.getString("name"), null,null,null);
					mp.setId(d.getString("_id"));
					mp.setBio(d.getString("biography"));
					mp.setProfession(MediaPersonType.valueOf(d.getString("profession").toUpperCase()));
					list.add(mp);
					
				}
			} finally{
				i.close();
			}
			System.out.println(list);
		}
		return list;
	}	
}
		
