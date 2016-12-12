package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Media {

	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private int rating;
	private String title;
	private Date releaseDate;
	private String addedBy;
	private List<MediaPerson> mediaPersons;
	private List<Genre> genres;
	private MediaType mediaType;

	public Media() {
		super();
		id = "-1"; rating = 0;
		title = null;
		releaseDate = null;
		addedBy = null;
		genres = new ArrayList<Genre>();
		mediaPersons = new ArrayList<MediaPerson>();
		
		
	}
	
	public Media(String title, Date releaseDate,MediaPerson mPerson, List<Genre> genres){
		this();
		this.title = title;
		if(mPerson != null) this.mediaPersons.add(mPerson);
		this.genres = genres;
		if(releaseDate != null) this.releaseDate = releaseDate;

	}
	
	public Media(String title, Date releaseDate, List<MediaPerson> mPersons, List<Genre> genres, String addedBy){
		this(title,releaseDate,mPersons.remove(0), genres);
		this.mediaPersons.addAll(mPersons);
		this.addedBy = addedBy;
		
	}


	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public Object[] toArray() {
		Object[] arr = new Object[6];
		arr[0] = this.title;
		arr[1] = this.releaseDate;
		arr[2] = this.mediaPersons.toString().replace("]", "").replace("[", "");;
		arr[3] = this.genres.toString().replace("]", "").replace("[", "");;
		arr[4] = this.rating;
		arr[5] = this.addedBy;
		return arr;
	}

	public boolean compareArrays(Object[] other) {
		Object[] thisArr = this.toArray();
		if (thisArr[0].equals(other[0]) &&
			thisArr[1].equals(other[1]) &&
			thisArr[2].equals(other[2]) &&
			thisArr[3].equals(other[3]) &&
			thisArr[4].equals(other[4]) &&
			thisArr[5].equals(other[5])) return true;
		return false;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String string) {
		this.addedBy = string;
	}
	
	public List<MediaPerson> getMediaPersons(){
		return mediaPersons;
	}

	public MediaType getType() {
		return this.mediaType;
	}
	
	public void setType(MediaType type){
		this.mediaType = type;
	}
	
	@Override
	public String toString(){
		String sep = ";";
		StringBuilder string = new StringBuilder();
		string.append(String.valueOf(this.id)).append(sep); // Mongo index
		string.append(this.mediaType.toString()).append(sep).append(this.getTitle()).append(sep);
		string.append(this.releaseDate.toString()).append(sep);
		string.append("Genre(s):");
		for(Genre g: genres){
			string.append(g.getName() + " ");
		}
		string.append(sep);
		for(MediaPerson mp: mediaPersons){
			string.append(mp.getProfession().toString() + ":" + mp.getName()).append(sep);
		}
		return string.toString();
	}


}