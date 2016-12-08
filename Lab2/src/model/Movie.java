package src.model;
/**
 * Represents a movie entity.
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Movie extends Media{
	private int id;
	private String title;
	private Date releaseDate;
	private int rating;
	private List<Director> directors = new ArrayList<>();
	private List<Genre> genres = new ArrayList<>();
	private User addedBy;
	
	public Movie(){
		super();
	}
	
	public Movie(String title, Date releaseDate, int rating, List<MediaPerson> directors, List<Genre> genres, User addedBy){
		super(title,releaseDate,rating,directors,genres,addedBy);
		
	}

	public int getId() throws NullPointerException {
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

	public List<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(List<Director> directors) {
		this.directors = directors;
	}
	
	public Object[] getArray() {
		Object[] arr = new Object[6];
		arr[0] = this.title;
		arr[1] = this.releaseDate;
		arr[2] = this.directors.toString().replace("]", "").replace("[", "");
		arr[3] = this.genres.toString().replace("]", "").replace("[", "");
		arr[4] = this.rating;
		arr[5] = this.addedBy;
		return arr;
	}
	
	public boolean compareArrays(Object[] other)
	{
		Object[] thisArr = this.getArray();
		if (thisArr[0].equals(other[0]) &&
			thisArr[1].equals(other[1]) &&
			thisArr[2].equals(other[2]) &&
			thisArr[3].equals(other[3]) &&
			thisArr[4].equals(other[4]) &&
			thisArr[5].equals(other[5])) return true;
		return false;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
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
}
