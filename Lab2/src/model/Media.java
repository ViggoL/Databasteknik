package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Media {

	protected int id;
	protected String title;
	protected Date releaseDate;
	private List<Genre> genres = new ArrayList<Genre>();
	protected int rating;
	private User addedBy;
	private List<MediaPerson> mediaPersons;

	public Media() {
		super();
	}
	
	public Media(String title, Date releaseDate, int rating, List<MediaPerson> mPersons, List<Genre> genres, User addedBy){
		this.title = title;
		this.releaseDate = releaseDate;
		this.mediaPersons = mPersons;
		this.rating = rating;
		this.genres = genres;
		this.addedBy = addedBy;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}
	
	public List<MediaPerson> getMediaPersons(){
		return mediaPersons;
	}


}