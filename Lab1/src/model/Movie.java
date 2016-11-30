package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie {
	private int id;
	private String name;
	private Date releaseDate;
	private List<Director> directors = new ArrayList<>();
	
	public Movie(){
		
	}
	
	public Movie(int id, String name, Date releaseDate, List<Director> directors)
	{
		this.setId(id);
		this.setName(name);
		this.setReleaseDate(releaseDate);
		this.setDirectors(directors);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
