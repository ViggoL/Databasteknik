package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Movie {
	private int id;
	private String title;
	private Date releaseDate;
	private List<Director> directors = new ArrayList<>();
	private List<Movie> genres = new ArrayList<>();
	
	public Movie(){
		
	}
	public Movie(String title, Date releaseDate, List<Director> directors){
		this.setTitle(title);
		this.setReleaseDate(releaseDate);
		this.setDirectors(directors);
		
	}
	public Movie(int id, String title, Date releaseDate, List<Director> directors)
	{
		this(title,releaseDate,directors);
		this.setId(id);
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
		Object[] arr = new Object[4];
		arr[0] = this.getTitle();
		arr[1] = this.getReleaseDate();
		arr[2] = this.getDirectors();
		arr[3] = this.getGenres();
		return arr;
	}
	public List<Movie> getGenres() {
		return genres;
	}
	public void setGenres(List<Movie> genres) {
		this.genres = genres;
	}
}
