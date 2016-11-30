package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Album {
	private int id;
	private String name;
	private Date releaseDate;
	private List<Artist> artists = new ArrayList<Artist>();
	private List<Genre> genres = new ArrayList<Genre>();
	
	
	public Album(int id, String name, Date releaseDate, List<Artist> artists)
	{
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.setArtists(artists);
	}
	
	public Album() {
		// TODO Auto-generated constructor stub
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

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}
